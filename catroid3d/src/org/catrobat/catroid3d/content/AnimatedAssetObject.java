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

import org.catrobat.catroid3d.common.ModelDescriptor;
import org.catrobat.catroid3d.io.StorageHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.IntAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.collision.btConvexHullShape;
import com.badlogic.gdx.physics.bullet.collision.btConvexShape;
import com.badlogic.gdx.physics.bullet.collision.btShapeHull;
import com.badlogic.gdx.utils.Array;

public class AnimatedAssetObject extends Object {

	private static final String ANIMATION_IDLE = "Idle";
	private static final String ANIMATION_WALK = "Walk";
	private static final String ANIMATION_ATTACK = "Attack";

	private static final long serialVersionUID = 1L;
	private ModelDescriptor modelDescriptor;
	private AnimationController animation = null;

	public AnimatedAssetObject(String name, float mass, Matrix4 position, ModelDescriptor modelDescriptor) {
		super(name, mass, position);
		this.modelDescriptor = modelDescriptor;
	}

	@Override
	public void createModel() {
		model = StorageHandler.getInstance().getAsset(modelDescriptor.getModelPath());
		Array<Attribute> materialAttributes = new Array<Attribute>();
		materialAttributes.add(new TextureAttribute(TextureAttribute.Diffuse, (Texture) StorageHandler.getInstance()
				.getAsset(modelDescriptor.getTexturePath())));
		materialAttributes.add(new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, 1f));
		if (textureType == TEXTURE_TYPE.TEXTURE_BEHIND) {
			materialAttributes.add(new IntAttribute(IntAttribute.CullFace, 0));
		}
		model.materials.first().set(materialAttributes);

	}

	@Override
	protected btConvexShape createShape() {
		Mesh mesh = model.meshes.get(0);
		btConvexHullShape shape = new btConvexHullShape(mesh.getVerticesBuffer(), mesh.getNumVertices(),
				mesh.getVertexSize());
		btShapeHull hull = new btShapeHull(shape);
		hull.buildHull(shape.getMargin());
		btConvexHullShape resultShape = new btConvexHullShape(hull);
		shape.dispose();
		return resultShape;
	}

	@Override
	public void render() {
		if (animation == null) {
			animation = new AnimationController(entity.modelInstance);
			animation.animate(ANIMATION_IDLE, -1, 1f, null, 0.2f);
		}
		animation.update(Gdx.graphics.getDeltaTime());

	}
}
