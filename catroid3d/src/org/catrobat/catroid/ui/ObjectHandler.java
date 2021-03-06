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
package org.catrobat.catroid.ui;

import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Constants.MODEL;
import org.catrobat.catroid.common.ModelDescriptor;
import org.catrobat.catroid.content.models.ComplexAssetObject;
import org.catrobat.catroid.content.models.Abstract3dObject.TEXTURE_TYPE;
import org.catrobat.catroid.physics.Entity;
import org.catrobat.catroid.ui.element.AddModelImageButton;
import org.catrobat.catroid.ui.element.ChooseCategoryObjectButton;
import org.catrobat.catroid.ui.element.ObjectDialogBox;
import org.catrobat.catroid.ui.worldeditscreen.ProjectBuildScreen;
import org.catrobat.catroid.utils.Math;
import org.catrobat.catroid.utils.Util3d;
import org.catrobat.catroid.utils.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject.CollisionFlags;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

public class ObjectHandler {

    // TODO: change this
    private int currentIndex = 0;

    private ProjectBuildScreen projectBuildScreen;
    private SplitPane chooseObjectSplitPane;
    private Table chooseObjectTable;
    private Array<Table> chooseObjectTableArray = new Array<Table>();
    private ObjectDialogBox objectDialogBox;

    public ObjectHandler(ProjectBuildScreen projectBuildScreen) {
        this.projectBuildScreen = projectBuildScreen;
        createChooseObjectSplitPane();
        objectDialogBox = new ObjectDialogBox(this);
        hideObjectDialogBox();
        projectBuildScreen.getStage().addActor(objectDialogBox);
    }

    public void showObjectDialogBox(Entity entity, float x, float y) {
        // TODO refactor --------------------------
        if (x + objectDialogBox.getWidth() > Gdx.graphics.getWidth()) {
            x -= objectDialogBox.getWidth();
        }
        if (y + objectDialogBox.getHeight() > Gdx.graphics.getHeight()) {
            y -= objectDialogBox.getHeight();
        }
        // ------------------------------
        objectDialogBox.setPosition(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        objectDialogBox.setEntityAndShowObjectDialog(entity);
    }

    public void hideObjectDialogBox() {
        objectDialogBox.resetEntityAndHideObjectDialog();
    }

    public void hideChooseObjectSplitPane() {
        chooseObjectSplitPane.setVisible(false);
        projectBuildScreen.showControlPane();
    }

    public void showChooseObjectSplitPane() {
        projectBuildScreen.hideControlPane();
        chooseObjectSplitPane.setVisible(true);
    }

    public void objectCategoryChanged(int index) {
        chooseObjectTable.clearChildren();
        chooseObjectTable.add(chooseObjectTableArray.get(index)).top();
    }

    public void addObjectToWorld(ModelDescriptor modelDescriptor) {
        float mass = 0f;
        int flags = CollisionFlags.CF_STATIC_OBJECT | CollisionFlags.CF_NO_CONTACT_RESPONSE;
        if (modelDescriptor.equals(Util3d.getModelDescriptor(MODEL.MODEL_BIG_WOOD_BARREL))) {
            mass = 10f;
            flags = 0;
        }
        if (modelDescriptor.equals(Util3d.getModelDescriptor(MODEL.MODEL_PALM_TREE_01))) {
            flags = CollisionFlags.CF_STATIC_OBJECT;
        }

        String name = modelDescriptor.getNameInSkinFile();
        name += Utils.uniqueString();
        ComplexAssetObject object = new ComplexAssetObject(name, mass,
                Math.createCenterPositionMatrix(), modelDescriptor);
        object.setTextureType(TEXTURE_TYPE.NONE);
        object.setCollisionFlags(flags);
        ProjectManager.getInstance().getCurrentProject().addObject(object);
        ProjectManager.getInstance().getWorldListener().addObjectToWorld(object);
    }

    public void removeObjectFromWorld(Entity entity) {
        if (entity != null) {
            ProjectManager.getInstance().getWorldListener().getWorld().removeEntity(entity);
            String name = entity.getName();
            entity.dispose();
            ProjectManager.getInstance().getCurrentProject().removeObject(name);
        }
    }

    private void createChooseObjectSplitPane() {
        Skin skin = ProjectManager.getInstance().getSkin();
        ChooseCategoryObjectButton chooseCategoryGroundObjectButton = addChooseObjectTableEntry(
                "Ground", new MODEL[]{MODEL.MODEL_PALM_TREE_01,
                        MODEL.MODEL_TROPICAL_PLANT_01, MODEL.MODEL_TROPICAL_PLANT_02, MODEL.MODEL_PALM_PLANT_01});
        ChooseCategoryObjectButton chooseCategoryMiscellaneousObjectButton = addChooseObjectTableEntry(
                "Miscellaneous", new MODEL[]{MODEL.MODEL_BIG_WOOD_BARREL});
        Table chooseCategoryObjectTable = new Table(skin);
        chooseCategoryObjectTable.align(Align.top);
        chooseCategoryObjectTable.add(chooseCategoryGroundObjectButton).fillX();
        chooseCategoryObjectTable.row();
        chooseCategoryObjectTable.add(chooseCategoryMiscellaneousObjectButton).fillX();
        chooseObjectTable = new Table();
        chooseObjectTable.align(Align.top | Align.left);
        chooseObjectTable.add(chooseObjectTableArray.get(0));
        chooseObjectSplitPane = new SplitPane(new ScrollPane(chooseCategoryObjectTable, skin), new ScrollPane(
                chooseObjectTable, skin), false, skin);
        chooseObjectSplitPane.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        chooseObjectSplitPane.setSplitAmount(0.3f);
        chooseObjectSplitPane.setVisible(false);
        projectBuildScreen.getStage().addActor(chooseObjectSplitPane);
    }

    private ChooseCategoryObjectButton addChooseObjectTableEntry(String category, MODEL[] modelsInCategory) {
        ChooseCategoryObjectButton chooseObjectCategoryButton = new ChooseCategoryObjectButton(this, category,
                currentIndex);
        projectBuildScreen.putButton(category, chooseObjectCategoryButton);
        currentIndex++;
        Table chooseObjectTable = new Table();
        for (MODEL model : modelsInCategory) {
            AddModelImageButton addModelImageButton = new AddModelImageButton(model, this);
            projectBuildScreen.putButton(model.name(), addModelImageButton);
            chooseObjectTable.add(addModelImageButton).padRight(0.2f);
        }
        chooseObjectTableArray.add(chooseObjectTable);
        return chooseObjectCategoryButton;
    }

    public void addScripts(Entity entity) {
        UiUtils.InvokeOnUiThread(() ->
        {
            ProjectManager.getInstance().setCurrentSpriteEntity(entity);
            ProjectManager.getInstance().getWorldActivity().navigateToScriptsEditView();
        });

    }
}
