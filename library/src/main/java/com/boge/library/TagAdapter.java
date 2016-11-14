package com.boge.library;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author boge
 * @version 1.0
 * @date 2016/11/14
 */

public class TagAdapter<T> extends BaseAdapter {
    int tag0 = Color.argb(Integer.parseInt("99",16),Integer.parseInt("0",16)
                ,Integer.parseInt("b9",16),Integer.parseInt("da",16));
    int tag1 = Color.argb(Integer.parseInt("55",16),Integer.parseInt("66",16)
            ,Integer.parseInt("0",16),Integer.parseInt("99",16));
    int tag2 = Color.argb(Integer.parseInt("88",16),Integer.parseInt("cd",16)
            ,Integer.parseInt("95",16),Integer.parseInt("0c",16));
    int tag3 = Color.argb(Integer.parseInt("99",16),Integer.parseInt("7C",16)
            ,Integer.parseInt("CD",16),Integer.parseInt("7C",16));
    int tag4 = Color.argb(Integer.parseInt("99",16),Integer.parseInt("ff",16)
            ,Integer.parseInt("0",16),Integer.parseInt("0",16));
    int tag5 = Color.argb(Integer.parseInt("99",16),Integer.parseInt("DE",16)
            ,Integer.parseInt("60",16),Integer.parseInt("14",16));
    int tag6 = Color.argb(Integer.parseInt("AA",16),Integer.parseInt("22",16)
            ,Integer.parseInt("8B",16),Integer.parseInt("22",16));
    int tag7 = Color.argb(Integer.parseInt("55",16),Integer.parseInt("FE",16)
            ,Integer.parseInt("39",16),Integer.parseInt("81",16));

    private int[] colors = {tag0, tag1, tag2, tag3, tag4, tag5, tag6, tag7};

    private final Context mContext;
    private List<T> mDataList;

    public TagAdapter(Context context) {
        this.mContext = context;
        mDataList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public T getItem(int i) {
        return mDataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        TextView textView = new TextView(mContext);
        textView.setTextColor(Color.WHITE);
        textView.setPadding(5, 5, 5, 5);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(textSize);
        //设置背景颜色
        textView.setBackgroundColor(colors[i%8]);

        T t = mDataList.get(i);

        if (t instanceof String) {
            textView.setText((String) t);
        }
        return textView;
    }

    private int textSize = 18;

    public void setTextSize(int size){
        this.textSize = size;
    }

    public void addAll(List<T> list){
        mDataList.addAll(list);
        notifyDataSetChanged();
    }
    public void addAll(List<T> list, int position){
        mDataList.addAll(position, list);
        notifyDataSetChanged();
    }

    public void add(T data){
        mDataList.add(data);
        notifyDataSetChanged();
    }
    public void add(T data, int position){
        mDataList.add(position, data);
        notifyDataSetChanged();
    }

    public List<T> getmDataList() {
        return mDataList;
    }

    public void setmDataList(List<T> mDataList) {
        this.mDataList = mDataList;
        notifyDataSetChanged();
    }
}
