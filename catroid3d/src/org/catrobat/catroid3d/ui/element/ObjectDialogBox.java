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
package org.catrobat.catroid3d.ui.element;

import org.catrobat.catroid3d.ProjectManager;
import org.catrobat.catroid3d.R;
import org.catrobat.catroid3d.WorldActivity;
import org.catrobat.catroid3d.physics.Entity;
import org.catrobat.catroid3d.ui.ObjectHandler;
import org.catrobat.catroid3d.ui.screen.ProjectBuildScreen;

import com.badlogic.gdx.physics.bullet.collision.btCollisionObject.CollisionFlags;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class ObjectDialogBox extends Table {

	private ObjectHandler objectHandler;
	private Entity selectedEntity = null;
	private CollisionObjectButton collisionObjectButton;
	private Table chooseCollisionObjectTable;

	public ObjectDialogBox(ObjectHandler objectHandler) {
		super(ProjectManager.getInstance().getSkin());
		align(Align.top | Align.right);
		this.objectHandler = objectHandler;
		collisionObjectButton = new CollisionObjectButton();
		//add(collisionObjectButton);
		DeleteObjectButton deleteObjectButton = new DeleteObjectButton();
		CancelObjectButton cancelObjectButton = new CancelObjectButton();
		add(deleteObjectButton);
		add(cancelObjectButton);
		chooseCollisionObjectTable = new Table();
		WorldActivity worldActivity = ProjectManager.getInstance().getWorldActivity();
		chooseCollisionObjectTable.add(new ChooseCollisionObjectButton(
				worldActivity.getString(R.string.dynamic_object), 0));
		chooseCollisionObjectTable.row();
		chooseCollisionObjectTable.add(new ChooseCollisionObjectButton(worldActivity.getString(R.string.static_object),
				CollisionFlags.CF_STATIC_OBJECT));
		chooseCollisionObjectTable.row();
		chooseCollisionObjectTable.add(new ChooseCollisionObjectButton(worldActivity.getString(R.string.simple_object),
				CollisionFlags.CF_STATIC_OBJECT | CollisionFlags.CF_NO_CONTACT_RESPONSE));
		chooseCollisionObjectTable.setVisible(false);
		ProjectBuildScreen projectBuildScreen = worldActivity.getWorldListener().getProjectBuildScreen();
		projectBuildScreen.getStage().addActor(chooseCollisionObjectTable);
		projectBuildScreen.putButton(ProjectManager.getInstance().getWorldActivity().getString(R.string.delete),
				deleteObjectButton);
		projectBuildScreen.putButton(ProjectManager.getInstance().getWorldActivity().getString(R.string.cancel),
				cancelObjectButton);
	}

	public void setEntityAndShowObjectDialog(Entity entity) {
		selectedEntity = entity;
		String collisionFlagName = getCollisionNameFromFlag(entity.body.getCollisionFlags());
		collisionObjectButton.setText(collisionFlagName);
		chooseCollisionObjectTable.setPosition(collisionObjectButton.getX(), collisionObjectButton.getY());
		setVisible(true);
	}

	public void resetEntityAndHideObjectDialog() {
		selectedEntity = null;
		chooseCollisionObjectTable.setVisible(false);
		setVisible(false);
	}

	private String getCollisionNameFromFlag(int flag) {
		WorldActivity worldActivity = ProjectManager.getInstance().getWorldActivity();
		if (flag == 0) {
			return worldActivity.getString(R.string.dynamic_object);
		}
		if (flag == CollisionFlags.CF_STATIC_OBJECT) {
			return worldActivity.getString(R.string.static_object);
		}
		if (flag == (CollisionFlags.CF_STATIC_OBJECT | CollisionFlags.CF_NO_CONTACT_RESPONSE)) {
			return worldActivity.getString(R.string.simple_object);
		}
		return null;
	}

	private class CollisionObjectButton extends TextButton {

		public CollisionObjectButton() {
			super("", ProjectManager.getInstance().getSkin());
			addListener(new InputListener() {

				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					setChecked(false);
					ObjectDialogBox.this.chooseCollisionObjectTable
							.setVisible(!ObjectDialogBox.this.chooseCollisionObjectTable.isVisible());
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
					setChecked(false);
				}
			});
		}
	}

	private class ChooseCollisionObjectButton extends TextButton {

		private int flagToSet;

		public ChooseCollisionObjectButton(String text, int flagToSet) {
			super(text, ProjectManager.getInstance().getSkin());
			this.flagToSet = flagToSet;
			addListener(new InputListener() {

				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					setChecked(false);
					selectedEntity.body.setCollisionFlags(ChooseCollisionObjectButton.this.flagToSet);
					ObjectDialogBox.this.chooseCollisionObjectTable.setVisible(false);
					ObjectDialogBox.this.collisionObjectButton
							.setText(getCollisionNameFromFlag(ChooseCollisionObjectButton.this.flagToSet));
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
					setChecked(false);
				}
			});
		}
	}

	private class DeleteObjectButton extends TextButton {

		public DeleteObjectButton() {
			super(ProjectManager.getInstance().getWorldActivity().getString(R.string.delete), ProjectManager
					.getInstance().getSkin());
			addListener(new InputListener() {

				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					setChecked(false);
					objectHandler.removeObjectFromWorld(selectedEntity);
					resetEntityAndHideObjectDialog();
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
					setChecked(false);
				}
			});
		}
	}

	private class CancelObjectButton extends TextButton {

		public CancelObjectButton() {
			super(ProjectManager.getInstance().getWorldActivity().getString(R.string.cancel), ProjectManager
					.getInstance().getSkin());
			addListener(new InputListener() {

				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					setChecked(false);
					resetEntityAndHideObjectDialog();
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
					setChecked(false);
				}
			});
		}
	}
}
