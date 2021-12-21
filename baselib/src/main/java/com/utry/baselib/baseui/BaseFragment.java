package com.utry.baselib.baseui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewbinding.ViewBinding;


/**
 * Title: BaseFragment.java
 * Description: 基类
 * Copyright (c) 小远机器人版权所有 2021
 * Created DateTime: 2021/8/20 16:49
 * Created by xueli.
 */
public abstract class BaseFragment<T extends ViewBinding> extends Fragment {

    private T _binding;

    protected T getBinding() {
        return _binding;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        _binding = getViewBinding(inflater, container);
        return _binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
    }

    protected abstract T getViewBinding(LayoutInflater inflater, ViewGroup container);


    protected void initView(View view) {

    }

    protected void initData() {
    }


    private static ProgressDialog progressDialog;

    final public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    public void showProgress(CharSequence message) {
        progressDialog = new ProgressDialog(getBaseActivity());
        progressDialog.setMessage(TextUtils.isEmpty(message) ? "请稍后..." : message);
        progressDialog.show();
    }

    public void hideProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public boolean isShowingProgress() {
        if (progressDialog != null) {
            return progressDialog.isShowing();
        } else {
            return false;
        }
    }


}
