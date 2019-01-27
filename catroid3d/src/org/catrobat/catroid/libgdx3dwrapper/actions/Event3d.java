/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
//this class is based on and contains source code from on libgdx scene2d Event.class

package org.catrobat.catroid.libgdx3dwrapper.actions;

import org.catrobat.catroid.libgdx3dwrapper.scene.Actor3d;

public class Event3d {
    private Actor3d targetActor;
    private Actor3d listenerActor;
    private boolean capture; // true means event occurred during the capture phase
    private boolean bubbles = true; // true means propagate to target's parents
    private boolean handled; // true means the event was handled (the stage will eat the input)
    private boolean stopped; // true means event propagation was stopped
    private boolean cancelled; // true means propagation was stopped and any action that this event would cause should not happen

    /**
     * Marks this event as handled. This does not affect event propagation inside scene2d, but causes the {Stage} event
     * methods to return true, which will eat the event so it is not passed on to the application under the stage.
     */
    public void handle() {
        handled = true;
    }

    /**
     * Marks this event cancelled. This {@link #handle() handles} the event and {@link #stop() stops} the event propagation. It
     * also cancels any default action that would have been taken by the code that fired the event. Eg, if the event is for a
     * checkbox being checked, cancelling the event could uncheck the checkbox.
     */
    public void cancel() {
        cancelled = true;
        stopped = true;
        handled = true;
    }

    /**
     * Marks this event has being stopped. This halts event propagation. Any other listeners on the {@link #getListenerActor()
     * listener actor} are notified, but after that no other listeners are notified.
     */
    public void stop() {
        stopped = true;
    }

    public void reset() {
        targetActor = null;
        listenerActor = null;
        capture = false;
        bubbles = true;
        handled = false;
        stopped = false;
        cancelled = false;
    }

    /**
     * Returns the actor that the event originated from.
     */
    public Actor3d getTarget() {
        return targetActor;
    }

    public void setTarget(Actor3d targetActor) {
        this.targetActor = targetActor;
    }

    /**
     * Returns the actor that this listener is attached to.
     */
    public Actor3d getListenerActor() {
        return listenerActor;
    }

    public void setListenerActor(Actor3d listenerActor) {
        this.listenerActor = listenerActor;
    }

    public boolean getBubbles () {
        return bubbles;
    }

    /** If true, after the event is fired on the target actor, it will also be fired on each of the parent actors, all the way to
     * the root. */
    public void setBubbles (boolean bubbles) {
        this.bubbles = bubbles;
    }

    /**
     * {@link #handle()}
     */
    public boolean isHandled() {
        return handled;
    }

    /**
     * @see #stop()
     */
    public boolean isStopped() {
        return stopped;
    }

    /**
     * @see #cancel()
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * If true, the event was fired during the capture phase.
     *
     * @see Actor3d#fire(Event3d)
     */
    public boolean isCapture() {
        return capture;
    }

    public void setCapture(boolean capture) {
        this.capture = capture;
    }
}
