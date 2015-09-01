package org.catrobat.catroid3d.test.cucumber.util;

import org.catrobat.catroid3d.ProjectManager;
import org.catrobat.catroid3d.WorldListener;
import org.catrobat.catroid3d.io.GestureHandler;
import org.catrobat.catroid3d.physics.CollisionDetector;
import org.catrobat.catroid3d.physics.Entity;
import org.catrobat.catroid3d.ui.ObjectHandler;
import org.catrobat.catroid3d.ui.element.ObjectDialogBox;
import org.catrobat.catroid3d.ui.element.ToggleOnOffButton;
import org.catrobat.catroid3d.ui.screen.BaseScreen;
import org.catrobat.catroid3d.ui.screen.MainMenuScreen;
import org.catrobat.catroid3d.ui.screen.ProjectBuildScreen;

import android.app.Activity;
import android.app.Instrumentation;
import android.graphics.PointF;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.robotium.solo.Solo;

import cucumber.api.android.CucumberInstrumentation;

public class SoloLibgdxWrapper extends Solo {

	private WorldListener worldListener;
	
	public SoloLibgdxWrapper(Instrumentation instrumentation, Activity activity) {
		super(instrumentation, activity);
		worldListener = ProjectManager.getInstance().getWorldActivity().getWorldListener();
	}
	
	public void clickOnButton(String buttonId) {
		try {
			Button buttonToClick = getActiveScreen().getButton(buttonId);
			if(buttonToClick == null) {
				Log.e(CucumberInstrumentation.TAG, "Button (" + buttonId + ") was not found in current screen!");
				return;
			}
			Vector2 stageCoordinates = libgdxLocalCoordinatesToSoloCoordinates(buttonToClick);
			this.clickOnScreen(stageCoordinates.x, stageCoordinates.y);
		} catch(Exception e) {
			Log.e(CucumberInstrumentation.TAG, e.toString());
		}
	}
	
	public boolean isEntityAtPosition(String entityName, Matrix4 position) {
		Vector3 entityPosition = getEntityPosition(entityName);
		Vector3 positionToCheck = new Vector3();
		position.getTranslation(positionToCheck);
		if(entityPosition.epsilonEquals(positionToCheck, 5.0f)) {
			return true;
		}
		return false;
	}
	
	public boolean isEntityVisible(String entityName) {
		Entity entity = worldListener.getWorld().getEntity(entityName);
		if(entity == null) {
			return false;
		}
		return true;
	}
	
	public Vector3 getEntityPosition(String entityName) {
		Entity entity = worldListener.getWorld().getEntity(entityName);
		Vector3 entityPosition = new Vector3();
		entity.modelInstance.transform.getTranslation(entityPosition);
		return entityPosition;
	}
	
	public boolean isEntityFallingDown(String entityName) {
		Entity entity = worldListener.getWorld().getEntity(entityName);
		Vector3 velocity = entity.body.getLinearVelocity();
		if(velocity.y < 0) {
			return true;
		}
		return false;
	}
	
	public boolean checkEntityCollision(String entityName, String entityNameToHit) {
		Entity entity = worldListener.getWorld().getEntity(entityName);
		Entity entityToHit = worldListener.getWorld().getEntity(entityNameToHit);
		return entityToHit.body.checkCollideWith(entity.body);
	}
	
	public boolean isToggleOnOffButtonChecked(String buttonId) {
		try {
			Button buttonToClick = getActiveScreen().getButton(buttonId);
			if(buttonToClick instanceof ToggleOnOffButton)
			{
				return buttonToClick.isChecked();
			}
		} catch(Exception e) {
			Log.e(CucumberInstrumentation.TAG, e.toString());
		}
		return false;
	}
	
	public boolean isButtonVisible(String buttonId) {
		try {
			Button buttonToClick = getActiveScreen().getButton(buttonId);
			return buttonToClick.isVisible();
		} catch(Exception e) {
			Log.e(CucumberInstrumentation.TAG, e.toString());
		}
		return false;
	}
	
	public boolean isChooseObjectSplitPaneVisible() {
		try {
			BaseScreen screen = getActiveScreen();
			if(screen instanceof ProjectBuildScreen)
			{
				ObjectHandler objectHandler = UtilTest.getFieldFromObject((ProjectBuildScreen)screen, "objectHandler", ObjectHandler.class);
				SplitPane chooseObjectSplitPane = UtilTest.getFieldFromObject(objectHandler, "chooseObjectSplitPane", SplitPane.class);
				return chooseObjectSplitPane.isVisible();
			}
		} catch(Exception e) {
			Log.e(CucumberInstrumentation.TAG, e.toString());
		}
		return false;
	}
	
	public boolean isObjectDialogBoxVisible() {
		try {
			BaseScreen screen = getActiveScreen();
			if(screen instanceof ProjectBuildScreen)
			{
				ObjectHandler objectHandler = UtilTest.getFieldFromObject((ProjectBuildScreen)screen, "objectHandler", ObjectHandler.class);
				ObjectDialogBox objectDialogBox = UtilTest.getFieldFromObject(objectHandler, "objectDialogBox", ObjectDialogBox.class);
				return objectDialogBox.isVisible();
			}
		} catch(Exception e) {
			Log.e(CucumberInstrumentation.TAG, e.toString());
		}
		return false;
	}
	
	public void clickLongOnEntity(String entityName) {
		clickLongOnEntityPostion(getEntityPosition(entityName));
	}
	
	public void clickLongOnEntityPostion(Vector3 entityPosition) {
		Vector2 screenCoords = entityWorldCoordsToScreenCoords(entityPosition);
		this.clickLongOnScreen(screenCoords.x, screenCoords.y);
	}
	
