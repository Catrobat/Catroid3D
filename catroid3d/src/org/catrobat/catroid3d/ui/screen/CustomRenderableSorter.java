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
package org.catrobat.catroid3d.ui.screen;

import java.util.Comparator;

import org.catrobat.catroid3d.content.Object.RenderType;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.utils.RenderableSorter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class CustomRenderableSorter implements RenderableSorter, Comparator<Renderable> {

	private Camera camera;
	private final Vector3 tmpV1 = new Vector3();
	private final Vector3 tmpV2 = new Vector3();

	@Override
	public void sort(final Camera camera, final Array<Renderable> renderables) {
		this.camera = camera;
		renderables.sort(this);
	}

	@Override
	public int compare(final Renderable o1, final Renderable o2) {
		final int rt1 = ((RenderType) o1.userData).getRenderType();
		final int rt2 = ((RenderType) o2.userData).getRenderType();
		if (rt1 > 0 || rt2 > 0) {
			return rt1 > rt2 ? -1 : (rt2 > rt1 ? 1 : 0);
		}
		final boolean b1 = o1.material.has(BlendingAttribute.Type);
		final boolean b2 = o2.material.has(BlendingAttribute.Type);
		if (b1 != b2) {
			return b1 ? 1 : -1;
		}
		// FIXME implement better sorting algorithm
		// final boolean same = o1.shader == o2.shader && o1.mesh == o2.mesh && (o1.lights == null) == (o2.lights == null) && 
		// o1.material.equals(o2.material);
		o1.worldTransform.getTranslation(tmpV1);
		o2.worldTransform.getTranslation(tmpV2);
		final float dst = camera.position.dst2(tmpV1) - camera.position.dst2(tmpV2);
		return dst < 0f ? 1 : (dst > 0f ? -1 : 0);
	}
}
