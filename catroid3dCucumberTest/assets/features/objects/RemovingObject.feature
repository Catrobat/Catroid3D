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
Feature: Removing objects from the world view

  The user can remove objects from
  the world view via a long click
  onto the object and the context menu.
  
  Background: 
  	Given I am in the main menu
  	When I press on the splash screen
  	Then I should see the world
  	
  Scenario Outline: Removing object from the world view
  	When I long click on the "<model>"
  	Then the object context menu should show up
  	When I click on the delete button
  	Then the "<model>" should be removed from the world view

	Examples:
	   |  model  |
	   |  tree   |
	   |  barrel |
  	
  	
  	
  	