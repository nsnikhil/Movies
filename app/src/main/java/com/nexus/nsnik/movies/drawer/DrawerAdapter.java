package com.nexus.nsnik.movies.drawer;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nexus.nsnik.movies.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DrawerAdapter extends ArrayAdapter<DrawerCustomList> {

    private static final int vTypeHeader = 0;
    private static final int vTypeItem = 1;
    ArrayList<DrawerCustomList> mList = new ArrayList<>();

    public DrawerAdapter(Context context, ArrayList<DrawerCustomList> list) {
        super(context, 0, list);
        mList = list;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0) ? vTypeHeader : vTypeItem;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        View v = convertView;
        MyViewHolder mViewHolder = null;
        if (v == null) {
            if (position == 0) {
                v = LayoutInflater.from(getContext()).inflate(R.layout.drawer_header_layout, parent, false);
                mViewHolder = new MyViewHolder(v, type);
            }
            else  {
                v = LayoutInflater.from(getContext()).inflate(R.layout.drawer_single_item, parent, false);
                mViewHolder = new MyViewHolder(v, type);
                DrawerCustomList items = mList.get(position);
                mViewHolder.itemName.setText(items.getmListName());
                mViewHolder.itemImage.setImageResource(items.getmListImage());
            }
        }
        if(position==1){
            v.setSelected(true);
        }
        v.setTag(mViewHolder);
        return v;
    }


    public static class MyViewHolder {

        TextView itemName, headerName, headerEmail;
        CircleImageView headerPicture;
        ImageView itemImage;

        public MyViewHolder(View v, int type) {
            if (type == vTypeHeader) {
                headerName = (TextView) v.findViewById(R.id.drawerHeaderName);
                headerEmail = (TextView) v.findViewById(R.id.drawerHeaderEmail);
                headerPicture = (CircleImageView) v.findViewById(R.id.drawerCircleImageView);
            } else {
                itemName = (TextView) v.findViewById(R.id.drawerItemName);
                itemImage = (ImageView) v.findViewById(R.id.drawerItemImage);
            }
        }
    }
}




