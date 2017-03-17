package com.stfalcon.bottomtablayout;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.annotation.ColorRes;
import android.support.annotation.MenuRes;
import android.support.annotation.StyleRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.PopupMenu;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.stfalcon.buttontablayout.R;

import java.util.ArrayList;

/**
 * Created by Anton Bevza on 5/5/16.
 */
public class BottomTabLayout extends RelativeLayout {
    private ArrayList<TabButton> buttons = new ArrayList<>();
    private OnItemSelectedListener listener;
    private int selectedId;
    private ViewGroup indicatorGroup;
    private View indicator;
    private View indicatorLine;
    private LinearLayout content;

    private int buttonTextStyle;
    private int bubbleColor = ContextCompat.getColor(getContext(), R.color.blue);
    private int bubblePaddingLeft;
    private int bubblePaddingRight = getResources().getDimensionPixelSize(R.dimen.bubble_padding_right);
    private int bubblePaddingTop = getResources().getDimensionPixelSize(R.dimen.bubble_padding_top);
    private int bubblePaddingBottom;
    private int bubbleTextStyle;

    public BottomTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.view_bottom_tab_layout, this, true);

        content = (LinearLayout) v.findViewById(R.id.content);
        content.setOrientation(LinearLayout.HORIZONTAL);

        indicatorGroup = (ViewGroup) v.findViewById(R.id.group_indicator);
        indicator = v.findViewById(R.id.indicator);
        indicatorLine = v.findViewById(R.id.indicator_line);
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
        content.removeAllViews();

        content.setWeightSum(menu.size());
        for (int i = 0; i < menu.size(); i++) {
            final TabButton tabButton = new TabButton(getContext());
            if (menu.getItem(i).getTitle() != null) {
                tabButton.setText(menu.getItem(i).getTitle().toString());
            } else {
                tabButton.setText(null);
            }
            tabButton.setIcon(menu.getItem(i).getIcon());
            tabButton.setTag(menu.getItem(i).getItemId());
            tabButton.setButtonTextStyle(buttonTextStyle);

            //setup tab bubble
            tabButton.setBubbleColor(bubbleColor);
            tabButton.setBubbleTextStyle(bubbleTextStyle);
            tabButton.setBubblePadding(bubblePaddingLeft, bubblePaddingTop, bubblePaddingRight, bubblePaddingBottom);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
            tabButton.setLayoutParams(params);
            buttons.add(tabButton);
            content.addView(tabButton);

            tabButton.setListener(new TabButton.ClickListener() {
                @Override
                public void onClick() {
                    int id = (int) tabButton.getTag();
                    selectTab(id);
                }
            });
        }
        //set indicator width
        content.post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams layoutParams = indicator.getLayoutParams();
                layoutParams.width = buttons.get(0).getWidth();
                indicator.setLayoutParams(layoutParams);
            }
        });
    }

    /**
     * Select tab by menu res id
     *
     * @param id Menu item res id
     */
    public void selectTab(int id) {
        selectTab(id, true);
    }

    private void selectTab(int id, boolean animated) {
        if (selectedId != id) {
            for (TabButton b : buttons) {
                b.setSelected(id == (int) b.getTag());
            }
            selectedId = id;
            if (listener != null) {
                listener.onItemSelected(id);
            }
            if (animated) {
                updateIndicator();
            } else {
                updateIndicatorWithoutAnimation();
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
            selectTab(tabId, false);
        }
    }

    /**
     * Set text button style.
     *
     * @param res Style res id
     */
    public void setButtonTextStyle(@StyleRes int res) {
        buttonTextStyle = res;
        if (buttons.size() > 0) {
            for (int i = 0; i < buttons.size(); i++) {
                buttons.get(i).setButtonTextStyle(buttonTextStyle);
            }
        }
    }

    /**
     * Show bubble with count on tab with tabId.
     *
     * @param tabId Menu item res id
     * @param count count. If count==0 bubble will be hide
     */
    public void showTabBubbleCount(int tabId, int count) {
        for (TabButton b : buttons) {
            if (tabId == (int) b.getTag()) {
                b.setUnreadCount(count);
            }
        }
    }

    /**
     * Set tab bubble color
     *
     * @param color rgb
     */
    public void setTabBubbleColor(int color) {
        bubbleColor = color;
        for (TabButton b : buttons) {
            b.setBubbleColor(color);
        }
    }

    /**
     * Set tab bubble padding
     *
     * @param left   px
     * @param top    px
     * @param right  px
     * @param bottom px
     */
    public void setTabBubblePadding(int left, int top, int right, int bottom) {
        bubblePaddingLeft = left;
        bubblePaddingTop = top;
        bubblePaddingRight = right;
        bubblePaddingBottom = bottom;
        for (TabButton b : buttons) {
            b.setBubblePadding(left, top, right, bottom);
        }
    }

    public void setTabBubbleTextStyle(@StyleRes int res) {
        bubbleTextStyle = res;
        for (TabButton b : buttons) {
            b.setBubbleTextStyle(res);
        }
    }

    /**
     * Set indicator group visibility
     *
     * @param isVisible visibility
     */
    public void setIndicatorVisible(boolean isVisible) {
        indicatorGroup.setVisibility(isVisible ? VISIBLE : GONE);
    }

    /**
     * Set indicator height
     *
     * @param height height
     */
    public void setIndicatorHeight(float height) {
        ViewGroup.LayoutParams layoutParams = indicator.getLayoutParams();
        layoutParams.height = (int) height;
        indicator.setLayoutParams(layoutParams);
    }

    /**
     * Set indicator color
     *
     * @param color Color res id
     */
    public void setIndicatorColor(@ColorRes int color) {
        indicator.setBackgroundResource(color);
    }

    /**
     * Set indicator line color
     *
     * @param color Color res id
     */
    public void setIndicatorLineColor(@ColorRes int color) {
        indicatorLine.setBackgroundResource(color);
    }

    /**
     * Update indicator position
     */
    private void updateIndicator() {
        ObjectAnimator animX = ObjectAnimator.ofFloat(indicator, "x", buttons.get(getCurrentPosition()).getX() + content.getX());
        animX.setDuration(200);
        animX.start();
    }

    /**
     * Update indicator position
     */
    private void updateIndicatorWithoutAnimation() {
        if (buttons.size() > 0) {
            buttons.get(getCurrentPosition()).post(new Runnable() {
                @Override
                public void run() {
                    indicator.setX(buttons.get(getCurrentPosition()).getX() + content.getX());
                }
            });
        }
    }

    /**
     * Get current selected position
     *
     * @return Position
     */
    private int getCurrentPosition() {
        for (int i = 0; i < buttons.size(); i++) {
            if ((int) buttons.get(i).getTag() == selectedId) {
                return i;
            }
        }
        return 1;
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

    @BindingAdapter("app:indicatorVisible")
    public static void bindIndicatorVisibke(BottomTabLayout bottomTabLayout, boolean isVisible) {
        bottomTabLayout.setIndicatorVisible(isVisible);
    }

    @BindingAdapter("app:indicatorHeight")
    public static void bindIndicatorHeight(BottomTabLayout bottomTabLayout, float height) {
        bottomTabLayout.setIndicatorHeight(height);
    }

    @BindingAdapter("app:indicatorColor")
    public static void bindIndicatorColor(BottomTabLayout bottomTabLayout, @ColorRes int color) {
        bottomTabLayout.setIndicatorColor(color);
    }

    @BindingAdapter("app:indicatorLineColor")
    public static void bindIndicatorLineColor(BottomTabLayout bottomTabLayout, @ColorRes int color) {
        bottomTabLayout.setIndicatorLineColor(color);
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
