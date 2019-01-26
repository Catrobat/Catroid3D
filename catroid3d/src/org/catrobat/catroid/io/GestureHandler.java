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
package org.catrobat.catroid.io;

import org.catrobat.catroid.CameraManager;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.WorldListener;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.io.GestureDetector.GestureListener;
import org.catrobat.catroid.physics.CollisionDetector;
import org.catrobat.catroid.physics.Entity;
import org.catrobat.catroid.physics.GroundBuilder;
import org.catrobat.catroid.physics.World;
import org.catrobat.catroid.ui.element.MoveCameraButton;
import org.catrobat.catroid.ui.worldeditscreen.ProjectBuildScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.Collision;

public class GestureHandler implements GestureListener {

    private final Vector3 tmpV1 = new Vector3();
    private final Vector3 tmpV2 = new Vector3();
    private float startX;
    private float startY;
    private Entity entityToMove = null;
    private World world;
    private ProjectBuildScreen screen;
    private CollisionDetector collisionDetector;
    private GroundBuilder groundBuilder;
    private boolean isZooming;

    public GestureHandler(ProjectBuildScreen screen) {
        WorldListener worldListener = ProjectManager.getInstance().getWorldListener();
        world = worldListener.getWorld();
        this.screen = screen;
        collisionDetector = new CollisionDetector(CameraManager.getInstance().getCollisionCamera(), world);
        groundBuilder = new GroundBuilder(collisionDetector);
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        boolean result = false;
        isZooming = false;
        startX = x;
        startY = y;
        result = screen.getStage().touchDown((int) x, (int) y, pointer, button);
        if (result) {
            screen.getGestureDetector().setPinching(false);
            return result;
        }
        Entity entity = collisionDetector.hastHitNonGroundAndNonSkyObjectFromScreenCoords(x, y);
        if (entity != null && entity.body != null) {
            setEntityToMove(entity);
            result = true;
        }
        return result;

    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        boolean result = false;
        result = screen.getStage().touchUp((int) x, (int) y, 0, button);
        if (result) {
            screen.getGestureDetector().setPinching(true);
            return result;
        }
        resetEntityToMove();

        groundBuilder.setCursorPosition(x, y);
        if (screen.getButton(Constants.UI_REMOVE_GROUND_BUTTON).isPressed()) {
            Entity entity = collisionDetector.hasHitStaticObjectFromScreenCoords(x, y);
            if (entity != null) {
                world.removeEntity(entity);
                entity.dispose();
            }
        }
        return result;
    }

    @Override
    public boolean longPress(float x, float y) {
        boolean result = false;
        Entity entity = collisionDetector.hastHitNonGroundAndNonSkyObjectFromScreenCoords(x, y);
        if (entity != null && entity.body != null) {
            screen.getObjectHandler().showObjectDialogBox(entity, x, Gdx.graphics.getHeight() - y);
            result = true;
        }
        return result;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        boolean result = false;
        resetEntityToMove();
        return result;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        boolean result = false;
        if (entityToMove != null && screen.getButton(Constants.UI_MOVE_OBJECT_BUTTON).isPressed()) {
            Vector3 worldGroundCoords = collisionDetector.screenCoordsToWorldGroundCoords(x, y);
            if (worldGroundCoords != null) {
                entityToMove.setWorldTransform(new Matrix4().setToTranslation(worldGroundCoords));
                result = true;
            }
        } else if (screen.getButton(Constants.UI_GROUND_BUTTON).isPressed()) {
            groundBuilder.setCursorPosition(x, y);
        } else if (screen.getButton(Constants.UI_MOVE_CAMERA_BUTTON).isPressed()) {
            CameraManager.getInstance().translateXY(tmpV1,tmpV2, deltaX,deltaY);
        } else {
            if (!isZooming) {
                float rotateDeltaX = (x - startX) / Gdx.graphics.getWidth();
                float rotateDeltaY = (startY - y) / Gdx.graphics.getHeight();
                startX = x;
                startY = y;
                CameraManager.getInstance().updateRotationXY(rotateDeltaX, rotateDeltaY);
            }
        }
        return result;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        isZooming = true;
        return CameraManager.getInstance().zoom(initialDistance,distance);
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        // TODO Auto-generated method stub
        return false;
    }

    public void setEntityToMove(Entity entity) {
        entityToMove = entity;
        entity.body.setActivationState(Collision.DISABLE_DEACTIVATION);
        entity.body.setLinearFactor(new Vector3(1, 0, 1));
        entity.body.setAngularFactor(new Vector3(0, 0, 0));
    }

    private void resetEntityToMove() {
        if (entityToMove != null && entityToMove.body != null) {
            entityToMove.body.setLinearFactor(new Vector3(1, 1, 1));
            entityToMove.body.setAngularFactor(new Vector3(1, 1, 1));
            entityToMove.body.forceActivationState(Collision.ACTIVE_TAG);
            entityToMove.body.setDeactivationTime(0f);
        }
        entityToMove = null;
    }

    public GroundBuilder getGroundBuilder() {
        return groundBuilder;
    }
}
