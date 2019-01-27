package org.catrobat.catroid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

import org.catrobat.catroid.libgdx3dwrapper.scene.Actor3d;
import org.catrobat.catroid.ui.UiUtils;

public class CameraManager {

    private static final String TAG = CameraManager.class.getSimpleName();

    //region singelton ctor
    private static final CameraManager INSTANCE = new CameraManager();
    private Vector3 _tempV3_1 = Vector3.Zero;
    private float _aabHeight = 0f;

    private CameraManager() {

        movingCamera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        initMovingCamera();

        collisionCamera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        initCollisionCamera();
    }
    public static CameraManager getInstance() {
        return INSTANCE;
    }
    //endregion

    private PerspectiveCamera movingCamera, collisionCamera;
    private Actor3d _firstPersonActor = null;
    private Matrix4 _tmpMatrix4 = new Matrix4();
    private Quaternion _cameraRotQuat = new Quaternion();
    private Vector3 _position = new Vector3();
    private Vector3 _collisionShapeOffset = new Vector3();

    private float zoomUnits = 7f;
    private float translateUnits = 1f;
    private float rotateAngle = 360f;
    private Vector3 target = new Vector3();
    private Vector3 _tempV3 = new Vector3();

    public PerspectiveCamera getMovingCamera() {
        return movingCamera;
    }

    public void setMovingCamera(PerspectiveCamera movingCamera) {
        this.movingCamera = movingCamera;
    }

    public PerspectiveCamera getCollisionCamera() {
        return collisionCamera;
    }

    public void setCollisionCamera(PerspectiveCamera collisionCamera) {
        this.collisionCamera = collisionCamera;
    }

    private void initMovingCamera() {
        movingCamera.position.set(700f, 500f, 700f);

        movingCamera.lookAt(0.0f, 0, 0.0f);
        movingCamera.near = 0.0000001f;
        movingCamera.far = 1000;
        movingCamera.update();
    }

    private void initCollisionCamera() {
        collisionCamera.position.set(400f, 400f, 550f);
        collisionCamera.lookAt(0, 0, 0);
        //		collisionCamera.near = 0.0001f;
        collisionCamera.far = 1300;
        collisionCamera.update();
    }

    private void updateCollisionCamera(PerspectiveCamera camera) {
        collisionCamera.position.set(camera.position);
        collisionCamera.direction.set(camera.direction);
        collisionCamera.up.set(camera.up);
        collisionCamera.update();
    }


    private void updateMovingCamera(Vector3 position, Vector3 lookAt, Vector3 up){
        movingCamera.position.set(position);
        movingCamera.lookAt(lookAt);
        movingCamera.up.set(up);
        movingCamera.update();
    }

    public void setFirstPersonActor(Actor3d actor){
        UiUtils.InvokeOnUiThread(()-> {
            _firstPersonActor = actor;
            _firstPersonActor.body.getAabb(_tempV3_1,_tempV3);
            _aabHeight = _tempV3.y;
        });
    }

    public void resetFirstPersonActor(){
        UiUtils.InvokeOnUiThread(()->{
            _firstPersonActor.body.getWorldTransform().getTranslation(_tempV3);
            _firstPersonActor = null;

            movingCamera.translate(_tempV3_1.set(movingCamera.direction).scl(-400f));
            movingCamera.position.y = _aabHeight + 300;
            movingCamera.lookAt(_tempV3);

            movingCamera.up.set(0f,1f,0f);
            updateCollisionCamera(movingCamera);
        });
    }

    public void update() {
        if(_firstPersonActor != null) {
            Matrix4 worldTransform = _firstPersonActor.body.getWorldTransform();
            worldTransform.getTranslation(_position);

            _position.y += (_aabHeight*0.8f);
            setCameraPosition(_position);
        }
        movingCamera.update();
        updateCollisionCamera(movingCamera);
    }

    public void setCameraPosition(Vector3 position){
        movingCamera.position.set(position);
    }

    public boolean zoom(float initialDistance, float distance) {
        boolean isZoomIn = initialDistance < distance;
        if (isZoomIn) {
            movingCamera.translate(_tempV3.set(movingCamera.direction).scl(zoomUnits));
        } else {
            movingCamera.translate(_tempV3.set(movingCamera.direction).scl(-zoomUnits));
        }
        return true;
    }

    public void translateXY(Vector3 tmpV1, Vector3 tmpV2, float deltaX, float deltaY) {
        movingCamera.translate(tmpV1.set(movingCamera.direction).crs(movingCamera.up).nor()
                .scl(-deltaX * translateUnits));
        movingCamera.translate(tmpV2.set(movingCamera.up).scl(deltaY * translateUnits));
        target.add(tmpV1).add(tmpV2);
    }

    public void updateRotationXY(float rotateDeltaX, float rotateDeltaY) {
        _tempV3.set(movingCamera.direction).crs(movingCamera.up).y = 0f;
        movingCamera.rotateAround(target, _tempV3.nor(), rotateDeltaY * rotateAngle);
        movingCamera.rotateAround(target, Vector3.Y, rotateDeltaX * -rotateAngle);
        movingCamera.update();
    }

    public boolean isFirstPersonActive(){
        return _firstPersonActor != null;
    }
}
