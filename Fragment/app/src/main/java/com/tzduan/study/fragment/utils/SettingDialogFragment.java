package com.tzduan.study.fragment.utils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.tzduan.study.fragment.LifeCycleController;
import com.tzduan.study.fragment.R;
import com.tzduan.study.fragment.SettingDialogCallback;
import com.tzduan.study.fragment.basefragment.BaseFragment;

/**
 * Created by tzduan on 17/10/31.
 */

public class SettingDialogFragment extends BaseFragment {

    private SettingDialogCallback mCallBack;

    public static SettingDialogFragment getInstant(LifeCycleController controller, String pageCode, SettingDialogCallback callback){
        SettingDialogFragment fragment = new SettingDialogFragment();
        fragment.mController = controller;
        fragment.mPageCode = pageCode;
        fragment.mCallBack = callback;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.setting_dialog_fragment, null);
        final CheckBox cancelable = rootView.findViewById(R.id.cb_setting_dialog_cancelable);
        final CheckBox cancelableOntouch = rootView.findViewById(R.id.cb_setting_dialog_cancelable);
        cancelable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mCallBack.setCancelable(cancelable.isChecked(), cancelableOntouch.isChecked());
            }
        });
        cancelableOntouch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mCallBack.setCancelable(cancelable.isChecked(), cancelableOntouch.isChecked());
            }
        });
        return rootView;
    }
}
