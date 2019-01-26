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
package org.catrobat.catroid.ui.element;

import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Constants.MODEL;
import org.catrobat.catroid.common.ModelDescriptor;
import org.catrobat.catroid.ui.ObjectHandler;
import org.catrobat.catroid.utils.Util3d;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

public class AddModelImageButton extends ImageButton {

    private ModelDescriptor modelDescriptor;
    private ObjectHandler objectHandler;

    public AddModelImageButton(MODEL model, ObjectHandler addObjectButton) {
        super(ProjectManager.getInstance().getSkin(), Util3d.getModelDescriptor(model).getNameInSkinFile());
        this.setName(Util3d.getModelDescriptor(model).getNameInSkinFile());
        modelDescriptor = Util3d.getModelDescriptor(model);
        this.objectHandler = addObjectButton;

        addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                objectHandler.addObjectToWorld(modelDescriptor);
                objectHandler.hideChooseObjectSplitPane();
                return true;
            }

        });
    }
}
