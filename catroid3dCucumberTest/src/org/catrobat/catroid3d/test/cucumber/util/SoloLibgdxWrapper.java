package org.catrobat.catroid3d.test.cucumber.util;

import java.lang.reflect.Field;

import org.catrobat.catroid3d.ProjectManager;
import org.catrobat.catroid3d.WorldListener;
import org.catrobat.catroid3d.ui.element.ToggleOnOffButton;
import org.catrobat.catroid3d.ui.screen.BaseScreen;
import org.catrobat.catroid3d.ui.screen.MainMenuScreen;
import org.catrobat.catroid3d.ui.screen.ProjectBuildScreen;

import android.app.Activity;
import android.app.Instrumentation;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
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
	
	
	public BaseScreen getActiveScreen() throws IllegalArgumentException, NoSuchFieldException, IllegalAccessException {	
		boolean showMainMenu = getFieldFromObject(worldListener, "showMainMenu", Boolean.class);
		boolean loading = getFieldFromObject(worldListener, "loading", Boolean.class);
		if(showMainMenu || loading) {
			return getFieldFromObject(worldListener, "mainMenuScreen", MainMenuScreen.class);
		}
		else {
			return getFieldFromObject(worldListener, "projectBuildScreen", ProjectBuildScreen.class);
		}
	}
	
	public PerspectiveCamera getCamera() {
		return worldListener.movingCamera;
	}
	
	public void swipeLeft(int stepCount) {
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();
//		float startPointX = width*3/4f;
		float startPointX = width*2/4f;
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
	
	private Vector2 libgdxLocalCoordinatesToSoloCoordinates(Actor actor) {
		Vector2 coordinates = new Vector2(0, 0);
		actor.localToStageCoordinates(coordinates);
		actor.getStage().stageToScreenCoordinates(coordinates);
		coordinates.x += actor.getWidth()/2.0f;
		coordinates.y -= actor.getHeight()/2.0f;
		return coordinates;
		
	}
	
	private <T> T getFieldFromObject(Object object, String fieldName, Class<T> type) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Field field = object.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		return type.cast(field.get(object));	
	}
	
	
	
}
