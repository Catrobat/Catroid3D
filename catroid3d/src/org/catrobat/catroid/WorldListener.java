package org.catrobat.catroid;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.DefaultTextureBinder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;

import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.eventids.EventId;
import org.catrobat.catroid.content.models.Abstract3dObject;
import org.catrobat.catroid.io.StorageHandler;
import org.catrobat.catroid.physics.World;
import org.catrobat.catroid.ui.worldeditscreen.MainMenuScreen;
import org.catrobat.catroid.ui.worldeditscreen.ProjectBuildScreen;
import org.catrobat.catroid.utils.CustomRenderableSorter;
import org.catrobat.catroid.utils.Log;

import java.util.List;

public class WorldListener extends ApplicationAdapter {


    public boolean firstFrameDrawn;
    private Scene _scene;
    private List<Sprite> sprites;
    private ModelBatch modelBatch;
    //public boolean firstFrameDrawn = false;
    private RenderContext renderContext;
    private Array<ModelInstance> instances = new Array<ModelInstance>();
    private boolean loading;
    private ModelBuilder modelBuilder = new ModelBuilder();
    private boolean showMainMenu = true;
    private MainMenuScreen mainMenuScreen;
    private ProjectBuildScreen projectBuildScreen;
    private World world;
    private Environment environment;
    private boolean paused = false;
    private boolean finished = false;
    private boolean reloadProject = false;
    private CameraManager _cameraManager;

    public ModelBuilder getModelBuilder() {
        return modelBuilder;
    }

    public World getWorld() {
        return world;
    }

    public ProjectBuildScreen getProjectBuildScreen() {
        return projectBuildScreen;
    }

    @Override
    public void create() {
        StorageHandler.getInstance().createAssetManager();
        StorageHandler.getInstance().loadAllAssets();
        renderContext = new RenderContext(new DefaultTextureBinder(DefaultTextureBinder.ROUNDROBIN, 1));
        modelBatch = new ModelBatch(renderContext, new DefaultShaderProvider(), new CustomRenderableSorter());

        world = new World();

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.6f, 0.6f, 0.6f, 1f));
        environment.add(new DirectionalLight().set(0.9f, 0.9f, 0.9f, -1f, -0.8f, -0.2f));

        //init CameraManager
        _cameraManager = CameraManager.getInstance();

        mainMenuScreen = new MainMenuScreen(this);
        mainMenuScreen.show();
        loading = true;
    }

    private void doneLoading() {

        if(!ProjectManager.getInstance().initProject()){
            startScene("");
        }
        for (Abstract3dObject object : ProjectManager.getInstance().getCurrentProject().getObjectList()) {
            if (_scene != null) {
                Sprite sprite = _scene.getSprite(object.getName());
                if (sprite != null) {
                    world.addEntity(object.createEntityObject(sprite));
                } else {
                    world.addEntity(object.createEntityObject());
                }
            } else {
                world.addEntity(object.createEntityObject());
            }

        }
        disposeMainMenu();
        projectBuildScreen = new ProjectBuildScreen();
        projectBuildScreen.show();
        loading = false;
    }

    @Override
    public void render() {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        if (loading && StorageHandler.getInstance().updateAssetManager()) {
            ProjectManager.getInstance().initializeSkin();
            doneLoading();
        }
        if (showMainMenu || loading) {
            mainMenuScreen.render(Gdx.graphics.getDeltaTime());
        } else {
            if (_scene != null && _scene.firstStart && sprites != null) {
                for (Sprite sprite : sprites) {
                    sprite.initializeEventThreads(EventId.START);
                    sprite.initConditionScriptTriggers();
                }
                _scene.firstStart = false;
            }

            world.update(paused);

            _cameraManager.update();
            if (!finished) {
                renderContext.begin();
                modelBatch.begin(_cameraManager.getMovingCamera());
                //call render for animated 3d objects and other stuff
                for (Abstract3dObject object : ProjectManager.getInstance().getCurrentProject().getObjectList()) {
                    object.render();
                }
                world.render(modelBatch, environment);
                modelBatch.end();
                renderContext.end();
                projectBuildScreen.render(Gdx.graphics.getDeltaTime());
                firstFrameDrawn = true;
            }
        }
    }

    public void addObjectToWorld(Abstract3dObject object) {
        world.addEntity(object.createEntityObject());
        ProjectManager.getInstance().getProject().addObject(object);
    }

    public void disposeMainMenu() {
        showMainMenu = false;
        if (!loading) {
            mainMenuScreen.dispose();
        }
    }

    public boolean isProjectLoading() {
        return loading;
    }

    @Override
    public void resize(int arg0, int arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
//		for (Sprite sprite : sprites) {
//			sprite.ActorEntity.refreshTextures();
//		}
    }

    @Override
    public void pause() {
        if (finished) {
            return;
        }
    }

    void menuResume() {
        if (reloadProject) {
            return;
        }
        paused = false;
    }

    void menuPause() {
        if (finished || reloadProject) {
            return;
        }
        paused = true;
    }

    public void startScene(String sceneName) {
        _scene = ProjectManager.getInstance().getCurrentlyEditedScene();

        if (_scene == null) {
            return;
        }

        //stageBackupMap.put(scene.getName(), saveToBackup());
        //pause();

        //scene = newScene;
        ProjectManager.getInstance().setCurrentlyPlayingScene(_scene);

//		SoundManager.getInstance().clear();
//		stageBackupMap.remove(sceneName);

        //Gdx.input.setInputProcessor(stage);
        sprites = _scene.getSpriteList();
        _scene.firstStart = true;
        //create();
    }

    @Override
    public void dispose() {
        modelBatch.dispose();
        world.dispose();
        instances.clear();
        try {
            StorageHandler.getInstance().disposeAssets();
        } catch (GdxRuntimeException e) {
            Log.error(e.toString());
        }

    }
}
