package lty.buu.irrigation.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import android.widget.ImageView;
import android.widget.Toast;


import org.json.JSONObject;

import lty.buu.irrigation.IrrigationApplication;
import lty.buu.irrigation.R;
import lty.buu.irrigation.http.aip.AipUtils;


public class CameraAnlysisActivity extends Activity {
    Camera.Parameters parameters;
    IrrigationApplication app;
    public String TAG = "CameraSignInActivity";

    private ImageView btn;
    //   private ImageView iv;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private Camera camera;
    private Camera.ShutterCallback shutter;
    private Camera.PictureCallback jepg;
    Intent it;
    Bundle datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (IrrigationApplication) getApplication();
            setContentView(R.layout.activity_flowerpic_main);

            initview();




    }

    private void initview() {


        btn = findViewById(R.id.test);
        surfaceView = (SurfaceView) findViewById(R.id.cameraSV);
        surfaceHolder = surfaceView.getHolder();
        SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                camera.stopPreview();
                camera.release();
                camera = null;
            }

            //获取前置摄像头
            public Camera getCamera() {
                camera = null;
                Camera.CameraInfo info = new Camera.CameraInfo();
                int cnt = Camera.getNumberOfCameras();
                for (int i = 0; i < cnt; i++) {
                    Camera.getCameraInfo(i, info);

                    if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                        try {
                            camera = Camera.open(i);
                            Log.e("fontcamerainfo",i+"");
                        } catch (RuntimeException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return camera;
            }



            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                camera = getCamera();
                try {
                    camera.setPreviewDisplay(holder);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                camera.startPreview();
            }

            @SuppressWarnings("deprecation")
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                parameters = camera.getParameters();
                parameters.setPictureSize(640, 480);
                parameters.getFocusMode();
                parameters.setPictureFormat(PixelFormat.JPEG);
                camera.autoFocus(new Camera.AutoFocusCallback() {
                    @Override
                    public void onAutoFocus(boolean b, Camera camera) {
                        camera.cancelAutoFocus();// 只有加上了这一句，才会自动对焦
                        doAutoFocus();
                    }
                });
                camera.setParameters(parameters);
                //
                camera.setDisplayOrientation(90);
                camera.startPreview();
            }
        };

        surfaceHolder.addCallback(surfaceCallback);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        // 设置显示图片
        jepg = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(final byte[] data, Camera camera) {
                camera.startPreview();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                       final JSONObject getinfo= AipUtils.flowerinfo(data);
                      // Log.e("getinfo",getinfo.toString());
                                Intent it = new Intent(CameraAnlysisActivity.this,Activity_showFlower.class);
                                it.putExtra("data",getinfo.toString());
                                startActivity(it);
                                finish();

                    }
                }).start();

                final Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);
                //  iv.setImageBitmap(bm);
                Toast.makeText(CameraAnlysisActivity.this, "拍照成功！", Toast.LENGTH_SHORT).show();


            }
        };

        shutter = new Camera.ShutterCallback() {
            @Override
            public void onShutter() {
                // Toast.makeText(getApplicationContext(), "成功拍照", Toast.LENGTH_SHORT).show();

            }
        };
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camera.takePicture(shutter, null, jepg);
            }
        });


    }

    private void doAutoFocus() {
        parameters = camera.getParameters();
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        camera.setParameters(parameters);
        camera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                if (success) {
                    camera.cancelAutoFocus();// 只有加上了这一句，才会自动对焦。
                    if (!Build.MODEL.equals("KORIDY H30")) {
                        parameters = camera.getParameters();
                        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);// 1连续对焦
                        camera.setParameters(parameters);
                    }else{
                        parameters = camera.getParameters();
                        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                        camera.setParameters(parameters);
                    }
                }
            }
        });
    }


}
