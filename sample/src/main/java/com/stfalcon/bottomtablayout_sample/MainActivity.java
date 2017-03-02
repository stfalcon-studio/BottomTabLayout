package com.stfalcon.bottomtablayout_sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.stfalcon.bottomtablayout.BottomTabLayout;

public class MainActivity extends AppCompatActivity {
    private BottomTabLayout bottomTabLayout;
    private int container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = R.id.container; //Container for fragments

        //Setup button tab layout
        bottomTabLayout = (BottomTabLayout) findViewById(R.id.bottomTabLayout);
        //set button text style
        bottomTabLayout.setButtonTextStyle(R.style.TextGray12);
        // set buttons from menu resource
        bottomTabLayout.setItems(R.menu.menu_bottom_layout);
        //set on selected tab listener.
        bottomTabLayout.setListener(new BottomTabLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id) {
                switchFragment(id);
            }
        });
        //set button that will be select on start activity
        bottomTabLayout.setSelectedTab(R.id.menu_button1);
        //enable indicator
        bottomTabLayout.setIndicatorVisible(true);
        //indicator height
        bottomTabLayout.setIndicatorHeight(getResources().getDimension(R.dimen.indicator_height));
        //indicator color
        bottomTabLayout.setIndicatorColor(R.color.green);
        //indicator line color
        bottomTabLayout.setIndicatorLineColor(R.color.dark);
        bottomTabLayout.setSelectedTab(R.id.menu_button5);
    }

    /**
     * Show fragment in container
     *
     * @param id Menu item res id
     */
    public void switchFragment(int id) {
        Fragment fragment = null;
        switch (id) {
            case R.id.menu_button1:
                fragment = ColoredFragment.newInstance(R.color.blue, "Fragment 1");
                break;
            case R.id.menu_button2:
                fragment = ColoredFragment.newInstance(R.color.green, "Fragment 2");
                break;
            case R.id.menu_button3:
                fragment = ColoredFragment.newInstance(R.color.pink, "Fragment 3");
                break;
            case R.id.menu_button4:
                fragment = ColoredFragment.newInstance(R.color.blueDark, "Fragment 4");
                break;
            case R.id.menu_button5:
                fragment = ColoredFragment.newInstance(R.color.white, "Fragment 5");
                break;
        }
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(container, fragment);
            transaction.commit();
        }
    }
}
