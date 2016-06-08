package com.stfalcon.bottomtablayout;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.annotation.MenuRes;
import android.support.annotation.StyleRes;
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
    private ArrayList<TabButton> buttons = new ArrayList<>();
    private OnItemSelectedListener listener;
    private int buttonTextStyle;
    private int selectedId;

    public BottomTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);
    }

    /**
     * Create and configure tab buttons.
     *
     * @param res Menu resource id
     */
    public void setItems(@MenuRes int res) {
        //Need for getting values from menu resource
        PopupMenu p = new PopupMenu(getContext(), null);
        Menu menu = p.getMenu();
        p.getMenuInflater().inflate(res, menu);

        setWeightSum(menu.size());
        for (int i = 0; i < menu.size(); i++) {
            final TabButton tabButton = new TabButton(getContext());
            tabButton.setText(menu.getItem(i).getTitle().toString());
            tabButton.setIcon(menu.getItem(i).getIcon());
            tabButton.setTag(menu.getItem(i).getItemId());
            tabButton.setButtonTextStyle(buttonTextStyle);
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

    /**
     * Select tab by menu res id
     *
     * @param id Menu item res id
     */
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


    /**
     * Set first selected tab after creating tab layout.
     *
     * @param tabId Menu item res id
     */
    public void setSelectedTab(int tabId) {
        if (tabId != 0) {
            selectTab(tabId);
        }
    }

    /**
     * Set text button style. Must be call before setItems() method
     *
     * @param res Style res id
     */
    public void setButtonTextStyle(@StyleRes int res) {
        if (buttons.size() > 0) {
            throw new IllegalStateException("Call this before setItem()");
        }
        buttonTextStyle = res;
    }

    /**
     * Set on item select listener
     *
     * @param listener OnItemSelectedListener
     */
    public void setListener(OnItemSelectedListener listener) {
        this.listener = listener;
    }


    /*
        ---------------------------------
        ADAPTERS FOR ANDROID DATA BINDINGS
        ---------------------------------
     */

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
        bottomTabLayout.setSelectedTab(id);
    }

    @BindingAdapter("app:buttonTextStyle")
    public static void bindButtonTextStyle(BottomTabLayout bottomTabLayout, @StyleRes int res) {
        bottomTabLayout.setButtonTextStyle(res);
    }
    /*
        ---------------------------------
     */


    /**
     * Interface for on item click listener
     */
    public interface OnItemSelectedListener {
        void onItemSelected(int id);
    }
}
