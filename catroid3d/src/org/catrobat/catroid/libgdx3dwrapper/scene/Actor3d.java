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
//this class is based on and contains source code from on libgdx scene2d Actor.class

package org.catrobat.catroid.libgdx3dwrapper.scene;

import android.support.annotation.IntDef;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.linearmath.btMotionState;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.Disposable;

import org.catrobat.catroid.libgdx3dwrapper.actions.Action3d;
import org.catrobat.catroid.libgdx3dwrapper.actions.Event3d;
import org.catrobat.catroid.physics.Entity;
import org.catrobat.catroid.physics.EventListener3d;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.badlogic.gdx.math.Matrix4.M03;
import static com.badlogic.gdx.math.Matrix4.M13;
import static com.badlogic.gdx.math.Matrix4.M23;

public class Actor3d {

    public ModelInstance modelInstance;
    public btRigidBody body;


    protected Entity.MotionState motionState;
    protected Matrix4 transform;
    protected final Array<Action3d> actions = new Array(0);

    private boolean visible;
    private final Vector3 motionImpulse = new Vector3();
    private final Vector3 motionAngularImpulse = new Vector3();
    private final Vector3 motionPositionBuffer = new Vector3();
    private final Quaternion rotationQuaternion = new Quaternion();
    private final DelayedRemovalArray<EventListener3d> listeners = new DelayedRemovalArray(0);
    private final DelayedRemovalArray<EventListener3d> captureListeners = new DelayedRemovalArray(0);
    //private Group parent;
    private float rotation = 0f;

    public Actor3d() {
    }

    public Actor3d(Entity.MotionState motionState, btRigidBody rigidBody) {
        this.body = rigidBody;
        this.motionState = motionState;
        rotation = getDirectionInUserInterfaceDimensionUnit();
    }

    public boolean fire(Event3d event) {
        //if (event.getStage() == null) event.setStage(getStage());
        event.setTarget(this);

//        // Collect ancestors so event propagation is unaffected by hierarchy changes.
//        Array<Group> ancestors = Pools.obtain(Array.class);
//        Group parent = this.parent;
//        while (parent != null) {
//            ancestors.add(parent);
//            parent = parent.parent;
//        }

        try {
            // Notify all parent capture listeners, starting at the root. Ancestors may stop an event before children receive it.
//            Object[] ancestorsArray = ancestors.items;
//            for (int i = ancestors.size - 1; i >= 0; i--) {
//                Group currentTarget = (Group)ancestorsArray[i];
//                currentTarget.notify(event, true);
//                if (event.isStopped()) return event.isCancelled();
//            }

            // Notify the target capture listeners.
            notify(event, true);
            if (event.isStopped()) return event.isCancelled();

            // Notify the target listeners.
            notify(event, false);
            if (!event.getBubbles()) return event.isCancelled();
            if (event.isStopped()) return event.isCancelled();

            // Notify all parent listeners, starting at the target. Children may stop an event before ancestors receive it.
//            for (int i = 0, n = ancestors.size; i < n; i++) {
//                ((Group)ancestorsArray[i]).notify(event, false);
//                if (event.isStopped()) return event.isCancelled();
//            }

            return event.isCancelled();
        } finally {
//            ancestors.clear();
//            Pools.free(ancestors);
        }
    }

    public boolean notify(Event3d event, boolean capture) {
        if (event.getTarget() == null)
            throw new IllegalArgumentException("The event target cannot be null.");

        DelayedRemovalArray<EventListener3d> listeners = capture ? captureListeners : this.listeners;
        if (listeners.size == 0) return event.isCancelled();

        event.setListenerActor(this);
        event.setCapture(capture);
        //if (event.getStage() == null) event.setStage(stage);

        listeners.begin();
        for (int i = 0, n = listeners.size; i < n; i++) {
            EventListener3d listener = listeners.get(i);
            if (listener.handle(event)) {
                event.handle();
//                if (event instanceof InputEvent) {
//                    InputEvent inputEvent = (InputEvent)event;
//                    if (inputEvent.getType() == Type.touchDown) {
//                        event.getStage().addTouchFocus(listener, this, inputEvent.getTarget(), inputEvent.getPointer(),
//                                inputEvent.getButton());
//                    }
//                }
            }
        }
        listeners.end();

        return event.isCancelled();
    }


    public boolean addListener(EventListener3d listener) {
        if (!listeners.contains(listener, true)) {
            listeners.add(listener);
            return true;
        }
        return false;
    }

    public boolean removeListener(EventListener3d listener) {
        return listeners.removeValue(listener, true);
    }

    public Array<EventListener3d> getListeners() {
        return listeners;
    }

    public boolean addCaptureListener(EventListener3d listener) {
        if (!captureListeners.contains(listener, true)) captureListeners.add(listener);
        return true;
    }

    public boolean removeCaptureListener(EventListener3d listener) {
        return captureListeners.removeValue(listener, true);
    }

