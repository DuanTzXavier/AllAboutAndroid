#Fragment
###导言
本文会有大量的代码，请尽量在Android Studio 上看[Demo]()
为何要使用Fragment？
Fragment可以实现Activity的绝大部分功能（见注1），Fragment更加的轻量，并且不需要注册在Manifest中。只需要附着在Activity中就可以了。当Activity被杀死，其下所有的Fragment都被轻易的销毁。

*（注1：什么功能实现不了？当有Fragment设置为最顶层的时候，无法简单的覆盖在被设置的Fragment上；Fragment没有onNewIntent方法等等）*

### 从Fragment的生命周期说起

以下代码就是顺序的Fragment的生命周期，注释写的比较清楚，看代码即可
```java

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

```
### Fragment的使用 FragmentTransaction
说到Fragment的使用就不得不提FragmentTransaction，FragmentTransaction是什么呢？
相信大家都用过git，FragmentTransaction相当于commit的构造者，它可以
* `{@link FragmentTransaction#add(int, Fragment, String)}`添加Fragment
* `{@link FragmentTransaction#replace(int, Fragment, String)}`替换Fragment
* `{@link FragmentTransaction#remove(Fragment)}`移除Fragment
* `{@link FragmentTransaction#hide(Fragment)}` 隐藏Fragment
* `{@link FragmentTransaction#show(Fragment)}` 显示Fragment
* `{@link FragmentTransaction#detach(Fragment)}` 与UI分离
* `{@link FragmentTransaction#attach(Fragment)}` 在与UI分离后重新依附于Activity

它还可以在切换Fragment的时候添加动画，不过必须为写死的xml动画 `{@link FragmentTransaction#setCustomAnimations(int, int, int, int)}`,见Demo中Utils.java中：
```java
    /**
     * 添加Fragment在布局id为contentID的布局上
     * @param fragmentManager   fragmentManager
     * @param contentID         要添加的布局的ID
     * @param targetFragment    要添加的targetFragment
     */
    public static void addFragment(FragmentManager fragmentManager, int contentID, BaseFragment targetFragment){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fragment_in, R.anim.fragment_out, R.anim.fragment_close_in, R.anim.fragment_close_out);
        Fragment fragment = fragmentManager.findFragmentById(contentID); //先获取当前布局ID上的fragment

        if(fragment != null) { //若fragment不为null，则将此fragment hide
            transaction.hide(fragment);
        }

        transaction.add(contentID, targetFragment, targetFragment.getTag()); //为布局ID为contentID的布局添加targetFragment
        transaction.addToBackStack(targetFragment.getTag()); //在BackStack中添加targetFragment tag
        transaction.commitAllowingStateLoss();
    }

```
关于全屏面的Fragment，其实就是讲contentID替换为`android.R.id.content`。注释中都写的特别清楚了，请看代码及注释
### BackStack
BackStack是什么？顾名思义，返回栈。
为什么会有返回栈，因为按返回键的时候会将当前的Fragment销毁，在返回栈中取出上一个Fragment显示。参看`{@link FragmentActivity#onBackPressed()}`，在Demo中我也提到了，返回键会调用onBackPressed方法：
```java
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
```
关于`{@link FragmentManager#popBackStack(String, int)}`参数具体意义如下：[(参考文章)](http://blog.csdn.net/qq_16247851/article/details/52793061)
> tag可以为null或者相对应的tag，flags只有0和1(POP_BACK_STACK_INCLUSIVE)两种情况： 
>* 如果tag为null，flags为0时，弹出回退栈中最上层的那个fragment。
>* 如果tag为null ，flags为1时，弹出回退栈中所有fragment。
>* 如果tag不为null，那就会找到这个tag所对应的fragment，flags为0时，弹出该fragment以上的Fragment，如果是1，弹出该fragment（包括该fragment）以上的fragment。

顺便讲了一下Fragment的出栈。在这里可以看出`{@link FragmentManager#popBackStackImmediate()}`方法其实是上一个Fragment出栈的FragmentTransaction。
### FragmentManager
提了这么多次的FragmentManager，可以看出FragmentManager与Fragment关系密切。
那么FragmentManager到底是干什么的呢？
FragmentManager就像Fragment的老大，控制Fragment的生命周期的，之前提到的Fragment生命周期之所以是那样，是因为FragmentManager代码就是那么写的！！可以参看FragmentManager的moveToState()方法。所以说Fragment与FragmentManager其实就是利用一种特别~~恶心~~美妙的关系。FragmentManager其实也是执行FragmentTransaction的那个人。FragmentManager同时也有很多很棒的API比如：`getSupportFragmentManager().getFragments()`可以获取当前Activity下所有的Fragment。
### DialogFragment
其实DialogFragment是Fragment的子类，继承了Fragment的所有方法。但是DialogFragment与Fragment有什么不同呢？
最大的不同就是在添加的时候，如果DialogFragment在添加的时候指定contentID的话，会显示在指定的布局上，与Fragment一样。但是如果没有指定contentID的时候，DialogFragment会展示成Dialog的样式，背景为透明黑色，在`onCreateView()`方法中定义的View会居中显示出来。
比Fragment多一个重载方法，`onCreateDialog()`这个方法其实是构造Dialog的View的方法，与`onCreateView()`方法类似，不过只能是Dialog的样式。
```java
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }
```
所以一般情况下，为了迎合~~变态~~优美的设计的理念，我们都会使用`onCreateView()`方法来定义DialogFragment的View
### [Demo]()
