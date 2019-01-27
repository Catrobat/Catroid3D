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
//this class is based on and contains source code from on libgdx scene2d SequenceAction.class

package org.catrobat.catroid.libgdx3dwrapper.actions;

import com.badlogic.gdx.utils.Pool;

public class SequenceAction3d extends ParallelAction3d {
    private int index;

    public SequenceAction3d(){}
    public SequenceAction3d (Action3d action1) {
        addAction(action1);
    }

    public SequenceAction3d (Action3d action1, Action3d action2) {
        addAction(action1);
        addAction(action2);
    }

    public SequenceAction3d (Action3d action1, Action3d action2, Action3d action3) {
        addAction(action1);
        addAction(action2);
        addAction(action3);
    }

    public SequenceAction3d (Action3d action1, Action3d action2, Action3d action3, Action3d action4) {
        addAction(action1);
        addAction(action2);
        addAction(action3);
        addAction(action4);
    }

    public SequenceAction3d (Action3d action1, Action3d action2, Action3d action3, Action3d action4, Action3d action5) {
        addAction(action1);
        addAction(action2);
        addAction(action3);
        addAction(action4);
        addAction(action5);
    }

    @Override
    public boolean act(float delta) {
        if (index >= super.actions.size) return true;
        Pool pool = getPool();
        setPool(null); // Ensure this action can't be returned to the pool while executings.
        try {
            if (super.actions.get(index).act(delta)) {
                if (actor == null) return true; // This action was removed.
                index++;
                return index >= super.actions.size;
            }
            return false;
        } finally {
            setPool(pool);
        }
    }

    @Override
    public void restart () {
        super.restart();
        index = 0;
    }
}
