package com.sunny.face.activity;

import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.sunny.face.R;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FaceDetectActivity extends BaseActivity implements Callback, PreviewCallback {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.sv_preview)
    SurfaceView svPreview;
    
    private Camera mCamera;
    
    private int width = 640;
    private int height = 480;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_detect);
        ButterKnife.inject(this);

        initToolBar(true, toolbar, R.string.title_detect);
        initData();
    }

    private void initData() {
        svPreview.getHolder().addCallback(this);
        svPreview.setKeepScreenOn(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mCamera = Camera.open(1);
        Camera.Parameters para = mCamera.getParameters();
        para.setPreviewSize(width, height);
        mCamera.setParameters(para);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.setDisplayOrientation(90);
            mCamera.startPreview();
            mCamera.setPreviewCallback(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.setPreviewCallback(null);
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
    }

    @Override
    public void onPreviewFrame(final byte[] data, final Camera camera) {

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}
