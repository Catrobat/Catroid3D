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
		solo.swipeLeft(20);
		solo.sleep(1000);	
	}
	
	@When("^the camera should rotate to the right on the correct position$")
	public void the_camera_should_rotate_to_the_right() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		PerspectiveCamera camera = solo.getCamera();
		//TODO
//		double angle = Math.acos(Vector3.dot(startDirection.nor().x, camera.direction.nor().x, startDirection.nor().y, camera.direction.nor().y,startDirection.nor().z, camera.direction.nor().z));
//		double angleDegrees = Math.toDegrees(angle);
//		double x = camera.position.x - startPosition.x;
//		double y = camera.position.y - startPosition.y;
//		double z = camera.position.z - startPosition.z;
//		double alt = Math.toDegrees(Math.atan2(y, Math.sqrt(x*x + z*z)));
//		double az = Math.toDegrees(Math.atan2(-x, -z));
//		Vector3 center = new Vector3(startPosition);
//		center = center.sub(camera.position);
//		Vector3 right = new Vector3(camera.direction);
//		right = right.crs(0, 1, 0).nor();
//		Vector3 up = new Vector3(right);
//		up = up.crs(camera.direction);
//		center.set(center.dot(right), center.dot(up), center.dot(camera.direction));
		
		double azimuth = UtilTest.calculateAzimuthAngle(camera.position, camera.direction, startPosition, true);
		double altitude = UtilTest.calculateAltitudeAngle(camera.position, camera.direction, startPosition, true);
		
		assertTrue(UtilTest.evaluateDistanceOfPosition(startPosition, camera.position, 1979f));
		assertTrue(UtilTest.evaluateDistanceOfDirection(startDirection, camera.direction, 1.8f));
	}
	
	@When("^I swipe my finger to the right$")
	public void I_swipe_my_finger_to_the_right() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		startPosition = new Vector3(solo.getCamera().position);
		startDirection = new Vector3(solo.getCamera().direction);
		solo.sleep(500);
		solo.swipeRight(20);
		solo.sleep(1000);	
	}
	
	@When("^the camera should rotate to the left on the correct position$")
	public void the_camera_should_rotate_to_the_left() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		PerspectiveCamera camera = solo.getCamera();
		assertTrue(UtilTest.evaluateDistanceOfPosition(startPosition, camera.position, 1979f));
		assertTrue(UtilTest.evaluateDistanceOfDirection(startDirection, camera.direction, 1.8f));
	}
}
