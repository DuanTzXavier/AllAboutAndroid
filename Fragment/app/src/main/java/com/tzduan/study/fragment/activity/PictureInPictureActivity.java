package com.tzduan.study.fragment.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.tzduan.study.fragment.R;
import com.tzduan.study.fragment.baseactivity.PIPBaseActivity;
import com.tzduan.study.fragment.library.PIPController;
import com.tzduan.study.fragment.library.SlideViewOnTouchController;

import java.util.Random;


/**
 * Created by tzduan on 17/11/8.
 */

public class PictureInPictureActivity extends PIPBaseActivity {
    private LinearLayout pipFragmentContainer;
    private LinearLayout llContainer;

    private PIPController mPip;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.pip_activity_layout);
        super.onCreate(savedInstanceState);
        initViews();
    }

    private void initViews() {
//        llContainer = (LinearLayout) findViewById(R.id.ll_container);
        pipFragmentContainer = (LinearLayout) findViewById(R.id.ll_pip_fragment_container);
        Random random = new Random();
        int backgroundColor = 0xff000000 | random.nextInt(0x00ffffff);
        pipFragmentContainer.setBackgroundColor(backgroundColor);
        pipFragmentContainer.post(new Runnable() {
            @Override
            public void run() {
                pipFragmentContainer.setOnTouchListener(new SlideViewOnTouchController(pipFragmentContainer, getApplicationContext()));
            }
        });
//        mPip = new PIPController(this);
    }
}
