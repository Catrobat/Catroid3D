package org.catrobat.catroid3d.test.cucumber;

import org.catrobat.catroid3d.WorldActivity;
import org.catrobat.catroid3d.test.cucumber.util.SoloLibgdxWrapper;

import android.test.AndroidTestCase;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

public class MainMenuSteps extends AndroidTestCase {
	@Given("^I am in the main menu$")
	public void I_am_in_the_main_menu() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		assertEquals("I am not in the main menu.", WorldActivity.class, solo.getCurrentActivity().getClass());
	}
	
	@When("^I press the (\\w+) button$")
	public void i_press_the_button(String button) {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		if (button.equalsIgnoreCase("continue")) {
		
		} else {
			fail("Button " + button + " not found");
		}
	}

}
