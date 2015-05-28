package com.sunny.face.activity;

import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.widget.Toolbar;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.faceplusplus.api.FaceDetecter;
import com.faceplusplus.api.FaceDetecter.Face;
import com.sunny.face.R;
import com.sunny.face.constant.Global;
import com.sunny.face.view.FaceMask;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FaceTrackActivity extends BaseActivity implements Callback, PreviewCallback {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.sv_preview)
    SurfaceView svPreview;
    @InjectView(R.id.fm_mask)
    FaceMask fmMask;
    
    private Camera mCamera;
    private HandlerThread handleThread;
    private Handler detectHandler;
    
    private int width = 640;
    private int height = 480;
    private FaceDetecter facedetecter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_track);
        ButterKnife.inject(this);

        initToolBar(true, toolbar, R.string.title_track);
        initData();
    }

    private void initData() {
        handleThread = new HandlerThread("dt");
        handleThread.start();
        detectHandler = new Handler(handleThread.getLooper());
        svPreview.getHolder().addCallback(this);
        svPreview.setKeepScreenOn(true);

        facedetecter = new FaceDetecter();
        facedetecter.init(this, Global.FACEPP_KEY);
        facedetecter.setTrackingMode(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mCamera = Camera.open(1);
        Camera.Parameters para = mCamera.getParameters();
        para.setPreviewSize(width, height);
        mCamera.setParameters(para);
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
        camera.setPreviewCallback(null);
        if (mCamera == null) return;
        detectHandler.post(new Runnable() {
            @Override
            public void run() {
                int is = 0;
                byte[] ori = new byte[width * height];
                for (int x = width - 1; x >= 0; x--) {
                    for (int y = height - 1; y >= 0; y--) {
                        ori[is++] = data[y * width + x];
                    }
                }
                final Face[] faceinfo = facedetecter.findFaces(ori, height, width);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        fmMask.setFaceInfo(faceinfo);
                    }
                });
                try {
                    camera.setPreviewCallback(FaceTrackActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        facedetecter.release(this);
        handleThread.quit();
        detectHandler=null;
    }

}
