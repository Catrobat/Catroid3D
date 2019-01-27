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
//this class is based on and contains source code from on libgdx scene2d Actions.class

package org.catrobat.catroid.libgdx3dwrapper.actions;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import org.catrobat.catroid.libgdx3dwrapper.scene.Actor3d;
import org.catrobat.catroid.physics.EventListener3d;

public class Actions3d {
    /** Returns a new or pooled action of the specified type. */
    static public <T extends Action3d> T action (Class<T> type) {
        Pool<T> pool = Pools.get(type);
        T action = pool.obtain();
        action.setPool(pool);
        return action;
    }

//    static public AddAction3d addAction3d (Action3d action) {
//        AddAction3d addAction3d = action(AddAction3d.class);
//        addAction3d.setAction3d(action);
//        return addAction3d;
//    }
//
//    static public AddAction3d addAction3d (Action3d action, Actor3d targetActor3d) {
//        AddAction3d addAction3d = action(AddAction3d.class);
//        addAction3d.setTarget(targetActor3d);
//        addAction3d.setAction3d(action);
//        return addAction3d;
//    }
//
//    static public RemoveAction3d removeAction3d (Action3d action) {
//        RemoveAction3d removeAction3d = action(RemoveAction3d.class);
//        removeAction3d.setAction3d(action);
//        return removeAction3d;
//    }
//
//    static public RemoveAction3d removeAction3d (Action3d action, Actor3d targetActor3d) {
//        RemoveAction3d removeAction3d = action(RemoveAction3d.class);
//        removeAction3d.setTarget(targetActor3d);
//        removeAction3d.setAction3d(action);
//        return removeAction3d;
//    }
//
//    /** Moves the actor instantly. */
//    static public MoveToAction3d moveTo (float x, float y, float z) {
//        return moveTo(x, y, z, 0, null);
//    }
//
//    static public MoveToAction3d moveTo (float x, float y, float z, float duration) {
//        return moveTo(x, y, z, duration, null);
//    }
//
//    static public MoveToAction3d moveTo (float x, float y, float z, float duration, Interpolation interpolation) {
//        MoveToAction3d action = action(MoveToAction3d.class);
//        action.setPosition(x, y, z);
//        action.setDuration(duration);
//        action.setInterpolation(interpolation);
//        return action;
//    }
//
//    static public MoveToAction3d moveToAligned (float x, float y, float z, int alignment) {
//        return moveToAligned(x, y, z, alignment, 0, null);
//    }
//
//    static public MoveToAction3d moveToAligned (float x, float y, float z, int alignment, float duration) {
//        return moveToAligned(x, y, z, alignment, duration, null);
//    }
//
//    static public MoveToAction3d moveToAligned (float x, float y, float z, int alignment, float duration, Interpolation interpolation) {
//        MoveToAction3d action = action(MoveToAction3d.class);
//        action.setPosition(x, y, z, alignment);
//        action.setDuration(duration);
//        action.setInterpolation(interpolation);
//        return action;
//    }
//
//    /** Moves the actor instantly. */
//    static public MoveByAction3d moveBy (float amountX, float amountY, float amountZ) {
//        return moveBy(amountX, amountY, amountZ, 0, null);
//    }
//
//    static public MoveByAction3d moveBy (float amountX, float amountY, float amountZ, float duration) {
//        return moveBy(amountX, amountY, amountZ, duration, null);
//    }
//
//    static public MoveByAction3d moveBy (float amountX, float amountY, float amountZ, float duration, Interpolation interpolation) {
//        MoveByAction3d action = action(MoveByAction3d.class);
//        action.setAmount(amountX, amountY, amountZ);
//        action.setDuration(duration);
//        action.setInterpolation(interpolation);
//        return action;
//    }

//    /** Sizes the actor instantly. */
//    static public SizeToAction3d sizeTo (float x, float y, float z) {
//        return sizeTo(x, y, z, 0, null);
//    }
//
//    static public SizeToAction3d sizeTo (float x, float y, float z, float duration) {
//        return sizeTo(x, y, z, duration, null);
//    }
//
//    static public SizeToAction3d sizeTo (float x, float y, float z, float duration, Interpolation interpolation) {
//        SizeToAction3d action = action(SizeToAction3d.class);
//        action.setSize(x, y, z);
//        action.setDuration(duration);
//        action.setInterpolation(interpolation);
//        return action;
//    }
//
//    /** Sizes the actor instantly. */
//    static public SizeByAction3d sizeBy (float amountX, float amountY, float amountZ) {
//        return sizeBy(amountX, amountY, amountZ, 0, null);
//    }
//
//    static public SizeByAction3d sizeBy (float amountX, float amountY, float amountZ, float duration) {
//        return sizeBy(amountX, amountY, amountZ, duration, null);
//    }
//
//    static public SizeByAction3d sizeBy (float amountX, float amountY, float amountZ, float duration, Interpolation interpolation) {
//        SizeByAction3d action = action(SizeByAction3d.class);
//        action.setAmount(amountX, amountY, amountZ);
//        action.setDuration(duration);
//        action.setInterpolation(interpolation);
//        return action;
//    }
//
//    /** Scales the actor instantly. */
//    static public ScaleToAction3d scaleTo (float x, float y , float z) {
//        return scaleTo(x, y, z, 0, null);
//    }
//
//    static public ScaleToAction3d scaleTo (float x, float y , float z, float duration) {
//        return scaleTo(x, y, z, duration, null);
//    }
//
//    static public ScaleToAction3d scaleTo (float x, float y , float z float duration, Interpolation interpolation) {
//        ScaleToAction3d action = action(ScaleToAction3d.class);
//        action.setScale(x, y, z);
//        action.setDuration(duration);
//        action.setInterpolation(interpolation);
//        return action;
//    }
//
//    /** Scales the actor instantly. */
//    static public ScaleByAction3d scaleBy (float amountX, float amountY, float amountZ) {
//        return scaleBy(amountX, amountY, amountZ, 0, null);
//    }
//
//    static public ScaleByAction3d scaleBy (float amountX, float amountY, float amountZ, float duration) {
//        return scaleBy(amountX, amountY, amountZ, duration, null);
//    }
//
//    static public ScaleByAction3d scaleBy (float amountX, float amountY, float amountZ, float duration, Interpolation interpolation) {
//        ScaleByAction3d action = action(ScaleByAction3d.class);
//        action.setAmount(amountX, amountY, amountZ);
//        action.setDuration(duration);
//        action.setInterpolation(interpolation);
//        return action;
//    }

//    /** Rotates the actor instantly. */
//    static public RotateToAction3d rotateTo (float rotation) {
//        return rotateTo(rotation, 0, null);
//    }
//
//    static public RotateToAction3d rotateTo (float rotation, float duration) {
//        return rotateTo(rotation, duration, null);
//    }
//
//    static public RotateToAction3d rotateTo (float rotation, float duration, Interpolation interpolation) {
//        RotateToAction3d action = action(RotateToAction3d.class);
//        action.setRotation(rotation);
//        action.setDuration(duration);
//        action.setInterpolation(interpolation);
//        return action;
//    }
//
//    /** Rotates the actor instantly. */
//    static public RotateByAction3d rotateBy (float rotationAmount) {
//        return rotateBy(rotationAmount, 0, null);
//    }
//
//    static public RotateByAction3d rotateBy (float rotationAmount, float duration) {
//        return rotateBy(rotationAmount, duration, null);
//    }
//
//    static public RotateByAction3d rotateBy (float rotationAmount, float duration, Interpolation interpolation) {
//        RotateByAction3d action = action(RotateByAction3d.class);
//        action.setAmount(rotationAmount);
//        action.setDuration(duration);
//        action.setInterpolation(interpolation);
//        return action;
//    }

//    /** Sets the actor's color instantly. */
//    static public ColorAction3d color (Color color) {
//        return color(color, 0, null);
//    }
//
//    /** Transitions from the color at the time this action starts to the specified color. */
//    static public ColorAction3d color (Color color, float duration) {
//        return color(color, duration, null);
//    }
//
//    /** Transitions from the color at the time this action starts to the specified color. */
//    static public ColorAction3d color (Color color, float duration, Interpolation interpolation) {
//        ColorAction3d action = action(ColorAction3d.class);
//        action.setEndColor(color);
//        action.setDuration(duration);
//        action.setInterpolation(interpolation);
//        return action;
//    }
//
//    /** Sets the actor's alpha instantly. */
//    static public AlphaAction3d alpha (float a) {
//        return alpha(a, 0, null);
//    }
//
//    /** Transitions from the alpha at the time this action starts to the specified alpha. */
//    static public AlphaAction3d alpha (float a, float duration) {
//        return alpha(a, duration, null);
//    }
//
//    /** Transitions from the alpha at the time this action starts to the specified alpha. */
//    static public AlphaAction3d alpha (float a, float duration, Interpolation interpolation) {
//        AlphaAction3d action = action(AlphaAction3d.class);
//        action.setAlpha(a);
//        action.setDuration(duration);
//        action.setInterpolation(interpolation);
//        return action;
//    }
//
//    /** Transitions from the alpha at the time this action starts to an alpha of 0. */
//    static public AlphaAction3d fadeOut (float duration) {
//        return alpha(0, duration, null);
//    }
//
//    /** Transitions from the alpha at the time this action starts to an alpha of 0. */
//    static public AlphaAction3d fadeOut (float duration, Interpolation interpolation) {
//        AlphaAction3d action = action(AlphaAction3d.class);
//        action.setAlpha(0);
//        action.setDuration(duration);
//        action.setInterpolation(interpolation);
//        return action;
//    }
//
//    /** Transitions from the alpha at the time this action starts to an alpha of 1. */
//    static public AlphaAction3d fadeIn (float duration) {
//        return alpha(1, duration, null);
//    }
//
//    /** Transitions from the alpha at the time this action starts to an alpha of 1. */
//    static public AlphaAction3d fadeIn (float duration, Interpolation interpolation) {
//        AlphaAction3d action = action(AlphaAction3d.class);
//        action.setAlpha(1);
//        action.setDuration(duration);
//        action.setInterpolation(interpolation);
//        return action;
//    }

//    static public VisibleAction3d show () {
//        return visible(true);
//    }
//
//    static public VisibleAction3d hide () {
//        return visible(false);
//    }
//
//    static public VisibleAction3d visible (boolean visible) {
//        VisibleAction3d action = action(VisibleAction3d.class);
//        action.setVisible(visible);
//        return action;
//    }

//    static public TouchableAction3d touchable (Touchable touchable) {
//        TouchableAction3d action = action(TouchableAction3d.class);
//        action.setTouchable(touchable);
//        return action;
//    }
//
//    static public RemoveActor3dAction3d removeActor3d () {
//        return action(RemoveActor3dAction3d.class);
//    }
//
//    static public RemoveActor3dAction3d removeActor3d (Actor3d removeActor3d) {
//        RemoveActor3dAction3d action = action(RemoveActor3dAction3d.class);
//        action.setTarget(removeActor3d);
//        return action;
//    }

