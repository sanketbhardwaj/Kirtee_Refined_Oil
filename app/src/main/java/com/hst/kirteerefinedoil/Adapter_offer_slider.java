package com.hst.kirteerefinedoil;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adapter_offer_slider extends PagerAdapter {
    Context mContext;
    private ArrayList<modelOffer> data = new ArrayList<>();

    public Adapter_offer_slider(Context mContext, ArrayList<modelOffer> data) {
        this.mContext = mContext;
        this.data = data;

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // TODO Auto-generated method stub

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        View rootview = inflater.inflate(R.layout.viewpager_view, container, false);

        ImageView iv = (ImageView) rootview.findViewById(R.id.imageView1);
        //Here set your image for every slide
        Glide.with(mContext).load(data.get(position).getCover()).into(iv);

        ((ViewPager) container).addView(rootview, 0);
        return rootview;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        ((ViewPager) container).removeView((View) object);

    }
}