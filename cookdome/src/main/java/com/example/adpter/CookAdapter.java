package com.example.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cookdome.R;
import com.example.model.CookBean;
import com.example.view.SmartImageView;

import java.util.List;

/**
 * Created by 10734 on 2018/4/18 0018.
 */

public class CookAdapter extends BaseAdapter{

    LayoutInflater layoutInflater;
    List<CookBean> cookBeans;

    public CookAdapter(Context context, List<CookBean> cookBeans) {
        this.layoutInflater = LayoutInflater.from(context);
        this.cookBeans = cookBeans;
    }

    @Override
    public int getCount() {
        return cookBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return cookBeans.get(position);
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
            convertView = layoutInflater.inflate(R.layout.adapter_cook_item,null);
            viewHolder.cookName = convertView.findViewById(R.id.cookName);
            viewHolder.cookTitle = convertView.findViewById(R.id.cookTitle);
            viewHolder.cookIntro = convertView.findViewById(R.id.cookIntro);
            viewHolder.cookImg = convertView.findViewById(R.id.cookImg);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        CookBean cookBean = cookBeans.get(position);

        viewHolder.cookName.setText(cookBean.getTitle());
        viewHolder.cookTitle.setText(cookBean.getTags());
        if(cookBean.getTitle().length() > 90){
            viewHolder.cookIntro.setText(cookBean.getImtro().substring(0, 80)+"...");
        }else{
            viewHolder.cookIntro.setText(cookBean.getImtro());
        }
        viewHolder.cookImg.setImageUrl(cookBean.getAlbums().get(0));
        return convertView;
    }

    class ViewHolder{
        TextView cookName ;
        TextView cookTitle ;
        TextView cookIntro ;
        SmartImageView cookImg ;
    }
}
