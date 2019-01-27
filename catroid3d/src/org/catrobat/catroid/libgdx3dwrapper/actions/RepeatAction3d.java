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
//this class is based on and contains source code from on libgdx scene2d RepeatAction.class

package org.catrobat.catroid.libgdx3dwrapper.actions;

public class RepeatAction3d extends DelegateAction3d {
    static public final int FOREVER = -1;

    private int repeatCount, executedCount;
    private boolean finished;

    protected boolean delegate (float delta) {
        if (executedCount == repeatCount) return true;
        if (action.act(delta)) {
            if (finished) return true;
            if (repeatCount > 0) executedCount++;
            if (executedCount == repeatCount) return true;
            if (action != null) action.restart();
        }
        return false;
    }

    /** Causes the action to not repeat again. */
    public void finish () {
        finished = true;
    }

    public void restart () {
        super.restart();
        executedCount = 0;
        finished = false;
    }

    /** Sets the number of times to repeat. Can be set to {@link #FOREVER}. */
    public void setCount (int count) {
        this.repeatCount = count;
    }

    public int getCount () {
        return repeatCount;
    }
}
