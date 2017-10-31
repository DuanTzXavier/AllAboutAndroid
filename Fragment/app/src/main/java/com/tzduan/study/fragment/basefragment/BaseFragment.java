package com.tzduan.study.fragment.basefragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.tzduan.study.fragment.LifeCycleController;

/**
 * Created by tzduan on 17/10/27.
 */

public class BaseFragment extends Fragment {

    protected LifeCycleController mController;
    protected String mPageCode; //埋点使用
    /*
    * onCreate start
    **/
    @Override public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO  getArguments().getParcelable() 处理序列化数据 ，一般是ViewData （例：是否显示标题）初始化Presenter
        logText("onCreate");
    }

    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //TODO 设置rootView
        logText("onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //TODO 初始化Views initViews()
        super.onViewCreated(view, savedInstanceState);
        logText("onViewCreated");
    }

    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        //TODO 父Activity启动完成，可以做与父Activity交互 register Broadcast 等
        super.onActivityCreated(savedInstanceState);
        logText("onActivityCreated");
    }
    /*
    * onCreate end
    **/

    /*
    * onStart start
    **/
    @Override public void onStart() {
        super.onStart();
        // 与Activity 相同
        logText("onStart");
    }
    /*
    * onStart end
    **/

    /*
    * onResume start
    **/
    @Override public void onResume() {
        super.onResume();
        //TODO 一般业务用的比较多，跳转其他页面后，返回该页面回调
        logText("onResume");
    }
    /*
    * onResume end
    **/

    /*
    * onPause start
    **/
    @Override public void onPause() {
        super.onPause();
        //TODO save user data
        logText("onPause");
    }
    /*
    * onPause end
    **/

    /*
    * onStop start
    **/
    @Override public void onStop() {
        /**
         * 一般操作：
         * {@link Handler#removeCallbacksAndMessages}
         * unRegister Broadcast
         * hideSoftInput
         * remove callbacks
         */
        super.onStop();
        logText("onStop");
    }
    /*
    * onStop end
    **/

    /*
    * onDestroy start
    **/
    @Override public void onDestroyView() {
        //TODO 将Views 置为null 防止内存泄露
        super.onDestroyView();
        logText("onDestroyView");
    }

    @Override public void onDestroy() {
        super.onDestroy();
        // 与Activity 相同
        logText("onDestroy");
    }

    @Override public void onDetach() {
        super.onDetach();
        /**
         * 在操作Fragment的时候，可以{@link FragmentTransaction#detach(Fragment)}，这样可以使Fragment不被
         * 销毁，但是用户之前保存的数据都会消失
         */
        logText("onDetach");
    }
    /*
    * onDestroy end
    **/

    /*
    * 非生命周期回调函数
    * */
    @Override public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        /**
         * 为什么会有这个函数呢，因为Fragment 所有的View都保存在 {@link Fragment#mView },
         * 每次变换Fragment是否显示的状态都会调用次方法，并且会设置
         * mView.setVisibility(fragment.mHidden && !fragment.isHideReplaced()? View.GONE: View.VISIBLE;)
         * mView会硬件加速渲染
         * 参看 {@link FragmentManager#completeShowHideFragment(Fragment)}.
         **/
        logText("onHiddenChanged");
    }

    @Override public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {

        //TODO 切换Fragment时的动画，可在这里手动修改
        logText("onCreateAnimation");
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    private void logText(String text){
        if (mController != null){
            mController.addLogToShow(mPageCode + ": " + text);
            mController.addLogToShow("\n");
        }
    }

    protected void setPageCode(){

    }
}
