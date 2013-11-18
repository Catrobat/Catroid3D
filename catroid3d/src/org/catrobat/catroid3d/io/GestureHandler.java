/**
 *  Catroid: An on-device visual programming system for Android devices
 *  Copyright (C) 2010-2013 The Catrobat Team
 *  (<http://developer.catrobat.org/credits>)
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *  
 *  An additional term exception under section 7 of the GNU Affero
 *  General Public License, version 3, is available at
 *  http://developer.catrobat.org/license_additional_term
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 *  
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.catroid3d.io;

import org.catrobat.catroid3d.ProjectManager;
import org.catrobat.catroid3d.WorldListener;
import org.catrobat.catroid3d.io.GestureDetector.GestureListener;
import org.catrobat.catroid3d.physics.CollisionDetector;
import org.catrobat.catroid3d.physics.Entity;
import org.catrobat.catroid3d.physics.GroundBuilder;
import org.catrobat.catroid3d.physics.World;
import org.catrobat.catroid3d.ui.element.GroundButton;
import org.catrobat.catroid3d.ui.element.MoveCameraButton;
import org.catrobat.catroid3d.ui.element.MoveObjectButton;
import org.catrobat.catroid3d.ui.element.RemoveGroundButton;
import org.catrobat.catroid3d.ui.screen.ProjectBuildScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.Collision;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GestureHandler implements GestureListener {

	private float zoomUnits = 7f;
	private float translateUnits = 1f;
	private float rotateAngle = 360f;
	private Vector3 target = new Vector3();
	private float startX;
	private float startY;

	private Entity entityToMove = null;
	private PerspectiveCamera collisionCamera;
	private PerspectiveCamera movingCamera;
	private World world;
	private Stage guiStage;
	private ProjectBuildScreen screen;
	private final Vector3 tmpV1 = new Vector3();
	private final Vector3 tmpV2 = new Vector3();
	private CollisionDetector collisionDetector;
	private GroundBuilder groundBuilder;

	public GestureHandler(ProjectBuildScreen screen) {
		WorldListener worldListener = ProjectManager.getInstance().getWorldActivity().getWorldListener();
		movingCamera = worldListener.movingCamera;
		collisionCamera = worldListener.collisionCamera;
		world = worldListener.getWorld();
		this.guiStage = screen.getStage();
		this.screen = screen;
		collisionDetector = new CollisionDetector(collisionCamera, world);
		groundBuilder = new GroundBuilder(collisionDetector);
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		boolean result = false;
		startX = x;
		startY = y;
		result = guiStage.touchDown((int) x, (int) y, pointer, button);
		if (result) {
			screen.getGestureDetector().setPinching(false);
			return result;
		}
		Entity entity = collisionDetector.hasHitDynamicObjectFromScreenCoords(x, y);
		if (entity != null && entity.body != null) {
			entityToMove = entity;
			entity.body.setActivationState(Collision.DISABLE_DEACTIVATION);
			entity.body.setLinearFactor(new Vector3(1, 0, 1));
			entity.body.setAngularFactor(new Vector3(0, 0, 0));
			result = true;
		}
		return result;

	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		boolean result = false;
		result = guiStage.touchUp((int) x, (int) y, 0, button);
		if (result) {
			screen.getGestureDetector().setPinching(true);
			return result;
		}
		resetEntityToMove();

		groundBuilder.setCursorPosition(x, y);
		if (screen.getButton(RemoveGroundButton.class).isPressed()) {
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
		return guiStage.touchUp((int) x, (int) y, 0, 0);
		//		return false;
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
		if (entityToMove != null && screen.getButton(MoveObjectButton.class).isPressed()) {
			Vector3 worldGroundCoords = collisionDetector.screenCoordsToWorldGroundCoords(x, y);
			if (worldGroundCoords != null) {
				//				entityToMove.body.setWorldTransform(new Matrix4().setToTranslation(worldGroundCoords));
				entityToMove.setWorldTransform(new Matrix4().setToTranslation(worldGroundCoords));
				result = true;
			}
		} else if (screen.getButton(GroundButton.class).isPressed()) {
			groundBuilder.setCursorPosition(x, y);
		} else if (((MoveCameraButton) screen.getButton(MoveCameraButton.class)).isPressed()) {
			movingCamera.translate(tmpV1.set(movingCamera.direction).crs(movingCamera.up).nor()
					.scl(-deltaX * translateUnits));
			movingCamera.translate(tmpV2.set(movingCamera.up).scl(-deltaY * translateUnits));
			target.add(tmpV1).add(tmpV2);
		} else {
			float rotateDeltaX = (x - startX) / Gdx.graphics.getWidth();
			float rotateDeltaY = (startY - y) / Gdx.graphics.getHeight();
			startX = x;
			startY = y;
			tmpV1.set(movingCamera.direction).crs(movingCamera.up).y = 0f;
			movingCamera.rotateAround(target, tmpV1.nor(), rotateDeltaY * rotateAngle);
			movingCamera.rotateAround(target, Vector3.Y, rotateDeltaX * -rotateAngle);
			movingCamera.update();
		}
		return result;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		boolean isZoomIn = initialDistance < distance ? true : false;
		if (isZoomIn) {
			movingCamera.translate(tmpV1.set(movingCamera.direction).scl(zoomUnits));
		} else {
			movingCamera.translate(tmpV1.set(movingCamera.direction).scl(-zoomUnits));
		}
		return true;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		return false;
	}

	private void resetEntityToMove() {
		if (entityToMove != null) {
			entityToMove.body.setLinearFactor(new Vector3(1, 1, 1));
			entityToMove.body.setAngularFactor(new Vector3(1, 1, 1));
			entityToMove.body.forceActivationState(Collision.ACTIVE_TAG);
			entityToMove.body.setDeactivationTime(0f);
			entityToMove = null;
		}
	}

	public GroundBuilder getGroundBuilder() {
		return groundBuilder;
	}
}