    static public DelayAction3d delay (float duration) {
        DelayAction3d action = action(DelayAction3d.class);
        action.setDuration(duration);
        return action;
    }

    static public DelayAction3d delay (float duration, Action3d delayedAction3d) {
        DelayAction3d action = action(DelayAction3d.class);
        action.setDuration(duration);
        action.setAction(delayedAction3d);
        return action;
    }

//    static public TimeScaleAction3d timeScale (float scale, Action3d scaledAction3d) {
//        TimeScaleAction3d action = action(TimeScaleAction3d.class);
//        action.setScale(scale);
//        action.setAction3d(scaledAction3d);
//        return action;
//    }

    static public SequenceAction3d sequence (Action3d action1) {
        SequenceAction3d action = action(SequenceAction3d.class);
        action.addAction(action1);
        return action;
    }

    static public SequenceAction3d sequence (Action3d action1, Action3d action2) {
        SequenceAction3d action = action(SequenceAction3d.class);
        action.addAction(action1);
        action.addAction(action2);
        return action;
    }

    static public SequenceAction3d sequence (Action3d action1, Action3d action2, Action3d action3) {
        SequenceAction3d action = action(SequenceAction3d.class);
        action.addAction(action1);
        action.addAction(action2);
        action.addAction(action3);
        return action;
    }

