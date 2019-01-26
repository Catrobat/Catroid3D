package org.catrobat.catroid.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.badlogic.gdx.backends.android.AndroidFragmentApplication;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.badlogic.gdx.utils.SharedLibraryLoader;

import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.WorldListener;

public class GameFragment extends AndroidFragmentApplication {

    public static String TAG = "GameFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ProjectManager projectManager = ProjectManager.getInstance();
        GdxNativesLoader.load();
        new SharedLibraryLoader().load("gdx-bullet");
        WorldListener listener = projectManager.getWorldListener();
        if (listener == null) {
            listener = new WorldListener();
            projectManager.setWorldListener(listener);
        }
        // return the GLSurfaceView on which libgdx is drawing game
        return initializeForView(listener);
    }
}

