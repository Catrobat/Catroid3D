package org.catrobat.catroid3d.test.cucumber.util;

import java.lang.reflect.Field;

import com.badlogic.gdx.math.Vector3;

public class UtilTest {
	
	public static boolean evaluateRotation(Vector3 position, String x, String y, String z) {
		if(isCoordinateGreaterThanNull(x)) {
			if(position.x < 0) return false;
		}
		else {
			if(position.x > 0) return false;
		}
		if(isCoordinateGreaterThanNull(y)) {
			if(position.y < 0) return false;
		}
		else {
			if(position.y > 0) return false;
		}
		if(isCoordinateGreaterThanNull(z)) {
			if(position.z < 0) return false;
		}
		else {
			if(position.z > 0) return false;
		}
		return true;
	}
	
	private static boolean isCoordinateGreaterThanNull(String cucumberCoordinate) {
		if(cucumberCoordinate.contentEquals(">0")) {
			return true;
		}
		return false;
	}
	
	public static boolean evaluateDistanceOfPosition(Vector3 startPosition, Vector3 endPosition, float distancePosition, float threshold) {
		float currentDistancePostition = startPosition.dst(endPosition);
		if((currentDistancePostition-threshold) <= distancePosition &&  distancePosition <= (currentDistancePostition+threshold)){
			return true;
		}
		return false;
	}
	
	public static boolean evaluateDistanceOfDirection(Vector3 startDirection, Vector3 endDirection, float distanceDirection, float threshold) {
		float currentDistanceDirection = startDirection.dst(endDirection);
		if((currentDistanceDirection-threshold) <= distanceDirection &&  distanceDirection <= (currentDistanceDirection+threshold)) {
			return true;
		}
		return false;
	}
	
	public static boolean evaluateDistanceOfPosition(Vector3 startPosition, Vector3 endPosition, float distancePosition) {
		return evaluateDistanceOfPosition(startPosition, endPosition, distancePosition, 1f);
	}
	
	public static boolean evaluateDistanceOfDirection(Vector3 startDirection, Vector3 endDirection, float distanceDirection) {
		return evaluateDistanceOfDirection(startDirection, endDirection, distanceDirection, 1f);
	}
	
	public static double calculateAzimuthAngle(Vector3 fromPosition, Vector3 fromDirection, Vector3 toPosition, boolean resultAsDegree) {
		Vector3 centerVector = transformCoordinateSpaceToCenter(fromPosition, fromDirection, toPosition);
		if(resultAsDegree) {
			return Math.toDegrees(Math.atan2(centerVector.x, centerVector.z));
		}
		return Math.atan2(centerVector.x, centerVector.z);
	}
	
	public static double calculateAltitudeAngle(Vector3 fromPosition, Vector3 fromDirection, Vector3 toPosition, boolean resultAsDegree) {
		Vector3 centerVector = transformCoordinateSpaceToCenter(fromPosition, fromDirection, toPosition);
		Vector3 projectionVector = new Vector3(centerVector.x, 0, centerVector.z);
		if(resultAsDegree) {
			return Math.toDegrees(Math.acos(centerVector.nor().dot(projectionVector.nor())));
		}
		return Math.acos(centerVector.nor().dot(projectionVector.nor()));
	}
	
	public static <T> T getFieldFromObject(Object object, String fieldName, Class<T> type) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Field field = object.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		return type.cast(field.get(object));	
	}
	
	private static Vector3 transformCoordinateSpaceToCenter(Vector3 fromPosition, Vector3 fromDirection, Vector3 toPosition) {
		Vector3 center = new Vector3(toPosition);
		center = center.sub(fromPosition);
		Vector3 right = new Vector3(fromDirection);
		right = right.crs(0, 1, 0).nor();
		Vector3 up = new Vector3(right);
		up = up.crs(fromDirection);
		center.set(center.dot(right), center.dot(up), center.dot(fromDirection));
		return center;
	}

}
