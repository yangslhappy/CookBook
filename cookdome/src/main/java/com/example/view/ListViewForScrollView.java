package com.example.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ListView;

/**
 * Created by 10734 on 2018/4/20 0020.
 */

public class ListViewForScrollView extends ListView {
    public ListViewForScrollView(Context context) {
        super(context);
    }

    public ListViewForScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int exp = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2 ,MeasureSpec.AT_MOST);
        Log.i("onMeasure", exp+"");
        super.onMeasure(widthMeasureSpec, exp);
    }
}
