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

import com.badlogic.gdx.utils.Logger;

public class Log {

    private static Logger logger = new Logger("Catroid3D", Logger.DEBUG);

    public static void error(String message) {
        logger.error(message);
    }

    public static void error(String message, Throwable exception) {
        logger.error(message, exception);
    }

    public static void info(String message) {
        logger.info(message);
    }

    public static void info(String message, Exception exception) {
        logger.info(message, exception);
    }

    public static void debug(String message) {
        logger.debug(message);
    }
}
