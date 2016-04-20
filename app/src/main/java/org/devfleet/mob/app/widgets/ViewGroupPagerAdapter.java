package org.devfleet.mob.app.widgets;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ViewGroupPagerAdapter extends PagerAdapter {
    public ViewGroupPagerAdapter(ViewGroup viewGroup) {
        while (viewGroup.getChildCount() > 0) {
            views.add(viewGroup.getChildAt(0));
            viewGroup.removeViewAt(0);
        }
    }

    private List<View> views = new ArrayList<View>();

    @Override
    public Object instantiateItem(ViewGroup parent, int position) {
        View view = views.get(position);
        ViewPager.LayoutParams lp = new ViewPager.LayoutParams();
        lp.width = ViewPager.LayoutParams.FILL_PARENT;
        lp.height = ViewPager.LayoutParams.FILL_PARENT;
        view.setLayoutParams(lp);
        parent.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup parent, int position, Object object) {
        View view = (View) object;
        parent.removeView(view);
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}