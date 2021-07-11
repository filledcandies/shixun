package com.example.myapp.logIn.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.myapp.R;
import com.example.myapp.entity.Result;
import com.example.myapp.entity.User;
import com.example.myapp.myapplication.ApplicationStatus;
import com.example.myapp.post.PostActivity;

import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener, ViewTreeObserver.OnGlobalLayoutListener, TextWatcher {

    private String TAG = "ifu25";

    private ImageButton mIbNavigationBack;
    private LinearLayout mLlLoginPull;
    private View mLlLoginLayer;
    private LinearLayout mLlLoginOptions;
    private EditText mEtLoginUsername;
    private EditText mEtLoginPwd;
    private LinearLayout mLlLoginUsername;
    private ImageView mIvLoginUsernameDel;
    private Button mBtLoginSubmit;
    private LinearLayout mLlLoginPwd;
    private ImageView mIvLoginPwdDel;
    private ImageView mIvLoginLogo;
    private LinearLayout mLayBackBar;
    private TextView mTvLoginForgetPwd;
    private Button mBtLoginRegister;

    //全局Toast
    private Toast mToast;

    private int mLogoHeight;
    private int mLogoWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        initView();
    }

    //初始化视图
    private void initView() {
        //登录层、下拉层、其它登录方式层
        mLlLoginLayer = findViewById(R.id.ll_login_layer);
        mLlLoginPull = findViewById(R.id.ll_login_pull);
        mLlLoginOptions = findViewById(R.id.ll_login_options);

        //导航栏+返回按钮
        mLayBackBar = findViewById(R.id.ly_retrieve_bar);
        mIbNavigationBack = findViewById(R.id.ib_navigation_back);

        //logo
        mIvLoginLogo = findViewById(R.id.iv_login_logo);

        //username
        mLlLoginUsername = findViewById(R.id.ll_login_username);
        mEtLoginUsername = findViewById(R.id.et_login_username);
        mIvLoginUsernameDel = findViewById(R.id.iv_login_username_del);

        //passwd
        mLlLoginPwd = findViewById(R.id.ll_login_pwd);
        mEtLoginPwd = findViewById(R.id.et_login_pwd);
        mIvLoginPwdDel = findViewById(R.id.iv_login_pwd_del);

        //提交、注册
        mBtLoginSubmit = findViewById(R.id.bt_login_submit);
        mBtLoginRegister = findViewById(R.id.bt_login_register);

        //忘记密码
        mTvLoginForgetPwd = findViewById(R.id.tv_login_forget_pwd);
        mTvLoginForgetPwd.setOnClickListener(this);

        //注册点击事件
        mLlLoginPull.setOnClickListener(this);
        mIbNavigationBack.setOnClickListener(this);
        mEtLoginUsername.setOnClickListener(this);
        mIvLoginUsernameDel.setOnClickListener(this);
        mBtLoginSubmit.setOnClickListener(this);
        mBtLoginRegister.setOnClickListener(this);
        mEtLoginPwd.setOnClickListener(this);
        mIvLoginPwdDel.setOnClickListener(this);
        findViewById(R.id.ib_login_weibo).setOnClickListener(this);
        findViewById(R.id.ib_login_qq).setOnClickListener(this);
        findViewById(R.id.ib_login_wx).setOnClickListener(this);

        //注册其它事件
        mLayBackBar.getViewTreeObserver().addOnGlobalLayoutListener(this);
        mEtLoginUsername.setOnFocusChangeListener(this);
        mEtLoginUsername.addTextChangedListener(this);
        mEtLoginPwd.setOnFocusChangeListener(this);
        mEtLoginPwd.addTextChangedListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ib_navigation_back) {//返回
            finish();
        } else if (id == R.id.et_login_username) {
            mEtLoginPwd.clearFocus();
            mEtLoginUsername.setFocusableInTouchMode(true);
            mEtLoginUsername.requestFocus();
        } else if (id == R.id.et_login_pwd) {
            mEtLoginUsername.clearFocus();
            mEtLoginPwd.setFocusableInTouchMode(true);
            mEtLoginPwd.requestFocus();
        } else if (id == R.id.iv_login_username_del) {//清空用户名
            mEtLoginUsername.setText(null);
        } else if (id == R.id.iv_login_pwd_del) {//清空密码
            mEtLoginPwd.setText(null);
        } else if (id == R.id.bt_login_submit) {//登录
            loginRequest();
        } else if (id == R.id.bt_login_register) {//注册
            startActivity(new Intent(LogInActivity.this, RegisterActivity.class));
        } else if (id == R.id.tv_login_forget_pwd) {//忘记密码
            startActivity(new Intent(LogInActivity.this, ForgetPwdActivity.class));
        } else if (id == R.id.ll_login_layer || id == R.id.ll_login_pull) {
            mLlLoginPull.animate().cancel();
            mLlLoginLayer.animate().cancel();

            int height = mLlLoginOptions.getHeight();
            float progress = (mLlLoginLayer.getTag() != null && mLlLoginLayer.getTag() instanceof Float) ? (float) mLlLoginLayer.getTag() : 1;
            int time = (int) (360 * progress);

            if (mLlLoginPull.getTag() != null) {
                mLlLoginPull.setTag(null);
                glide(height, progress, time);
            } else {
                mLlLoginPull.setTag(true);
                upGlide(height, progress, time);
            }
        } else if (id == R.id.ib_login_weibo) {
            weiboLogin();
        } else if (id == R.id.ib_login_qq) {
            qqLogin();
        } else if (id == R.id.ib_login_wx) {
            weixinLogin();
        }
    }

    //用户名密码焦点改变
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        int id = v.getId();

        if (id == R.id.et_login_username) {
            if (hasFocus) {
                mLlLoginUsername.setActivated(true);
                mLlLoginPwd.setActivated(false);
            }
        } else {
            if (hasFocus) {
                mLlLoginPwd.setActivated(true);
                mLlLoginUsername.setActivated(false);
            }
        }
    }

    /**
     * menu glide
     *
     * @param height   height
     * @param progress progress
     * @param time     time
     */
    private void glide(int height, float progress, int time) {
        mLlLoginPull.animate()
                .translationYBy(height - height * progress)
                .translationY(height)
                .setDuration(time)
                .start();

        mLlLoginLayer.animate()
                .alphaBy(1 * progress)
                .alpha(0)
                .setDuration(time)
                .setListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        if (animation instanceof ValueAnimator) {
                            mLlLoginLayer.setTag(((ValueAnimator) animation).getAnimatedValue());
                        }
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (animation instanceof ValueAnimator) {
                            mLlLoginLayer.setTag(((ValueAnimator) animation).getAnimatedValue());
                        }
                        mLlLoginLayer.setVisibility(View.GONE);
                    }
                })
                .start();
    }

    /**
     * menu up glide
     *
     * @param height   height
     * @param progress progress
     * @param time     time
     */
    private void upGlide(int height, float progress, int time) {
        mLlLoginPull.animate()
                .translationYBy(height * progress)
                .translationY(0)
                .setDuration(time)
                .start();
        mLlLoginLayer.animate()
                .alphaBy(1 - progress)
                .alpha(1)
                .setDuration(time)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        mLlLoginLayer.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        if (animation instanceof ValueAnimator) {
                            mLlLoginLayer.setTag(((ValueAnimator) animation).getAnimatedValue());
                        }
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (animation instanceof ValueAnimator) {
                            mLlLoginLayer.setTag(((ValueAnimator) animation).getAnimatedValue());
                        }
                    }
                })
                .start();
    }

    //显示或隐藏logo
    @Override
    public void onGlobalLayout() {
        final ImageView ivLogo = this.mIvLoginLogo;
        Rect KeypadRect = new Rect();

        mLayBackBar.getWindowVisibleDisplayFrame(KeypadRect);

        int screenHeight = mLayBackBar.getRootView().getHeight();
        int keypadHeight = screenHeight - KeypadRect.bottom;

        //隐藏logo
        if (keypadHeight > 300 && ivLogo.getTag() == null) {
            final int height = ivLogo.getHeight();
            final int width = ivLogo.getWidth();
            this.mLogoHeight = height;
            this.mLogoWidth = width;

            ivLogo.setTag(true);

            ValueAnimator valueAnimator = ValueAnimator.ofFloat(1, 0);
            valueAnimator.setDuration(400).setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float animatedValue = (float) animation.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = ivLogo.getLayoutParams();
                    layoutParams.height = (int) (height * animatedValue);
                    layoutParams.width = (int) (width * animatedValue);
                    ivLogo.requestLayout();
                    ivLogo.setAlpha(animatedValue);
                }
            });

            if (valueAnimator.isRunning()) {
                valueAnimator.cancel();
            }
            valueAnimator.start();
        }
        //显示logo
        else if (keypadHeight < 300 && ivLogo.getTag() != null) {
            final int height = mLogoHeight;
            final int width = mLogoWidth;

            ivLogo.setTag(null);

            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
            valueAnimator.setDuration(400).setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float animatedValue = (float) animation.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = ivLogo.getLayoutParams();
                    layoutParams.height = (int) (height * animatedValue);
                    layoutParams.width = (int) (width * animatedValue);
                    ivLogo.requestLayout();
                    ivLogo.setAlpha(animatedValue);
                }
            });

            if (valueAnimator.isRunning()) {
                valueAnimator.cancel();
            }
            valueAnimator.start();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    //用户名密码输入事件
    @Override
    public void afterTextChanged(Editable s) {
        String username = mEtLoginUsername.getText().toString().trim();
        String pwd = mEtLoginPwd.getText().toString().trim();

        //是否显示清除按钮
        if (username.length() > 0) {
            mIvLoginUsernameDel.setVisibility(View.VISIBLE);
        } else {
            mIvLoginUsernameDel.setVisibility(View.INVISIBLE);
        }
        if (pwd.length() > 0) {
            mIvLoginPwdDel.setVisibility(View.VISIBLE);
        } else {
            mIvLoginPwdDel.setVisibility(View.INVISIBLE);
        }

        //登录按钮是否可用
        if (!TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(username)) {
            mBtLoginSubmit.setBackgroundResource(R.drawable.bg_login_submit);
            mBtLoginSubmit.setTextColor(getResources().getColor(R.color.white));
        } else {
            mBtLoginSubmit.setBackgroundResource(R.drawable.bg_login_submit_lock);
            mBtLoginSubmit.setTextColor(getResources().getColor(R.color.account_lock_font_color));
        }
    }

    //登录
    private void loginRequest() {
        new Thread(() -> {
            try {
                String json = JSON.toJSONString(new User(mEtLoginPwd.getText().toString(),
                        mEtLoginUsername.getText().toString()));
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(ApplicationStatus.HOST + "/user/login")
                        .post(RequestBody.create(MediaType.parse("application/json"), json))
                        .build();
                Response response = client.newCall(request).execute();
                String res = Objects.requireNonNull(response.body()).string();
                Result<Integer> result = JSON.parseObject(res, new TypeReference<Result<Integer>>() {});
                if (result.isSuccess()) {
                    ApplicationStatus.setUserId(result.get());
                    startActivity(new Intent(LogInActivity.this, PostActivity.class));
                    finish();
                } else {
                    runOnUiThread(() -> Toast.makeText(LogInActivity.this, "邮箱或密码错误",
                            Toast.LENGTH_SHORT).show());
                }
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(LogInActivity.this, "网络连接失败",
                        Toast.LENGTH_SHORT).show());
            }
        }).start();

    }

    //微博登录
    private void weiboLogin() {
        showToast(R.string.not_support_to_login);
    }

    //QQ登录
    private void qqLogin() {
        showToast(R.string.not_support_to_login);
    }

    //微信登录
    private void weixinLogin() {
        showToast(R.string.not_support_to_login);
    }

    /**
     * 显示Toast
     *
     * @param msg 提示信息内容
     */
    private void showToast(int msg) {
        if (null != mToast) {
            mToast.setText(msg);
        } else {
            mToast = Toast.makeText(LogInActivity.this, msg, Toast.LENGTH_SHORT);
        }

        mToast.show();
    }
}
