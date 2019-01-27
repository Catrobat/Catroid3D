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

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class Math {

    public static Matrix4 createPositionMatrix(float x, float y, float z) {
        return new Matrix4().setToTranslation(x, y, z);
    }

    public static Matrix4 createPositionMatrix(Vector3 positon) {
        return new Matrix4().setToTranslation(positon);
    }

    public static Matrix4 createCenterPositionMatrix() {
        return createPositionMatrix(0f, 0f, 0f);
    }

    public static Matrix4 createRandomPosition(float minimum, float maximum) {
        float x = minimum + (float) (java.lang.Math.random() * ((maximum - minimum) + 1));
        float z = minimum + (float) (java.lang.Math.random() * ((maximum - minimum) + 1));
        return createPositionMatrix(x, 0f, z);
    }
}
