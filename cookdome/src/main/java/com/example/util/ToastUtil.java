package com.example.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 10734 on 2018/5/4 0004.
 */

public class ToastUtil {

    public static void toastShow(Context context,String str){
        Toast.makeText(context,str,Toast.LENGTH_SHORT).show();
    }
}
