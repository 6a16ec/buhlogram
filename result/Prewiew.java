package com.example.user.lapin;

import android.app.Activity;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

/**
 * Created by user on 26.05.18.
 */

public class Preview extends SurfaceView implements SurfaceHolder.Callback {
    SurfaceHolder mHolder;
    Camera mCamera;
    Activity activity;
    final int CAMERA_ID = 0;

    Preview(Activity activity) {
        super(activity);
        this.activity = activity;

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
// The Surface has been created, acquire the camera and tel it where
// to draw.
        mCamera = Camera.open();
        try {
            mCamera.setPreviewDisplay(holder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // Surface will be destroyed when we return, so stop the preview.
        // Because the CameraDevice object is not a shared resource, it's very
        // important to release it when the activity is paused.
        mCamera.stopPreview();
        mCamera = null;
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        Camera.Parameters parameters = mCamera.getParameters();
        List<Camera.Size> previewSizes = parameters.getSupportedPreviewSizes();

        Camera.Size previewSize = previewSizes.get(0);
        parameters.setPreviewSize(previewSize.width, previewSize.height);
        mCamera.setParameters(parameters);
        mCamera.startPreview();

    }





}
