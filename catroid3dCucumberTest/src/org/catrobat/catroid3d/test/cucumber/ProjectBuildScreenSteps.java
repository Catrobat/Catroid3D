package org.catrobat.catroid3d.test.cucumber;

import org.catrobat.catroid3d.common.Constants;
import org.catrobat.catroid3d.test.cucumber.util.SoloLibgdxWrapper;

import android.test.AndroidTestCase;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ProjectBuildScreenSteps extends AndroidTestCase {

	
	@When("^I press the move-camera button$")
	public void i_press_the_move_camera_button() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		solo.clickOnButton(Constants.UI_MOVE_CAMERA_BUTTON);
		solo.sleep(500);
	}
	
	@Then("^the move-camera button should be checked$")
	public void the_the_move_camera_button_should_be_checked() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		assertTrue(solo.isToggleOnOffButtonChecked(Constants.UI_MOVE_CAMERA_BUTTON));
	}
	
	@Then("^the move-camera button should be unchecked$")
	public void the_the_move_camera_button_should_be_unchecked() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		assertFalse(solo.isToggleOnOffButtonChecked(Constants.UI_MOVE_CAMERA_BUTTON));
	}
	
	@When("^I press the move-object button$")
	public void i_press_the_move_object_button() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		solo.clickOnButton(Constants.UI_MOVE_OBJECT_BUTTON);
		solo.sleep(500);
	}
	
	@Then("^the move-object button should be checked$")
	public void the_the_move_object_button_should_be_checked() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		assertTrue(solo.isToggleOnOffButtonChecked(Constants.UI_MOVE_OBJECT_BUTTON));
	}
	
	@Then("^the move-object button should be unchecked$")
	public void the_the_move_object_button_should_be_unchecked() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		assertFalse(solo.isToggleOnOffButtonChecked(Constants.UI_MOVE_OBJECT_BUTTON));
	}
	
	@When("^I press the add-or-remove-ground button$")
	public void i_press_the_add_or_remove_ground_button() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		solo.clickOnButton(Constants.UI_GROUND_BUTTON);
		solo.sleep(500);
	}
	
	@Then("^the add-or-remove-ground button should be checked$")
	public void the_add_or_remove_ground_button_should_be_checked() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		assertTrue(solo.isToggleOnOffButtonChecked(Constants.UI_GROUND_BUTTON));
	}
	
	@Then("^the add-or-remove-ground button should be unchecked$")
	public void the_add_or_remove_ground_button_should_be_unchecked() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		assertFalse(solo.isToggleOnOffButtonChecked(Constants.UI_GROUND_BUTTON));
	}
	
	@And("^the add-ground button should be visible$")
	public void the_add_ground_button_should_be_visible() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		assertTrue(solo.isButtonVisible(Constants.UI_ADD_GROUND_BUTTON));
	}
	
	@And("^the add-ground button should not be visible$")
	public void the_add_ground_button_should_not_be_visible() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		assertFalse(solo.isButtonVisible(Constants.UI_ADD_GROUND_BUTTON));
	}
	
	@And("^the remove-ground button should be visible$")
	public void the_remove_ground_button_should_be_visible() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		assertTrue(solo.isButtonVisible(Constants.UI_REMOVE_GROUND_BUTTON));
	}
	
	@And("^the remove-ground button should not be visible$")
	public void the_remove_ground_button_should_not_be_visible() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		assertFalse(solo.isButtonVisible(Constants.UI_REMOVE_GROUND_BUTTON));
	}
	
	@When("^I press the add-object button$")
	public void i_press_the_add_object_button() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		solo.clickOnButton(Constants.UI_ADD_OBJECT_BUTTON);
		solo.sleep(2000);
	}
	
	@Then("^the object dialog box should show up$")
	public void the_object_dialog_box_should_show_up() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		assertTrue(solo.isChooseObjectSplitPaneVisible());
	}
	
//	@When("^I press the (\\w+)$")
//	public void i_press_the_button(String button) {
//		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
//		if (button.equalsIgnoreCase("movecamerabutton")) {
//			solo.clickOnButton(Constants.UI_MOVE_CAMERA_BUTTON);
//			solo.sleep(4000);
//		}
//		else if (button.equalsIgnoreCase("moveobjectbutton")) {
//			solo.clickOnButton(Constants.UI_MOVE_OBJECT_BUTTON);
//			solo.sleep(4000);
//		} else {
//			fail("Button " + button + " not found");
//		}
//	}
//	
//	@When("^the (\\w+) should be checked$")
//	public void the_button_should_be_checked(String button) {
//		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
//		if (button.equalsIgnoreCase("movecamerabutton")) {
//			assertTrue(solo.isToggleOnOffButtonChecked(Constants.UI_MOVE_CAMERA_BUTTON));
//		}
//		else if (button.equalsIgnoreCase("moveobjectbutton")) {
//			assertTrue(solo.isToggleOnOffButtonChecked(Constants.UI_MOVE_OBJECT_BUTTON));
//		} else {
//			fail("Button " + button + " not found");
//		}
//	}
	
}
