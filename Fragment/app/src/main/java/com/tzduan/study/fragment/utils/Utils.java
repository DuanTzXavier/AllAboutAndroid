package com.tzduan.study.fragment.utils;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.tzduan.study.fragment.BaseApplication;
import com.tzduan.study.fragment.R;
import com.tzduan.study.fragment.basefragment.BaseFragment;

/**
 * Created by tzduan on 17/10/27.
 */

public class Utils {

    /**
     * 添加Fragment在布局id为contentID的布局上
     * @param fragmentManager   fragmentManager
     * @param contentID         要添加的布局的ID
     * @param targetFragment    要添加的targetFragment
     */
    public static void addFragment(FragmentManager fragmentManager, int contentID, BaseFragment targetFragment, String targetFragmentTag){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fragment_in, R.anim.fragment_out, R.anim.fragment_close_in, R.anim.fragment_close_out);
        Fragment fragment = fragmentManager.findFragmentById(contentID); //先获取当前布局ID上的fragment

        if(fragment != null) { //若fragment不为null，则将此fragment hide
            transaction.hide(fragment);
        }

        transaction.add(contentID, targetFragment, targetFragmentTag); //为布局ID为contentID的布局添加targetFragment
        transaction.addToBackStack(targetFragmentTag); //在BackStack中添加targetFragment tag
        transaction.commitAllowingStateLoss();
    }

    public static void addFragmentWithoutAnimation(FragmentManager fragmentManager, int contentID, BaseFragment targetFragment, String targetFragmentTag){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentById(contentID); //先获取当前布局ID上的fragment

        if(fragment != null) { //若fragment不为null，则将此fragment hide
            transaction.hide(fragment);
        }

        transaction.add(contentID, targetFragment, targetFragmentTag); //为布局ID为contentID的布局添加targetFragment
        transaction.addToBackStack(targetFragmentTag); //在BackStack中添加targetFragment tag
        transaction.commitAllowingStateLoss();
    }

    /**
     * 添加Fragment在布局id为contentID的布局上
     * @param fragmentManager   fragmentManager
     * @param targetFragment    要添加的targetFragment
     */
    public static void addDialogFragment(FragmentManager fragmentManager, Fragment targetFragment){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(targetFragment, targetFragment.getTag());
        transaction.commitAllowingStateLoss();
    }

    public static void removeFragment(FragmentManager fragmentManager, String targetFragmentTag){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentByTag(targetFragmentTag);
        transaction.remove(fragment);
        transaction.commitAllowingStateLoss();
    }

    public static void showToast(String toastString){
        Toast.makeText(BaseApplication.mContext, toastString, Toast.LENGTH_SHORT).show();
    }

    public static int getSreenHeight(Activity activity){
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }
}
