package com.tzduan.study.fragment.basefragment;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by tzduan on 17/11/8.
 */

public class PIPBaseFragment extends BaseFragment {

    @Override
    public void onStart() {
        super.onStart();
        if (getView() != null){
            getView().setOnTouchListener(mPIPOnTouchListener);
        }
    }

    View.OnTouchListener mPIPOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return false;
        }
    };
}
