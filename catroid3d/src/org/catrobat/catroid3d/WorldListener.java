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
package org.catrobat.catroid3d;

import org.catrobat.catroid3d.content.Object;
import org.catrobat.catroid3d.io.StorageHandler;
import org.catrobat.catroid3d.physics.World;
import org.catrobat.catroid3d.ui.screen.MainMenuScreen;
import org.catrobat.catroid3d.ui.screen.ProjectBuildScreen;
import org.catrobat.catroid3d.utils.CustomRenderableSorter;
import org.catrobat.catroid3d.utils.Log;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
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

public class WorldListener implements ApplicationListener {

	public PerspectiveCamera movingCamera, collisionCamera;
	private ModelBatch modelBatch;
	private RenderContext renderContext;
	private Array<ModelInstance> instances = new Array<ModelInstance>();
	private boolean loading;
	private ModelBuilder modelBuilder = new ModelBuilder();
	private boolean showMainMenu = true;
	private MainMenuScreen mainMenuScreen;
	private ProjectBuildScreen projectBuildScreen;
	private World world;
	private Environment environment;

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

		movingCamera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		movingCamera.position.set(700f, 500f, 700f);

		movingCamera.lookAt(0, 0, 0);
		movingCamera.near = 0.0000001f;
		movingCamera.far = 1000;
		movingCamera.update();

		collisionCamera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		collisionCamera.position.set(400f, 400f, 550f);
		collisionCamera.lookAt(0, 0, 0);
		//		collisionCamera.near = 0.0001f;
		collisionCamera.far = 1300;
		collisionCamera.update();

		mainMenuScreen = new MainMenuScreen(this);
		mainMenuScreen.show();
		loading = true;
	}

	private void doneLoading() {
		ProjectManager.getInstance().loadProject(null);
		for (Object object : ProjectManager.getInstance().getCurrentProject().getObjectList()) {
			world.addEntity(object.createEntityObject());
		}
		disposeMainMenu();
		projectBuildScreen = new ProjectBuildScreen();
		projectBuildScreen.show();
		loading = false;
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

	@Override
	public void pause() {
		// TODO Auto-generated method stub

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
			movingCamera.update();
			collisionCamera.position.set(movingCamera.position);
			collisionCamera.direction.set(movingCamera.direction);
			collisionCamera.up.set(movingCamera.up);
			collisionCamera.update();

			for (Object object : ProjectManager.getInstance().getCurrentProject().getObjectList()) {
				object.render();
			}

			world.update();

			renderContext.begin();
			modelBatch.begin(movingCamera);
			world.render(modelBatch, environment);
			modelBatch.end();
			renderContext.end();

			projectBuildScreen.render(Gdx.graphics.getDeltaTime());
		}
	}

	public void addObjectToWorld(Object object) {
		world.addEntity(object.createEntityObject());
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
		// TODO Auto-generated method stub

	}

	public ModelBuilder getModelBuilder() {
		return modelBuilder;
	}

	public World getWorld() {
		return world;
	}

	public ProjectBuildScreen getProjectBuildScreen() {
		return projectBuildScreen;
	}

}
