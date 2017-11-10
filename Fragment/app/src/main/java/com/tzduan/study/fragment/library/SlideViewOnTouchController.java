package com.tzduan.study.fragment.library;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.tzduan.study.fragment.R;

/**
 * Created by tzduan on 17/11/9.
 */

public class SlideViewOnTouchController implements View.OnTouchListener{
    private View view;
    private Context mContext;
    private final int originRight;
    private final int originLeft;
    private final int originTop;
    private final int originBottom;

    private final int targetRight;
    private final int targetLeft;
    private final int targetTop;
    private final int targetBottom;

    private int lastY;

    private double X;
    private double Lx;
    private double Rx;
    private double Tx;
    private double Bx;

    private int originWidth;
    private int originHeight;
    private int originRightM;


    private RelativeLayout.LayoutParams originLayoutParams;
    private RelativeLayout.LayoutParams targetLayoutParams;

    public SlideViewOnTouchController(View view, Context context){
        this.view = view;
        this.mContext = context;
        this.originRight = view.getRight();
        this.originLeft = view.getLeft();
        this.originTop = view.getTop();
        this.originBottom = view.getBottom();

        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int screenHeight = (int) (wm.getDefaultDisplay().getHeight() - mContext.getResources().getDimension(R.dimen.DP_20));
        int screenWidth = wm.getDefaultDisplay().getWidth();
        this.targetRight = (int) (screenWidth - mContext.getResources().getDimension(R.dimen.DP_16));
        this.targetLeft = (int) (screenWidth - mContext.getResources().getDimension(R.dimen.DP_16) - mContext.getResources().getDimension(R.dimen.DP_160));
        this.targetTop = (int) (screenHeight - (mContext.getResources().getDimension(R.dimen.DP_16) + mContext.getResources().getDimension(R.dimen.DP_90)));
        this.targetBottom = (int) (screenHeight - mContext.getResources().getDimension(R.dimen.DP_16));
        X = targetBottom - mContext.getResources().getDimension(R.dimen.DP_90) / 2 - (originBottom - originTop) / 2;
        Lx = ((double)(targetLeft - originLeft)) / X;
        Rx = ((double)(targetRight - originRight)) / X;
        Tx = ((double)(targetTop - originTop)) / X;
        Bx = ((double)(targetBottom - originBottom)) / X;

        originLayoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        targetLayoutParams = new RelativeLayout.LayoutParams(
                (int) mContext.getResources().getDimension(R.dimen.DP_160),
                (int) mContext.getResources().getDimension(R.dimen.DP_90));
        targetLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        targetLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        targetLayoutParams.rightMargin = mContext.getResources().getDimensionPixelOffset(R.dimen.DP_16);
        targetLayoutParams.bottomMargin = mContext.getResources().getDimensionPixelOffset(R.dimen.DP_16);

        originWidth = view.getMeasuredWidth();
        originHeight = view.getMeasuredHeight();
        originRightM = originLayoutParams.rightMargin;
    }



    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int y = (int) event.getY();
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                setLayout(y - lastY);
                break;
        }

        return true;
    }

    private void setLayout(int offY){
        if (isAtBottom(offY)){
            view.setLayoutParams(targetLayoutParams);
        } else if (view.getTop()+offY >= mContext.getResources().getDimension(R.dimen.DP_5)){
            int offL = (int) (offY * Lx);
            int offR = (int) (offY * Rx);
            int offT = (int) (offY * Tx);
            int offB = (int) (offY * Bx);
            Log.i("layout", "\n Lx: " + Lx + "\n" +
                    "\n Rx: " + Rx + "\n" +
                    "\n Tx: " + Tx + "\n" +
                    "\n Bx: " + Bx + "\n");
            Log.i("layout", "\n offL: " + offL + "\n" +
                    "\n offR: " + offR + "\n" +
                    "\n offT: " + offT + "\n" +
                    "\n offB: " + offB + "\n" + view.getMeasuredWidth());

            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
            layoutParams.topMargin = view.getTop() + offY;
            layoutParams.rightMargin = (int) (originRightM - (originRightM - mContext.getResources().getDimension(R.dimen.DP_16)) * calculatePercentage());
            layoutParams.width = (int) (originWidth - (originWidth - mContext.getResources().getDimension(R.dimen.DP_160)) * calculatePercentage());
            layoutParams.height = (int) (originHeight - (originHeight - mContext.getResources().getDimension(R.dimen.DP_90)) * calculatePercentage());
            view.setLayoutParams(layoutParams);
//            view.layout(
//                    view.getLeft() + offL,
//                    view.getTop() + offT,
//                    view.getRight() + offR,
//                    view.getBottom() + offB);
        } else { //Set original layout
            view.setLayoutParams(originLayoutParams);
        }
    }

    private boolean isAtBottom(int offY){
        return view.getTop() >= targetTop;
    }

    private double caculateX(){
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int maxY = (int) (wm.getDefaultDisplay().getHeight() - mContext.getResources().getDimension(R.dimen.DP_16) - originBottom);
        return (mContext.getResources().getDimension(R.dimen.DP_90) * 2) / maxY;
    }

    private double calculatePercentage(){
        return ((double) view.getTop()) / targetTop;
    }
}
