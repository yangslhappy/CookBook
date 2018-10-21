package com.example.cookdome;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adpter.CookDetailsAdapter;
import com.example.db.CookDB;
import com.example.model.CookBean;
import com.example.model.CookDetails;
import com.example.model.CookStep;
import com.example.model.Steps;
import com.example.util.ToastUtil;
import com.example.util.WebUtil;
import com.example.view.SmartImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CookActivity extends AppCompatActivity {

    SmartImageView smartImageView;
    TextView intro_tv,ingredients_tv,burden_tv,cook_details_name;
    ImageView shoucan_img;
    ListView cook_details_lv;

    CookDB cookDB = new CookDB(this,"t_cook");
    CookDB cookDB2 = new CookDB(this,"t_step");
    CookBean cookBean;

    Intent intent ;

    boolean isShoucan = false;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_details_h);
        intent = getIntent();
        cookBean = (CookBean) intent.getSerializableExtra("cookbean");
        isShoucan =  cookDB.hasThisMenuID(cookBean.getId());
        initView();
        progressDialog = ProgressDialog.show(CookActivity.this,null,"请稍后...");
        initData();

    }

    public void initView(){
        View view = getLayoutInflater().inflate(R.layout.activity_cook_details_head,null);
        shoucan_img = findViewById(R.id.shoucan_btn);
        smartImageView = view.findViewById(R.id.cook_details_Img);
        cook_details_name = view.findViewById(R.id.cook_details_name);
        intro_tv = view.findViewById(R.id.cook_details_intro);
        ingredients_tv = view.findViewById(R.id.ingredients);
        burden_tv = view.findViewById(R.id.burden);
        cook_details_lv = findViewById(R.id.cook_details_lv);
        cook_details_lv.addHeaderView(view);
        if(isShoucan){
            shoucan_img.setImageResource(R.drawable.isshoucan);
        }
    }

    public void initData(){
        progressDialog.dismiss();
        cook_details_name.setText(cookBean.getTitle());
        smartImageView.setImageUrl(cookBean.getAlbums().get(0));
        intro_tv.setText("\u3000\u3000"+cookBean.getImtro());
        ingredients_tv.setText(cookBean.getIngredients());
        burden_tv.setText(cookBean.getBurden());
        cook_details_lv.setAdapter(new CookDetailsAdapter(CookActivity.this,cookBean.getSteps()));
    }

    public void onShoucan(View view){
        if(isShoucan){
            cookDB.delData(cookBean.getId());
            cookDB2.delData(cookBean.getId());
            isShoucan = false;
            shoucan_img.setImageResource(R.drawable.shoucan);
            ToastUtil.toastShow(this,"取消成功");
        }else {
            ContentValues contentValues = new ContentValues();
            contentValues.put("cook_id",cookBean.getId());
            contentValues.put("cook_title",cookBean.getTitle());
            contentValues.put("cook_tags",cookBean.getTags());
            contentValues.put("cook_imtro",cookBean.getImtro());
            contentValues.put("cook_ingredients",cookBean.getIngredients());
            contentValues.put("cook_burden",cookBean.getBurden());
            contentValues.put("cook_albums",cookBean.getAlbums().get(0));
            contentValues.put("step_id",cookBean.getId());
            cookDB.addData(contentValues);

            ContentValues contentValues2 = new ContentValues();
            for(Steps steps : cookBean.getSteps()){
                contentValues2.put("step_id",cookBean.getId());
                contentValues2.put("step_step",steps.getStep());
                contentValues2.put("step_img",steps.getImg());
                cookDB2.addData(contentValues2);
            }
            Log.i("CookActivity", "收藏成功");
            isShoucan = true;
            shoucan_img.setImageResource(R.drawable.isshoucan);
            ToastUtil.toastShow(this,"收藏成功");
        }
    }

    public void onBack(View view){
        finish();
    }

}
