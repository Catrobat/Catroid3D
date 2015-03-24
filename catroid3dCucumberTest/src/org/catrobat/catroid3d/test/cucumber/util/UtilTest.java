package org.catrobat.catroid3d.test.cucumber.util;

import com.badlogic.gdx.math.Vector3;

public class UtilTest {
	
	public static boolean evaluateDistanceOfPosition(Vector3 startPosition, Vector3 endPosition, float distancePosition, float threshold)
	{
		float currentDistancePostition = startPosition.dst(endPosition);
		if((currentDistancePostition-threshold) <= distancePosition &&  distancePosition <= (currentDistancePostition+threshold))
		{
			return true;
		}
		return false;
	}
	
	public static boolean evaluateDistanceOfDirection(Vector3 startDirection, Vector3 endDirection, float distanceDirection, float threshold)
	{
		float currentDistanceDirection = startDirection.dst(endDirection);
		if((currentDistanceDirection-threshold) <= distanceDirection &&  distanceDirection <= (currentDistanceDirection+threshold))
		{
			return true;
		}
		return false;
	}
	
	public static boolean evaluateDistanceOfPosition(Vector3 startPosition, Vector3 endPosition, float distancePosition)
	{
		return evaluateDistanceOfPosition(startPosition, endPosition, distancePosition, 1f);
	}
	
	public static boolean evaluateDistanceOfDirection(Vector3 startDirection, Vector3 endDirection, float distanceDirection)
	{
		return evaluateDistanceOfDirection(startDirection, endDirection, distanceDirection, 1f);
	}

}
