package org.catrobat.catroid3d.test.cucumber;

import org.catrobat.catroid3d.test.cucumber.util.SoloLibgdxWrapper;

import android.test.AndroidTestCase;
import cucumber.api.java.en.When;

public class CameraSteps extends AndroidTestCase {
	
	@When("^I swipe my finger to the left$")
	public void I_swipe_my_finger_to_the_left() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		solo.drag(100, 1000, 10, 10, 20);
		solo.sleep(3000);
	}

}