    public Array<EventListener3d> getCaptureListeners() {
        return captureListeners;
    }

    public void addAction(Action3d action) {
        action.setActor(this);
        actions.add(action);
        Gdx.graphics.requestRendering();
    }

    public void removeAction(Action3d action) {
        if (actions.removeValue(action, true)) action.setActor(null);
    }

    public Array<Action3d> getActions() {
        return actions;
    }

    /**
     * Removes all actions on this actor.
     */
    public void clearActions() {
        for (int i = actions.size - 1; i >= 0; i--)
            actions.get(i).setActor(null);
        actions.clear();
    }

    /**
     * Removes all listeners on this actor.
     */
    public void clearListeners() {
        listeners.clear();
        captureListeners.clear();
    }

    /**
     * Removes all actions and listeners on this actor.
     */
    public void clear() {
        clearActions();
        clearListeners();
    }

    public boolean isVisible() {
        return visible;
    }

    /**
     * If false, the actor will not be drawn and will not receive touch events. Default is true.
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    //region motions
    private void resetMotionBuffers() {
        motionImpulse.set(0,0,0);
        motionAngularImpulse.set(0,0,0);
        motionPositionBuffer.set(0,0,0);
    }

    public void setXInUserInterfaceDimensionUnit(Float x) {
        resetMotionBuffers();
        motionState.transform.getTranslation(motionPositionBuffer);
        motionPositionBuffer.x = x;
        motionState.transform.setTranslation(motionPositionBuffer);
        body.setWorldTransform(motionState.transform);
        body.activate();
    }


    public void setYInUserInterfaceDimensionUnit(Float y) {
        resetMotionBuffers();
        motionState.transform.getTranslation(motionPositionBuffer);
        motionPositionBuffer.y = y;
        motionState.transform.setTranslation(motionPositionBuffer);
        body.setWorldTransform(motionState.transform);
        body.activate();
    }

    public void setZInUserInterfaceDimensionUnit(Float z) {
        resetMotionBuffers();
        motionState.transform.getTranslation(motionPositionBuffer);
        motionPositionBuffer.z = z;
        motionState.transform.setTranslation(motionPositionBuffer);
        body.setWorldTransform(motionState.transform);
        body.activate();
    }

    public void changeXInUserInterfaceDimensionUnit(Float x) {
        resetMotionBuffers();
        motionState.transform.trn(x,0,0);
        body.setWorldTransform(motionState.transform);
        body.activate();
    }

    public void changeYInUserInterfaceDimensionUnit(Float y) {
        resetMotionBuffers();
        motionState.transform.trn(0,y,0);
        body.setWorldTransform(motionState.transform);
        body.activate();
    }

    public void changeZInUserInterfaceDimensionUnit(Float z) {
        resetMotionBuffers();
        motionState.transform.trn(0,0,z);
        body.setWorldTransform(motionState.transform);
        body.activate();
    }

    public float getXInUserInterfaceDimensionUnit() {
        return motionState.transform.val[M03];
    }

    public float getYInUserInterfaceDimensionUnit() {
        return motionState.transform.val[M13];
    }

    public float getZInUserInterfaceDimensionUnit() {
        return motionState.transform.val[M23];
    }

    public void setPositionInUserInterfaceDimensionUnit(float currentX, float currentY, float currentZ) {
        //todo use velocity??
        motionState.transform.setTranslation(currentX,currentY,currentZ);
        body.setWorldTransform(motionState.transform);
        body.activate();
    }

    public void changeDirectionInUserInterfaceDimensionUnit(Float newDegrees) {
        rotateBy(newDegrees);
    }

    public float getDirectionInUserInterfaceDimensionUnit() {
        return getRotation();
    }

    public void setDirectionInUserInterfaceDimensionUnit(Float degrees) {
        degrees = degrees % 360;
        float rotationAngle = getRotation();
        rotateBy(degrees - rotationAngle);
    }

    public float getRotation () {
        motionState.transform.getRotation(rotationQuaternion);
        return rotationQuaternion.getAngleAround(Vector3.Y);
    }

    public void rotateBy (float amountInDegrees) {
        motionState.transform.rotate(Vector3.Y,amountInDegrees);
        body.setWorldTransform(motionState.transform);
        body.activate();
    }

    //endregion

    public void setWorldTransform(Matrix4 transform) {
        if (body != null) {
            body.setWorldTransform(transform);
        }
        modelInstance.transform.set(transform);
    }

    public static class MotionState extends btMotionState implements Disposable {
        private final Matrix4 transform;

        public MotionState(final Matrix4 transform) {
            this.transform = transform;
        }

        @Override
        public void getWorldTransform(Matrix4 worldTrans) {
            worldTrans.set(transform);
        }

        @Override
        public void setWorldTransform(Matrix4 worldTrans) {
            transform.set(worldTrans);
        }

        @Override
        public void dispose() {
            delete();
        }

    }
}
