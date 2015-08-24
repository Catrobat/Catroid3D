/**
 *  Catroid: An on-device visual programming system for Android devices
 *  Copyright (C) 2010-2013 The Catrobat Team
 *  (<http://developer.catrobat.org/credits>)
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *
 *  An additional term exception under section 7 of the GNU Affero
 *  General Public License, version 3, is available at
 *  http://developer.catrobat.org/license_additional_term
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.catroid3d.ui;

import org.catrobat.catroid3d.ProjectManager;
import org.catrobat.catroid3d.R;
import org.catrobat.catroid3d.WorldActivity;
import org.catrobat.catroid3d.common.Constants.MODEL;
import org.catrobat.catroid3d.common.ModelDescriptor;
import org.catrobat.catroid3d.content.ComplexAssetObject;
import org.catrobat.catroid3d.content.Object;
import org.catrobat.catroid3d.content.Object.TEXTURE_TYPE;
import org.catrobat.catroid3d.physics.Entity;
import org.catrobat.catroid3d.ui.element.AddModelImageButton;
import org.catrobat.catroid3d.ui.element.ChooseCategoryObjectButton;
import org.catrobat.catroid3d.ui.element.ObjectDialogBox;
import org.catrobat.catroid3d.ui.screen.ProjectBuildScreen;
import org.catrobat.catroid3d.utils.Math;
import org.catrobat.catroid3d.utils.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject.CollisionFlags;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
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
		if (modelDescriptor.equals(Util.getModelDescriptor(MODEL.MODEL_BIG_WOOD_BARREL))) {
			mass = 10f;
			flags = 0;
		}
		if (modelDescriptor.equals(Util.getModelDescriptor(MODEL.MODEL_PALM_TREE_01))) {
			flags = CollisionFlags.CF_STATIC_OBJECT;
		}
		ComplexAssetObject object = new ComplexAssetObject(modelDescriptor.getNameInSkinFile(), mass,
				Math.createCenterPositionMatrix(), modelDescriptor);
		object.setTextureType(TEXTURE_TYPE.NONE);
		object.setCollisionFlags(flags);
		ProjectManager.getInstance().getCurrentProject().addObject(object);
		ProjectManager.getInstance().getWorldActivity().getWorldListener().addObjectToWorld(object);
	}

	public void removeObjectFromWorld(Entity entity) {
		if (entity != null) {
			Object object = entity.getObject();
			ProjectManager.getInstance().getWorldActivity().getWorldListener().getWorld().removeEntity(entity);
			entity.dispose();
			ProjectManager.getInstance().getCurrentProject().removeObject(object);
		}
	}

	private void createChooseObjectSplitPane() {
		Skin skin = ProjectManager.getInstance().getSkin();
		WorldActivity worldActivity = ProjectManager.getInstance().getWorldActivity();
		ChooseCategoryObjectButton chooseCategoryGroundObjectButton = addChooseObjectTableEntry(
				worldActivity.getString(R.string.ground), new MODEL[] { MODEL.MODEL_PALM_TREE_01,
					MODEL.MODEL_TROPICAL_PLANT_01, MODEL.MODEL_TROPICAL_PLANT_02, MODEL.MODEL_PALM_PLANT_01 });
		ChooseCategoryObjectButton chooseCategoryMiscellaneousObjectButton = addChooseObjectTableEntry(
				worldActivity.getString(R.string.miscellaneous), new MODEL[] { MODEL.MODEL_BIG_WOOD_BARREL });
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
}
