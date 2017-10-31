package com.tzduan.study.fragment.dialog.callback;

/**
 * Created by tzduan on 17/10/31.
 */

public interface AlertCallBack extends DialogBaseCallBack {

    /**
     * @param left true  = left
     *             false = right
     */
    void buttonClicked(boolean left);

}
