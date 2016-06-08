package com.stfalcon.bottomtablayout_sample;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.stfalcon.bottomtablayout.BottomTabLayout;

public class MainActivity extends AppCompatActivity {
    private BottomTabLayout bottomTabLayout;
    private int container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container = R.id.container;

        bottomTabLayout = (BottomTabLayout) findViewById(R.id.bottomTabLayout);
        bottomTabLayout.setButtonTextStyle(R.style.TextGray16);
        bottomTabLayout.setItems(R.menu.menu_bottom_layout);
        bottomTabLayout.setListener(new BottomTabLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id) {
                switchFragment(id);
            }
        });
        bottomTabLayout.setSelectedTab(R.id.menu_button1);
    }

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
