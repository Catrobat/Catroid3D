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
package org.catrobat.catroid.content.models;

import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.Constants.TEXTURE;
import org.catrobat.catroid.io.StorageHandler;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.IntAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.TextureDescriptor;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.collision.btConvexHullShape;
import com.badlogic.gdx.physics.bullet.collision.btConvexShape;
import com.badlogic.gdx.physics.bullet.collision.btShapeHull;
import com.badlogic.gdx.utils.Array;

public class SphereAssetObject extends Abstract3dObject {

    private static final long serialVersionUID = 1L;
    private float width, height, depth;
    private TEXTURE assetTextureId;
    private int divisionsU;
    private int divisionsV;

    public SphereAssetObject(String name, float mass, Matrix4 position, float width, float height, float depth,
                             TEXTURE assetTextureId, int divisonsU, int divisionsV) {
        super(name, mass, position);
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.assetTextureId = assetTextureId;
        this.divisionsU = divisonsU;
        this.divisionsV = divisionsV;
    }

    @Override
    protected void createModel() {
        ModelBuilder modelBuilder = ProjectManager.getInstance().getWorldListener()
                .getModelBuilder();
        Texture modelTexture = StorageHandler.getInstance().getAsset(
                Constants.TEXTURE_ARRAY.get(assetTextureId.ordinal()));
        TextureDescriptor<Texture> textureDescriptor = new TextureDescriptor<Texture>(modelTexture);
        Array<Attribute> materialAttributes = new Array<Attribute>();
        materialAttributes.add(new TextureAttribute(TextureAttribute.Diffuse, textureDescriptor));
        if (textureType == TEXTURE_TYPE.TEXTURE_BEHIND) {
            materialAttributes.add(new IntAttribute(IntAttribute.CullFace, 0));
        }
        model = modelBuilder.createSphere(width, height, depth, divisionsU, divisionsV,
                new Material(materialAttributes), Usage.Position | Usage.Normal | Usage.TextureCoordinates);
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

    }

}
