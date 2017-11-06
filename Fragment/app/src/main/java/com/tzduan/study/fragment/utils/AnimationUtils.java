package com.tzduan.study.fragment.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.tzduan.study.fragment.R;

/**
 * Created by tzduan on 17/11/6.
 */

public class AnimationUtils {


    public static Animation liftAnimation(Context context, final int fromHeight, final int targetHeight, final View view){
        Animation anim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                int height = (int) ((targetHeight - fromHeight) * interpolatedTime) + fromHeight;
                view.getLayoutParams().height = height;
                view.requestLayout();
            }
        };
        anim.setDuration(context.getResources().getInteger(R.integer.animation_time));
        return anim;
    }
}
