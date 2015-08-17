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
Feature: Camera zooming

  If no other menu button is selected, the camera should be in rotation mode.
  If I'm zooming with my fingers then the camera should zoom.

  Background:
    Given I am in the main menu
    When I press on the splash screen
    Then I should see the world
    
  Scenario: I'm zooming in (drag two fingers apart from each other)
    When I zoom in with my fingers
    Then the camera should zoom in
    
  Scenario: I'm zooming out (drag two fingers together)
    When I zoom out with my fingers
    Then the camera should zoom out
    
    

