package org.catrobat.catroid3d.test.cucumber;

import org.catrobat.catroid3d.test.cucumber.util.SoloLibgdxWrapper;
import org.catrobat.catroid3d.test.cucumber.util.UtilTest;

import android.test.AndroidTestCase;

import com.badlogic.gdx.graphics.PerspectiveCamera;

import cucumber.api.java.en.When;

public class CameraSteps extends AndroidTestCase {
	
	@When("^I swipe my finger to the left and rotate the camera around (\\d+)$")
	public void I_swipe_my_finger_to_the_left(int degrees) {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		solo.sleep(500);
		solo.swipeLeftForRotation(degrees);
		solo.sleep(1000);	
	}
	
	@When("^the camera should rotate to the right and x should be \"([^\"]*)\" and y should be \"([^\"]*)\" and z should be \"([^\"]*)\"$")
	public void the_camera_should_rotate_to_the_right(String xShouldBe, String yShouldBe, String zShouldBe) {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		PerspectiveCamera camera = solo.getCamera();
		assertTrue(UtilTest.evaluateRotation(camera.position, xShouldBe, yShouldBe, zShouldBe));
	}
	
	@When("^I swipe my finger to the right and rotate the camera around (\\d+)$$")
	public void I_swipe_my_finger_to_the_right(int degrees) {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		solo.sleep(500);
		solo.swipeRightForRotation(degrees);
		solo.sleep(1000);	
	}
	
	@When("^the camera should rotate to the left and x should be \"([^\"]*)\" and y should be \"([^\"]*)\" and z should be \"([^\"]*)\"$")
	public void the_camera_should_rotate_to_the_left(String xShouldBe, String yShouldBe, String zShouldBe) {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		PerspectiveCamera camera = solo.getCamera();
		assertTrue(UtilTest.evaluateRotation(camera.position, xShouldBe, yShouldBe, zShouldBe));
	}
	
	@When("^I swipe my finger downwards and rotate the camera around (\\d+)$")
	public void I_swipe_my_finger_downwards(int degrees) {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		solo.sleep(500);
		solo.swipeDownwardsForRotation(degrees);
		solo.sleep(1000);	
	}
	
	@When("^the camera should rotate upwards and x should be \"([^\"]*)\" and y should be \"([^\"]*)\" and z should be \"([^\"]*)\"$")
	public void the_camera_should_rotate_upwards(String xShouldBe, String yShouldBe, String zShouldBe) {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		PerspectiveCamera camera = solo.getCamera();
		assertTrue(UtilTest.evaluateRotation(camera.position, xShouldBe, yShouldBe, zShouldBe));
	}
	
	@When("^I swipe my finger upwards and rotate the camera around (\\d+)$")
	public void I_swipe_my_finger_upwards(int degrees) {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		solo.sleep(500);
		solo.swipeUpwardsForRotation(degrees);
		solo.sleep(1000);	
	}
	
	@When("^the camera should rotate downwards and x should be \"([^\"]*)\" and y should be \"([^\"]*)\" and z should be \"([^\"]*)\"$")
	public void the_camera_should_rotate_downwards(String xShouldBe, String yShouldBe, String zShouldBe) {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		PerspectiveCamera camera = solo.getCamera();
		assertTrue(UtilTest.evaluateRotation(camera.position, xShouldBe, yShouldBe, zShouldBe));
	}
}
