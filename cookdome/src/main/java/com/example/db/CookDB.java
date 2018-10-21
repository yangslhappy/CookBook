package com.example.db;

/**
 * Created by 10734 on 2018/5/8 0008.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.model.CookBean;
import com.example.model.Steps;

import java.util.ArrayList;
import java.util.List;

/**
 * GridView数据库
 */
public class CookDB {
    private final String TAG = "CookDB";
            public static final String T_TABLE = "t_cook";
    public static final String T_STEP = "t_step";
    private String dbName = "cook.db";// 数据库名
    private String tableName;// 表名
    private int version = 1;// 版本号
    private DBManager mDBManager;
    private ArrayList<CookBean> cookBeans = new ArrayList<CookBean>();

    public CookDB(Context context, String tableName) {
        mDBManager = new DBManager(context);
        String sql = mDBManager.addPrimaryKey().addText("cook_id")
                .addText("cook_title")
                .addText("cook_tags")
                .addText("cook_imtro")
                .addText("cook_ingredients")
                .addText("cook_burden")
                .addText("cook_albums")
                .addText("step_id")
                .getSql();
        Log.i(TAG, sql);
        String sql2 = mDBManager.addPrimaryKey().addText("step_id")
                .addText("step_step")
                .addText("step_img")
                .getSql();
        Log.i(TAG, sql2);
        String[] tables = {T_TABLE,T_STEP};
        String[] slqs = {sql,sql2};
//        for (int i = 0; i < tables.length; i++) {
//            mDBManager.create(dbName, version, tables[i], slqs[i]);
//        }
        mDBManager.creates(dbName, version, tables, slqs);
        this.tableName = tableName;
    }


    public static CookDB init(Context context, String tableName) {
        return new CookDB(context, tableName);
    }

    /**
     * 增加新数据
     *
     * @param contentValues 数据源
     */
    public void addData(ContentValues contentValues) {
        mDBManager.mInsert(tableName, "cook_id", contentValues);
        Log.i(TAG, "delData:增加了一条数据 ");
    }

    /**
     * 删除一条数据
     *
     * @param menu_id
     */
    public void delData(String menu_id) {
        mDBManager.mDelete(tableName, "step_id=?", new String[]{menu_id});
        Log.i(TAG, "delData:删除了一条数据 ");
    }

    /**
     * 清空表中的内容
     */
    public void delTable() {
        mDBManager.mDeleteTable(tableName);
    }

    /**
     * 是否是空表
     *
     * @return true 是空表
     */
    public boolean isEmptyTable() {
        if (mDBManager.getDataNum(tableName) > 0) {
            return false;
        }
        return true;
    }

    /**
     * 数据库是否存在要查询的这条数据
     *
     * @param columnName 查询的字段
     * @param data       查询的数据
     * @return
     */
    public boolean hasThisData(String columnName, String data) {
        return mDBManager.hasThisData(tableName, columnName, data);
    }

    /**
     * 本地数据库是否存在这条MenuID
     *
     * @param data 查询的数据
     * @return true 有这条数据
     */
    public boolean hasThisMenuID(String data) {
        return mDBManager.hasThisData(tableName, "cook_id", data);
    }

    /**
     * 修改一条数据的内容
     *
     * @param values      数据
     * @param whereclause 条件
     */
    public void modifyData(ContentValues values, String whereclause) {
        mDBManager.mUpdate(tableName, values, whereclause);
        Log.i(TAG, "modifyData: 修改了一条数据");
    }

    /**
     * 查询本地数据库的全部数据
     *
     * @return
     */
    public ArrayList<CookBean> findAllData() {
        cookBeans.clear();
        Cursor cursor = mDBManager.mQueryAll(tableName, "cook_id asc");
        while (cursor.moveToNext()) {
            String cook_id = cursor.getString(cursor.getColumnIndex("cook_id"));
            String cook_title = cursor.getString(cursor.getColumnIndex("cook_title"));
            String cook_tags = cursor.getString(cursor.getColumnIndex("cook_tags"));
            String cook_imtro = cursor.getString(cursor.getColumnIndex("cook_imtro"));
            String cook_ingredients = cursor.getString(cursor.getColumnIndex("cook_ingredients"));
            String cook_burden = cursor.getString(cursor.getColumnIndex("cook_burden"));
            String cook_albums = cursor.getString(cursor.getColumnIndex("cook_albums"));
            List<Steps> stepsList = new ArrayList<>();
            Cursor cursor1 = mDBManager.mQuery(T_STEP, null,"step_id="+cursor.getString(cursor.getColumnIndex("step_id")),null,null,null,null);
            while (cursor1.moveToNext()){
                Steps steps = new Steps();
                steps.setImg(cursor1.getString(cursor1.getColumnIndex("step_img")));
                steps.setStep(cursor1.getString(cursor1.getColumnIndex("step_step")));
                stepsList.add(steps);
            }
            CookBean cookBean = new CookBean();
            cookBean.setId(cook_id);
            cookBean.setTitle(cook_title);
            cookBean.setTags(cook_tags);
            cookBean.setImtro(cook_imtro);
            cookBean.setIngredients(cook_ingredients);
            cookBean.setBurden(cook_burden);
            List<String> strings =  new ArrayList<String>();
            strings.add(cook_albums);
            cookBean.setSteps(stepsList);
            cookBean.setAlbums(strings);
            cookBeans.add(cookBean);
        }
        mDBManager.closeAll();
        return cookBeans;
    }

}

