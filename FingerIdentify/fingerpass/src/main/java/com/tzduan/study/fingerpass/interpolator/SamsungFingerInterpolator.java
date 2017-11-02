package com.tzduan.study.fingerpass.interpolator;

import android.content.Context;
import android.util.Log;

import com.samsung.android.sdk.pass.Spass;
import com.samsung.android.sdk.pass.SpassFingerprint;
import com.samsung.android.sdk.pass.SpassFingerprint.IdentifyListener;
import com.tzduan.study.fingerpass.FingerIdentifyCallBack;

public class SamsungFingerInterpolator extends BaseFingerInterpolator {
    private SpassFingerprint mSpassFingerprint;
    private Spass mSpass;
    private boolean isFingerEnable = true;

    public SamsungFingerInterpolator(Context context, FingerIdentifyCallBack callBack) {
        super(context, callBack);
        if (mContext == null) {
            return;
        }
        mSpass = new Spass();
        try {
            mSpass.initialize(mContext);
        } catch (Exception e) {
            isFingerEnable = false;
        }
        mSpassFingerprint = new SpassFingerprint(mContext);
    }


    @Override
    public boolean isFingerIdentifyEnabled() {
        if (isFingerEnable){
            isFingerEnable = mSpass.isFeatureEnabled(Spass.DEVICE_FINGERPRINT);
        }
        return isFingerEnable;
    }

    @Override
    public boolean hasEnrolledFingerprints() {
        return mSpassFingerprint.hasRegisteredFinger();
    }

    @Override
    public void startFingerPrints() {
        if (!couldStartIdentify()) return;
        mSpassFingerprint.startIdentify(mIdentifyListener);
    }

    private IdentifyListener mIdentifyListener = new IdentifyListener() {
        @Override
        public void onFinished(int eventStatus) {
            switch (eventStatus) {
                case SpassFingerprint.STATUS_AUTHENTIFICATION_SUCCESS:
                case SpassFingerprint.STATUS_AUTHENTIFICATION_PASSWORD_SUCCESS:
                    sendResult(FINGER_IDENTIFY_SUCCESS);
                    break;
                case SpassFingerprint.STATUS_USER_CANCELLED:
                case SpassFingerprint.STATUS_USER_CANCELLED_BY_TOUCH_OUTSIDE:
                    sendResult(FINGER_IDENTIFY_ERROR_USER_CANCEL);
                    break;
                case SpassFingerprint.STATUS_TIMEOUT_FAILED:
                    sendResult(FINGER_IDENTIFY_ERROR_TIMEOUT);
                    break;
                case SpassFingerprint.STATUS_SENSOR_FAILED:
                    sendResult(FINGER_IDENTIFY_ERROR_HW_UNAVAILABLE);
                    break;
                case SpassFingerprint.STATUS_OPERATION_DENIED:
                    sendResult(FINGER_IDENTIFY_ERROR_LOCKOUT);
                    break;
                case SpassFingerprint.STATUS_AUTHENTIFICATION_FAILED:
                case SpassFingerprint.STATUS_QUALITY_FAILED:
                    sendResult(FINGER_IDENTIFY_ERROR_AUTHENTIFICATION_FAILED);
                    break;
                case SpassFingerprint.STATUS_BUTTON_PRESSED:
                default:
                    sendResult(FINGER_IDENTIFY_ERROR_OTHER);
                    break;
            }
        }
        @Override public void onReady() {}
        @Override public void onStarted() {}
        @Override public void onCompleted() {}
    };

    @Override
    public void stopFingerPrints() {
        if (mSpassFingerprint != null) {
            mSpassFingerprint.cancelIdentify();
        }
    }

    private boolean couldStartIdentify(){
        return isFingerIdentifyEnabled() && hasEnrolledFingerprints();
    }
}
