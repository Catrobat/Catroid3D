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
Feature: Dynamic object with mass collides with other objects

  Dynamic objects with mass can collide with 
  other static and dynamic objects.
  
  Background: 
  	Given I am in the main menu
  	When I press on the splash screen
  	Then I should see the world
  	When I press the add-object button	
  	Then the choose object split pane should show up
  	When I press the miscellaneous button
  	And I click on the barrel image
  	Then the choose object split pane should hide
  	And a barrel should be placed in the middle of the ground
	When I press the move-object button
  	
  Scenario: Dynamic object with mass collides with another dynamic object with mass
  	When I move the barrel towards the second barrel and hit it
  	Then the second barrel should be pushed away in the same direction
  	
  Scenario: Dynamic object with mass collides with a static object
  	When I move the barrel towards the tree and hit it
  	Then the tree should stay at its position
  	
  	
  	