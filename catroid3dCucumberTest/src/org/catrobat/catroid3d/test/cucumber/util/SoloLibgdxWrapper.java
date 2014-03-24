package org.catrobat.catroid3d.test.cucumber.util;

import java.lang.reflect.Field;

import org.catrobat.catroid3d.ProjectManager;
import org.catrobat.catroid3d.WorldListener;
import org.catrobat.catroid3d.ui.screen.BaseScreen;
import org.catrobat.catroid3d.ui.screen.MainMenuScreen;
import org.catrobat.catroid3d.ui.screen.ProjectBuildScreen;

import android.app.Activity;
import android.app.Instrumentation;
import android.util.Log;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.jayway.android.robotium.solo.Solo;

import cucumber.api.android.CucumberInstrumentation;

public class SoloLibgdxWrapper {

	private Solo solo;
	private WorldListener worldListener;
	
	public SoloLibgdxWrapper(Instrumentation instrumentation, Activity activity) {
		solo = new Solo(instrumentation, activity);
		worldListener = ProjectManager.getInstance().getWorldActivity().getWorldListener();
	}
	
	public void clickOnButton(String buttonId) {
		try {	
			Button buttonToClick = getActiveScreen().getButton(buttonId);
			if(buttonToClick == null) {
				Log.e(CucumberInstrumentation.TAG, "Button (" + buttonId + ") was not found in current screen!");
				return;
			}
			float xPosition = buttonToClick.getX();
			float yPosition = buttonToClick.getY();
			solo.clickOnScreen(xPosition, yPosition);
		} catch(Exception e) {
			Log.e(CucumberInstrumentation.TAG, e.toString());
		}
	}
	
	public void finishOpenedActivities() {
		solo.finishOpenedActivities();
	}
	
	public Activity getCurrentActivity() {
		return solo.getCurrentActivity();
	}
	
	
	private BaseScreen getActiveScreen() throws IllegalArgumentException, NoSuchFieldException, IllegalAccessException {	
		boolean showMainMenu = getFieldFromObject(worldListener, "showMainMenu", Boolean.class);
		boolean loading = getFieldFromObject(worldListener, "loading", Boolean.class);
		if(showMainMenu || loading) {
			return getFieldFromObject(worldListener, "mainMenuScreen", MainMenuScreen.class);
		}
		else {
			return getFieldFromObject(worldListener, "projectBuildScreen", ProjectBuildScreen.class);
		}
		
		
		
		
	}
	
	private <T> T getFieldFromObject(Object object, String fieldName, Class<T> type) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Field field = object.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		return type.cast(field.get(object));	
	}
	
	
	
}
