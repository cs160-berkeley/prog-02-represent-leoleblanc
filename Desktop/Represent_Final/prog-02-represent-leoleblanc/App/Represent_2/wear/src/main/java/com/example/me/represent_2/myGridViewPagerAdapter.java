package com.example.me.represent_2;

import android.content.Context;
import android.support.wearable.view.GridPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Me on 3/2/16.
 */
public class myGridViewPagerAdapter extends GridPagerAdapter {

    private Context mContext;
    private List mRows;
    String[] names;
    String[] parties;
//    int[] images;
    Page[][] pages;
    LayoutInflater inflater;
    String[] urls;

    public myGridViewPagerAdapter(Context ctx, String[] nameList, int[] imageList, String[] partiesList, String[] picStrings) {
        mContext = ctx;
        names = nameList;
//        images = imageList;
        parties = partiesList;
        pages = new Page[names.length][1];
        urls = picStrings;
        fillPages();
    }

    public void fillPages() {
        for (int i = 0; i < names.length; i++) {
            Page page = new Page();
            page.name = names[i];
//            page.pic = images[i];
            page.party = parties[i];
            page.picUrl = urls[i];
            pages[i][0] = page;
        }
    }

    public static class Page {
        //resources
        String name;
//        int pic;
        String party;
        String picUrl;
    }

    @Override
    public int getRowCount() {
        return pages.length;
    }

    @Override
    public int getColumnCount(int i) {
        return pages[i].length;
    }

    public class ViewHolder {

        ImageView pic;
        TextView name;
        TextView party;
    }

    @Override
    public Object instantiateItem(ViewGroup viewGroup, int row, int col) {
        View v = View.inflate(mContext, R.layout.page_model, null);

        //my viewHolder object
//        Log.d("HERE?", urls[row]);
        ViewHolder holder = new ViewHolder();
//        viewGroup.

        //initialize views
        holder.pic = (ImageView) v.findViewById(R.id.modelPic);
        holder.name = (TextView) v.findViewById(R.id.modelName);
        holder.party = (TextView) v.findViewById(R.id.modelParty);
//        Log.d("HERE? part 2", "proceeded further");

        //set info
//        holder.pic.setImageResource(pages[row][col].pic);
//        Log.d("row is", urls[row]);
//        Log.d("url is", pages[row][col].picUrl);
        Picasso.with(this.mContext).load(pages[row][col].picUrl).into(holder.pic);//test code to set images
        holder.name.setText(pages[row][col].name);
        holder.party.setText(pages[row][col].party);

        viewGroup.addView(v); //adding to the group of views maybe?
        return v; //not sure why we need to return v, but returning holder does not return desired outcome
    }

    @Override
    public void destroyItem(ViewGroup viewGroup, int i, int i1, Object o) {
        viewGroup.removeView((View) o);
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view.equals(o);
//        return false;
    }
}