	public void dragEntityToPosition(String entityName, Vector3 newPosition) {
		dragEntityToPosition(getEntityPosition(entityName), newPosition);
	}
	
	public void dragEntityToPosition(Vector3 entityPosition, Vector3 newPosition) {
		Vector2 screenCoords = entityWorldCoordsToScreenCoords(entityPosition);
		Vector2 newPositionScreenCoords = entityWorldCoordsToScreenCoords(newPosition);
		this.drag(screenCoords.x, newPositionScreenCoords.x, screenCoords.y, newPositionScreenCoords.y, 20);
	}
	
	public void pinchToZoomIn() {
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();
		this.pinchToZoom(new PointF(width*2/6, height/2), new PointF(width*4/6, height/2), new PointF(width/6, height/2), new PointF(width*5/6, height/2));
	}
	
	public void pinchToZoomOut() {
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();
		this.pinchToZoom(new PointF(width/6, height/2), new PointF(width*5/6, height/2), new PointF(width*2/6, height/2), new PointF(width*4/6, height/2));
	}
	
	public BaseScreen getActiveScreen() throws IllegalArgumentException, NoSuchFieldException, IllegalAccessException {	
		boolean showMainMenu = UtilTest.getFieldFromObject(worldListener, "showMainMenu", Boolean.class);
		boolean loading = UtilTest.getFieldFromObject(worldListener, "loading", Boolean.class);
		if(showMainMenu || loading) {
			return UtilTest.getFieldFromObject(worldListener, "mainMenuScreen", MainMenuScreen.class);
		}
		else {
			return UtilTest.getFieldFromObject(worldListener, "projectBuildScreen", ProjectBuildScreen.class);
		}
	}
	
	public PerspectiveCamera getCamera() {
		return worldListener.movingCamera;
	}
	
	public void swipeLeftForRotation(int degrees) {
		int height = Gdx.graphics.getHeight();
		int width = Gdx.graphics.getWidth();
		float lengthToSwipe = degrees/360f * width;
		float startPointX = width/2 + lengthToSwipe/2;
		float endPointX = width/2 - lengthToSwipe/2;
		drag(startPointX, endPointX, height/2f, height/2f, 20);
	}
	
	public void swipeRightForRotation(int degrees) {
		int height = Gdx.graphics.getHeight();
		int width = Gdx.graphics.getWidth();
		float lengthToSwipe = degrees/360f * width;
		float startPointX = width/2 - lengthToSwipe/2;
		float endPointX = width/2 + lengthToSwipe/2;
		drag(startPointX, endPointX, height/2f, height/2f, 20);
	}
	
	public void swipeDownwardsForRotation(int degrees) {
		int height = Gdx.graphics.getHeight();
		int width = Gdx.graphics.getHeight();
		float lengthToSwipe = degrees/360f * height;
		float startPointY = height/2 - lengthToSwipe/2;
		float endPointY = height/2 + lengthToSwipe/2;
		drag(width/2f, width/2f, startPointY, endPointY,  20);
	}
	
	public void swipeUpwardsForRotation(int degrees) {
		int height = Gdx.graphics.getHeight();
		int width = Gdx.graphics.getHeight();
		float lengthToSwipe = degrees/360f * height;
		float startPointY = height/2 + lengthToSwipe/2;
		float endPointY = height/2 - lengthToSwipe/2;
		drag(width/2f, width/2f, startPointY, endPointY,  20);
	}
	
	public void swipeLeft(int stepCount) {
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();
		float startPointX = width*3/4f;
		float endPointX = width/4f;
		drag(startPointX,endPointX, height/2f, height/2f, stepCount);		
	}
	public void swipeRight(int stepCount) {
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();
		float startPointX = width/4f;
		float endPointX = width*3/4f;
		drag(startPointX,endPointX, height/2f, height/2f, stepCount);	
	}
	
	public void swipeUp(int stepCount) {
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();
		float startPointY = height*3/4f;
		float endPointY = height/4f;
		drag(width/2f, width/2f, startPointY, endPointY, stepCount);	
	}
	
	public void swipeDown(int stepCount) {
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();
		float startPointY = height/4f;
		float endPointY = height*3/4f;
		drag(width/2f, width/2f, startPointY, endPointY, stepCount);	
	}
	
	private Vector2 entityWorldCoordsToScreenCoords(Vector3 entityWorldCoords) {
		try {
			BaseScreen screen = getActiveScreen();
			if(screen instanceof ProjectBuildScreen)
			{
				GestureHandler gestureHandler = UtilTest.getFieldFromObject(screen, "gestureHandler", GestureHandler.class);
				CollisionDetector collisionDetector = UtilTest.getFieldFromObject(gestureHandler, "collisionDetector", CollisionDetector.class);
				Vector2 entityScreenCoords = new Vector2(collisionDetector.worldGroundCoordsToScreenCoords(entityWorldCoords));
				entityScreenCoords.y = Gdx.graphics.getHeight() - entityScreenCoords.y;
				return entityScreenCoords;			
			}
		} catch(Exception e) {
			Log.e(CucumberInstrumentation.TAG, e.toString());
		}
		return null;
	}
	
	private Vector2 libgdxLocalCoordinatesToSoloCoordinates(Actor actor) {
		Vector2 coordinates = new Vector2(0, 0);
		actor.localToStageCoordinates(coordinates);
		actor.getStage().stageToScreenCoordinates(coordinates);
		coordinates.x += actor.getWidth()/2.0f;
		coordinates.y -= actor.getHeight()/2.0f;
		return coordinates;
		
	}
	
	
	
}
