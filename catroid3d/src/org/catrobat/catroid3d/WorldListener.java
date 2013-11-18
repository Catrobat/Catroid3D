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

import org.catrobat.catroid3d.common.Constants.MODEL;
import org.catrobat.catroid3d.common.Constants.TEXTURE;
import org.catrobat.catroid3d.content.BoxAssetObject;
import org.catrobat.catroid3d.content.ComplexAssetObject;
import org.catrobat.catroid3d.content.Object;
import org.catrobat.catroid3d.content.Object.RENDER_TYPE;
import org.catrobat.catroid3d.content.Object.TEXTURE_TYPE;
import org.catrobat.catroid3d.content.SphereAssetObject;
import org.catrobat.catroid3d.io.StorageHandler;
import org.catrobat.catroid3d.physics.World;
import org.catrobat.catroid3d.ui.screen.CustomRenderableSorter;
import org.catrobat.catroid3d.ui.screen.MainMenuScreen;
import org.catrobat.catroid3d.ui.screen.ProjectBuildScreen;
import org.catrobat.catroid3d.utils.Log;
import org.catrobat.catroid3d.utils.Math;

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
	private Object groundObject;
	private Object skyObject;
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
		//		Object groundPlane = new BoxAssetObject("groundPlane", 0f, Math.createPositionMatrix(0f, -2f, 0f), 10000000f,
		//				1f, 10000000f, TEXTURE.TEXTURE_GRASS_01);
		//		groundPlane.setRenderType(RENDER_TYPE.GROUND);
		//		//		world.addEntity(groundPlane.createEntityObject(), (short) 2, (short) 1);
		//		world.addEntity(groundPlane.createEntityObject());
		groundObject = new BoxAssetObject("Ground", 0f, Math.createPositionMatrix(0f, -1f, 0f), 1000f, 1f, 1000f,
				TEXTURE.TEXTURE_GRASS_01);
		groundObject.setRenderType(RENDER_TYPE.GROUND);
		world.addEntity(groundObject.createEntityObject());

		for (int i = 0; i < 200; i++) {
			Object grass01 = new ComplexAssetObject("Gras01" + i, 0f, Math.createRandomPosition(-500, 500),
					MODEL.MODEL_GRASS_01);
			grass01.setHasRigidBody(false);
			world.addEntity(grass01.createEntityObject());
			Object grass02 = new ComplexAssetObject("Gras02" + i, 0f, Math.createRandomPosition(-500, 500),
					MODEL.MODEL_GRASS_02);
			grass02.setHasRigidBody(false);
			world.addEntity(grass02.createEntityObject());
		}

		for (int i = 0; i < 10; i++) {
			Object plant01 = new ComplexAssetObject("Plant01" + i, 0f, Math.createRandomPosition(-400, 400),
					MODEL.MODEL_TROPICAL_PLANT_01);
			plant01.setHasRigidBody(false);
			world.addEntity(plant01.createEntityObject());
			Object plant02 = new ComplexAssetObject("Plant02" + i, 0f, Math.createRandomPosition(-400, 400),
					MODEL.MODEL_TROPICAL_PLANT_02);
			plant02.setHasRigidBody(false);
			world.addEntity(plant02.createEntityObject());
		}

		Object barrel01 = new ComplexAssetObject("Barrel01", 10f, Math.createPositionMatrix(0f, 0f, -100f),
				MODEL.MODEL_BIG_WOOD_BARREL);
		barrel01.setTextureType(TEXTURE_TYPE.NONE);
		world.addEntity(barrel01.createEntityObject());

		Object barrel02 = new ComplexAssetObject("Barrel02", 10f, Math.createPositionMatrix(0f, -30f, -100f),
				MODEL.MODEL_BIG_WOOD_BARREL);
		barrel02.setTextureType(TEXTURE_TYPE.NONE);
		world.addEntity(barrel02.createEntityObject());

		Object tree01 = new ComplexAssetObject("Tree01", 0f, Math.createPositionMatrix(50f, 0f, -60f),
				MODEL.MODEL_PALM_TREE_01);
		world.addEntity(tree01.createEntityObject());

		skyObject = new SphereAssetObject("Sky", 0f, Math.createPositionMatrix(0f, 0f, 0f), 10000f, 10000f, 10000f,
				TEXTURE.TEXTURE_SKY_01, 20, 20);
		skyObject.setHasRigidBody(false);
		skyObject.setRenderType(RENDER_TYPE.SKYLINE);
		world.addEntity(skyObject.createEntityObject());

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

			world.update();

			renderContext.begin();
			modelBatch.begin(movingCamera);
			world.render(modelBatch, environment);
			modelBatch.end();
			renderContext.end();

			projectBuildScreen.render(Gdx.graphics.getDeltaTime());
		}
	}

	public void disposeMainMenu() {
		showMainMenu = false;
		mainMenuScreen.dispose();
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
