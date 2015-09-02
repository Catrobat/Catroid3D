package org.catrobat.catroid3d.test.cucumber;

import org.catrobat.catroid3d.common.Constants.MODEL;
import org.catrobat.catroid3d.test.cucumber.util.SoloLibgdxWrapper;
import org.catrobat.catroid3d.utils.Math;
import org.catrobat.catroid3d.utils.Util;

import android.test.AndroidTestCase;

import com.badlogic.gdx.math.Vector3;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class PhysicsSteps extends AndroidTestCase {
	
	private String standardBarrelModelName = "big-wood-barrel";
	private String barrelModelNameToHit = "Barrel01";
	private String treeModelNameToHit = "Tree01";
	private Vector3 positionVector = new Vector3();

	@When("^I click on the barrel image$")
	public void I_click_on_the_barrel_image() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		solo.sleep(1000);
		solo.clickOnButton(MODEL.MODEL_BIG_WOOD_BARREL.name());
		solo.sleep(1000);
	}
	
	@And("^a barrel should be placed in the middle of the ground$")
	public void a_barrel_should_be_placed_in_the_middle_of_the_ground() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		assertTrue(solo.isEntityAtPosition(Util.getModelDescriptor(MODEL.MODEL_BIG_WOOD_BARREL).getNameInSkinFile(), Math.createCenterPositionMatrix()));
	}
	
	@When("^I move the barrel towards the second barrel and hit it$")
	public void I_move_the_barrel_towards_the_second_barrel_and_hit_it() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		positionVector.set(0f, 0f, -400f);
		solo.sleep(1000);
		solo.dragEntityToPosition(standardBarrelModelName, positionVector);
		solo.sleep(500);
	}
	
	@Then("^the second barrel should be pushed away in the same direction$")
	public void the_barrel_should_be_pushed_away_in_the_same_direction() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		assertTrue(solo.checkEntityCollision(standardBarrelModelName, barrelModelNameToHit));
		assertFalse(solo.isEntityAtPosition(barrelModelNameToHit, Math.createPositionMatrix(new Vector3(0, 0, -200))));
	}
	
	@When("^I move the barrel towards the tree and hit it$")
	public void I_move_the_barrel_towards_the_tree_and_hit_it() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		positionVector.set(-200f, 0f, 300f);
		solo.sleep(1000);
		solo.dragEntityToPosition(standardBarrelModelName, positionVector);
		solo.sleep(500);
	}
	
	@Then("^the tree should stay at its position$")
	public void the_tree_should_stay_at_its_position() {
		SoloLibgdxWrapper solo = (SoloLibgdxWrapper) Cucumber.get(Cucumber.KEY_SOLO_WRAPPER);
		assertTrue(solo.checkEntityCollision(standardBarrelModelName, treeModelNameToHit));
		assertTrue(solo.isEntityAtPosition(treeModelNameToHit, Math.createPositionMatrix(new Vector3(-200f, 0f, 300f))));
	}
}
