package org.catrobat.catroid3d.test.cucumber;

import org.catrobat.catroid3d.ProjectManager;
import org.catrobat.catroid3d.R;
import org.catrobat.catroid3d.common.Constants.MODEL;
import org.catrobat.catroid3d.test.cucumber.util.SoloLibgdxWrapper;
import org.catrobat.catroid3d.utils.Math;
import org.catrobat.catroid3d.utils.Util;

import android.test.AndroidTestCase;

import com.badlogic.gdx.math.Vector3;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ObjectSteps extends AndroidTestCase {
	
	private final String standardBarrelModelName = "Barrel01";
	private final String standardTreeModelName = "Tree01";
	
	private Vector3 toPosition = new Vector3();

	@When("^I click on the \"([^\"]*)\" image$")
	public void I_click_on_the_object_image(String objectImage) {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		solo.sleep(1000);
		if(objectImage.contains("tree")) {
			solo.clickOnButton(MODEL.MODEL_PALM_TREE_01.name());
		}
		else if(objectImage.contains("plant01")) {
			solo.clickOnButton(MODEL.MODEL_TROPICAL_PLANT_01.name());
		}
		else if(objectImage.contains("plant02")) {
			solo.clickOnButton(MODEL.MODEL_TROPICAL_PLANT_02.name());
		}
		else if(objectImage.contains("barrel")) {
			solo.clickOnButton(MODEL.MODEL_BIG_WOOD_BARREL.name());
		}
		else {
			throw new PendingException();
		}
		solo.sleep(1000);
	}
	
	@Then("^the choose object split pane should hide$")
	public void the_choose_object_split_pane_should_hide() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		assertFalse(solo.isChooseObjectSplitPaneVisible());
	}
	
	@And("^a \"([^\"]*)\" should be placed in the middle of the ground$")
	public void a_object_should_be_placed_in_the_middle_of_the_ground(String objectToPlace) {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		if(objectToPlace.contains("tree")) {
			assertTrue(solo.isEntityAtPosition(Util.getModelDescriptor(MODEL.MODEL_PALM_TREE_01).getNameInSkinFile(), Math.createCenterPositionMatrix()));
		}
		else if(objectToPlace.contains("plant01")) {
			assertTrue(solo.isEntityAtPosition(Util.getModelDescriptor(MODEL.MODEL_TROPICAL_PLANT_01).getNameInSkinFile(), Math.createCenterPositionMatrix()));
		}
		else if(objectToPlace.contains("plant02")) {
			assertTrue(solo.isEntityAtPosition(Util.getModelDescriptor(MODEL.MODEL_TROPICAL_PLANT_02).getNameInSkinFile(), Math.createCenterPositionMatrix()));
		}
		else if(objectToPlace.contains("barrel")) {
			assertTrue(solo.isEntityAtPosition(Util.getModelDescriptor(MODEL.MODEL_BIG_WOOD_BARREL).getNameInSkinFile(), Math.createCenterPositionMatrix()));
		}
		else {
			throw new PendingException();
		}
	}
	
	@When("^I press the miscellaneous button$")
	public void I_press_the_miscellaneous_button() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		solo.sleep(1000);
		solo.clickOnButton(ProjectManager.getInstance().getWorldActivity().getString(R.string.miscellaneous));
		solo.sleep(1000);
	}
	
	@When("^I long click on the \"([^\"]*)\"$")
	public void I_long_click_on_the_model(String model) {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		if(model.contains("tree")) {
			solo.clickLongOnEntity(standardTreeModelName);
		}
		else if(model.contains("barrel")) {
			solo.clickLongOnEntity(standardBarrelModelName);
		}
		else {
			throw new PendingException();
		}
		solo.sleep(500);
	}
	
	@Then("^the object context menu should show up$")
	public void the_object_context_menu_should_show_up() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		assertFalse(solo.isChooseObjectSplitPaneVisible());
	}
	
	@When("^I click on the delete button$")
	public void I_click_on_the_delete_button() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		solo.clickOnButton(ProjectManager.getInstance().getWorldActivity().getString(R.string.delete));
		solo.sleep(500);
	}
	
	@Then("^the \"([^\"]*)\" should be removed from the world view$")
	public void the_model_should_be_removed_from_the_world_view(String model) {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		if(model.contains("tree")) {
			assertFalse(solo.isEntityVisible(standardTreeModelName));
		}
		else if(model.contains("barrel")) {
			assertFalse(solo.isEntityVisible(standardBarrelModelName));
		}
		else {
			throw new PendingException();
		}
	}
	
	@When("^I drag the barrel to the left$")
	public void I_drag_the_barrel_to_the_left() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		toPosition.set(200, 0, 200);
		solo.sleep(1000);
		solo.dragEntityToPosition(standardBarrelModelName, toPosition);
		solo.sleep(500);
	}
	
	@Then("^the barrel should move to the corresponding position$")
	public void the_barrel_should_move_to_the_corresponding_position() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		assertTrue(solo.isEntityAtPosition(standardBarrelModelName, Math.createPositionMatrix(toPosition)));
	}
	
	@When("^I drag the barrel to the left off the ground$")
	public void I_drag_the_barrel_to_the_left_off_the_ground() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		toPosition.set(600, 0, 200);
		solo.isEntityFallingDown(standardBarrelModelName);
		solo.sleep(1000);	
		solo.dragEntityToPosition(standardBarrelModelName, toPosition);
		solo.sleep(500);
	}
	
	@And("^the barrel should fall down because of gravity$")
	public void the_barrel_should_fall_down_because_of_gravity() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		solo.isEntityFallingDown(standardBarrelModelName);
		assertTrue(solo.isEntityFallingDown(standardBarrelModelName));
	}
}
