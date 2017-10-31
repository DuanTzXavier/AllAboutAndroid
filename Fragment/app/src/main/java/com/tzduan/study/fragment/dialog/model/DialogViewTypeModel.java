package com.tzduan.study.fragment.dialog.model;

import com.tzduan.study.fragment.dialog.callback.DialogBaseCallBack;

/**
 * Created by tzduan on 17/10/31.
 */

public class DialogViewTypeModel {
    private DialogBaseCallBack mCallBack;
    private DialogViewData mViewData;
    public DialogBaseCallBack getmCallBack() {
        return mCallBack;
    }

    public void setmCallBack(DialogBaseCallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    public DialogViewData getmViewData() {
        return mViewData;
    }

    public void setmViewData(DialogViewData mViewData) {
        this.mViewData = mViewData;
    }
}
