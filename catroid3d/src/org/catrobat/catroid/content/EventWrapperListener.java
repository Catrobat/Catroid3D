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
package org.catrobat.catroid.content;

import org.catrobat.catroid.content.actions.EventThread;
import org.catrobat.catroid.physics.Entity;
import org.catrobat.catroid.libgdx3dwrapper.actions.Event3d;
import org.catrobat.catroid.physics.EventListener3d;

import java.util.Collection;

public class EventWrapperListener implements EventListener3d {

    private final Entity lookEntity;

    public EventWrapperListener(Entity look) {
        this.lookEntity = look;
    }

    @Override
    public boolean handle(Event3d event) {
        if (event instanceof EventWrapper) {
            handleEvent((EventWrapper) event);
            return true;
        }
        return false;
    }

    private void handleEvent(EventWrapper event) {
        Collection<EventThread> threads = lookEntity.sprite.getIdToEventThreadMap().get(event.eventId);
        for (EventThread threadToBeStarted : threads) {
            if (event.waitMode == EventWrapper.WAIT) {
                event.addSpriteToWaitFor(lookEntity.sprite);
                threadToBeStarted = new EventThread(threadToBeStarted, lookEntity.sprite, event);
                // Copy original thread because it has a different NotifyWaiterAction
            }
            lookEntity.stopThreadWithScript(threadToBeStarted.getScript());
            lookEntity.startThread(threadToBeStarted);
        }
    }
}
