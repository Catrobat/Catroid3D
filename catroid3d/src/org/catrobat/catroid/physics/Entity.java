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
package org.catrobat.catroid.physics;

import android.support.annotation.IntDef;

import org.catrobat.catroid.common.ThreadScheduler;
import org.catrobat.catroid.content.EventWrapperListener;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.EventThread;
import org.catrobat.catroid.libgdx3dwrapper.actions.Action3d;
import org.catrobat.catroid.libgdx3dwrapper.scene.Actor3d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Entity extends Actor3d implements Disposable {

    private static final float DEGREE_UI_OFFSET = 90.0f;

    public final Sprite sprite;
    private final String name;
    private Color color = new Color(1f, 1f, 1f, 1f);
    private boolean isRenderable = true;
    private float rotation = 90f;
    private ThreadScheduler scheduler;


    public Entity(String name, Model model, Matrix4 position, final btRigidBody body, Sprite sprite) {
        this.name = name;
        this.modelInstance = new ModelInstance(model, position.cpy());
        this.transform = this.modelInstance.transform;
        this.body = body;
        if (body != null) {
            body.userData = this;
            if (body instanceof btRigidBody) {
                this.motionState = new MotionState(this.modelInstance.transform);
                this.body.setMotionState(motionState);
                //setRotation(0f);
            } else {
                body.setWorldTransform(transform);
            }
        }
        if (sprite != null) {
            for (Action3d action : sprite.ActorEntity.getActions()) {
                addAction(action);
            }
            scheduler = new ThreadScheduler(this);
            this.addListener(new EventWrapperListener(this));
        }
        this.sprite = sprite;
    }

    //@Override
    public void act(float delta) {
        if (scheduler != null) {
            scheduler.tick(delta);
        }

        if (sprite != null) {
            sprite.evaluateConditionScriptTriggers();
        }
    }

    public void startThread(EventThread threadToBeStarted) {
        if (scheduler != null) {
            scheduler.startThread(threadToBeStarted);
        }
    }

    public void stopThreads(Array<Action3d> threads) {
        if (scheduler != null) {
            scheduler.stopThreads(threads);
        }
    }

    public void stopThreadWithScript(Script script) {
        if (scheduler != null) {
            scheduler.stopThreadsWithScript(script);
        }
    }

    public String getName() {
        return name;
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

    void notifyAllWaiters() {
        for (Action3d action : getActions()) {
            if (action instanceof EventThread) {
                ((EventThread) action).notifyWaiter();
            }
        }
    }
}
