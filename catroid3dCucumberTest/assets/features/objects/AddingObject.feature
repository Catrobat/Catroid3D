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
Feature: Adding objects to the world view

  The user can add different objects to
  the world view via the add-object 
  button on upper left side.
  
  Background: 
  	Given I am in the main menu
  	When I press on the splash screen
  	Then I should see the world
  	When I press the add-object button	
  	Then the object dialog box should show up
  	
  Scenario Outline: Adding new object from the ground objects menu
  	When I click on the "<image>" image
  	Then the object dialog box should hide
  	And a "<image>" should be placed in the middle of the ground

	Examples:
	   |  image  |
	   |  tree   |
	   |  plant1 |
	   |  plant2 |
  	
  	Scenario: Adding new barrel object from the miscellaneous objects menu
  	When I press the miscellaneous button
  	And I click on the barrel image
  	Then the object dialog box should hide
  	And a barrel should be placed in the middle of the ground
  	
  	
  	