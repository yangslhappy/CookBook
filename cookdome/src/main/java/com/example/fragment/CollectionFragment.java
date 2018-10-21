package com.example.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.adpter.CookAdapter;
import com.example.cookdome.CookActivity;
import com.example.cookdome.ListActivity;
import com.example.cookdome.R;
import com.example.db.CookDB;
import com.example.model.CookBean;
import com.example.util.ToastUtil;

import java.util.List;

/**
 * Created by 10734 on 2018/4/27 0027.
 */

public class CollectionFragment extends BaseFragment {

    public static final int SUCCESS = 1;
    public static final int ERROR = 0;

    ListView shoucan_lv;

    CookDB cookDB = null;

    List<CookBean> cookBeans;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == SUCCESS){
                shoucan_lv.setAdapter(new CookAdapter(getContext(),cookBeans));
            }else {
                ToastUtil.toastShow(getContext(),"没有数据");
            }
        }
    };

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_collection;
    }

    @Override
    public void initView() {
        shoucan_lv = getActivity().findViewById(R.id.shoucan_lv);
        shoucan_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), CookActivity.class);
                intent.putExtra("cookbean",cookBeans.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {
        if(cookDB == null){
            cookDB = new CookDB(getActivity(),"t_cook");
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                cookBeans = cookDB.findAllData();
                if(cookBeans != null){
                    Message message = new Message();
                    message.what = SUCCESS;
                    handler.sendMessage(message);
                }else {
                    Message message = new Message();
                    message.what = ERROR;
                    handler.sendMessage(message);
                }
            }
        }).start();
    }
}
