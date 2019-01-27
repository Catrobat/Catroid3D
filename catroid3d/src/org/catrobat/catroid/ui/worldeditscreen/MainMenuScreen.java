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
package org.catrobat.catroid.ui.worldeditscreen;

import org.catrobat.catroid.WorldListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainMenuScreen extends BaseScreen {

    private WorldListener worldListener;
    private SpriteBatch spriteBatch;
    private Texture splashScreen;

    public MainMenuScreen(WorldListener worldListener) {
        this.worldListener = worldListener;
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        splashScreen.dispose();
    }

    @Override
    public void render(float delta) {
        if (worldListener.isProjectLoading()) {
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            spriteBatch.begin();
            spriteBatch.draw(splashScreen, 0, 0);
            spriteBatch.end();
        }
    }

    @Override
    public void show() {
        spriteBatch = new SpriteBatch();
        splashScreen = new Texture(Gdx.files.internal("pocket_code_3d_splash_screen.png"));
    }
}
