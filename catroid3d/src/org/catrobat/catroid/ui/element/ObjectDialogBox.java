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
import org.catrobat.catroid.R;
import org.catrobat.catroid.physics.Entity;
import org.catrobat.catroid.ui.ObjectHandler;
import org.catrobat.catroid.ui.worldeditscreen.ProjectBuildScreen;

import com.badlogic.gdx.physics.bullet.collision.btCollisionObject.CollisionFlags;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

public class ObjectDialogBox extends Table {

    private final ProjectManager _projectManager;
    private ObjectHandler objectHandler;
    private Entity selectedEntity = null;
    private CollisionObjectButton collisionObjectButton;
    private Table chooseCollisionObjectTable;

    public ObjectDialogBox(ObjectHandler objectHandler) {
        super(ProjectManager.getInstance().getSkin());
        _projectManager = ProjectManager.getInstance();
        align(Align.top | Align.right);
        this.objectHandler = objectHandler;
        collisionObjectButton = new CollisionObjectButton();
        //add(collisionObjectButton);
        DeleteObjectButton deleteObjectButton = new DeleteObjectButton();
        CancelObjectButton cancelObjectButton = new CancelObjectButton();
        AddScriptsToObjectButton addScriptsToObjectButton = new AddScriptsToObjectButton();
        add(deleteObjectButton);
        add(cancelObjectButton);
        add(addScriptsToObjectButton);

        chooseCollisionObjectTable = new Table();

        chooseCollisionObjectTable.add(new ChooseCollisionObjectButton(
                _projectManager.GetLocale(R.string.dynamic_object), 0));
        chooseCollisionObjectTable.row();
        chooseCollisionObjectTable.add(new ChooseCollisionObjectButton(_projectManager.GetLocale(R.string.static_object),
                CollisionFlags.CF_STATIC_OBJECT));
        chooseCollisionObjectTable.row();
        chooseCollisionObjectTable.add(new ChooseCollisionObjectButton(_projectManager.GetLocale(R.string.simple_object),
                CollisionFlags.CF_STATIC_OBJECT | CollisionFlags.CF_NO_CONTACT_RESPONSE));
        chooseCollisionObjectTable.setVisible(false);
        ProjectBuildScreen projectBuildScreen = _projectManager.getWorldListener().getProjectBuildScreen();
        projectBuildScreen.getStage().addActor(chooseCollisionObjectTable);
        projectBuildScreen.putButton(_projectManager.GetLocale(R.string.scripts), addScriptsToObjectButton);
        projectBuildScreen.putButton(_projectManager.GetLocale(R.string.delete),
                deleteObjectButton);
        projectBuildScreen.putButton(_projectManager.GetLocale(R.string.cancel),
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
        if (flag == 0) {
            return _projectManager.GetLocale(R.string.dynamic_object);
        }
        if (flag == CollisionFlags.CF_STATIC_OBJECT) {
            return _projectManager.GetLocale(R.string.static_object);
        }
        if (flag == (CollisionFlags.CF_STATIC_OBJECT | CollisionFlags.CF_NO_CONTACT_RESPONSE)) {
            return _projectManager.GetLocale(R.string.simple_object);
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

    private class AddScriptsToObjectButton extends TextButton {

        public AddScriptsToObjectButton() {
            super(_projectManager.GetLocale(R.string.scripts), ProjectManager
                    .getInstance().getSkin());
            addListener(new InputListener() {

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    setChecked(false);
                    Entity entity = selectedEntity;
                    resetEntityAndHideObjectDialog();
                    objectHandler.addScripts(entity);

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
            super(_projectManager.GetLocale(R.string.delete), ProjectManager
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
            super(_projectManager.GetLocale(R.string.cancel), ProjectManager
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
