package com.tzduan.study.fragment.library;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.tzduan.study.fragment.R;
import com.tzduan.study.fragment.utils.DialogUtils;

import java.util.Random;

/**
 * Created by tzduan on 17/11/8.
 */

public class PIPController {
    private AppCompatActivity mContext;
    private LinearLayout fragmentContainer;

    public PIPController(AppCompatActivity appCompatActivity){
        this.mContext = appCompatActivity;
        initViews();
    }

    private void initViews() {
        RelativeLayout rootFrontView = new RelativeLayout(mContext);
        fragmentContainer = new LinearLayout(mContext);
        fragmentContainer.setOrientation(LinearLayout.VERTICAL);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int)mContext.getResources().getDimension(R.dimen.DP_160), (int)mContext.getResources().getDimension(R.dimen.DP_90));
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParams.rightMargin = mContext.getResources().getDimensionPixelOffset(R.dimen.DP_16);
        layoutParams.bottomMargin = mContext.getResources().getDimensionPixelOffset(R.dimen.DP_16);
        fragmentContainer.setId(R.id.pip_fragment_container);
        Random random = new Random();
        int backgroundColor = 0xff000000 | random.nextInt(0x00ffffff);
        fragmentContainer.setBackgroundColor(backgroundColor);
        rootFrontView.addView(fragmentContainer, layoutParams);
        fragmentContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showAlert(mContext.getSupportFragmentManager());
            }
        });
        mContext.addContentView(rootFrontView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }
}
