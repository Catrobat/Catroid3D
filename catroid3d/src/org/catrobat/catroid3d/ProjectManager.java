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
package org.catrobat.catroid3d;

import org.catrobat.catroid3d.common.Constants;
import org.catrobat.catroid3d.common.Constants.MODEL;
import org.catrobat.catroid3d.common.Constants.TEXTURE;
import org.catrobat.catroid3d.content.BoxAssetObject;
import org.catrobat.catroid3d.content.ComplexAssetObject;
import org.catrobat.catroid3d.content.Object;
import org.catrobat.catroid3d.content.Object.OBJECT_TYPE;
import org.catrobat.catroid3d.content.Object.TEXTURE_TYPE;
import org.catrobat.catroid3d.content.Project;
import org.catrobat.catroid3d.content.SphereAssetObject;
import org.catrobat.catroid3d.io.StorageHandler;
import org.catrobat.catroid3d.utils.Math;
import org.catrobat.catroid3d.utils.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject.CollisionFlags;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ProjectManager {

	private static final ProjectManager INSTANCE = new ProjectManager();
	private Project currentProject;
	private WorldActivity worldActivity;
	private Skin skin;

	private ProjectManager() {

	}

	public static ProjectManager getInstance() {
		return INSTANCE;
	}

	public void loadProject(String projectName) {
		if (projectName == null) {
			loadDefaultProject();
		}
	}

	private void loadDefaultProject() {
		Project project = new Project("DefaultProject");
		Object groundObject = new BoxAssetObject("Ground", 0f, Math.createPositionMatrix(0f, -1f, 0f), 1000f, 1f,
				1000f, TEXTURE.TEXTURE_GRASS_01);
		groundObject.setObjectType(OBJECT_TYPE.GROUND);

		Object barrel01 = new ComplexAssetObject("Barrel01", 0f, Math.createPositionMatrix(0f, 0f, -200f),
				Util.getModelDescriptor(MODEL.MODEL_BIG_WOOD_BARREL));
		barrel01.setCollisionFlags(CollisionFlags.CF_NO_CONTACT_RESPONSE);
		barrel01.setTextureType(TEXTURE_TYPE.NONE);

		//		Object barrel02 = new ComplexAssetObject("Barrel02", 10f, Math.createPositionMatrix(30f, 0f, 200f),
		//				Util.getModelDescriptor(MODEL.MODEL_BIG_WOOD_BARREL));
		//		barrel02.setTextureType(TEXTURE_TYPE.NONE);

		Object tree01 = new ComplexAssetObject("Tree01", 0f, Math.createPositionMatrix(-200f, 0f, 300f),
				Util.getModelDescriptor(MODEL.MODEL_PALM_TREE_01));

		Object skyObject = new SphereAssetObject("Sky", 0f, Math.createPositionMatrix(0f, 0f, 0f), 10000f, 10000f,
				10000f, TEXTURE.TEXTURE_SKY_01, 20, 20);
		skyObject.setHasRigidBody(false);
		skyObject.setObjectType(OBJECT_TYPE.SKYLINE);

		project.addObject(groundObject);
		project.addObject(barrel01);
		//		project.addObject(barrel02);
		project.addObject(tree01);
		project.addObject(skyObject);

		currentProject = project;
	}

	public Project getCurrentProject() {
		return currentProject;
	}

	public void initializeSkin() {
		TextureAtlas packedTextures = StorageHandler.getInstance().getAsset(
				Util.getAssetPath(Constants.PACKED_TEXTURE_ATLAS_FILE));
		skin = new Skin(Gdx.files.internal(Constants.SKIN_FILE), packedTextures);
	}

	public Skin getSkin() {
		return skin;
	}

	public void setWorldActivity(WorldActivity worldActivity) {
		this.worldActivity = worldActivity;
	}

	public WorldActivity getWorldActivity() {
		return worldActivity;
	}

}