package org.catrobat.catroid3d.test.cucumber;

import org.catrobat.catroid3d.common.Constants;
import org.catrobat.catroid3d.test.cucumber.util.SoloLibgdxWrapper;

import android.test.AndroidTestCase;
import cucumber.api.java.en.When;

public class ProjectBuildScreenSteps extends AndroidTestCase {

	@When("^I press the (\\w+)$")
	public void i_press_the_button(String button) {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		if (button.equalsIgnoreCase("movecamerabutton")) {
			solo.clickOnButton(Constants.UI_MOVE_CAMERA_BUTTON);
			solo.sleep(4000);
		}
		else if (button.equalsIgnoreCase("moveobjectbutton")) {
			solo.clickOnButton(Constants.UI_MOVE_OBJECT_BUTTON);
			solo.sleep(4000);
		} else {
			fail("Button " + button + " not found");
		}
	}
	
	@When("^the (\\w+) should be checked$")
	public void the_button_should_be_checked(String button) {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		if (button.equalsIgnoreCase("movecamerabutton")) {
			assertTrue(solo.isToggleOnOffButtonChecked(Constants.UI_MOVE_CAMERA_BUTTON));
		}
		else if (button.equalsIgnoreCase("moveobjectbutton")) {
			assertTrue(solo.isToggleOnOffButtonChecked(Constants.UI_MOVE_OBJECT_BUTTON));
		} else {
			fail("Button " + button + " not found");
		}
	}
	
}
