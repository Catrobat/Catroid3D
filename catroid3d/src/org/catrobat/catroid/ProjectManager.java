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
package org.catrobat.catroid;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.Constants.MODEL;
import org.catrobat.catroid.common.Constants.TEXTURE;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.*;
import org.catrobat.catroid.content.models.Abstract3dObject;
import org.catrobat.catroid.content.models.BoxAssetObject;
import org.catrobat.catroid.content.models.ComplexAssetObject;
import org.catrobat.catroid.content.models.Abstract3dObject.OBJECT_TYPE;
import org.catrobat.catroid.content.models.Abstract3dObject.TEXTURE_TYPE;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.models.SphereAssetObject;
import org.catrobat.catroid.io.StorageHandler;
import org.catrobat.catroid.physics.Entity;
import org.catrobat.catroid.ui.controller.BackpackListManager;
import org.catrobat.catroid.utils.Math;
import org.catrobat.catroid.utils.Util3d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProjectManager {

    private static final String TAG = ProjectManager.class.getSimpleName();

    private static final ProjectManager INSTANCE = new ProjectManager();
    private Project project;
    private Skin skin;
    private WorldListener _worldListener = null;
    private WorldActivity _worldActivity = null;

    private Scene currentlyEditedScene;
    private Scene currentlyPlayingScene;
    private Scene startScene;
    private Script currentScript;
    private Sprite currentSprite;
    private UserBrick currentUserBrick;

    private ProjectManager() {

    }

    public static ProjectManager getInstance() {
        return INSTANCE;
    }

    public boolean initProject() {
        if(project == null)
        {
            loadDefaultProject();
            return true;
        }
        return false;
    }

    private void loadDefaultProject() {
        project = new Project("DefaultProject");
        Abstract3dObject groundObject = new BoxAssetObject("Ground", 0f, Math.createPositionMatrix(0f, -1f, 0f), 1000f, 1f,
                1000f, TEXTURE.TEXTURE_GRASS_01);
        groundObject.setObjectType(OBJECT_TYPE.GROUND);

        Abstract3dObject barrel01 = new ComplexAssetObject("Barrel01", 10f, Math.createPositionMatrix(0f, 0f, -200f),
                Util3d.getModelDescriptor(MODEL.MODEL_BIG_WOOD_BARREL));
        //barrel01.setCollisionFlags(CollisionFlags.CF_NO_CONTACT_RESPONSE);
        barrel01.setTextureType(TEXTURE_TYPE.NONE);

//		Abstract3dObject knight = new AnimatedAssetObject("Dog", 20f, Math.createPositionMatrix(10f, 2f, 250f).scl(2f),
//				Constants.ANIMATED_MODEL_DESCRIPTOR_ARRAY.get(ANIMATED_MODEL.MODEL_KNIGHT.ordinal()));

        //		Abstract3dObject knight = new ComplexAssetObject("Knight", 0f, Math.createPositionMatrix(200f, 0f, 250f),
        //				Util3d.getModelDescriptor(MODEL.MODEL_KNIGHT));

        //		Abstract3dObject barrel02 = new ComplexAssetObject("Barrel02", 10f, Math.createPositionMatrix(30f, 0f, 200f),
        //				Util3d.getModelDescriptor(MODEL.MODEL_BIG_WOOD_BARREL));
        //		barrel02.setTextureType(TEXTURE_TYPE.NONE);

		for (int i = 0; i < 100; i++) {
			Abstract3dObject grass01 = new ComplexAssetObject("Gras01" + i, 0f, Math.createRandomPosition(-490, 490),
					Util3d.getModelDescriptor(MODEL.MODEL_GRASS_01));
			grass01.setHasRigidBody(false);
			project.addObject(grass01);
			Abstract3dObject grass02 = new ComplexAssetObject("Gras02" + i, 0f, Math.createRandomPosition(-490, 490),
					Util3d.getModelDescriptor(MODEL.MODEL_GRASS_02));
			grass02.setHasRigidBody(false);
			project.addObject(grass02);
		}

		for (int i = 0; i < 10; i++) {
			Abstract3dObject plant01 = new ComplexAssetObject("Plant01" + i, 0f, Math.createRandomPosition(-400, 400),
					Util3d.getModelDescriptor(MODEL.MODEL_TROPICAL_PLANT_01));
			plant01.setHasRigidBody(false);
			project.addObject(plant01);
			Abstract3dObject plant02 = new ComplexAssetObject("Plant02" + i, 0f, Math.createRandomPosition(-400, 400),
					Util3d.getModelDescriptor(MODEL.MODEL_TROPICAL_PLANT_02));
			plant02.setHasRigidBody(false);
			project.addObject(plant02);
		}

        Abstract3dObject tree01 = new ComplexAssetObject("Tree01", 0f, Math.createPositionMatrix(-200f, 0f, 300f).scl(2f),
                Util3d.getModelDescriptor(MODEL.MODEL_PALM_TREE_01));

        Abstract3dObject skyObject = new SphereAssetObject("Sky", 0f, Math.createPositionMatrix(0f, 0f, 0f), 10000f, 10000f,
                10000f, TEXTURE.TEXTURE_SKY_01, 20, 20);
        skyObject.setHasRigidBody(false);
        skyObject.setObjectType(OBJECT_TYPE.SKYLINE);

        project.addObject(groundObject);
        project.addObject(barrel01);
        //		project.addObject(barrel02);
        project.addObject(tree01);
        project.addObject(skyObject);
        //project.addObject(knight);

        this.project = project;
        currentSprite = null;
        currentScript = null;
        currentlyEditedScene = project.getDefaultScene();
    }

    public Project getProject() {
        return project;
    }

    public void initializeSkin() {
        TextureAtlas packedTextures = StorageHandler.getInstance().getAsset(
                Util3d.getAssetPath(Constants.PACKED_TEXTURE_ATLAS_FILE));
        skin = new Skin(Gdx.files.internal(Constants.SKIN_FILE), packedTextures);
    }

    public Skin getSkin() {
        return skin;
    }

    public WorldListener getWorldListener() {
        return _worldListener;
    }

    public void setWorldListener(WorldListener worldListener) {
        _worldListener = worldListener;
    }

    public String GetLocale(int stringId) {
        return _worldActivity.getString(stringId);
    }

    public WorldActivity getWorldActivity() {
        return _worldActivity;
    }

    public void setWorldActivity(WorldActivity worldActivity) {
        _worldActivity = worldActivity;
    }

//	private void restorePreviousProject(Project previousProject) {
//		project = previousProject;
//		if (previousProject != null) {
//			currentlyPlayingScene = project.getDefaultScene();
//		}
//	}

//	private void makeShallowCopiesDeepAgain(Project project) {
//		for (Scene scene : project.getSceneList()) {
//
//			List<String> fileNames = new ArrayList<>();
//
//			for (Sprite sprite : scene.getSpriteList()) {
//				for (Iterator<LookData> iterator = sprite.getLookList().iterator(); iterator.hasNext(); ) {
//					LookData lookData = iterator.next();
//
//					if (fileNames.contains(lookData.getFile().getName())) {
//						try {
//							lookData.setFile(StorageOperations.duplicateFile(lookData.getFile()));
//						} catch (IOException e) {
//							iterator.remove();
//							Log.e(TAG, "Cannot not copy: " + lookData.getFile().getAbsolutePath()
//									+ ", removing LookData " + lookData.getName() + " from "
//									+ project.getName() + ", "
//									+ scene.getName() + ", "
//									+ sprite.getName() + ".");
//						}
//					}
//					fileNames.add(lookData.getFile().getName());
//				}
//
//				for (Iterator<SoundInfo> iterator = sprite.getSoundList().iterator(); iterator.hasNext(); ) {
//					SoundInfo soundInfo = iterator.next();
//
//					if (fileNames.contains(soundInfo.getFile().getName())) {
//						try {
//							soundInfo.setFile(StorageOperations.duplicateFile(soundInfo.getFile()));
//						} catch (IOException e) {
//							iterator.remove();
//							Log.e(TAG, "Cannot not copy: " + soundInfo.getFile().getAbsolutePath()
//									+ ", removing SoundInfo " + soundInfo.getName() + " from "
//									+ project.getName() + ", "
//									+ scene.getName() + ", "
//									+ sprite.getName() + ".");
//						}
//					}
//					fileNames.add(soundInfo.getFile().getName());
//				}
//			}
//		}
//	}
//
//	private void localizeBackgroundSprite(Context context) {
//		// Set generic localized name on background sprite and move it to the back.
//		if (currentlyEditedScene == null) {
//			return;
//		}
//		if (currentlyEditedScene.getSpriteList().size() > 0) {
//			currentlyEditedScene.getSpriteList().get(0).setName(context.getString(R.string.background));
//			currentlyEditedScene.getSpriteList().get(0).look.setZIndex(0);
//		}
//		currentSprite = null;
//		currentScript = null;
//
//		PreferenceManager.getDefaultSharedPreferences(context)
//				.edit()
//				.putString(PREF_PROJECTNAME_KEY, project.getName())
//				.commit();
//	}

    public void saveProject(Context context) {
        if (project == null) {
            return;
        }

        SaveProjectAsynchronousTask saveTask = new SaveProjectAsynchronousTask();
        saveTask.execute();
    }

//	public boolean initializeDefaultProject(Context context) {
//		try {
//			project = DefaultProjectHandler.createAndSaveDefaultProject(context);
//			currentSprite = null;
//			currentScript = null;
//			currentlyEditedScene = project.getDefaultScene();
//			currentlyPlayingScene = currentlyEditedScene;
//			return true;
//		} catch (IOException ioException) {
//			Log.e(TAG, "Cannot initialize default project.", ioException);
//			return false;
//		}
//	}

//	public void initializeNewProject(String projectName, Context context, boolean empty, boolean drone,
//									 boolean landscapeMode, boolean castEnabled, boolean jumpingSumo)
//			throws IllegalArgumentException, IOException {
//
//		if (empty) {
//			project = DefaultProjectHandler.createAndSaveEmptyProject(projectName, context, landscapeMode, castEnabled);
//		} else {
//			if (drone) {
//				DefaultProjectHandler.getInstance().setDefaultProjectCreator(DefaultProjectHandler.ProjectCreatorType
//						.PROJECT_CREATOR_DRONE);
//			} else if (castEnabled) {
//				DefaultProjectHandler.getInstance().setDefaultProjectCreator(DefaultProjectHandler.ProjectCreatorType
//						.PROJECT_CREATOR_CAST);
//			} else if (jumpingSumo) {
//				DefaultProjectHandler.getInstance().setDefaultProjectCreator(DefaultProjectHandler.ProjectCreatorType
//						.PROJECT_CREATOR_JUMPING_SUMO);
//			} else {
//				DefaultProjectHandler.getInstance().setDefaultProjectCreator(DefaultProjectHandler.ProjectCreatorType
//						.PROJECT_CREATOR_DEFAULT);
//			}
//			project = DefaultProjectHandler.createAndSaveDefaultProject(projectName, context, landscapeMode);
//		}
//
//		currentSprite = null;
//		currentScript = null;
//		currentlyEditedScene = project.getDefaultScene();
//		currentlyPlayingScene = currentlyEditedScene;
//	}

    public Project getCurrentProject() {
        return project;
    }

    public Scene getCurrentlyPlayingScene() {
        if (currentlyPlayingScene == null) {
            currentlyPlayingScene = getCurrentlyEditedScene();
        }
        return currentlyPlayingScene;
    }

    public void setCurrentlyPlayingScene(Scene scene) {
        currentlyPlayingScene = scene;
    }

//	public Scene getStartScene() {
//		if (startScene == null) {
//			startScene = getCurrentlyEditedScene();
//		}
//		return startScene;
//	}
//
//	public void setStartScene(Scene scene) {
//		startScene = scene;
//	}

    public Scene getCurrentlyEditedScene() {
        if (currentlyEditedScene == null) {
            currentlyEditedScene = project.getDefaultScene();
        }
        return currentlyEditedScene;
    }

//	public boolean isCurrentProjectLandscapeMode() {
//		int virtualScreenWidth = getCurrentProject().getXmlHeader().virtualScreenWidth;
//		int virtualScreenHeight = getCurrentProject().getXmlHeader().virtualScreenHeight;
//
//		return virtualScreenWidth > virtualScreenHeight;
//	}
//
//	public void setProject(Project project) {
//		currentScript = null;
//		currentSprite = null;
//
//		this.project = project;
//		if (project != null) {
//			currentlyEditedScene = project.getDefaultScene();
//			currentlyPlayingScene = currentlyEditedScene;
//		}
//	}
//
//	public void setProject(Project project) {
//		this.project = project;
//	}

//	public boolean renameProject(String newProjectName, Context context) {
//		if (XstreamSerializer.getInstance().projectExists(newProjectName)) {
//			return false;
//		}
//
//		String oldProjectPath = PathBuilder.buildProjectPath(project.getName());
//		File oldProjectDirectory = new File(oldProjectPath);
//
//		String newProjectPath = PathBuilder.buildProjectPath(newProjectName);
//		File newProjectDirectory = new File(newProjectPath);
//
//		boolean directoryRenamed;
//
//		if (oldProjectPath.equalsIgnoreCase(newProjectPath)) {
//			String tmpProjectPath = PathBuilder.buildProjectPath(createTemporaryDirectoryName(newProjectName));
//			File tmpProjectDirectory = new File(tmpProjectPath);
//			directoryRenamed = oldProjectDirectory.renameTo(tmpProjectDirectory);
//			if (directoryRenamed) {
//				directoryRenamed = tmpProjectDirectory.renameTo(newProjectDirectory);
//			}
//		} else {
//			directoryRenamed = oldProjectDirectory.renameTo(newProjectDirectory);
//		}
//
//		if (directoryRenamed) {
//			project.setName(newProjectName);
//			saveProject(context);
//		}
//
//		return directoryRenamed;
//	}

    public void setCurrentlyEditedScene(Scene scene) {
        this.currentlyEditedScene = scene;
        currentlyPlayingScene = scene;
    }

    public Sprite getCurrentSprite() {
        return currentSprite;
    }

    public void setCurrentSprite(Sprite sprite) {
        currentSprite = sprite;
    }

    public Script getCurrentScript() {
        return currentScript;
    }

    public void setCurrentScript(Script script) {
        if (script == null) {
            currentScript = null;
        } else if (currentSprite.getScriptIndex(script) != -1) {
            currentScript = script;
        }
    }

    public UserBrick getCurrentUserBrick() {
        return currentUserBrick;
    }

    public void setCurrentUserBrick(UserBrick brick) {
        currentUserBrick = brick;
    }

    public int getCurrentSpritePosition() {
        return getCurrentlyEditedScene().getSpriteList().indexOf(currentSprite);
    }

//	private String createTemporaryDirectoryName(String projectDirectoryName) {
//		String temporaryDirectorySuffix = "_tmp";
//		String temporaryDirectoryName = projectDirectoryName + temporaryDirectorySuffix;
//		int suffixCounter = 0;
//		while (Utils.checkIfProjectExistsOrIsDownloadingIgnoreCase(temporaryDirectoryName)) {
//			temporaryDirectoryName = projectDirectoryName + temporaryDirectorySuffix + suffixCounter;
//			suffixCounter++;
//		}
//		return temporaryDirectoryName;
//	}

    public boolean checkNestingBrickReferences(boolean assumeWrong, boolean inBackPack) {
        boolean projectCorrect = true;
        if (inBackPack) {

            List<Sprite> spritesToCheck = BackpackListManager.getInstance().getSprites();

            HashMap<String, List<Script>> backPackedScripts = BackpackListManager.getInstance().getBackpackedScripts();
            for (String scriptGroup : backPackedScripts.keySet()) {
                List<Script> scriptListToCheck = backPackedScripts.get(scriptGroup);
                for (Script scriptToCheck : scriptListToCheck) {
                    checkCurrentScript(scriptToCheck, assumeWrong);
                }
            }

            for (Sprite currentSprite : spritesToCheck) {
                if (!checkCurrentSprite(currentSprite, assumeWrong)) {
                    projectCorrect = false;
                }
            }
        } else {
            //todo 3d supports only one scene
//			for (Scene scene : project.getSceneList()) {
            if (ProjectManager.getInstance().getCurrentProject() == null) {
                return false;
            }

//				for (Sprite currentSprite : scene.getSpriteList()) {
            if (!checkCurrentSprite(currentSprite, assumeWrong)) {
                projectCorrect = false;
            }
//				}
//			}
        }
        return projectCorrect;
    }

    public boolean checkCurrentSprite(Sprite currentSprite, boolean assumeWrong) {
        boolean spriteCorrect = true;
        int numberOfScripts = currentSprite.getNumberOfScripts();
        for (int pos = 0; pos < numberOfScripts; pos++) {
            Script script = currentSprite.getScript(pos);
            if (!checkCurrentScript(script, assumeWrong)) {
                spriteCorrect = false;
            }
        }
        return spriteCorrect;
    }

    private boolean checkCurrentScript(Script script, boolean assumeWrong) {
        boolean scriptCorrect = true;
        if (assumeWrong) {
            scriptCorrect = false;
        }
        for (Brick currentBrick : script.getBrickList()) {
            if (!scriptCorrect) {
                break;
            }
            scriptCorrect = checkReferencesOfCurrentBrick(currentBrick);
        }
        if (!scriptCorrect) {
            correctAllNestedReferences(script);
        }
        return scriptCorrect;
    }

    private boolean checkReferencesOfCurrentBrick(Brick currentBrick) {
        if (currentBrick instanceof IfThenLogicBeginBrick) {
            IfThenLogicEndBrick endBrick = ((IfThenLogicBeginBrick) currentBrick).getIfThenEndBrick();
            if (endBrick == null || endBrick.getIfBeginBrick() == null
                    || !endBrick.getIfBeginBrick().equals(currentBrick)) {
                Log.d(TAG, "Brick has wrong reference:" + currentSprite + " "
                        + currentBrick);
                return false;
            }
        } else if (currentBrick instanceof IfElseLogicBeginBrick) {
            IfLogicElseBrick elseBrick = ((IfElseLogicBeginBrick) currentBrick).getIfElseBrick();
            IfLogicEndBrick endBrick = ((IfElseLogicBeginBrick) currentBrick).getIfEndBrick();
            if (elseBrick == null || endBrick == null || elseBrick.getIfBeginBrick() == null
                    || elseBrick.getIfEndBrick() == null || endBrick.getIfBeginBrick() == null
                    || endBrick.getIfElseBrick() == null
                    || !elseBrick.getIfBeginBrick().equals(currentBrick)
                    || !elseBrick.getIfEndBrick().equals(endBrick)
                    || !endBrick.getIfBeginBrick().equals(currentBrick)
                    || !endBrick.getIfElseBrick().equals(elseBrick)) {
                Log.d(TAG, "Brick has wrong reference:" + currentSprite + " "
                        + currentBrick);
                return false;
            }
        } else if (currentBrick instanceof LoopBeginBrick) {
            LoopEndBrick endBrick = ((LoopBeginBrick) currentBrick).getLoopEndBrick();
            if (endBrick == null || endBrick.getLoopBeginBrick() == null
                    || !endBrick.getLoopBeginBrick().equals(currentBrick)) {
                Log.d(TAG, "Brick has wrong reference:" + currentSprite + " "
                        + currentBrick);
                return false;
            }
        }
        return true;
    }

    private void correctAllNestedReferences(Script script) {
        ArrayList<IfElseLogicBeginBrick> ifBeginList = new ArrayList<>();
        ArrayList<IfThenLogicBeginBrick> ifThenBeginList = new ArrayList<>();
        ArrayList<Brick> loopBeginList = new ArrayList<>();
        ArrayList<Brick> bricksWithInvalidReferences = new ArrayList<>();

        for (Brick currentBrick : script.getBrickList()) {
            if (currentBrick instanceof IfThenLogicBeginBrick) {
                ifThenBeginList.add((IfThenLogicBeginBrick) currentBrick);
            } else if (currentBrick instanceof IfElseLogicBeginBrick) {
                ifBeginList.add((IfElseLogicBeginBrick) currentBrick);
            } else if (currentBrick instanceof LoopBeginBrick) {
                loopBeginList.add(currentBrick);
            } else if (currentBrick instanceof LoopEndBrick) {
                if (loopBeginList.isEmpty()) {
                    Log.e(TAG, "Removing LoopEndBrick without reference to a LoopBeginBrick");
                    bricksWithInvalidReferences.add(currentBrick);
                    continue;
                }
                LoopBeginBrick loopBeginBrick = (LoopBeginBrick) loopBeginList.get(loopBeginList.size() - 1);
                loopBeginBrick.setLoopEndBrick((LoopEndBrick) currentBrick);
                ((LoopEndBrick) currentBrick).setLoopBeginBrick(loopBeginBrick);
                loopBeginList.remove(loopBeginBrick);
            } else if (currentBrick instanceof IfLogicElseBrick) {
                if (ifBeginList.isEmpty()) {
                    Log.e(TAG, "Removing IfLogicElseBrick without reference to an IfBeginBrick");
                    bricksWithInvalidReferences.add(currentBrick);
                    continue;
                }
                IfElseLogicBeginBrick ifBeginBrick = ifBeginList.get(ifBeginList.size() - 1);
                ifBeginBrick.setIfElseBrick((IfLogicElseBrick) currentBrick);
                ((IfLogicElseBrick) currentBrick).setIfBeginBrick(ifBeginBrick);
            } else if (currentBrick instanceof IfThenLogicEndBrick) {
                if (ifThenBeginList.isEmpty()) {
                    Log.e(TAG, "Removing IfThenLogicEndBrick without reference to an IfBeginBrick");
                    bricksWithInvalidReferences.add(currentBrick);
                    continue;
                }
                IfThenLogicBeginBrick ifBeginBrick = ifThenBeginList.get(ifThenBeginList.size() - 1);
                ifBeginBrick.setIfThenEndBrick((IfThenLogicEndBrick) currentBrick);
                ((IfThenLogicEndBrick) currentBrick).setIfThenBeginBrick(ifBeginBrick);
                ifThenBeginList.remove(ifBeginBrick);
            } else if (currentBrick instanceof IfLogicEndBrick) {
                if (ifBeginList.isEmpty()) {
                    Log.e(TAG, "Removing IfLogicEndBrick without reference to an IfBeginBrick");
                    bricksWithInvalidReferences.add(currentBrick);
                    continue;
                }
                IfElseLogicBeginBrick ifBeginBrick = ifBeginList.get(ifBeginList.size() - 1);
                IfLogicElseBrick elseBrick = ifBeginBrick.getIfElseBrick();
                ifBeginBrick.setIfEndBrick((IfLogicEndBrick) currentBrick);
                elseBrick.setIfEndBrick((IfLogicEndBrick) currentBrick);
                ((IfLogicEndBrick) currentBrick).setIfBeginBrick(ifBeginBrick);
                ((IfLogicEndBrick) currentBrick).setIfElseBrick(elseBrick);
                ifBeginList.remove(ifBeginBrick);
            }
        }

        bricksWithInvalidReferences.addAll(ifBeginList);
        bricksWithInvalidReferences.addAll(ifThenBeginList);
        bricksWithInvalidReferences.addAll(loopBeginList);

        for (Brick brick : bricksWithInvalidReferences) {
            script.removeBrick(brick);
        }
    }

//	private void updateCollisionScriptsSpriteReference(Project project) {
//		for (Scene scene : project.getSceneList()) {
//			for (Sprite sprite : scene.getSpriteList()) {
//				for (Script script : sprite.getScriptList()) {
//					if (script instanceof CollisionScript) {
//						CollisionScript collisionScript = (CollisionScript) script;
//						collisionScript.updateSpriteToCollideWith(scene);
//					}
//				}
//			}
//		}
//	}

    public boolean isCurrentProjectLandscapeMode() {
        return true;
    }

    public void setCurrentSpriteEntity(Entity entity) {
        Sprite sprite = currentlyEditedScene.getSprite(entity.getName());
        if (sprite == null) {
            sprite = new Sprite(entity);
            currentlyEditedScene.addSprite(sprite);
        }
        currentSprite = sprite;
    }

    private class SaveProjectAsynchronousTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            //todo fix me?
            //XstreamSerializer.getInstance().saveProject(project);
            return null;
        }
    }
}
