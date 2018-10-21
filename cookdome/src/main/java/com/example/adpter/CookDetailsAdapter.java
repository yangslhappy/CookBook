package com.example.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cookdome.R;
import com.example.model.Steps;
import com.example.view.SmartImageView;

import java.util.List;

/**
 * Created by 10734 on 2018/4/18 0018.
 */

public class CookDetailsAdapter extends BaseAdapter{

    LayoutInflater layoutInflater;
    List<Steps> steps;

    public CookDetailsAdapter(Context context, List<Steps> steps) {
        this.layoutInflater = LayoutInflater.from(context);
        this.steps = steps;
    }

    @Override
    public int getCount() {
        return steps.size();
    }

    @Override
    public Object getItem(int position) {
        return steps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.adapter_cook_details_item,null);
            viewHolder.cookName = convertView.findViewById(R.id.step_tv);
            viewHolder.cookTitle = convertView.findViewById(R.id.cook_step_details_Intro);
            viewHolder.cookImg = convertView.findViewById(R.id.cook_step_details_Img);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Steps step = steps.get(position);

        viewHolder.cookName.setText("第"+(position+1)+"步");
        viewHolder.cookTitle.setText(step.getStep());
        viewHolder.cookImg.setImageUrl(step.getImg());
        return convertView;
    }

    class ViewHolder{
        TextView cookName ;
        TextView cookTitle ;
        SmartImageView cookImg ;
    }
}
