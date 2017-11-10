package com.tzduan.study.fragment.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tzduan.study.fragment.R;
import com.tzduan.study.fragment.basefragment.PIPBaseFragment;

/**
 * Created by tzduan on 17/11/8.
 */

public class PIPFragment extends PIPBaseFragment {

    private int mBackgroudColor = 0;

    public static PIPFragment getInstant(String pageCode, int backgroudColor){
        PIPFragment fragment = new PIPFragment();
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

            }
        });

        return rootView;
    }
}
