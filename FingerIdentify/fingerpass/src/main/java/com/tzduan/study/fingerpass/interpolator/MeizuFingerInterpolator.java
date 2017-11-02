package com.tzduan.study.fingerpass.interpolator;

import android.content.Context;

import com.fingerprints.service.FingerprintManager;
import com.tzduan.study.fingerpass.FingerIdentifyCallBack;

/**
 * Created by tzduan on 17/11/1.
 */

public class MeizuFingerInterpolator extends BaseFingerInterpolator {

    private FingerprintManager mFM;
    private FingerprintManager.IdentifyCallback mIdentifyCallback = new FingerprintManager.IdentifyCallback() {
        @Override
        public void onIdentified(int i, boolean b) {
            mFM.release();
            sendResult(FINGER_IDENTIFY_SUCCESS);
        }

        @Override
        public void onNoMatch() {
            mFM.release();
            sendResult(FINGER_IDENTIFY_ERROR_AUTHENTIFICATION_FAILED);
        }
    };

    public MeizuFingerInterpolator(Context context, FingerIdentifyCallBack callBack) {
        super(context, callBack);
        mFM = FingerprintManager.open();
    }

    @Override
    public boolean isFingerIdentifyEnabled() {
        return mFM.isSurpport() && mFM.isFingerEnable();
    }

    @Override
    public boolean hasEnrolledFingerprints() {
        return mFM.getIds() == null;
    }

    @Override
    public void startFingerPrints() {
        mFM.startIdentify(mIdentifyCallback, mFM.getIds());
    }

    @Override
    public void stopFingerPrints() {
        mFM.abort();
    }
}
