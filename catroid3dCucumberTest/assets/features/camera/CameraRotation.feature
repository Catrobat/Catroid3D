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
Feature: Camera rotation

  If no other menu button is selected, the camera should be in rotation mode.

  Background:
    Given I am in the main menu
    When I press on the splash screen
    Then I should see the world
    
  Scenario Outline: Swiping my finger to the left
    When I swipe my finger to the left and rotate the camera around <Degree>
    Then the camera should rotate to the right and x should be "<x>" and y should be "<y>" and z should be "<z>"
    
    Examples:
	   | Degree | x  | y  | z  |
	   | 90     | >0 | >0 | <0 |
	   | 180    | <0 | >0 | <0 |
	   | 270    | <0 | >0 | >0 |
    
  Scenario Outline: Swiping my finger to the right
    When I swipe my finger to the right and rotate the camera around <Degree>
    Then the camera should rotate to the left and x should be "<x>" and y should be "<y>" and z should be "<z>"
    
    Examples:
	   | Degree | x  | y  | z  |
	   | 90     | <0 | >0 | >0 |
	   | 180    | <0 | >0 | <0 |
	   | 270    | >0 | >0 | <0 |
    
  Scenario Outline: Swiping my finger downwards
    When I swipe my finger downwards and rotate the camera around <Degree>
    Then the camera should rotate upwards and x should be "<x>" and y should be "<y>" and z should be "<z>"
    
    Examples:
	   | Degree | x  | y  | z  |
	   | 90     | <0 | >0 | <0 |
	   | 180    | <0 | <0 | <0 |
	   | 270    | >0 | <0 | >0 |
    
  Scenario Outline: Swiping my finger upwards
    When I swipe my finger upwards and rotate the camera around <Degree>
    Then the camera should rotate downwards and x should be "<x>" and y should be "<y>" and z should be "<z>"
    
    Examples:
	   | Degree | x  | y  | z  |
	   | 90     | >0 | <0 | >0 |
	   | 180    | <0 | <0 | <0 |
	   | 270    | <0 | >0 | <0 |
    

