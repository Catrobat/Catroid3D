package org.catrobat.catroid3d.test.cucumber;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import cucumber.api.CucumberOptions;

@CucumberOptions(features = {"features"}, 
					format = {"pretty", "html:/sdcard/cucumber/report", 
										"json:target_json/cucumber.json",
										"junit:taget_junit/cucumber.xml"})
public final class Cucumber {
	public static final String KEY_SOLO_WRAPPER = "KEY_SOLO_WRAPPER";
	
	// The global state allows glue-class objects to share values with each other.
		private static final Map<String, Object> GLOBAL_STATE = Collections.synchronizedMap(new HashMap<String, Object>());

		private Cucumber() {
		}

		public static void put(String key, Object value) {
			GLOBAL_STATE.put(key, value);
		}

		public static Object get(String key) {
			return GLOBAL_STATE.get(key);
		}
}
