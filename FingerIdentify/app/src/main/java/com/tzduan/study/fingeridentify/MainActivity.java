package com.tzduan.study.fingeridentify;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.tzduan.study.fingerpass.FingerIdentify;
import com.tzduan.study.fingerpass.FingerIdentifyCallBack;
import com.tzduan.study.fingerpass.interpolator.BaseFingerInterpolator;

public class MainActivity extends AppCompatActivity implements CancelClick {

    FingerIdentify indentify;
    ShowFingerPassMessage showMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doFingerIdentify(view);
            }
        });
    }

    private void doFingerIdentify(View view){
        indentify = new FingerIdentify(getBaseContext(), callBack);
        indentify.setSupportRoot(true);
        String snackbarString = "";
        switch (indentify.isSupportFinger()){
            case FingerIdentify.SUPPORT:
                snackbarString = "SUPPORT";
                startIdentify();
                break;
            case FingerIdentify.NOT_SUPPORT:
                snackbarString = "NOT_SUPPORT";
                break;
            case FingerIdentify.ROOTED:
                snackbarString = "ROOTED";
                break;
            default:
                break;
        }

        Snackbar.make(view, snackbarString, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private void startIdentify() {
        if (indentify.startFingerIdentify()){
            Toast.makeText(getBaseContext(),  "开始识别", Toast.LENGTH_SHORT).show();
            FingerPassDialogFragment fragment = FingerPassDialogFragment.getInstant(this);
            showMessage = fragment;
//            fragment.
            addDialogFragment(getSupportFragmentManager(), fragment);
        }else {
            Toast.makeText(getBaseContext(),  "开始失败", Toast.LENGTH_SHORT).show();
        }

    }

    FingerIdentifyCallBack callBack = new FingerIdentifyCallBack() {
        @Override
        public void onIdentifyResult(int result) {
            switch (result){
                case BaseFingerInterpolator.FINGER_IDENTIFY_SUCCESS:
                    Toast.makeText(getBaseContext(),  "识别成功", Toast.LENGTH_SHORT).show();
                    showMessage.dissmissDialog();
                    break;
                case BaseFingerInterpolator.FINGER_IDENTIFY_ERROR_USER_CANCEL:
                    Toast.makeText(getBaseContext(),  "用户取消", Toast.LENGTH_SHORT).show();
                    showMessage.dissmissDialog();
                    break;
                case BaseFingerInterpolator.FINGER_IDENTIFY_ERROR_AUTHENTIFICATION_FAILED:
                    indentify.startFingerIdentify();
                    showMessage.showFingerPassMessage("指纹验证失败，请重试");
                    break;
                case BaseFingerInterpolator.FINGER_IDENTIFY_ERROR_TIMEOUT:
                    indentify.startFingerIdentify();
                    showMessage.showFingerPassMessage("请重新验证指纹");
                    break;
                default:
                    Toast.makeText(getBaseContext(),  "指纹识别异常，请重试", Toast.LENGTH_SHORT).show();
                    showMessage.dissmissDialog();
                    break;
            }
        }
    };

    private void addDialogFragment(FragmentManager fragmentManager, Fragment targetFragment){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(targetFragment, targetFragment.getTag());
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void cancelClick() {
        indentify.stopFingerIdentify();
    }
}
