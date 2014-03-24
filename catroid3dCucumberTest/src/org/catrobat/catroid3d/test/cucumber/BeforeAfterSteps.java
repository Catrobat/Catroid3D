package org.catrobat.catroid3d.test.cucumber;

import org.catrobat.catroid3d.WorldActivity;
import org.catrobat.catroid3d.test.cucumber.util.SoloLibgdxWrapper;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import cucumber.api.android.CucumberInstrumentation;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class BeforeAfterSteps extends ActivityInstrumentationTestCase2<WorldActivity> {
	private SoloLibgdxWrapper solo;

	public BeforeAfterSteps() {
		super(WorldActivity.class);
	}
	
	@Before
	public void before() {
		Log.d(CucumberInstrumentation.TAG, "before step");

		solo = new SoloLibgdxWrapper(getInstrumentation(), getActivity());
		solo.clickOnButton("asf");
		Cucumber.put(Cucumber.KEY_SOLO_WRAPPER, solo);
	}
	
	@After
	public void after() {
		Log.d(CucumberInstrumentation.TAG, "after step");
		solo.finishOpenedActivities();
		resetGlobalState();
	}
	
	private void resetGlobalState() {
		Cucumber.put(Cucumber.KEY_SOLO_WRAPPER, null);
	}

}
