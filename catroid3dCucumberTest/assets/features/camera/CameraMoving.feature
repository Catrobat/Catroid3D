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
Feature: Camera moving

  If the camere move button is activated, then the camera should move
  on x and y axis.

  Background:
    Given I am in the main menu
    When I press on the splash screen
    Then I should see the world
    When I press the camera move button
    Then I should be in the camera moving mode
    
  Scenario: Swiping my finger to the left
    When I swipe my finger to the left
    Then the camera should move to the right on the correct position
    
  Scenario: Swiping my finger to the right
    When I swipe my finger to the right
    Then the camera should move to the left on the correct position
    
  Scenario: Swiping my finger downwards
    When I swipe my finger downwards
    Then the camera should move upwards on the correct position
    
  Scenario: Swiping my finger upwards
    When I swipe my finger upwards
    Then the camera should move downwards on the correct position
    

