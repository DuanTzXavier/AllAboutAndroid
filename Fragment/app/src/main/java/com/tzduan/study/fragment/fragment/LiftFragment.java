package com.tzduan.study.fragment.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tzduan.study.fragment.AddFragmentCallback;
import com.tzduan.study.fragment.R;
import com.tzduan.study.fragment.basefragment.BaseFragment;

import java.util.Random;

/**
 * Created by tzduan on 17/11/6.
 */

public class LiftFragment extends BaseFragment {
    private AddFragmentCallback mCallback;

    private int mBackgroudColor = 0;

    public static LiftFragment getInstant(AddFragmentCallback callback, String pageCode, int backgroudColor){
        LiftFragment fragment = new LiftFragment();
        fragment.mCallback = callback;
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

                if (mCallback != null){
                    Random random = new Random();
                    int type = random.nextInt(101) % 3;
                    int distance = 0;
                    switch (type){
                        case 0:
                            distance = 0;
                            break;
                        case 1:
                            distance = getResources().getDimensionPixelOffset(R.dimen.DP_155);
                            break;
                        case 2:
                            distance = getResources().getDimensionPixelOffset(R.dimen.DP_250);
                            break;
                        default:
                            break;
                    }
                    mCallback.addFragment(distance);
                }
            }
        });

        return rootView;
    }

}
