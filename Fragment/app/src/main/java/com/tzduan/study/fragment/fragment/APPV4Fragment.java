package com.tzduan.study.fragment.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tzduan.study.fragment.LifeCycleController;
import com.tzduan.study.fragment.R;
import com.tzduan.study.fragment.basefragment.BaseFragment;

/**
 * Created by tzduan on 17/10/27.
 */

public class APPV4Fragment extends BaseFragment {

    private int mBackgroudColor = 0;

    public static APPV4Fragment getInstant(LifeCycleController controller, String pageCode, int backgroudColor){
        APPV4Fragment fragment = new APPV4Fragment();
        fragment.mController = controller;
        fragment.mPageCode = pageCode;
        fragment.mBackgroudColor = backgroudColor;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        LinearLayout rootView = new LinearLayout(getContext());
        rootView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        rootView.setBackgroundColor(mBackgroudColor);
        rootView.setGravity(Gravity.CENTER);

        TextView fragmentName = new TextView(getContext());
        fragmentName.setTextColor(getResources().getColor(R.color.color_ffffff));
        fragmentName.setText(mPageCode);
        rootView.addView(fragmentName);

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mController.addFragment();
            }
        });

        return rootView;
    }
}
