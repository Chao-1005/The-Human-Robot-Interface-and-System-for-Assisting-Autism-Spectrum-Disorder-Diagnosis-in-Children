package com.example.autotakepic_and_upload;

import android.os.Bundle;
import android.os.Handler;
import android.view.TextureView;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CameraActivity extends AppCompatActivity {
    private CameraHelper cameraHelper;
    private TextureView textureView;
    private ImageView image;
    private Handler timerUpdateHandler;
    private boolean timerRunning = false;
    private int currentTimer = 3;
    private Button btnSelect;

    // instance for firebase storage and StorageReference

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);//硬件加速
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//保持常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//全屏，包含系统状态栏

        setContentView(R.layout.activity_camera);
        textureView = findViewById(R.id.textureView);
        cameraHelper = new CameraHelper(this, textureView);

        /*ImageButton imageButton = findViewById(R.id.btnTakePic);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraHelper.takePic();
            }
        });*/
      /* ImageView imageView = findViewById(R.id.ivExchange);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraHelper.exchangeCamera();
            }
        });*/
        image = findViewById(R.id.image);
        initBrightness();
        timerUpdateHandler = new Handler();

        if (!timerRunning) {
            timerRunning = true;
            timerUpdateHandler.post(timerUpdateTask);
        }


    }
    private Runnable timerUpdateTask = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub


            if (currentTimer > 0) {
                currentTimer--;
                timerUpdateHandler.postDelayed(timerUpdateTask, 1000);
            } else {
                cameraHelper.takePic();
               timerRunning = false;
                currentTimer = 3;



                }
            }


    };

    /**
     * 初始化屏幕亮度，不到200自动调整到200
     */
    private void initBrightness() {
        int brightness = BrightnessTools.getScreenBrightness(this);
        if (brightness < 200) {
            BrightnessTools.setBrightness(this, 200);
        }

    }

    /*@Override
    protected void onDestroy() {
        super.onDestroy();
        cameraHelper.releaseThread();
    }*/

}
