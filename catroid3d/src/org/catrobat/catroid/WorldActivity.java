package org.catrobat.catroid;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidFragmentApplication;

import org.catrobat.catroid.ui.BaseExceptionHandler;
import org.catrobat.catroid.ui.BottomBar;
import org.catrobat.catroid.ui.fragment.GameFragment;
import org.catrobat.catroid.ui.fragment.ScriptFragment;

public class WorldActivity extends AppCompatActivity implements AndroidFragmentApplication.Callbacks {

    private ProjectManager _projectManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new BaseExceptionHandler(this));
        setContentView(R.layout.activity_world_layout);
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        GameFragment libgdxFragment = new GameFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, libgdxFragment, GameFragment.TAG)
                .addToBackStack(GameFragment.TAG)
                .commit();
        _projectManager = ProjectManager.getInstance();
        _projectManager.setWorldActivity(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        _projectManager.getWorldListener().menuPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        _projectManager.getWorldListener().menuResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void exit() {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void handleAddButton(View view) {
        if (getCurrentFragment() instanceof ScriptFragment) {
            ((ScriptFragment) getCurrentFragment()).handleAddButton();
            return;
        }
//		if (getCurrentFragment() instanceof DataListFragment) {
//			handleAddUserDataButton();
//			return;
//		}
//		if (getCurrentFragment() instanceof LookListFragment) {
//			handleAddLookButton();
//			return;
//		}
//		if (getCurrentFragment() instanceof SoundListFragment) {
//			handleAddSoundButton();
//		}
    }

    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.fragment_container);
    }

    @Override
    public void onBackPressed() {
//		if (getCurrentFragment() instanceof FormulaEditorFragment) {
//			((FormulaEditorFragment) getCurrentFragment()).promptSave();
//		} else {
        Fragment currentFragment = getCurrentFragment();
        if(currentFragment instanceof ScriptFragment){
            if (((ScriptFragment) getCurrentFragment()).getListView().isCurrentlyDragging()) {
                ScriptFragment fragment = ((ScriptFragment) getCurrentFragment());
                fragment.getListView().animateHoveringBrick();
                return;
            }
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) findViewById(R.id.fragment_container).getLayoutParams();
            params.removeRule(RelativeLayout.ABOVE);
            //todo: check if script is selected
            BottomBar.hideAddButton(this);
            BottomBar.showPlayButton(this);
            _projectManager.getWorldListener().menuPause();
        }
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            BottomBar.showBottomBar(this);
            BottomBar.showPlayButton(this);
            //super.onBackPressed();
        }
        //}
    }

    public void handlePlayButton(View view) {
        boolean draggingActive = getCurrentFragment() instanceof ScriptFragment
                && ((ScriptFragment) getCurrentFragment()).getListView().isCurrentlyDragging();

        if (draggingActive) {
            ScriptFragment fragment = ((ScriptFragment) getCurrentFragment());
            fragment.getListView().animateHoveringBrick();
            return;
        }

        getSupportFragmentManager().popBackStackImmediate(GameFragment.TAG, 0);

//		ProjectManager projectManager = ProjectManager.getInstance();
//		Scene currentScene = ProjectManager.getInstance().getCurrentlyEditedScene();
//		Scene defaultScene = projectManager.getCurrentProject().getDefaultScene();
//
//		if (currentScene.getName().equals(defaultScene.getName())) {
//			projectManager.setCurrentlyPlayingScene(defaultScene);
//			projectManager.setStartScene(defaultScene);
//			startPreStageActivity();
//		} else {
//			new PlaySceneDialog.Builder(this)
//					.setPositiveButton(R.string.play, new DialogInterface.OnClickListener() {
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							startPreStageActivity();
//						}
//					})
//					.create()
//					.show();
//		}

        BottomBar.hideBottomBar(this);
        String sceneName = _projectManager.getCurrentlyEditedScene().getName();
        _projectManager.getWorldListener().startScene(sceneName);
        _projectManager.getWorldListener().menuResume();
    }

    public void navigateToScriptsEditView() {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) findViewById(R.id.fragment_container).getLayoutParams();
        params.addRule(RelativeLayout.ABOVE, R.id.bottom_bar);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(ScriptFragment.TAG).replace(R.id.fragment_container, new ScriptFragment(), ScriptFragment.TAG)
                .commit();
    }

//	void startPreStageActivity() {
//		Intent intent = new Intent(this, PreStageActivity.class);
//		startActivityForResult(intent, PreStageActivity.REQUEST_RESOURCES_INIT);
//	}

//	void startStageActivity() {
//		if (DroneServiceWrapper.checkARDroneAvailability()) {
//			startActivity(new Intent(this, DroneStageActivity.class));
//		} else {
//			startActivity(new Intent(this, StageActivity.class));
//		}
//	}

}
