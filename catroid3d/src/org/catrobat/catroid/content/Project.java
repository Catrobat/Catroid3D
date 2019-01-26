/**
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2013 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * <p>
 * An additional term exception under section 7 of the GNU Affero
 * General Public License, version 3, is available at
 * http://developer.catrobat.org/license_additional_term
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.catroid.content;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import org.catrobat.catroid.content.models.Abstract3dObject;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.formulaeditor.UserVariable;

import java.util.ArrayList;
import java.util.List;

public class Project {

    private String name;
    private Scene scene;
    private ArrayList<Abstract3dObject> objectList = new ArrayList<Abstract3dObject>();

    @XStreamAlias("programVariableList")
    private List<UserVariable> projectVariables = new ArrayList<>();
    @XStreamAlias("programListOfLists")
    private List<UserList> projectLists = new ArrayList<>();

    public Project(String name) {

        this.name = name;
        scene = new Scene(name, this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addObject(Abstract3dObject object) {
        for(Abstract3dObject obj : objectList){
            if(obj.getName() == object.getName()){
                return;
            }
        }
        objectList.add(object);
    }

    public void removeObject(Abstract3dObject object) {
        objectList.remove(object);
    }

    public void removeObject(String name) {
        Abstract3dObject object = null;
        for (Abstract3dObject obj : objectList) {
            if (obj.getName() == name) {
                object = obj;
            }
        }
        if (object != null) {
            objectList.remove(object);
        }
    }

    public ArrayList<Abstract3dObject> getObjectList() {
        return objectList;
    }

    public Scene getDefaultScene() {
        return scene;
    }

    public List<UserVariable> getProjectVariables() {
        if (projectVariables == null) {
            projectVariables = new ArrayList<>();
        }
        return projectVariables;
    }

    public List<UserList> getProjectLists() {
        if (projectLists == null) {
            projectLists = new ArrayList<>();
        }
        return projectLists;
    }

    public void fireToAllSprites(EventWrapper event) {
        //todo impl?
    }
}
