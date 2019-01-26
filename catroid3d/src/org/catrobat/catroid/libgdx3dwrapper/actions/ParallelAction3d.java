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
//this class is based on and contains source code from on libgdx scene2d ParallelAction.class

package org.catrobat.catroid.libgdx3dwrapper.actions;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

import org.catrobat.catroid.libgdx3dwrapper.scene.Actor3d;

public class ParallelAction3d extends Action3d {
    Array<Action3d> actions = new Array(4);
    private boolean complete;

    public ParallelAction3d () {
    }

    public ParallelAction3d (Action3d action1) {
        addAction(action1);
    }

    public ParallelAction3d (Action3d action1, Action3d action2) {
        addAction(action1);
        addAction(action2);
    }

    public ParallelAction3d (Action3d action1, Action3d action2, Action3d action3) {
        addAction(action1);
        addAction(action2);
        addAction(action3);
    }

    public ParallelAction3d (Action3d action1, Action3d action2, Action3d action3, Action3d action4) {
        addAction(action1);
        addAction(action2);
        addAction(action3);
        addAction(action4);
    }

    public ParallelAction3d (Action3d action1, Action3d action2, Action3d action3, Action3d action4, Action3d action5) {
        addAction(action1);
        addAction(action2);
        addAction(action3);
        addAction(action4);
        addAction(action5);
    }

    public boolean act (float delta) {
        if (complete) return true;
        complete = true;
        Pool pool = getPool();
        setPool(null); // Ensure this action can't be returned to the pool while executing.
        try {
            Array<Action3d> actions = this.actions;
            for (int i = 0, n = actions.size; i < n && actor != null; i++) {
                Action3d currentAction = actions.get(i);
                if (currentAction.getActor() != null && !currentAction.act(delta)) complete = false;
                if (actor == null) return true; // This action was removed.
            }
            return complete;
        } finally {
            setPool(pool);
        }
    }

    public void restart () {
        complete = false;
        Array<Action3d> actions = this.actions;
        for (int i = 0, n = actions.size; i < n; i++)
            actions.get(i).restart();
    }

    public void reset () {
        super.reset();
        actions.clear();
    }

    public void addAction (Action3d action) {
        actions.add(action);
        if (actor != null) action.setActor(actor);
    }

    public void setActor (Actor3d actor) {
        Array<Action3d> actions = this.actions;
        for (int i = 0, n = actions.size; i < n; i++)
            actions.get(i).setActor(actor);
        super.setActor(actor);
    }

    public Array<Action3d> getActions () {
        return actions;
    }

    public String toString () {
        StringBuilder buffer = new StringBuilder(64);
        buffer.append(super.toString());
        buffer.append('(');
        Array<Action3d> actions = this.actions;
        for (int i = 0, n = actions.size; i < n; i++) {
            if (i > 0) buffer.append(", ");
            buffer.append(actions.get(i));
        }
        buffer.append(')');
        return buffer.toString();
    }
}
