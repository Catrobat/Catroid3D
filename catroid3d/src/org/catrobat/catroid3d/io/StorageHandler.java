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

import org.catrobat.catroid3d.common.Constants;
import org.catrobat.catroid3d.common.ModelDescriptor;
import org.catrobat.catroid3d.utils.Util;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

public class StorageHandler {

	private static final StorageHandler INSTANCE = new StorageHandler();
	private AssetManager assetManager;

	private StorageHandler() {
	}

	public static StorageHandler getInstance() {
		return INSTANCE;
	}

	public void createAssetManager() {
		assetManager = new AssetManager();
	}

	public void loadAllAssets() {
		loadModelAssets();
		loadTextureAssets();
		loadUiAssets();
	}

	private void loadModelAssets() {
		Array<ModelDescriptor> modelArray = Constants.MODEL_DESCRIPTOR_ARRAY;
		for (int index = 0; index < modelArray.size; index++) {
			ModelDescriptor modelDescriptor = modelArray.get(index);
			assetManager.load(modelDescriptor.getModelPath(), Model.class);
			assetManager.load(modelDescriptor.getTexturePath(), Texture.class);
		}
		Array<ModelDescriptor> animatedModelArray = Constants.ANIMATED_MODEL_DESCRIPTOR_ARRAY;
		for (int index = 0; index < animatedModelArray.size; index++) {
			ModelDescriptor modelDescriptor = animatedModelArray.get(index);
			assetManager.load(modelDescriptor.getModelPath(), Model.class);
			String name = modelDescriptor.getTexturePath();
			assetManager.load(modelDescriptor.getTexturePath("tga"), Texture.class);
		}
	}

	private void loadTextureAssets() {
		Array<String> textureArray = Constants.TEXTURE_ARRAY;
		for (int index = 0; index < textureArray.size; index++) {
			String texturePath = textureArray.get(index);
			assetManager.load(texturePath, Texture.class);
		}
	}

	private void loadUiAssets() {
		assetManager.load(Util.getAssetPath(Constants.PACKED_TEXTURE_ATLAS_FILE), TextureAtlas.class);
	}

	public <T> T getAsset(String fileName) {
		return assetManager.get(fileName);
	}

	public <T> T getAsset(AssetDescriptor<T> assetDescriptor) {
		return assetManager.get(assetDescriptor.fileName, assetDescriptor.type);
	}

	public TextureRegionDrawable getAssetAsTextureDrawable(AssetDescriptor<Texture> assetDescriptor) {
		return new TextureRegionDrawable(new TextureRegion(getAsset(assetDescriptor)));
	}

	public boolean updateAssetManager() {
		return assetManager.update();
	}

	public void disposeAssets() {
		assetManager.clear();
		assetManager.dispose();
	}

}
