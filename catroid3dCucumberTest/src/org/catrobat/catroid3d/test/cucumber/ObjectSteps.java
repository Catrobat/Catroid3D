package org.catrobat.catroid3d.test.cucumber;

import org.catrobat.catroid3d.test.cucumber.util.SoloLibgdxWrapper;

import android.test.AndroidTestCase;
import cucumber.api.java.en.When;

public class ObjectSteps extends AndroidTestCase {

	@When("^I click on the \"([^\"]*)\" image$")
	public void I_click_on_the_object_image(String objectImage) {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		
	}
}
