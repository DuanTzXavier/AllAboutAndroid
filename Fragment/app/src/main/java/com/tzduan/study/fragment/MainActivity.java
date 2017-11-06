package com.tzduan.study.fragment;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.tzduan.study.fragment.activity.LiftFragmentActivity;
import com.tzduan.study.fragment.dialog.callback.AlertCallBack;
import com.tzduan.study.fragment.dialog.model.AlertViewData;
import com.tzduan.study.fragment.dialog.model.DialogViewTypeModel;
import com.tzduan.study.fragment.fragment.APPV4DialogFragment;
import com.tzduan.study.fragment.fragment.APPV4Fragment;
import com.tzduan.study.fragment.utils.SettingDialogFragment;
import com.tzduan.study.fragment.utils.Utils;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements LifeCycleController, SettingDialogCallback {
    private TextView tvLogView;
    private Button btAddFragment;
    private Button btRemoveFragment;
    private Button btClearLog;
    private Button btStartLiftFragmentActivity;
    private RadioGroup rgFragment;
    private CheckBox cbIsFullScreen;

    private int mFragmentNumb = 0;
    private boolean mIsDialogFragment = false;

    private boolean mDialogCancelable = false;
    private boolean mDialogCancelableOntouch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        tvLogView = (TextView) findViewById(R.id.tv_log_view);
        btAddFragment = (Button) findViewById(R.id.bt_add_fragment);
        btRemoveFragment = (Button) findViewById(R.id.bt_remove_fragment);
        btClearLog = (Button) findViewById(R.id.bt_clear_log);
        btStartLiftFragmentActivity = (Button) findViewById(R.id.bt_start_lift_fragment_activity);
        rgFragment = (RadioGroup) findViewById(R.id.rg_fragment);
        cbIsFullScreen = (CheckBox) findViewById(R.id.cb_is_full_screen);
        setListener();
    }

    private void setListener() {
        btAddFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragment();
            }
        });

        btRemoveFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!getSupportFragmentManager().popBackStackImmediate()) {
                    Utils.showToast("Fragment 已全部出栈");
                }
                /**
                 * {@link FragmentActivity#onBackPressed()}
                 * if (!mFragments.getSupportFragmentManager().popBackStackImmediate()) {
                 *      super.onBackPressed();
                 * }
                 * 可以看出 按返回键也是可以直接将Fragment出栈的, 所以说Fragment的出栈方法就是通过{@link FragmentManager#popBackStackImmediate()}
                 * 其实还有很多种方法将Fragment出栈，如下：
                 * {@link FragmentManager#popBackStack()}
                 * {@link FragmentManager#popBackStack(String, int)}
                 * {@link FragmentManager#popBackStack(int, int)}
                 * {@link FragmentManager#popBackStackImmediate()}
                 * {@link FragmentManager#popBackStackImmediate(int, int)}
                 * {@link FragmentManager#popBackStackImmediate(String, int)}
                 *
                 * 在这里我们只讲一下{@link FragmentManager#popBackStack()} 与 {@link FragmentManager#popBackStackImmediate()} 方法的不同，具体方法请参见 SDK
                 * {@link FragmentManager#popBackStackImmediate()} 比 {@link FragmentManager#popBackStack()} 多执行了一步 {@link FragmentManager#executePendingTransactions()}
                 * 其实{@link FragmentManager#executePendingTransactions()}这个方法就是将{@link FragmentTransaction}立即执行。一般情况下{@link FragmentTransaction#commit()}
                 * 执行之后并不会立即执行，只是再加主线程的任务栈之中，等主线程准备好了之后执行。
                 */
            }
        });

        btClearLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvLogView.setText("");
                Utils.showToast("日志已清空");
            }
        });

        btStartLiftFragmentActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), LiftFragmentActivity.class);
                startActivity(intent);
            }
        });

        rgFragment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_dialog_fragment){
                    mIsDialogFragment = true;
                    addSettingDialogFragment();
                }else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_fragment){
                    mIsDialogFragment = false;
                    removeSettingDialogFragment();
                }
            }
        });
    }

    private void removeSettingDialogFragment() {
        getSupportFragmentManager().popBackStack(SettingDialogFragment.class.getName(), 1);
    }

    private void addSettingDialogFragment() {
        SettingDialogFragment fragment = SettingDialogFragment.getInstant(
                this,
                "SettingDialogFragment",
                this);
        Utils.addFragment(getSupportFragmentManager(), R.id.ll_fragment_container, fragment, SettingDialogFragment.class.getName());
    }

    @Override
    public void addLogToShow(String text) {
        tvLogView.append(text);
    }

    @Override
    public void addFragment() {

        if (mIsDialogFragment){
            DialogViewTypeModel model = new DialogViewTypeModel();
            model.setmCallBack(new AlertCallBack() {
                @Override
                public void buttonClicked(boolean left) {
                    Utils.showToast(left ? "你说不是就不是嘛？？" : "哈哈哈，你说的对");
                }
            });
            AlertViewData viewData = new AlertViewData();
            viewData.alertMessage = "我是ALERT吗？？？";
            viewData.leftButton = "不，你不是";
            viewData.rightButton = "是的";
            viewData.cancelable = mDialogCancelable;
            viewData.canceledOnTouchOutside = mDialogCancelableOntouch;
            model.setmViewData(viewData);
            APPV4DialogFragment fragment = APPV4DialogFragment.getInstant(model);
            Utils.addDialogFragment(getSupportFragmentManager(), fragment);
        }else {
            Random random = new Random();
            APPV4Fragment fragment = APPV4Fragment.getInstant(
                    MainActivity.this,
                    "Fragment" + mFragmentNumb,
                    0xff000000 | random.nextInt(0x00ffffff));
            Utils.addFragment(
                    getSupportFragmentManager(),
                    cbIsFullScreen.isChecked() ?
                            android.R.id.content :
                            R.id.ll_fragment_container,
                    fragment,
                    APPV4Fragment.class.getClass().getName());
        }

        mFragmentNumb ++;
    }

    @Override
    public void setCancelable(boolean cancelable, boolean cancelableOntouch) {
        mDialogCancelable = cancelable;
        mDialogCancelableOntouch = cancelableOntouch;
    }
}
