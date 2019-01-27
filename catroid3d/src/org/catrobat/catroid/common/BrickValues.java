/*
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2018 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * An additional term exception under section 7 of the GNU Affero
 * General Public License, version 3, is available at
 * http://developer.catrobat.org/license_additional_term
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.catroid.common;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

//import org.catrobat.catroid.physics.PhysicsObject;
//import org.catrobat.catroid.physics.PhysicsObject.Type;
//import org.catrobat.catroid.physics.PhysicsWorld;

public final class BrickValues {

    //constants Motions
    public static final int X_POSITION = 100;
    public static final int Y_POSITION = 0;
    public static final int Z_POSITION = 100;
    public static final int CHANGE_X_BY = 10;
    public static final int CHANGE_Y_BY = 10;
    public static final int CHANGE_Z_BY = 10;
    public static final double MOVE_STEPS = 10;
    public static final double TURN_DEGREES = 15;
    public static final double POINT_IN_DIRECTION = 90;
    public static final int GLIDE_SECONDS = 1000;
    public static final int GO_BACK = 1;
    public static final int DURATION = 1;

    //constants Physics
    //public static final PhysicsObject.Type PHYSIC_TYPE = Type.DYNAMIC;
    public static final double PHYSIC_MASS = 1.0;
    public static final double PHYSIC_BOUNCE_FACTOR = 0.8;
    public static final double PHYSIC_FRICTION = 0.2;
    //public static final Vector2 PHYSIC_GRAVITY = PhysicsWorld.DEFAULT_GRAVITY;
    public static final Vector2 PHYSIC_VELOCITY = new Vector2();
    public static final double PHYSIC_TURN_DEGREES = TURN_DEGREES;

    //constants Looks
    public static final double SET_SIZE_TO = 60;
    public static final double CHANGE_SIZE_BY = 10;
    public static final double SET_TRANSPARENCY = 50;
    public static final double CHANGE_TRANSPARENCY_EFFECT = 25;
    public static final double SET_BRIGHTNESS_TO = 50;
    public static final double CHANGE_BRITHNESS_BY = 25;
    public static final double SET_COLOR_TO = 0;
    public static final double CHANGE_COLOR_BY = 25;
    public static final double VIBRATE_SECONDS = 1;
    public static final int GO_TO_TOUCH_POSITION = 80;
    public static final int GO_TO_RANDOM_POSITION = 81;
    public static final int GO_TO_OTHER_SPRITE_POSITION = 82;
    public static final int SET_LOOK_BY_INDEX = 1;

    //constants Sounds
    public static final double SET_VOLUME_TO = 60;
    public static final double CHANGE_VOLUME_BY = -10;

    //Constants Control
    public static final int WAIT = 1000;
    public static final int REPEAT = 10;
    public static final String IF_CONDITION = "1 < 2";
    public static final String NOTE = "add comment here…";
    public static final int STOP_THIS_SCRIPT = 0;
    public static final int STOP_ALL_SCRIPTS = 1;
    public static final int STOP_OTHER_SCRIPTS = 2;

    //Constants Variables
    public static final double SET_VARIABLE = 1d;
    public static final double CHANGE_VARIABLE = 1d;

    //Constants Lists
    public static final double ADD_ITEM_TO_USERLIST = 1;
    public static final int DELETE_ITEM_OF_USERLIST = 1;
    public static final int INSERT_ITEM_INTO_USERLIST_INDEX = 1;
    public static final double INSERT_ITEM_INTO_USERLIST_VALUE = 1;
    public static final int REPLACE_ITEM_IN_USERLIST_INDEX = 1;
    public static final double REPLACE_ITEM_IN_USERLIST_VALUE = 1;

    private BrickValues() {
        throw new AssertionError("No.");
    }
}
