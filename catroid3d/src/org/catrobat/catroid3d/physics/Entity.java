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
package org.catrobat.catroid3d.physics;

import org.catrobat.catroid3d.content.Object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.linearmath.btMotionState;
import com.badlogic.gdx.utils.Disposable;

public class Entity implements Disposable {

	private Matrix4 transform;
	private Color color = new Color(1f, 1f, 1f, 1f);
	private boolean isRenderable = true;
	private Object object;
	public ModelInstance modelInstance;
	public Entity.MotionState motionState;
	public btRigidBody body;

	public Entity(final Object object, final btRigidBody body) {
		this.modelInstance = new ModelInstance(object.getModel(), object.getTransformation().cpy());
		this.transform = this.modelInstance.transform;
		this.body = body;
		this.object = object;

		if (body != null) {
			body.userData = this;
			if (body instanceof btRigidBody) {
				this.motionState = new MotionState(this.modelInstance.transform);
				this.body.setMotionState(motionState);
			} else {
				body.setWorldTransform(transform);
			}
		}
	}

	public void setColor(Color color) {
		setColor(color.r, color.g, color.b, color.a);
	}

	public void setColor(float r, float g, float b, float a) {
		color.set(r, g, b, a);
		if (modelInstance != null) {
			for (Material material : modelInstance.materials) {
				ColorAttribute colorAttribute = (ColorAttribute) material.get(ColorAttribute.Diffuse);
				if (colorAttribute != null) {
					colorAttribute.color.set(r, g, b, a);
				}
			}
		}
	}

	public Object getObject() {
		return object;
	}

	public boolean isRenderable() {
		return isRenderable;
	}

	public void setIsRenderable(boolean isRenderable) {
		this.isRenderable = isRenderable;
	}

	@Override
	public void dispose() {
		if (motionState != null) {
			motionState.dispose();
		}
		if (body != null) {
			body.dispose();
		}
		motionState = null;
		body = null;
	}

	public void setWorldTransform(Matrix4 transform) {
		if (body != null) {
			body.setWorldTransform(transform);
		}
		this.modelInstance.transform.set(transform);
	}

	static class MotionState extends btMotionState implements Disposable {
		private final Matrix4 transform;

		public MotionState(final Matrix4 transform) {
			this.transform = transform;
		}

		@Override
		public void getWorldTransform(Matrix4 worldTrans) {
			worldTrans.set(transform);
		}

		@Override
		public void setWorldTransform(Matrix4 worldTrans) {
			transform.set(worldTrans);
		}

		@Override
		public void dispose() {
			delete();
		}

	}

}
