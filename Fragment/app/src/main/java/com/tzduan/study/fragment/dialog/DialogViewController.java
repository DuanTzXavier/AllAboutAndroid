package com.tzduan.study.fragment.dialog;

import android.content.Context;
import android.view.View;

import com.tzduan.study.fragment.dialog.callback.AlertCallBack;
import com.tzduan.study.fragment.dialog.model.AlertViewData;
import com.tzduan.study.fragment.dialog.model.DialogViewTypeModel;
import com.tzduan.study.fragment.dialog.view.AlertView;

/**
 * Created by tzduan on 17/10/31.
 */

public class DialogViewController {
    private Context mContext;
    private DialogViewTypeModel mModel;

    public DialogViewController(Context context, DialogViewTypeModel model){
        mContext = context;
        mModel = model;
    }

    public View buildDialogView() {
        View dialogView = null;
        if (mModel.getmCallBack() instanceof AlertCallBack && mModel.getmViewData() instanceof AlertViewData) {
            dialogView = new AlertView(mContext, (AlertCallBack) mModel.getmCallBack(), (AlertViewData) mModel.getmViewData());
        }

        return dialogView;
    }

}
