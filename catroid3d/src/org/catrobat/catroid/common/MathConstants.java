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
package org.catrobat.catroid.common;

import com.badlogic.gdx.math.Vector3;

public class MathConstants {

    /* These points define a xz plane for ground collsion detection */
    public static final Vector3 GROUND_PLANE_POINT1 = new Vector3(0f, 0f, 0f);
    public static final Vector3 GROUND_PLANE_POINT2 = new Vector3(4f, 0f, 5f);
    public static final Vector3 GROUND_PLANE_POINT3 = new Vector3(7f, 0f, 8f);

    public static final float RAY_SCALE = 100000000f;

}
