# Catroid: An on-device visual programming system for Android devices
# Copyright (C) 2010-2014 The Catrobat Team
# (<http://developer.catrobat.org/credits>)
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU Affero General Public License as
# published by the Free Software Foundation, either version 3 of the
# License, or (at your option) any later version.
#
# An additional term exception under section 7 of the GNU Affero
# General Public License, version 3, is available at
# http://developer.catrobat.org/license_additional_term
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
# GNU Affero General Public License for more details.
#
# You should have received a copy of the GNU Affero General Public License
# along with this program.  If not, see <http://www.gnu.org/licenses/>.
Feature: Ui elements in the project build screen

  The first screen where the user can interact 
  with the system is the project build screen. 
  There are several toggle on-off buttons which 
  can be checked or unchecked.
  
  Background: 
  	Given I am in the main menu
  	When I press on the splash screen
  	Then I should see the world
  	
  Scenario: Tapping the move-camera button twice
  	When I press the move-camera button
  	Then the move-camera button should be checked
  	When I press the move-camera button
  	Then the move-camera button should be unchecked
  	
  Scenario: Tapping the move-object button twice
  	When I press the move-object button
  	Then the move-object button should be checked
  	When I press the move-object button
  	Then the move-object button should be unchecked
  	
  Scenario: Tapping the add-or-remove-ground button twice
  	When I press the add-or-remove-ground button
  	Then the add-or-remove-ground button should be checked
  	And the add-ground button should be visible
	And the remove-ground button should be visible
	When I press the add-or-remove-ground button
  	Then the add-or-remove-ground button should be unchecked
  	And the add-ground button should not be visible
	And the remove-ground button should not be visible
  	
  Scenario: Tapping the add-object button
  	When I press the add-object button	
  	Then the object dialog box should show up
  	
  	
  	
  	