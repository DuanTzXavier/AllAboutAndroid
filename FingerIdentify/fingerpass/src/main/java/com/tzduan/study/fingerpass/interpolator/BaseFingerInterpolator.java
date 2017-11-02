package com.tzduan.study.fingerpass.interpolator;

import android.content.Context;

import com.tzduan.study.fingerpass.FingerIdentifyCallBack;

/**
 * Created by tzduan on 17/11/1.
 */

public abstract class BaseFingerInterpolator {
    public final static int FINGER_IDENTIFY_SUCCESS = 0;
    public final static int FINGER_IDENTIFY_ERROR_USER_CANCEL = 1;
    public final static int FINGER_IDENTIFY_ERROR_HW_UNAVAILABLE = 2;
    public final static int FINGER_IDENTIFY_ERROR_UNABLE_TO_PROCESS = 3;
    public final static int FINGER_IDENTIFY_ERROR_TIMEOUT = 4;
    public final static int FINGER_IDENTIFY_ERROR_NO_SPACE = 5;
    public final static int FINGER_IDENTIFY_ERROR_LOCKOUT = 6;
    public final static int FINGER_IDENTIFY_ERROR_OTHER = 7;
    public final static int FINGER_IDENTIFY_ERROR_AUTHENTIFICATION_FAILED = 8;

    protected FingerIdentifyCallBack mCallBack;
    protected Context mContext;

    public BaseFingerInterpolator(Context context, FingerIdentifyCallBack callBack) {
        this.mCallBack = callBack;
        this.mContext = context;
    }

    protected void sendResult(int result){
        if (mCallBack != null){
            mCallBack.onIdentifyResult(result);
        }
    }

    public abstract boolean isFingerIdentifyEnabled();

    public abstract boolean hasEnrolledFingerprints();

    public abstract void startFingerPrints();

    public abstract void stopFingerPrints();
}
