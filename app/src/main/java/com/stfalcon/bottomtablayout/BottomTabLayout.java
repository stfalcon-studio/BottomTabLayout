package com.stfalcon.bottomtablayout;

import android.app.Activity;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.annotation.MenuRes;
import android.support.v7.widget.PopupMenu;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by Anton Bevza on 5/5/16.
 */
public class BottomTabLayout extends LinearLayout {
    private Activity activity;
    private ArrayList<TabButton> buttons = new ArrayList<>();
    private OnItemSelectedListener listener;
    private int selectedId;

    public BottomTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setItems(@MenuRes int res) {
        PopupMenu p = new PopupMenu(getContext(), null);
        Menu menu = p.getMenu();
        activity.getMenuInflater().inflate(res, menu);
        setWeightSum(menu.size());
        for (int i = 0; i < menu.size(); i++) {
            final TabButton tabButton = new TabButton(getContext());
            tabButton.setText(menu.getItem(i).getTitle().toString());
            tabButton.setIcon(menu.getItem(i).getIcon());
            tabButton.setTag(menu.getItem(i).getItemId());
            LayoutParams params = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
            tabButton.setLayoutParams(params);
            buttons.add(tabButton);
            addView(tabButton);

            tabButton.setListener(new TabButton.ClickListener() {
                @Override
                public void onClick() {
                    int id = (int) tabButton.getTag();
                    selectTab(id);
                }
            });
        }
    }

    public void selectTab(int id) {
        if (selectedId != id) {
            for (TabButton b : buttons) {
                b.setSelected(id == (int) b.getTag());
            }
            selectedId = id;
            if (listener != null) {
                listener.onItemSelected(id);
            }
        }
    }

    public void setListener(OnItemSelectedListener listener) {
        this.listener = listener;
    }

    @BindingAdapter("app:activity")
    public static void setActivity(BottomTabLayout bottomTabLayout, Activity activity) {
        bottomTabLayout.setActivity(activity);
    }

    @BindingAdapter("app:items")
    public static void bindItems(BottomTabLayout bottomTabLayout, @MenuRes int res) {
        bottomTabLayout.setItems(res);
    }

    @BindingAdapter("app:listener")
    public static void bindListener(BottomTabLayout bottomTabLayout, OnItemSelectedListener listener) {
        bottomTabLayout.setListener(listener);
    }

    @BindingAdapter("app:selectedTab")
    public static void bindSelectedTab(BottomTabLayout bottomTabLayout, int id) {
        if (id != 0) {
            bottomTabLayout.selectTab(id);
        }
    }

    public interface OnItemSelectedListener {
        void onItemSelected(int id);
    }
}
