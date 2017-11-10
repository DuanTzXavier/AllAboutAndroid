package com.tzduan.study.fragment.baseactivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tzduan.study.fragment.R;
import com.tzduan.study.fragment.basefragment.PIPBaseFragment;
import com.tzduan.study.fragment.fragment.PIPFragment;
import com.tzduan.study.fragment.library.PIPController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by tzduan on 17/11/8.
 */

public class PIPBaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pip_activity_layout);
        initRootView();
    }


    private void initRootView() {

    }


}
