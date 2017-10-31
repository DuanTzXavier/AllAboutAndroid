package com.tzduan.study.fragment.dialog.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tzduan.study.fragment.R;
import com.tzduan.study.fragment.dialog.callback.AlertCallBack;
import com.tzduan.study.fragment.dialog.model.AlertViewData;

/**
 * Created by tzduan on 17/10/31.
 */

public class AlertView extends LinearLayout {
    private AlertCallBack mCallBack;
    private AlertViewData mViewData;

    private TextView tvMessage;
    private TextView tvLeft;
    private TextView tvRight;

    private Context mContext;

    public AlertView(Context context, AlertCallBack callBack, AlertViewData viewData) {
        super(context);
        mContext = context;
        mCallBack = callBack;
        mViewData = viewData;
        initView();
        initLisenter();
        setViewData();
    }

    private void initLisenter() {
        tvLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallBack.buttonClicked(true);
            }
        });
        tvRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallBack.buttonClicked(false);
            }
        });
    }

    private void setViewData() {
        if (mViewData == null) return;
        if (!TextUtils.isEmpty(mViewData.alertMessage)){
            tvMessage.setText(mViewData.alertMessage);
        }else {
            tvMessage.setVisibility(GONE);
        }
        if (!TextUtils.isEmpty(mViewData.leftButton)){
            tvLeft.setText(mViewData.leftButton);
        }
        if (!TextUtils.isEmpty(mViewData.rightButton)){
            tvRight.setText(mViewData.rightButton);
        }
    }

    private void initView() {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.alert, this);
        tvMessage = rootView.findViewById(R.id.tv_alert_message);
        tvLeft = rootView.findViewById(R.id.tv_alert_left_button);
        tvRight = rootView.findViewById(R.id.tv_alert_right_button);
    }
}
