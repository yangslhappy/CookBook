package com.example.fragment;

import android.database.DataSetObserver;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.config.AppConfig;
import com.example.cookdome.R;
import com.example.model.CategrgoryList;
import com.example.model.CookCategory;
import com.example.util.ToastUtil;
import com.example.util.WebUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 10734 on 2018/4/27 0027.
 */

public class MainFragment extends BaseFragment {

    GridView main_gridview;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

        }
    };

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_main;
    }

    @Override
    public void initView() {
        main_gridview = getActivity().findViewById(R.id.main_gridview);
    }

    @Override
    public void initData() {

    }
}