    static public SequenceAction3d sequence (Action3d action1, Action3d action2, Action3d action3, Action3d action4) {
        SequenceAction3d action = action(SequenceAction3d.class);
        action.addAction(action1);
        action.addAction(action2);
        action.addAction(action3);
        action.addAction(action4);
        return action;
    }

    static public SequenceAction3d sequence (Action3d action1, Action3d action2, Action3d action3, Action3d action4, Action3d action5) {
        SequenceAction3d action = action(SequenceAction3d.class);
        action.addAction(action1);
        action.addAction(action2);
        action.addAction(action3);
        action.addAction(action4);
        action.addAction(action5);
        return action;
    }

    static public SequenceAction3d sequence (Action3d... actions) {
        SequenceAction3d action = action(SequenceAction3d.class);
        for (int i = 0, n = actions.length; i < n; i++)
            action.addAction(actions[i]);
        return action;
    }

    static public SequenceAction3d sequence () {
        return action(SequenceAction3d.class);
    }

    static public ParallelAction3d parallel (Action3d action1) {
        ParallelAction3d action = action(ParallelAction3d.class);
        action.addAction(action1);
        return action;
    }

    static public ParallelAction3d parallel (Action3d action1, Action3d action2) {
        ParallelAction3d action = action(ParallelAction3d.class);
        action.addAction(action1);
        action.addAction(action2);
        return action;
    }

