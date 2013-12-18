/**
 *  Catroid: An on-device visual programming system for Android devices
 *  Copyright (C) 2010-2013 The Catrobat Team
 *  (<http://developer.catrobat.org/credits>)
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *  
 *  An additional term exception under section 7 of the GNU Affero
 *  General Public License, version 3, is available at
 *  http://developer.catrobat.org/license_additional_term
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 *  
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.catroid3d.content;

import java.io.Serializable;

import org.catrobat.catroid3d.physics.Entity;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btConvexShape;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBodyConstructionInfo;

public abstract class Object implements Serializable {

	public static enum TEXTURE_TYPE {
		NONE, TEXTURE_BEHIND
	}

	public static enum OBJECT_TYPE {
		NORMAL, GROUND, SKYLINE
	}

	private static final long serialVersionUID = 1L;
	private boolean hasRigidBody = true;
	protected String name;
	protected float positionX, postitionY, positionZ;
	protected Matrix4 position;
	protected float mass;
	protected Model model;
	protected Entity entity;
	protected TEXTURE_TYPE textureType = TEXTURE_TYPE.TEXTURE_BEHIND;
	protected OBJECT_TYPE objectType = OBJECT_TYPE.NORMAL;
	protected int collisionFlags = 0;

	public Object(String name, float mass, Matrix4 position) {
		this.name = name;
		if (mass < 0) {
			mass = 0;
		} else {
			this.mass = mass;
		}
		this.position = position;
	}

	public void setObjectType(OBJECT_TYPE renderType) {
		this.objectType = renderType;
	}

	public OBJECT_TYPE getObjectType() {
		return objectType;
	}

	public void setTextureType(TEXTURE_TYPE textureType) {
		this.textureType = textureType;
	}

	public abstract void createModel();

	public void setHasRigidBody(boolean hasRigidBody) {
		this.hasRigidBody = hasRigidBody;
	}

	public void setCollisionFlags(int collisionFlags) {
		this.collisionFlags = collisionFlags;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public Model getModel() {
		return model;
	}

	public void setTransformation(Matrix4 transform) {
		this.position = transform;
	}

	public Matrix4 getTransformation() {
		return position;
	}

	public Entity createEntityObject() {
		createModel();
		btRigidBody body = null;
		if (hasRigidBody) {
			btConvexShape shape = createShape();
			btRigidBodyConstructionInfo bodyInfo = new btRigidBodyConstructionInfo(mass, null, shape,
					calculateLocalInertia(shape));
			body = new btRigidBody(bodyInfo);
			body.setCollisionFlags(body.getCollisionFlags() | collisionFlags);
		}
		entity = new Entity(this, body);
		entity.modelInstance.userData = new ObjectType(objectType.ordinal());
		return entity;
	}

	protected abstract btConvexShape createShape();

	private Vector3 calculateLocalInertia(btCollisionShape shape) {
		Vector3 localInertia = new Vector3();
		if (mass == 0) {
			localInertia = Vector3.Zero;
		} else {
			shape.calculateLocalInertia(mass, localInertia);
		}
		return localInertia;
	}

	public class ObjectType extends java.lang.Object {

		private int objectType;

		public ObjectType(int objectType) {
			this.objectType = objectType;
		}

		public int getObjectType() {
			return objectType;
		}
	}
}
