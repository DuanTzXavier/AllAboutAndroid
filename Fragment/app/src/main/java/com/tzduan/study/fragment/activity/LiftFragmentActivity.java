package com.tzduan.study.fragment.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tzduan.study.fragment.callback.AddFragmentCallback;
import com.tzduan.study.fragment.R;
import com.tzduan.study.fragment.fragment.LiftFragment;
import com.tzduan.study.fragment.library.PIPController;
import com.tzduan.study.fragment.utils.AnimationUtils;
import com.tzduan.study.fragment.utils.DialogUtils;
import com.tzduan.study.fragment.utils.Utils;

import java.util.Random;
import java.util.Stack;

/**
 * Created by tzduan on 17/11/6.
 */

public class LiftFragmentActivity extends AppCompatActivity implements AddFragmentCallback {
    private LinearLayout mFragmentLayout;

    private int mFragmentNumb = 0;

    private Stack<Integer> backEntryHeight = new Stack<>();
    private Stack<Integer> backEntryColor = new Stack<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lift_fragment_activity_layout);
        initViews();
        new PIPController(this);
    }

    private void initViews() {
        mFragmentLayout = (LinearLayout) findViewById(R.id.ll_lift_fragment_container);
        mFragmentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentLayout.startAnimation(AnimationUtils.liftAnimation(getBaseContext(), mFragmentLayout.getHeight(), mFragmentLayout.getHeight() - 100, mFragmentLayout));
            }
        });
        addFragmentWithoutAnimation();
    }

    private void addFragmentWithoutAnimation() {
        addFragment(false);
    }

    @Override
    public void addFragment(int distanceToTop) {
        mFragmentLayout.setBackgroundColor(backEntryColor.lastElement());

        addFragment(true);

        int targetHeight = Utils.getSreenHeight(this) - distanceToTop;
        backEntryHeight.push(mFragmentLayout.getHeight());
        mFragmentLayout.startAnimation(AnimationUtils.liftAnimation(getBaseContext(), mFragmentLayout.getHeight(), targetHeight, mFragmentLayout));
    }

    @Override
    public void onBackPressed() {
        if (!backEntryColor.empty()){
            mFragmentLayout.setBackgroundColor(backEntryColor.pop());
        }
        if (!backEntryHeight.empty()){
            mFragmentLayout.startAnimation(AnimationUtils.liftAnimation(getBaseContext(), mFragmentLayout.getHeight(), backEntryHeight.pop(), mFragmentLayout));
        }

        if (getSupportFragmentManager().getBackStackEntryCount() == 1){
            finish();
        }else {
            getSupportFragmentManager().popBackStackImmediate();
        }
    }

    private void addFragment(boolean isAnimation){
        Random random = new Random();
        int backgroundColor = 0xff000000 | random.nextInt(0x00ffffff);
        LiftFragment fragment = LiftFragment.getInstant(
                this,
                "Fragment" + mFragmentNumb,
                backgroundColor);
        backEntryColor.push(backgroundColor);
        if (isAnimation){
            Utils.addFragment(
                    getSupportFragmentManager(),
                    R.id.ll_lift_fragment_container,
                    fragment,
                    LiftFragment.class.getClass().getName());
        }else {
            Utils.addFragmentWithoutAnimation(
                    getSupportFragmentManager(),
                    R.id.ll_lift_fragment_container,
                    fragment,
                    LiftFragment.class.getClass().getName());
        }
        mFragmentNumb ++;
    }
}
