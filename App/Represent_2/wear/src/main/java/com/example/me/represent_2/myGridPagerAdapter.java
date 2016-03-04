package com.example.me.represent_2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.FragmentGridPagerAdapter;

import java.util.List;

public class myGridPagerAdapter extends FragmentGridPagerAdapter {

    private Context mContext;
    private List mRows;
    String[] names;
    int[] images;
    Page[][] pages;
//    Page[] pages;

    public myGridPagerAdapter(Context ctx, FragmentManager fm, String[] nameList, int[] imageList) {
        super(fm);
        mContext = ctx;
        names = nameList;
        images = imageList;
//        int length = names.length;
//        pages = new Page[names.length];
        pages = new Page[names.length][1];
        fillPages();

    }

    public void fillPages() {
        for (int i = 0; i < names.length; i++) {
            Page page = new Page();
            page.name = names[i];
            page.pic = images[i];
//            pages[i] = page;
            pages[i][0] = page;
        }
//        Page page = new Page();

    }

    private static class Page {
        //resources
        String name;
        int pic;
    }

    //Page[][] pages = {}; //not sure how to initialize pages


    @Override
    public Fragment getFragment(int row, int col) {
//        Page page = pages[row][col];
        Page page = pages[row][col]; //row or col
        String name = page.name;
        int pic = page.pic;
        CardFragment fragment = CardFragment.create("", name, pic);
        return fragment;
    }

    @Override
    public int getRowCount() {
        return pages.length;
    }

    @Override
    public int getColumnCount(int i) {
        return pages[i].length;
    }
}
