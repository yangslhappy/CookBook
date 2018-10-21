package com.example.fragment;

import android.content.ContentValues;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.config.AppConfig;
import com.example.cookdome.ListActivity;
import com.example.cookdome.R;
import com.example.db.LableDB;
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
 *
 * Created by 10734 on 2018/4/27 0027.
 */

public class ClassIficationFragment extends BaseFragment {

    public static final int SUCCESS = 1;
    public static final int ERROR = 0;

    LableDB lableDB,lableDB2;

    ExpandableListView expandableListView;
    List<CookCategory> cookCategories;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == SUCCESS){
                expandableListView.setAdapter(new ExpandableListAdapter() {
                    @Override
                    public void registerDataSetObserver(DataSetObserver observer) {

                    }

                    @Override
                    public void unregisterDataSetObserver(DataSetObserver observer) {

                    }

                    @Override
                    public int getGroupCount() {
                        return cookCategories.size();
                    }

                    @Override
                    public int getChildrenCount(int groupPosition) {
                        return cookCategories.get(groupPosition).getList().size();
                    }

                    @Override
                    public Object getGroup(int groupPosition) {
                        return cookCategories.get(groupPosition);
                    }

                    @Override
                    public Object getChild(int groupPosition, int childPosition) {
                        return cookCategories.get(groupPosition).getList().get(childPosition);
                    }

                    @Override
                    public long getGroupId(int groupPosition) {
                        return 0;
                    }

                    @Override
                    public long getChildId(int groupPosition, int childPosition) {
                        return 0;
                    }

                    @Override
                    public boolean hasStableIds() {
                        return false;
                    }

                    @Override
                    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
                        ViewHolder viewHolder;
                        if(convertView == null){
                            viewHolder = new ViewHolder();
                            convertView = getLayoutInflater().inflate(R.layout.class_item,null);
                            viewHolder.textView = convertView.findViewById(R.id.class_item_tv);
                            convertView.setTag(viewHolder);
                        }else {
                            viewHolder = (ViewHolder) convertView.getTag();
                        }

                        CookCategory cookCategory = cookCategories.get(groupPosition);

                        viewHolder.textView.setText(cookCategory.getName());
                        return convertView;
                    }

                    @Override
                    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
                        ViewHolder viewHolder;
                        if(convertView == null){
                            viewHolder = new ViewHolder();
                            convertView = getLayoutInflater().inflate(R.layout.class_item_two,null);
                            viewHolder.textView = convertView.findViewById(R.id.class_item_two_tv);
                            convertView.setTag(viewHolder);
                        }else {
                            viewHolder = (ViewHolder) convertView.getTag();
                        }

                        CategrgoryList categrgoryList = cookCategories.get(groupPosition).getList().get(childPosition);

                        viewHolder.textView.setText(categrgoryList.getName());
                        return convertView;
                    }

                    @Override
                    public boolean isChildSelectable(int groupPosition, int childPosition) {
                        return true;
                    }

                    @Override
                    public boolean areAllItemsEnabled() {
                        return false;
                    }

                    @Override
                    public boolean isEmpty() {
                        return false;
                    }

                    @Override
                    public void onGroupExpanded(int groupPosition) {

                    }

                    @Override
                    public void onGroupCollapsed(int groupPosition) {

                    }

                    @Override
                    public long getCombinedChildId(long groupId, long childId) {
                        return 0;
                    }

                    @Override
                    public long getCombinedGroupId(long groupId) {
                        return 0;
                    }

                    class ViewHolder{
                        TextView textView;
                    }
                });
            }else {

            }
        }
    };

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_classification;
    }

    @Override
    public void initView() {
        expandableListView = getActivity().findViewById(R.id.calss_exlv);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Log.i("TAG", "onChildClick: "+groupPosition+childPosition);
                Intent intent = new Intent(getActivity(), ListActivity.class);
                intent.putExtra("name",cookCategories.get(groupPosition).getList().get(childPosition).getName());
                startActivity(intent);
                return true;
            }
        });
    }

    @Override
    public void initData() {
        lableDB = new LableDB(getContext(),"t_lableone");
        lableDB2 = new LableDB(getContext(),"t_labletwo");
        new Thread(new Runnable() {
            @Override
            public void run() {
                String str = AppConfig.CATEGORY + "?key=" + AppConfig.KEY;
                InputStream is = null;
                cookCategories = new ArrayList<>();
                cookCategories = lableDB.findAllData();
                if(cookCategories.size()>0){
                    Message message = new Message();
                    message.what = SUCCESS;
                    handler.sendMessage(message);
                    return;
                }
                try {
                    is = WebUtil.getByWeb(str);
                    String json = WebUtil.getStringByInputStream(is);
                        Log.i("TAG", json);
                        JSONObject jsonObject = new JSONObject(json);
                        cookCategories = new ArrayList<>();
                        if(jsonObject.getString("resultcode").equals("200")){
                            JSONArray jsonArray  = jsonObject.getJSONArray("result");
                            for(int i = 0 ; i < jsonArray.length() ; i++){
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                CookCategory cookCategory = new CookCategory();
                                cookCategory.setParentId(jsonObject1.getString("parentId"));
                                cookCategory.setName(jsonObject1.getString("name"));
                                List<CategrgoryList> categrgoryLists = new ArrayList<>();
                                JSONArray jsonArray1 = jsonObject1.getJSONArray("list");
                                for(int j = 0 ;  j < jsonArray1.length() ; j++){
                                    JSONObject jsonObject2 = jsonArray1.getJSONObject(j);
                                    CategrgoryList categrgoryList = new CategrgoryList();
                                    categrgoryList.setId(jsonObject2.getString("id"));
                                    categrgoryList.setName(jsonObject2.getString("name"));
                                    categrgoryList.setParentId(jsonObject2.getString("parentId"));
                                    categrgoryLists.add(categrgoryList);
                                }
                                cookCategory.setCategrgoryList(categrgoryLists);
                                cookCategories.add(cookCategory);


                            }
                            Message message = new Message();
                            message.what = SUCCESS;
                            handler.sendMessage(message);
                            addData(cookCategories);
                        }
                        else {
                            ToastUtil.toastShow(getContext(),"请求错误");
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
                    } finally {
                        if(is!=null){
                            try {
                                is.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                }
            }
        }).start();
    }

    public void addData(List<CookCategory> list){
        ContentValues contentValues = null;
        ContentValues contentValues2 = null;
        for(CookCategory cookCategory : list){
            if(contentValues == null){
                contentValues = new ContentValues();
            }
            else {
                contentValues.clear();
            }
            contentValues.put("parent_id",cookCategory.getParentId());
            contentValues.put("name",cookCategory.getName());
            lableDB.addData(contentValues);
            for(CategrgoryList categrgoryList : cookCategory.getList()){
                if(contentValues2 == null){
                    contentValues2 = new ContentValues();
                }
                else {
                    contentValues2.clear();
                }
                contentValues2.put("table_id",categrgoryList.getId());
                contentValues2.put("parent_id",categrgoryList.getParentId());
                contentValues2.put("name",categrgoryList.getName());
                lableDB2.addData(contentValues2);
            }
        }
    }

}
