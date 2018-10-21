package com.example.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.adpter.CookAdapter;
import com.example.config.AppConfig;
import com.example.cookdome.CookActivity;
import com.example.cookdome.R;
import com.example.model.CookBean;
import com.example.model.Steps;
import com.example.util.ToastUtil;
import com.example.util.WebUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by 10734 on 2018/4/27 0027.
 */

public class SearchFragment extends BaseFragment {

    public static final int SUCCESS = 1;
    public static final int ERROR = 0;

    EditText search_edit;
    Button search_btn;
    ListView search_lv;

    List<CookBean> cookBeans;

    Handler handler;

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_search;
    }

    @Override
    public void initView() {
        search_edit = getActivity().findViewById(R.id.search_edit);
        search_btn = getActivity().findViewById(R.id.search_btn);
        search_lv = getActivity().findViewById(R.id.search_lv);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               query();
            }
        });
        search_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == SUCCESS){
                    search_lv.setAdapter(new CookAdapter(getContext(),cookBeans));
                }else if(msg.what == ERROR){
                    ToastUtil.toastShow(getContext(),"网络错误");
                }
            }
        };
    }

    public void query(){
        if(!"".equals(search_edit.getText().toString())){new Thread(new Runnable() {
            @Override
            public void run() {
                String url = null;
                try {
                    url = AppConfig.QUERY +"?key="+AppConfig.KEY+"&menu="+ URLEncoder.encode(search_edit.getText().toString(),"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Log.i("TAG", url);
                try {
                    String json = WebUtil.getStringByInputStream(WebUtil.getByWeb(url));
                    Log.i("TAG", json);
                    JSONObject jsonObject = new JSONObject(json);
                    if(jsonObject.getString("resultcode").equals("200")){
                        cookBeans = new ArrayList<>();
                        JSONArray jsonArray = jsonObject.getJSONObject("result").getJSONArray("data");
                        Log.i("TAG", jsonArray.toString());
                        for(int i = 0 ; i < jsonArray.length() ; i++){
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            CookBean cookBean = new CookBean();
                            cookBean.setId(jsonObject1.getString("id"));
                            cookBean.setTitle(jsonObject1.getString("title"));
                            cookBean.setTags(jsonObject1.getString("tags"));
                            cookBean.setImtro(jsonObject1.getString("imtro"));
                            cookBean.setIngredients(jsonObject1.getString("ingredients"));
                            cookBean.setBurden(jsonObject1.getString("burden"));
                            List<String> albums = new ArrayList<>();
                            JSONArray jsonArray1 = jsonObject1.getJSONArray("albums");
                            for(int j = 0 ; j < jsonArray1.length() ; j++){
                                albums.add(jsonArray1.getString(j));
                            }
                            cookBean.setAlbums(albums);
                            JSONArray jsonArray2 = jsonObject1.getJSONArray("steps");
                            List<Steps> steps = new ArrayList<>();
                            for(int k = 0 ; k < jsonArray2.length() ; k++){
                                JSONObject jsonObject2 = jsonArray2.getJSONObject(k);
                                Steps step = new Steps();
                                step.setImg(jsonObject2.getString("img"));
                                step.setStep(jsonObject2.getString("step"));
                                steps.add(step);
                            }
                            cookBean.setSteps(steps);
                            cookBeans.add(cookBean);
                        }
                        Message message = new Message();
                        message.what = SUCCESS;
                        handler.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Message message = new Message();
                    message.what = ERROR;
                    handler.sendMessage(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Message message = new Message();
                    message.what = ERROR;
                    handler.sendMessage(message);
                }
            }
        }).start();
        }else {
            ToastUtil.toastShow(getContext(),"请输入菜名");
        }
    }
}