    static public ParallelAction3d parallel (Action3d action1, Action3d action2, Action3d action3) {
        ParallelAction3d action = action(ParallelAction3d.class);
        action.addAction(action1);
        action.addAction(action2);
        action.addAction(action3);
        return action;
    }

    static public ParallelAction3d parallel (Action3d action1, Action3d action2, Action3d action3, Action3d action4) {
        ParallelAction3d action = action(ParallelAction3d.class);
        action.addAction(action1);
        action.addAction(action2);
        action.addAction(action3);
        action.addAction(action4);
        return action;
    }

    static public ParallelAction3d parallel (Action3d action1, Action3d action2, Action3d action3, Action3d action4, Action3d action5) {
        ParallelAction3d action = action(ParallelAction3d.class);
        action.addAction(action1);
        action.addAction(action2);
        action.addAction(action3);
        action.addAction(action4);
        action.addAction(action5);
        return action;
    }

    static public ParallelAction3d parallel (Action3d... actions) {
        ParallelAction3d action = action(ParallelAction3d.class);
        for (int i = 0, n = actions.length; i < n; i++)
            action.addAction(actions[i]);
        return action;
    }

    static public ParallelAction3d parallel () {
        return action(ParallelAction3d.class);
    }

    static public RepeatAction3d repeat (int count, Action3d repeatedAction3d) {
        RepeatAction3d action = action(RepeatAction3d.class);
        action.setCount(count);
        action.setAction(repeatedAction3d);
        return action;
    }

    static public RepeatAction3d forever (Action3d repeatedAction3d) {
        RepeatAction3d action = action(RepeatAction3d.class);
        action.setCount(RepeatAction3d.FOREVER);
        action.setAction(repeatedAction3d);
        return action;
    }

//    static public RunnableAction3d run (Runnable runnable) {
//        RunnableAction3d action = action(RunnableAction3d.class);
//        action.setRunnable(runnable);
//        return action;
//    }

//    static public LayoutAction3d layout (boolean enabled) {
//        LayoutAction3d action = action(LayoutAction3d.class);
//        action.setLayoutEnabled(enabled);
//        return action;
//    }

//    static public AfterAction3d after (Action3d action) {
//        AfterAction3d afterAction3d = action(AfterAction3d.class);
//        afterAction3d.setAction3d(action);
//        return afterAction3d;
//    }
//
//    static public AddListenerAction3d addListener (EventListener3d listener, boolean capture) {
//        AddListenerAction3d addAction3d = action(AddListenerAction3d.class);
//        addAction3d.setListener(listener);
//        addAction3d.setCapture(capture);
//        return addAction3d;
//    }
//
//    static public AddListenerAction3d addListener (EventListener3d listener, boolean capture, Actor3d targetActor3d) {
//        AddListenerAction3d addAction3d = action(AddListenerAction3d.class);
//        addAction3d.setTarget(targetActor3d);
//        addAction3d.setListener(listener);
//        addAction3d.setCapture(capture);
//        return addAction3d;
//    }
//
//    static public RemoveListenerAction3d removeListener (EventListener3d listener, boolean capture) {
//        RemoveListenerAction3d addAction3d = action(RemoveListenerAction3d.class);
//        addAction3d.setListener(listener);
//        addAction3d.setCapture(capture);
//        return addAction3d;
//    }
//
//    static public RemoveListenerAction3d removeListener (EventListener3d listener, boolean capture, Actor3d targetActor3d) {
//        RemoveListenerAction3d addAction3d = action(RemoveListenerAction3d.class);
//        addAction3d.setTarget(targetActor3d);
//        addAction3d.setListener(listener);
//        addAction3d.setCapture(capture);
//        return addAction3d;
//    }
}

