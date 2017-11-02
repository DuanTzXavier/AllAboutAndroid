package com.tzduan.study.fingerpass.interpolator;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.Handler;

import com.tzduan.study.fingerpass.FingerIdentifyCallBack;

/**
 * Created by tzduan on 17/10/31.
 */

@TargetApi(Build.VERSION_CODES.M)
public class MFingerInterpolator extends BaseFingerInterpolator {
    private FingerprintManager mManager;
    private FingerprintManager.CryptoObject mCryptoObject;
    private CancellationSignal mCancellationSignal;
    private FingerprintManager.AuthenticationCallback mAuthenticationCallback;
    private Handler mHandler;

    public MFingerInterpolator(Context context, FingerIdentifyCallBack callBack){
        super(context, callBack);
        initWork();
    }


    private void initWork() {
        mManager = mContext.getSystemService(FingerprintManager.class);
        mCancellationSignal = new CancellationSignal();
        mAuthenticationCallback = new FingerprintManager.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                switch (errorCode) {
                    case FingerprintManager.FINGERPRINT_ERROR_HW_UNAVAILABLE:
                        sendResult(FINGER_IDENTIFY_ERROR_HW_UNAVAILABLE);
                        break;
                    case FingerprintManager.FINGERPRINT_ERROR_UNABLE_TO_PROCESS:
                        sendResult(FINGER_IDENTIFY_ERROR_UNABLE_TO_PROCESS);
                        break;
                    case FingerprintManager.FINGERPRINT_ERROR_TIMEOUT:
                        sendResult(FINGER_IDENTIFY_ERROR_TIMEOUT);
                        break;
                    case FingerprintManager.FINGERPRINT_ERROR_NO_SPACE:
                        sendResult(FINGER_IDENTIFY_ERROR_NO_SPACE);
                        break;
                    case FingerprintManager.FINGERPRINT_ERROR_CANCELED:
                        sendResult(FINGER_IDENTIFY_ERROR_USER_CANCEL);
                        break;
                    case FingerprintManager.FINGERPRINT_ERROR_LOCKOUT:
                        sendResult(FINGER_IDENTIFY_ERROR_LOCKOUT);
                        break;
                    default:
                        sendResult(FINGER_IDENTIFY_ERROR_OTHER);
                        break;
                }
            }
            @Override
            public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                sendResult(FINGER_IDENTIFY_SUCCESS);
            }

            @Override
            public void onAuthenticationFailed() {
                sendResult(FINGER_IDENTIFY_ERROR_AUTHENTIFICATION_FAILED);
            }
        };
    }

    @Override
    public boolean isFingerIdentifyEnabled() {
        return mManager.isHardwareDetected();
    }

    @Override
    public boolean hasEnrolledFingerprints() {
        return mManager.hasEnrolledFingerprints();
    }

    @Override
    public void startFingerPrints() {
        if (!isFingerIdentifyEnabled() || !hasEnrolledFingerprints()) return;

        if (mCancellationSignal.isCanceled()){
            mCancellationSignal = new CancellationSignal();
        }

        mManager.authenticate(mCryptoObject, mCancellationSignal, 0, mAuthenticationCallback, mHandler);
    }

    @Override
    public void stopFingerPrints() {
        mCancellationSignal.cancel();
    }

    public void setCryptoObject(FingerprintManager.CryptoObject cryptoObject) {
        this.mCryptoObject = cryptoObject;
    }

    public void setHandler(Handler handler) {
        this.mHandler = handler;
    }
}
