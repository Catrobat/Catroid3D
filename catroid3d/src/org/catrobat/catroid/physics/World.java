/**
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2013 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * <p>
 * An additional term exception under section 7 of the GNU Affero
 * General Public License, version 3, is available at
 * http://developer.catrobat.org/license_additional_term
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.catroid.physics;

import android.os.SystemClock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface;
import com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionWorld;
import com.badlogic.gdx.physics.bullet.collision.btDbvtBroadphase;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.dynamics.btConstraintSolver;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class World implements Disposable {

    private static final float DELTA_ACTIONS_DIVIDER_MAXIMUM = 50f;
    private static final int ACTIONS_COMPUTATION_TIME_MAXIMUM = 8;
    public final btCollisionConfiguration collisionConfiguration;
    public final btCollisionDispatcher dispatcher;
    public final btBroadphaseInterface broadphase;
    public final btConstraintSolver constraintSolver;
    public final btCollisionWorld collisionWorld;
    public final Vector3 gravity;
    private final Array<Entity> entities = new Array<Entity>();
    private float deltaActionTimeDivisor = 10f;
    private int maxSubSteps = 5;

    public World() {
        this(new Vector3(0, -10, 0));
    }

    public World(final Vector3 gravity) {
        collisionConfiguration = new btDefaultCollisionConfiguration();
        dispatcher = new btCollisionDispatcher(collisionConfiguration);
        broadphase = new btDbvtBroadphase();
        constraintSolver = new btSequentialImpulseConstraintSolver();
        collisionWorld = new btDiscreteDynamicsWorld(dispatcher, broadphase, constraintSolver, collisionConfiguration);
        ((btDynamicsWorld) collisionWorld).setGravity(gravity);
        this.gravity = gravity;
    }

    public void addEntity(final Entity entity) {
        entities.add(entity);
        if (entity.body != null) {
            if (entity.body instanceof btRigidBody) {
                ((btDiscreteDynamicsWorld) collisionWorld).addRigidBody(entity.body);
            } else {
                collisionWorld.addCollisionObject(entity.body);
            }
            entity.body.setUserValue(entities.size - 1);
        }
    }

    public void addEntity(final Entity entity, short collisionGroup, short collisionMask) {
        entities.add(entity);
        if (entity.body != null) {
            if (entity.body instanceof btRigidBody) {
                ((btDiscreteDynamicsWorld) collisionWorld).addRigidBody(entity.body, collisionGroup, collisionMask);
            } else {
                collisionWorld.addCollisionObject(entity.body);
            }
            entity.body.setUserValue(entities.size - 1);
        }
    }

    public void removeEntity(final Entity entity) {
        if (entity.body != null) {
            ((btDynamicsWorld) collisionWorld).removeRigidBody(entity.body);
        }
        entities.removeValue(entity, false);
    }

    public Entity getEntity(String name) {
        for (int count = 0; count < entities.size; count++) {
            Entity entity = entities.get(count);
            if (entity.getName().contains(name)) {
                return entity;
            }
        }
        return null;
    }

    public void update(boolean paused) {
        if (!(collisionWorld instanceof btDynamicsWorld)) {
            return;
        }

        float deltaTime = Gdx.graphics.getDeltaTime();
        float optimizedDeltaTime = deltaTime / deltaActionTimeDivisor;

        long timeBeforeActionsUpdate = SystemClock.uptimeMillis();
        while (deltaTime > 0f) {
            ((btDynamicsWorld) collisionWorld).stepSimulation(optimizedDeltaTime, maxSubSteps);
            //dont act entities if paused
            if (paused) {
                return;
            }
            for (Entity entity : entities) {
                entity.act(optimizedDeltaTime);
            }
            deltaTime -= optimizedDeltaTime;
        }

        long executionTimeOfActionsUpdate = SystemClock.uptimeMillis() - timeBeforeActionsUpdate;
        if (executionTimeOfActionsUpdate <= ACTIONS_COMPUTATION_TIME_MAXIMUM) {
            deltaActionTimeDivisor += 1f;
            deltaActionTimeDivisor = Math.min(DELTA_ACTIONS_DIVIDER_MAXIMUM, deltaActionTimeDivisor);
        } else {
            deltaActionTimeDivisor -= 1f;
            deltaActionTimeDivisor = Math.max(1f, deltaActionTimeDivisor);
        }
    }

    public void render(final ModelBatch modelBatch, final Environment environment) {
        for (final Entity entity : entities) {
            if (entity.isRenderable()) {
                entity.modelInstance.calculateTransforms();
                modelBatch.render(entity.modelInstance, environment);
            }
        }
    }

    @Override
    public void dispose() {
        for (int i = 0; i < entities.size; i++) {
            btCollisionObject body = entities.get(i).body;
            if (body != null) {
                if (body instanceof btRigidBody) {
                    ((btDynamicsWorld) collisionWorld).removeRigidBody((btRigidBody) body);
                } else {
                    collisionWorld.removeCollisionObject(body);
                }
            }
            entities.get(i).dispose();
        }
        entities.clear();
        collisionWorld.dispose();
        if (constraintSolver != null) {
            constraintSolver.dispose();
        }
        if (broadphase != null) {
            broadphase.dispose();
        }
        if (dispatcher != null) {
            dispatcher.dispose();
        }
        if (collisionConfiguration != null) {
            collisionConfiguration.dispose();
        }
    }

}
