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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.IntAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.TextureDescriptor;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btConvexShape;

public class BoxAssetObject extends Abstract3dObject {

    private static final long serialVersionUID = 1L;
    private float width, height, depth;
    private TEXTURE assetTextureId;

    public BoxAssetObject(String name, float mass, Matrix4 position, float width, float height, float depth,
                          TEXTURE assetTextureId) {
        super(name, mass, position);
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.assetTextureId = assetTextureId;
    }

    @Override
    protected void createModel() {
        ModelBuilder modelBuilder = ProjectManager.getInstance().getWorldListener()
                .getModelBuilder();
        if (assetTextureId != null) {
            Texture texture = StorageHandler.getInstance().getAsset(
                    Constants.TEXTURE_ARRAY.get(assetTextureId.ordinal()));
            TextureDescriptor<Texture> textureDescriptor = new TextureDescriptor<Texture>(texture);
            model = modelBuilder.createBox(width, height, depth, new Material(new TextureAttribute(
                            TextureAttribute.Diffuse, textureDescriptor), new IntAttribute(IntAttribute.CullFace, 0)),
                    Usage.Position | Usage.Normal | Usage.TextureCoordinates);
        } else {
            model = modelBuilder.createBox(width, height, depth,
                    new Material(ColorAttribute.createDiffuse(Color.MAGENTA)), Usage.Position | Usage.Normal);
        }
    }

    @Override
    protected btConvexShape createShape() {
        return new btBoxShape(new Vector3(width / 2f, height / 2f, depth / 2f));
    }

    @Override
    public void render() {

    }


}
