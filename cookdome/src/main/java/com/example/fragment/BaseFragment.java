package com.example.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cookdome.R;

/**
 * Created by 10734 on 2018/4/27 0027.
 */

public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getFragmentLayout(), container , false);
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
        initData();
    }

    public abstract int getFragmentLayout();

    //初始化界面
    public abstract void initView();

    //初始化数据
    public abstract void initData();

}
