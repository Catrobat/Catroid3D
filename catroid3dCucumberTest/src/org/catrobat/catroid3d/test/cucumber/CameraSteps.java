package org.catrobat.catroid3d.test.cucumber;

import org.catrobat.catroid3d.common.Constants;
import org.catrobat.catroid3d.test.cucumber.util.SoloLibgdxWrapper;
import org.catrobat.catroid3d.test.cucumber.util.UtilTest;

import android.test.AndroidTestCase;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CameraSteps extends AndroidTestCase {
	
	Vector3 startPosition = new Vector3();
	
	@When("^I swipe my finger to the left and rotate the camera around (\\d+)$")
	public void I_swipe_my_finger_to_the_left(int degrees) {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		solo.swipeLeftForRotation(degrees);
		solo.sleep(1000);	
	}
	
	@Then("^the camera should rotate to the right and x should be \"([^\"]*)\" and y should be \"([^\"]*)\" and z should be \"([^\"]*)\"$")
	public void the_camera_should_rotate_to_the_right(String xShouldBe, String yShouldBe, String zShouldBe) {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		PerspectiveCamera camera = solo.getCamera();
		assertTrue(UtilTest.evaluateRotation(camera.position, xShouldBe, yShouldBe, zShouldBe));
	}
	
	@When("^I swipe my finger to the right and rotate the camera around (\\d+)$$")
	public void I_swipe_my_finger_to_the_right(int degrees) {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		solo.swipeRightForRotation(degrees);
		solo.sleep(1000);	
	}
	
	@Then("^the camera should rotate to the left and x should be \"([^\"]*)\" and y should be \"([^\"]*)\" and z should be \"([^\"]*)\"$")
	public void the_camera_should_rotate_to_the_left(String xShouldBe, String yShouldBe, String zShouldBe) {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		PerspectiveCamera camera = solo.getCamera();
		assertTrue(UtilTest.evaluateRotation(camera.position, xShouldBe, yShouldBe, zShouldBe));
	}
	
	@When("^I swipe my finger downwards and rotate the camera around (\\d+)$")
	public void I_swipe_my_finger_downwards(int degrees) {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		solo.swipeDownwardsForRotation(degrees);
		solo.sleep(1000);	
	}
	
	@Then("^the camera should rotate upwards and x should be \"([^\"]*)\" and y should be \"([^\"]*)\" and z should be \"([^\"]*)\"$")
	public void the_camera_should_rotate_upwards(String xShouldBe, String yShouldBe, String zShouldBe) {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		PerspectiveCamera camera = solo.getCamera();
		assertTrue(UtilTest.evaluateRotation(camera.position, xShouldBe, yShouldBe, zShouldBe));
	}
	
	@When("^I swipe my finger upwards and rotate the camera around (\\d+)$")
	public void I_swipe_my_finger_upwards(int degrees) {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		solo.swipeUpwardsForRotation(degrees);
		solo.sleep(1000);	
	}
	
	@Then("^the camera should rotate downwards and x should be \"([^\"]*)\" and y should be \"([^\"]*)\" and z should be \"([^\"]*)\"$")
	public void the_camera_should_rotate_downwards(String xShouldBe, String yShouldBe, String zShouldBe) {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		PerspectiveCamera camera = solo.getCamera();
		assertTrue(UtilTest.evaluateRotation(camera.position, xShouldBe, yShouldBe, zShouldBe));
	}
	
	@When("^I press the camera move button$")
	public void I_press_the_camera_move_button() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		solo.sleep(500);
		solo.clickOnButton(Constants.UI_MOVE_CAMERA_BUTTON);
		solo.sleep(1000);
	}
	
	@Then("^I should be in the camera moving mode$")
	public void I_should_be_in_the_camera_moving_mode() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		assertTrue(solo.isToggleOnOffButtonChecked(Constants.UI_MOVE_CAMERA_BUTTON));
	}
	
	@When("^I swipe my finger to the left$")
	public void I_swipe_my_finger_to_the_left() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		PerspectiveCamera camera = solo.getCamera();
		startPosition.set(camera.position.cpy());
		solo.swipeLeft(20);
		solo.sleep(1000);
	}
	
	@Then("^the camera should move to the right$")
	public void the_camera_should_move_to_the_right() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		PerspectiveCamera camera = solo.getCamera();
		assertTrue((camera.position.x > startPosition.x ? true : false));
	}
	
	@When("^I swipe my finger to the right$")
	public void I_swipe_my_finger_to_the_right() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		PerspectiveCamera camera = solo.getCamera();
		startPosition.set(camera.position.cpy());
		solo.swipeRight(20);
		solo.sleep(1000);
	}
	
	@Then("^the camera should move to the left$")
	public void the_camera_should_move_to_the_left() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		PerspectiveCamera camera = solo.getCamera();
		assertTrue((camera.position.x < startPosition.x ? true : false));
	}
	
	@When("^I swipe my finger downwards$")
	public void I_swipe_my_finger_downwards() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		PerspectiveCamera camera = solo.getCamera();
		startPosition.set(camera.position.cpy());
		solo.swipeDown(20);
		solo.sleep(1000);
	}
	
	@Then("^the camera should move upwards$")
	public void the_camera_should_move_upwards() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		PerspectiveCamera camera = solo.getCamera();
		assertTrue((camera.position.y > startPosition.y ? true : false));
	}
	
	@When("^I swipe my finger upwards$")
	public void I_swipe_my_finger_upwards() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		PerspectiveCamera camera = solo.getCamera();
		startPosition.set(camera.position.cpy());
		solo.swipeUp(20);
		solo.sleep(1000);
	}
	
	@Then("^the camera should move downwards$")
	public void the_camera_should_move_downwards() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		PerspectiveCamera camera = solo.getCamera();
		assertTrue((camera.position.y < startPosition.y ? true : false));
	}
	
	@When("^I zoom in with my fingers$")
	public void I_zoom_in_with_my_fingers() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		PerspectiveCamera camera = solo.getCamera();
		startPosition.set(camera.position.cpy());
		solo.pinchToZoomIn();
		solo.sleep(1000);
	}
	
	@Then("^the camera should zoom in$")
	public void the_camera_should_zoom_in() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		PerspectiveCamera camera = solo.getCamera();
		assertTrue((camera.position.z < startPosition.z ? true : false));
	}
	
	@When("^I zoom out with my fingers$")
	public void I_zoom_out_with_my_fingers() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		PerspectiveCamera camera = solo.getCamera();
		startPosition.set(camera.position.cpy());
		solo.pinchToZoomOut();
		solo.sleep(1000);
	}
	
	@Then("^the camera should zoom out$")
	public void the_camera_should_zoom_out() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		PerspectiveCamera camera = solo.getCamera();
		assertTrue((camera.position.z > startPosition.z ? true : false));
	}
	
}
