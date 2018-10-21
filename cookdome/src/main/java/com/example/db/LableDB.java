package com.example.db;

/**
 * Created by 10734 on 2018/5/8 0008.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.model.CategrgoryList;
import com.example.model.CookBean;
import com.example.model.CookCategory;
import com.example.model.Steps;

import java.util.ArrayList;
import java.util.List;

/**
 * Lable数据库
 */
public class LableDB {
    private final String TAG = "LableDB";
    public static final String T_TABLE = "t_lableone";
    public static final String T_STEP = "t_labletwo";
    private String dbName = "lable.db";// 数据库名
    private String tableName;// 表名
    private int version = 1;// 版本号
    private DBManager mDBManager;
    private ArrayList<CookCategory> cookCategories = new ArrayList<>();

    public LableDB(Context context, String tableName) {
        mDBManager = new DBManager(context);
        String sql = mDBManager.addPrimaryKey().addText("parent_id")
                .addText("name")
                .getSql();
        Log.i(TAG, sql);
        String sql2 = mDBManager.addPrimaryKey().addText("table_id")
                .addText("parent_id")
                .addText("name")
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


    public static LableDB init(Context context, String tableName) {
        return new LableDB(context, tableName);
    }

    /**
     * 增加新数据
     *
     * @param contentValues 数据源
     */
    public void addData(ContentValues contentValues) {
        mDBManager.mInsert(tableName, null, contentValues);
        Log.i(TAG, "delData:增加了一条数据 ");
    }

    /**
     * 删除一条数据
     *
     * @param menu_id
     */
    public void delData(String menu_id) {
        mDBManager.mDelete(tableName, "parent_id=?", new String[]{menu_id});
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
        return mDBManager.hasThisData(tableName, "parent_id", data);
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
    public ArrayList<CookCategory> findAllData() {
        cookCategories.clear();
        Cursor cursor = mDBManager.mQueryAll(tableName, null);
        while (cursor.moveToNext()) {
            String parent_id = cursor.getString(cursor.getColumnIndex("parent_id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            List<CategrgoryList> categrgoryLists = new ArrayList<>();
            Cursor cursor1 = mDBManager.mQuery(T_STEP, null,"parent_id="+cursor.getString(cursor.getColumnIndex("parent_id")),null,null,null,null);
            while (cursor1.moveToNext()){
                CategrgoryList categrgoryList = new CategrgoryList();
                categrgoryList.setId(cursor1.getString(cursor1.getColumnIndex("table_id")));
                categrgoryList.setParentId(cursor1.getString(cursor1.getColumnIndex("parent_id")));
                categrgoryList.setName(cursor1.getString(cursor1.getColumnIndex("name")));
                categrgoryLists.add(categrgoryList);
            }
            CookCategory cookCategory = new CookCategory();
            cookCategory.setParentId(parent_id);
            cookCategory.setName(name);
            cookCategory.setCategrgoryList(categrgoryLists);
            cookCategories.add(cookCategory);
        }
        mDBManager.closeAll();
        return cookCategories;
    }

}

