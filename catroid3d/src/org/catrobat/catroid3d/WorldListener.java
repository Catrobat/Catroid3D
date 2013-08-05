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

import android.util.Log;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.lights.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.lights.Lights;
import com.badlogic.gdx.graphics.g3d.materials.IntAttribute;
import com.badlogic.gdx.graphics.g3d.materials.Material;
import com.badlogic.gdx.graphics.g3d.materials.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.TextureDescriptor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class WorldListener implements ApplicationListener {

	private PerspectiveCamera camera;
	private ModelBatch modelBatch;
	private Lights lights;
	private CameraInputController cameraController;
	private AssetManager assets;
	private Array<ModelInstance> instances = new Array<ModelInstance>();
	private boolean loading;
	private ModelBuilder modelBuilder = new ModelBuilder();
	private boolean showMainMenu = true;
	private MainMenuScreen mainMenuScreen;

	@Override
	public void create() {
		modelBatch = new ModelBatch();

		lights = new Lights();
		lights.ambientLight.set(0.4f, 0.4f, 0.4f, 1f);
		lights.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

		camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(10f, 10f, 10f);
		camera.lookAt(0, 0, 0);
		camera.near = 0.1f;
		camera.far = 300f;
		camera.update();

		cameraController = new CameraInputController(camera);
		Gdx.input.setInputProcessor(cameraController);

		assets = new AssetManager();
		assets.load("data/Helicopter.g3db", Model.class);
		assets.load("data/ship.g3db", Model.class);
		assets.load("data/rock_texture.jpg", Texture.class);
		assets.load("data/cloud.jpg", Texture.class);
		mainMenuScreen = new MainMenuScreen(this);
		mainMenuScreen.show();
		mainMenuScreen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		loading = true;
	}

	private void doneLoading() {
		Model ship = assets.get("data/ship.g3db", Model.class);
		ModelInstance shipInstance = new ModelInstance(ship);
		shipInstance.transform.setToTranslation(-5f, 0f, 5f);
		instances.add(shipInstance);

		Model helicopter = assets.get("data/Helicopter.g3db", Model.class);
		ModelInstance helicopterInstance = new ModelInstance(helicopter);
		helicopterInstance.transform.setToTranslation(5f, 0f, -5f);
		instances.add(helicopterInstance);

		Model ground = modelBuilder.createBox(100f, 1f, 100f, new Material(), Usage.Position | Usage.Normal
				| Usage.TextureCoordinates);
		ModelInstance groundInstance = new ModelInstance(ground);
		groundInstance.transform.setToTranslation(-5f, -5f, 0f);
		Texture rock = assets.get("data/rock_texture.jpg", Texture.class);
		TextureDescriptor desc = new TextureDescriptor(rock, GL10.GL_INVALID_VALUE, GL10.GL_INVALID_VALUE,
				GL20.GL_REPEAT, GL20.GL_REPEAT);
		groundInstance.materials.first().set(new TextureAttribute(TextureAttribute.Diffuse, desc));
		instances.add(groundInstance);

		Model sky = modelBuilder.createSphere(500f, 500f, 500f, 20, 20, new Material(), Usage.Position
				| Usage.TextureCoordinates);
		ModelInstance skyInstance = new ModelInstance(sky);
		skyInstance.transform.setToTranslation(-10f, 0f, 10f);
		skyInstance.materials.first().set(TextureAttribute.createDiffuse(assets.get("data/cloud.jpg", Texture.class)));
		skyInstance.materials.first().set(new IntAttribute(IntAttribute.CullFace, 0));
		instances.add(skyInstance);
		loading = false;
	}

	@Override
	public void dispose() {
		modelBatch.dispose();
		instances.clear();
		try {
			assets.dispose();
		} catch (GdxRuntimeException e) {
			Log.e("Catroid3D", e.toString());
		}

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render() {
		if (loading && assets.update()) {
			doneLoading();
		}
		if (showMainMenu) {
			mainMenuScreen.render(Gdx.graphics.getDeltaTime());
		} else {
			cameraController.update();

			Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

			modelBatch.begin(camera);
			for (ModelInstance instance : instances) {
				modelBatch.render(instance, lights);
			}
			modelBatch.end();
		}
	}

	public void disposeMainMenu() {
		showMainMenu = false;
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

}
