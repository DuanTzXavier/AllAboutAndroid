package com.tzduan.study.fragment.utils;

import android.support.v4.app.FragmentManager;

import com.tzduan.study.fragment.dialog.callback.AlertCallBack;
import com.tzduan.study.fragment.dialog.model.AlertViewData;
import com.tzduan.study.fragment.dialog.model.DialogViewTypeModel;
import com.tzduan.study.fragment.fragment.APPV4DialogFragment;

/**
 * Created by tzduan on 17/11/8.
 */

public class DialogUtils {

    public static void showAlert(FragmentManager supportFragmentManager){
        DialogViewTypeModel model = new DialogViewTypeModel();
        model.setmCallBack(new AlertCallBack() {
            @Override
            public void buttonClicked(boolean left) {
                Utils.showToast(left ? "说对不起了么" : "哈哈哈，继续继续");
            }
        });
        AlertViewData viewData = new AlertViewData();
        viewData.alertMessage = "你点到我了";
        viewData.leftButton = "不小心的";
        viewData.rightButton = "是的呢";
        model.setmViewData(viewData);
        APPV4DialogFragment fragment = APPV4DialogFragment.getInstant(model);
        Utils.addDialogFragment(supportFragmentManager, fragment);
    }
}
