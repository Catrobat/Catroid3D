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

import org.catrobat.catroid.common.MathConstants;
import org.catrobat.catroid.content.models.Abstract3dObject;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.physics.bullet.collision.ClosestRayResultCallback;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;

public class CollisionDetector {

    private Camera collisionCamera;
    private World world;

    public CollisionDetector(Camera collisionCamera, World world) {
        this.collisionCamera = collisionCamera;
        this.world = world;
    }

    public Vector3 screenCoordsToWorldGroundCoords(float screenX, float screenY) {
        Plane groundPlane = new Plane(MathConstants.GROUND_PLANE_POINT1, MathConstants.GROUND_PLANE_POINT2,
                MathConstants.GROUND_PLANE_POINT3);
        Vector3 groundPlanePoint = new Vector3().set(MathConstants.GROUND_PLANE_POINT1);
        Vector3 worldCoords = null;
        Ray ray = collisionCamera.getPickRay(screenX, screenY);
        float t = ((groundPlanePoint.sub(ray.origin)).dot(groundPlane.normal))
                / (ray.direction.dot(groundPlane.normal));
        if (t > 0f) {
            worldCoords = new Vector3().set(ray.origin.add(ray.direction.scl(t)));
        }
        return worldCoords;
    }

    public Vector2 worldGroundCoordsToScreenCoords(Vector3 worldCoords) {
        Vector3 worldCoordsCpy = worldCoords.cpy();
        collisionCamera.project(worldCoordsCpy);
        return new Vector2(worldCoordsCpy.cpy().x, worldCoordsCpy.cpy().y);
    }

    public Entity hasHitObjectFromScreenCoords(float screenX, float screenY) {
        Entity entity = null;
        Vector3 rayVector = new Vector3();
        Ray ray = collisionCamera.getPickRay(screenX, screenY);
        rayVector.set(ray.direction).scl(MathConstants.RAY_SCALE).add(ray.origin);
        ClosestRayResultCallback rayResultCallback = new ClosestRayResultCallback(ray.origin, rayVector);
        world.collisionWorld.rayTest(ray.origin, rayVector, rayResultCallback);
        if (rayResultCallback.hasHit()) {
            entity = (Entity) ((btRigidBody) rayResultCallback.getCollisionObject()).userData;
        }
        rayResultCallback.dispose();
        return entity;
    }

    public Entity hastHitNonGroundAndNonSkyObjectFromScreenCoords(float screenX, float screenY) {
        Entity entity = hasHitObjectFromScreenCoords(screenX, screenY);
        if (entity != null && entity.body != null && ((Abstract3dObject.ObjectType) entity.modelInstance.userData).getObjectType() != Abstract3dObject.OBJECT_TYPE.GROUND.ordinal()
                && ((Abstract3dObject.ObjectType) entity.modelInstance.userData).getObjectType() != Abstract3dObject.OBJECT_TYPE.SKYLINE.ordinal()) {
            return entity;
        }
        return null;
    }

    public Entity hasHitDynamicObjectFromScreenCoords(float screenX, float screenY) {
        Entity entity = hastHitNonGroundAndNonSkyObjectFromScreenCoords(screenX, screenY);
        if (entity != null && entity.body != null && !entity.body.isStaticObject() && !entity.body.isKinematicObject()) {
            return entity;
        }
        return null;
    }

    public Entity hasHitStaticObjectFromScreenCoords(float screenX, float screenY) {
        Entity entity = hastHitNonGroundAndNonSkyObjectFromScreenCoords(screenX, screenY);
        if (entity != null && entity.body != null && entity.body.isStaticObject()) {
            return entity;
        }
        return null;
    }

    public Entity hasHitGroundObjectFromScreenCoords(float screenX, float screenY) {
        Entity entity = hasHitObjectFromScreenCoords(screenX, screenY);
        if (entity != null && entity.body != null && entity.body.isStaticObject()) {
            return entity;
        }
        return null;
    }
}
