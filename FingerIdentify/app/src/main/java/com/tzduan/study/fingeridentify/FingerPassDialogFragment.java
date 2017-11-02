package com.tzduan.study.fingeridentify;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by tzduan on 17/11/1.
 */

public class FingerPassDialogFragment extends DialogFragment implements ShowFingerPassMessage {
    private CancelClick callBack;

    private TextView errorMessage;

    public static FingerPassDialogFragment getInstant(CancelClick callBack){
        FingerPassDialogFragment fragment = new FingerPassDialogFragment();
        fragment.callBack = callBack;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.content_main, null);
        ImageView imageView = rootView.findViewById(R.id.iv_finger_print);
        errorMessage = rootView.findViewById(R.id.error_message);
        rootView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.cancelClick();
                dismiss();
            }
        });
        Glide.with( this ).asGif().load(R.drawable.finger_print).into(imageView) ;
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
    }

    @Override
    public void showFingerPassMessage(String message) {
        errorMessage.setText(message);
    }

    @Override
    public void dissmissDialog() {
        dismiss();
    }
}
