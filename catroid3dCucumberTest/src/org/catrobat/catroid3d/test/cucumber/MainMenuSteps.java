package org.catrobat.catroid3d.test.cucumber;

import org.catrobat.catroid3d.WorldActivity;
import org.catrobat.catroid3d.test.cucumber.util.SoloLibgdxWrapper;
import org.catrobat.catroid3d.ui.screen.MainMenuScreen;
import org.catrobat.catroid3d.ui.screen.ProjectBuildScreen;

import android.test.AndroidTestCase;
import cucumber.api.java.en.Given;

public class MainMenuSteps extends AndroidTestCase {
	
	@Given("^I am in the main menu$")
	public void I_am_in_the_main_menu() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		solo.waitForActivity(WorldActivity.class);
		solo.sleep(1000);
		try {
			assertTrue("I am not in the main menu.", solo.getActiveScreen() instanceof MainMenuScreen);
		} catch (Exception e) {
			fail("No active screen!");
		}
		
	}
	
	@Given("^I press on the splash screen$")
	public void I_press_on_the_splash_screen() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		solo.clickOnScreen(500, 500);
		solo.sleep(1000);
	}
	
	@Given("^I should see the world$")
	public void I_should_see_the_world() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		try {
			assertTrue("I am not in the main menu.", solo.getActiveScreen() instanceof ProjectBuildScreen);
		} catch (Exception e) {
			fail("No active screen!");
		}
	}
	
	

}
