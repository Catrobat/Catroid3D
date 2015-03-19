package org.catrobat.catroid3d.test.cucumber;

import org.catrobat.catroid3d.test.cucumber.util.SoloLibgdxWrapper;
import org.catrobat.catroid3d.test.cucumber.util.UtilTest;

import android.test.AndroidTestCase;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;

import cucumber.api.java.en.When;

public class CameraSteps extends AndroidTestCase {
	
	private Vector3 startPosition;
	private Vector3 startDirection;
	
	@When("^I swipe my finger to the left$")
	public void I_swipe_my_finger_to_the_left() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		startPosition = new Vector3(solo.getCamera().position);
		startDirection = new Vector3(solo.getCamera().direction);
		solo.sleep(500);
		solo.swipeLeft(30);
		solo.sleep(1000);	
	}
	
	@When("^the camera should rotate to the right on the correct position$")
	public void the_camera_should_rotate_to_the_right() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		PerspectiveCamera camera = solo.getCamera();
		assertTrue(UtilTest.evaluateDistanceOfPosition(startPosition, camera.position, 1979f));
		assertTrue(UtilTest.evaluateDistanceOfDirection(startDirection, camera.direction, 1.8f));
	}
}
