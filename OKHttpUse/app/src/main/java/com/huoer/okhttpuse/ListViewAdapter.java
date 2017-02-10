package com.huoer.okhttpuse;
/*
         |              |
         | \            | \
         |   | | | | | |    | | | | |||||\
         |                          |||||||\
         |         ( )              ||||||||
         |                           |||||/
         |                  | | | | | |||/
         |    |             |          |
         |    |             |          |
       / |   | |            |          |\
      |      |/             |          \|
       \ |                  |
         |                  |
           \ | | | | | | | /
             |       |            <-----辣鸡
             |       |
              |       |
*/

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {
    private ImgClickListener listener;
    private Context context;
    private List<OKBean.DataBean.ItemsBean> itemsBeanList;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setItemsBeanList(List<OKBean.DataBean.ItemsBean> itemsBeanList) {
        this.itemsBeanList = itemsBeanList;
        notifyDataSetChanged();
    }

    public void setOnImgClickListener(ImgClickListener imgClickListener){
        listener = imgClickListener;
    }

    @Override
    public int getCount() {
        return itemsBeanList == null ? 0 : itemsBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_listview, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final OKBean.DataBean.ItemsBean itemsBean = itemsBeanList.get(position);

        viewHolder.textView.setText(itemsBean.getIntroduction());
        final String img = itemsBean.getCover_image_url();
        Glide.with(context).load(img).into(viewHolder.imageView);
        //设置点击出现大图的操作
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onImgClick(img);
            }
        });

        return convertView;
    }

    class ViewHolder{
        TextView textView;
        ImageView imageView;
        ViewHolder(View v){
            textView = (TextView) v.findViewById(R.id.item_text_view);
            imageView = (ImageView) v.findViewById(R.id.item_image_view);
        }

    }

    public interface ImgClickListener{
        void onImgClick(String clickedImgUrl);
    }
}
