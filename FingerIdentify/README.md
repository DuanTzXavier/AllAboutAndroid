# FingerIdentify

# 导言
Android 6.0 之后开发指纹识别API，供开发者使用。其实在6.0之前就已经有了指纹识别这一功能。Samsung手机几乎在Android 4.2的时候就已经在手机上有指纹识别的传感器。各Android手机厂家为了竞争，也很早就加入了指纹识别这一功能。所以本文涉及的内容就是：
* Android 6.0及之后版本的指纹识别API实践
* Android Samsung 指纹识别API实践
* Android Meizu 指纹识别API实践
并将此指纹识别整合为SDK，供开发者使用。业务逻辑为：

![FingerPass生成实例流程图](http://upload-images.jianshu.io/upload_images/8757041-78f87223368c5877.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# Android M FingerManager API
注：本文只简单解析API的最佳调用方式，并不涉及FingerPrintManger类的解析。
首先需要构造FingerPrintManger的实例，此方法必须在OS Version>=23才能执行。需要权限USE_FINGERPRINT(已在Library 中配置)
控制FingerPrintManger指纹识别取消的是CancellationSignal类，所以构造此实例，用于取消指纹验证操作。
指纹识别会有自己的Callback，为FingerprintManager.AuthenticationCallback。重写三个方法即可：
* `onAuthenticationError` 指纹识别的每一次错误都会回调到这个方法
* `onAuthenticationSucceeded` 指纹识别认证成功调用此方法
* `onAuthenticationFailed` 指纹识别认证失败调用次方法
此外，FingerPrintManger还有三个方法需要注意：
* `isHardwareDetected` 是否支持指纹识别
* `hasEnrolledFingerprints` 是否注册了指纹
* `authenticate` 开始识别

其中`authenticate`方法有五个参数，分别为CryptoObject，CancellationSignal， int， AuthenticationCallback， Handler。 
* CryptoObject 为加密对象，指纹识别过程数据被此CryptoObject加密，如果为null，则不会被加密
* CancellationSignal 为取消信号
* int flag 标志位，暂时默认为0
* AuthenticationCallback 识别的Callback
* Handler 指定Handler来处理指纹识别

关于AuthenticationCallback回调值，请看以下代码：
```java
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
```
# Android Meizu FingerManager API

同FingerPrintManger类类似，Meizu自己也写了一个FingerPrintManger来处理指纹识别的事务，只不过API更为简单。
魅族的FingerprintManager可以直接调用`open()`方法初始化FingerprintManager实例。
通过`mFM.isSurpport() && mFM.isFingerEnable();`来判断是否支持指纹识别
查看是否已注册指纹的方法是：`mFM.getIds() != null`
取消指纹识别：`mFM.abort();`
魅族指纹识别的Callback：FingerprintManager.IdentifyCallback 具体实现方法如下：
```java
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
```
开始指纹识别：`mFM.startIdentify(mIdentifyCallback, mFM.getIds());`


更详细资料参见[flyme文档](http://open-wiki.flyme.cn/index.php?title=指纹识别API)

# Android Samsung Finger Spass API

三星的指纹识别比其他厂家复杂的多，需要用到两个实例，一个是Spass，一个是SpassFingerprint。同样需要两个SDK，sdk-v1.0.0.jar, pass-v1.2.2.jar
首先是初始化实例：
```java
        mSpass = new Spass();
        try {
            mSpass.initialize(mContext);
        } catch (Exception e) {
            isFingerEnable = false;
        }
        mSpassFingerprint = new SpassFingerprint(mContext);
```
注意一下，如果initialize方法抛异常的话，说明肯定不支持指纹验证
所以说是否支持指纹验证的方法就是:
```java
    @Override
    public boolean isFingerIdentifyEnabled() {
        if (isFingerEnable){
            isFingerEnable = mSpass.isFeatureEnabled(Spass.DEVICE_FINGERPRINT);
        }
        return isFingerEnable;
    }
```
是否已注册指纹: `mSpassFingerprint.hasRegisteredFinger();`
取消指纹验证: `mSpassFingerprint.cancelIdentify();`
开始指纹验证: `mSpassFingerprint.startIdentify(mIdentifyListener);`
指纹验证CallBack:
```java
    private IdentifyListener mIdentifyListener = new IdentifyListener() {
        @Override
        public void onFinished(int eventStatus) {
            Log.i("SamsungFinger", "eventStatus: " + eventStatus);
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
```
# 实现方式
首先，我们已知的环境是有多种指纹识别的厂商SDK，而且这些厂商SDK最终的方法除了换了一个名字以外，实现的意义都是一样的:
* 是否支持指纹验证
* 是否已注册指纹
* 开始指纹验证
* 取消指纹验证
所以我们只需要定义好abstract基类BaseFingerInterpolator，以及返回接口FingerIdentifyCallBack和返回的resultCode就好了
BaseFingerInterpolator:
```java
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
```

FingerIdentifyCallBack
```java
public interface FingerIdentifyCallBack {
    void onIdentifyResult(int result);
}
```
剩下的就是每个类实现对应的SDK的方法就好了。
# [Demo](https://github.com/DuanTzXavier/AllAboutAndroid/tree/master/FingerIdentify)
