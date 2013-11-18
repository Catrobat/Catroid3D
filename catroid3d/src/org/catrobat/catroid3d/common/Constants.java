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
package org.catrobat.catroid3d.common;

import android.os.Environment;

import com.badlogic.gdx.utils.Array;

public final class Constants {

	public static String PACKED_TEXTURE_ATLAS_FILE = "pack.atlas";
	public static String SKIN_FILE = "skin.json";

	/* UI Elements in skin.json */
	public static String UI_MOVE_CAMERA_BUTTON = "move-camera-button";
	public static String UI_MOVE_OBJECT_BUTTON = "move-object-button";
	public static String UI_GROUND_BUTTON = "ground-button";
	public static String UI_ADD_GROUND_BUTTON = "add-ground-button";
	public static String UI_REMOVE_GROUND_BUTTON = "remove-ground-button";
	public static String UI_ADD_OBJECT_BUTTON = "add-object-button";

	/* Asset paths */
	public static String ASSET_DATA_PATH = "data/";

	public static String ASSET_MODEL_PATH = ASSET_DATA_PATH + "model/";
	public static String ASSET_MODEL_GROUND_PATH = ASSET_MODEL_PATH + "ground/";
	public static String ASSET_MODEL_BUSH_PATH = ASSET_MODEL_GROUND_PATH + "bush/";
	public static String ASSET_MODEL_GRASS_PATH = ASSET_MODEL_GROUND_PATH + "grass/";
	public static String ASSET_MODEL_TREE_PATH = ASSET_MODEL_GROUND_PATH + "tree/";
	public static String ASSET_MODEL_MISCELLANEOUS_PATH = ASSET_MODEL_PATH + "miscellaneous/";

	public static String ASSET_TEXTURE_PATH = ASSET_DATA_PATH + "texture/";
	public static String ASSET_TEXTURE_GROUND_PATH = ASSET_TEXTURE_PATH + "ground/";
	public static String ASSET_TEXTURE_GRASS_PATH = ASSET_TEXTURE_GROUND_PATH + "grass/";
	public static String ASSET_TEXTURE_SKY_PATH = ASSET_TEXTURE_PATH + "sky/";

	public static String DEFAULT_MODEL_TEXTURE_FILE_TYPE = ".tga";

	public static final String DEFAULT_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath()
			+ "/Pocket Code 3D";

	/* ****** */
	/* Assets */
	/* ****** */

	// Model Enum

	public static enum MODEL {
		MODEL_TROPICAL_PLANT_01, //
		MODEL_TROPICAL_PLANT_02, //
		MODEL_GRASS_01, //
		MODEL_GRASS_02, //
		MODEL_PALM_TREE_01, //
		MODEL_BIG_WOOD_BARREL //
	}

	// Model Array

	public static final Array<String> MODEL_ARRAY = new Array<String>(new String[] {
			//
			ASSET_MODEL_BUSH_PATH + "tropical_plant_01.g3db", //
			ASSET_MODEL_BUSH_PATH + "tropical_plant_02.g3db", //
			ASSET_MODEL_GRASS_PATH + "grass_01.g3db", //
			ASSET_MODEL_GRASS_PATH + "grass_02.g3db", //
			ASSET_MODEL_TREE_PATH + "palm_tree_01.g3db", //
			ASSET_MODEL_MISCELLANEOUS_PATH + "big_wood_barrel.g3db" //
	});

	// Texture Enum

	public static enum TEXTURE {
		TEXTURE_GRASS_01, //
		TEXTURE_SKY_01 //
	}

	// Texture Array

	public static final Array<String> TEXTURE_ARRAY = new Array<String>(new String[] {
			ASSET_TEXTURE_GRASS_PATH + "grass_01.png", //
			ASSET_TEXTURE_SKY_PATH + "sky_01.jpg", //
	});

}
