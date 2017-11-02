package com.tzduan.study.fingerpass;

import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;

import com.tzduan.study.fingerpass.interpolator.BaseFingerInterpolator;
import com.tzduan.study.fingerpass.interpolator.MFingerInterpolator;
import com.tzduan.study.fingerpass.interpolator.MeizuFingerInterpolator;
import com.tzduan.study.fingerpass.interpolator.SamsungFingerInterpolator;
import com.tzduan.study.fingerpass.root.Root;

/**
 * Created by tzduan on 17/10/31.
 */

public class FingerIdentify {
    public static final int SUPPORT = 0;
    public static final int NOT_SUPPORT = -1;
    public static final int ROOTED = -2;

    private Context mContext;

    private FingerIdentifyCallBack mCallBack;
    private BaseFingerInterpolator mInterpolator;

    private boolean isSupportRoot = false;

    public FingerIdentify(Context context, FingerIdentifyCallBack callBack){
        mContext = context;
        mCallBack = callBack;

        initInterpolator();
    }

    private void initInterpolator() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mInterpolator = new MFingerInterpolator(mContext, mCallBack);
            if (!mInterpolator.isFingerIdentifyEnabled()) {
                mInterpolator = getFingerInterpolatorByBrand();
            }
        } else {
            mInterpolator = getFingerInterpolatorByBrand();
        }
    }

    public int isSupportFinger(){
        if (Root.isDeviceRooted() && !isSupportRoot) return ROOTED;

        if (mInterpolator == null){
            return NOT_SUPPORT;
        }else if (!mInterpolator.isFingerIdentifyEnabled()){
            return NOT_SUPPORT;
        }

        return SUPPORT;
    }

    private BaseFingerInterpolator getFingerInterpolatorByBrand() {
        BaseFingerInterpolator interpolator = null;
        if (!TextUtils.isEmpty(android.os.Build.BRAND)){
            if (isSamsungDevice()){
                interpolator = new SamsungFingerInterpolator(mContext, mCallBack);
            }
            if (isMEIZUDevice()){
                interpolator = new MeizuFingerInterpolator(mContext, mCallBack);
            }
        }
        return interpolator;
    }

    private boolean isSamsungDevice() {
        return Build.BRAND.toLowerCase().equals("samsung");
    }

    private boolean isMEIZUDevice() {
        return Build.BRAND.toLowerCase().equals("meizu");
    }

    public boolean hasEnrolledFingerprints() {
        return isSupportFinger() == SUPPORT && mInterpolator.hasEnrolledFingerprints();
    }

    public boolean startFingerIdentify(){
        if (isSupportFinger() == SUPPORT && mInterpolator.hasEnrolledFingerprints()){
            mInterpolator.startFingerPrints();
            return true;
        }else {
            return false;
        }
    }

    public void stopFingerIdentify(){
        if (isSupportFinger() == SUPPORT){
            mInterpolator.stopFingerPrints();
        }

    }

    public void setSupportRoot(boolean supportRoot) {
        isSupportRoot = supportRoot;
    }

    public boolean isSupportRoot() {
        return isSupportRoot;
    }

    public void setHandler(Handler handler){
        if (mInterpolator instanceof MFingerInterpolator){
            ((MFingerInterpolator) mInterpolator).setHandler(handler);
        }
    }

    public void setCryptoObject(FingerprintManager.CryptoObject cryptoObject){
        if (mInterpolator instanceof MFingerInterpolator){
            ((MFingerInterpolator) mInterpolator).setCryptoObject(cryptoObject);
        }
    }
}
