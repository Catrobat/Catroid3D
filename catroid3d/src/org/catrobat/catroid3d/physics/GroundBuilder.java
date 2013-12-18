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
package org.catrobat.catroid3d.physics;

import org.catrobat.catroid3d.ProjectManager;
import org.catrobat.catroid3d.common.Constants.TEXTURE;
import org.catrobat.catroid3d.content.BoxAssetObject;
import org.catrobat.catroid3d.content.Object;
import org.catrobat.catroid3d.content.Object.OBJECT_TYPE;
import org.catrobat.catroid3d.utils.Math;

import com.badlogic.gdx.math.Vector3;

public class GroundBuilder {

	private Object cursor;
	private float screenCursorX, screenCursorY;
	private Entity cursorEntity;
	private Object groundObject;
	private World world;
	private CollisionDetector collisionDetector;

	public GroundBuilder(CollisionDetector collisionDetector) {
		world = ProjectManager.getInstance().getWorldActivity().getWorldListener().getWorld();
		groundObject = new BoxAssetObject("Ground", 0f, Math.createPositionMatrix(0f, -1f, 0f), 500f, 1f, 500f,
				TEXTURE.TEXTURE_GRASS_01);
		groundObject.setObjectType(OBJECT_TYPE.GROUND);
		cursor = new BoxAssetObject("Cursor", 0f, Math.createPositionMatrix(0f, 1f, 0f), 500f, 1f, 500f, null);
		cursor.setObjectType(OBJECT_TYPE.NORMAL);
		cursor.setHasRigidBody(false);
		cursorEntity = cursor.createEntityObject();
		cursorEntity.setIsRenderable(false);
		world.addEntity(cursorEntity);
		this.collisionDetector = collisionDetector;
	}

	public void createGround() {
		Vector3 position = new Vector3();
		cursorEntity.modelInstance.transform.getTranslation(position);
		groundObject.setTransformation(Math.createPositionMatrix(position.x, -1f, position.z));
		world.addEntity(groundObject.createEntityObject());
	}

	public void removeGround() {
		Vector3 position = new Vector3();
		cursorEntity.modelInstance.transform.getTranslation(position);
		Entity entity = collisionDetector.hasHitGroundObjectFromScreenCoords(screenCursorX, screenCursorY);
		if (entity != null) {
			world.removeEntity(entity);
			entity.dispose();
		}
	}

	public void activateCursor(boolean activate) {
		cursorEntity.setIsRenderable(activate);
	}

	public void setCursorPosition(float screenX, float screenY) {
		screenCursorX = screenX;
		screenCursorY = screenY;
		Vector3 posVector = collisionDetector.screenCoordsToWorldGroundCoords(screenX, screenY);
		cursorEntity.setWorldTransform(Math.createPositionMatrix(posVector));
	}
}
