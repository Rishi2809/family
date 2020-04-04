package com.rishi.family.adapter;


import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rishi.family.R;
import com.rishi.family.model.Dates;

import java.util.ArrayList;


public class ViewPageAdapter extends PagerAdapter {
    private ArrayList<Dates> plist;

    private Context context;
    private LayoutInflater layoutInflater;
    private Integer [] images = {R.drawable.imgevents1, R.drawable.imgevents2,R.drawable.imgevents4,R.drawable.imgevents5};
    private String [] names = {"Rishi","Akash","Godha","Yash"};
    private String [] dates = {"28/09/1998","05/03/1997","03/09/1999","01/01/1997"};

    public ViewPageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_layout4, null);
        TextView name = (TextView)view.findViewById(R.id.name);
        TextView dob = (TextView)view.findViewById(R.id.occassion);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageView2);
        imageView.setImageResource(images[position]);
        name.setText(names[position]+"'s Birthday");
        dob.setText(dates[position]);
        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}