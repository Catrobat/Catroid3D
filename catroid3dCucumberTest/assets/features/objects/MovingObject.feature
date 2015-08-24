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
Feature: Moving objects onto the world view

  The user can move objects by pressing the 
  move-object button and drag the object to
  another position.
  
  Background: 
  	Given I am in the main menu
  	When I press on the splash screen
  	Then I should see the world
  	When I press the move-object button
  	
  Scenario: Moving object onto the ground
  	When I drag the barrel to the left
  	Then the barrel should move to the corresponding position
  	
   Scenario: Moving object off the ground
  	When I drag the barrel to the left off the ground
  	Then the barrel should move to the corresponding position
  	And the barrel should fall down because of gravity

  	
  	
  	
  	