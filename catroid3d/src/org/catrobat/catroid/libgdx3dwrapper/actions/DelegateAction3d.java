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
//this class is based on and contains source code from on libgdx scene2d DelegateAction.class

package org.catrobat.catroid.libgdx3dwrapper.actions;

import com.badlogic.gdx.utils.Pool;
import org.catrobat.catroid.libgdx3dwrapper.scene.Actor3d;

abstract public class DelegateAction3d extends Action3d {
    protected Action3d action;

    /** Sets the wrapped action. */
    public void setAction (Action3d action) {
        this.action = action;
    }

    public Action3d getAction () {
        return action;
    }

    abstract protected boolean delegate (float delta);

    public final boolean act (float delta) {
        Pool pool = getPool();
        setPool(null); // Ensure this action can't be returned to the pool inside the delegate action.
        try {
            return delegate(delta);
        } finally {
            setPool(pool);
        }
    }

    public void restart () {
        if (action != null) action.restart();
    }

    public void reset () {
        super.reset();
        action = null;
    }

    public void setActor (Actor3d actor) {
        if (action != null) action.setActor(actor);
        super.setActor(actor);
    }

    public void setTarget (Actor3d target) {
        if (action != null) action.setTarget(target);
        super.setTarget(target);
    }

    public String toString () {
        return super.toString() + (action == null ? "" : "(" + action + ")");
    }
}
