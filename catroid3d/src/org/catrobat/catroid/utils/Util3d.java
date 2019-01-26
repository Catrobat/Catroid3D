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
package org.catrobat.catroid.utils;

import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.ModelDescriptor;

import com.badlogic.gdx.Gdx;

public class Util3d {

    public static ModelDescriptor getModelDescriptor(Constants.MODEL model) {
        return Constants.MODEL_DESCRIPTOR_ARRAY.get(model.ordinal());
    }

    public static String getTexturePathFromModelPath(String modelPath) {
        return modelPath.substring(0, modelPath.lastIndexOf(".")) + Constants.DEFAULT_MODEL_TEXTURE_FILE_TYPE;
    }

    public static String getAssetPath() {
        if (Gdx.graphics.getWidth() > 1280f && Gdx.graphics.getHeight() > 768f) {
            return Constants.ASSET_PATH_XLARGE;
        }
        return Constants.ASSET_PATH_LARGE;
    }

    public static String getAssetPath(String name) {
        return getAssetPath() + name;
    }
}
