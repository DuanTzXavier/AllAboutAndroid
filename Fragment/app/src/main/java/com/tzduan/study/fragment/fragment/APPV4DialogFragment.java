package com.tzduan.study.fragment.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tzduan.study.fragment.basefragment.BaseDialogFragment;
import com.tzduan.study.fragment.dialog.DialogViewController;
import com.tzduan.study.fragment.dialog.callback.AlertCallBack;
import com.tzduan.study.fragment.dialog.model.DialogViewTypeModel;

/**
 * Created by tzduan on 17/10/27.
 */

public class APPV4DialogFragment extends BaseDialogFragment implements AlertCallBack {

    private DialogViewTypeModel mModel;

    private AlertCallBack mCallBack;

    public static APPV4DialogFragment getInstant(DialogViewTypeModel model){
        APPV4DialogFragment fragment = new APPV4DialogFragment();
        fragment.mModel = model;
        fragment.mCallBack = (AlertCallBack) fragment.mModel.getmCallBack();
        fragment.mModel.setmCallBack(fragment);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        DialogViewController controller = new DialogViewController(getContext(), mModel);
        return controller.buildDialogView();
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().setCancelable(mModel.getmViewData().cancelable);
        getDialog().setCanceledOnTouchOutside(mModel.getmViewData().canceledOnTouchOutside);
    }

    @Override
    public void buttonClicked(boolean left) {
        dismiss();
        mCallBack.buttonClicked(left);
    }
}
