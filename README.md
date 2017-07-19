# BottomTabLayout

![alt tag](http://i.imgur.com/V7wwa9U.gif)

Simple library for bottom tab layout

### Download

Download via Gradle:

```gradle
compile 'com.github.stfalcon:bottomtablayout:0.3'
```
or Maven:
```xml
<dependency>
  <groupId>com.github.stfalcon</groupId>
  <artifactId>bottomtablayout</artifactId>
  <version>0.3</version>
  <type>pom</type>
</dependency>
```

### Usage

Create text selector:
```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:color="@color/black" android:state_pressed="true" /> <!-- pressed -->
    <item android:color="@color/black" android:state_selected="true" /> <!-- selected -->
    <item android:color="@color/white" /> <!-- default -->
</selector>
```

Create drawable selector for each button icon:
```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:drawable="@drawable/ic_button1_dark" android:state_pressed="true" /> <!-- pressed -->
    <item android:drawable="@drawable/ic_button1_dark" android:state_selected="true" /> <!-- selected -->
    <item android:drawable="@drawable/ic_button1" /> <!-- default -->
</selector>
```

Create menu resource file with items. Where title is title of button and icon is button drawable.
```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">

    <item
        android:id="@+id/menu_button1"
        android:icon="@drawable/tab_button1_selector"
        android:title="Button1" />

    <item
        android:id="@+id/menu_button2"
        android:icon="@drawable/tab_button1_selector"
        android:title="Button2" />

    <item
        android:id="@+id/menu_button3"
        android:icon="@drawable/tab_button1_selector"
        android:title="Button3" />

    <item
        android:id="@+id/menu_button4"
        android:icon="@drawable/tab_button1_selector"
        android:title="Button4" />

    <item
        android:id="@+id/menu_button5"
        android:icon="@drawable/tab_button1_selector"
        android:title="Button5" />
</menu>
```

Create style for button
```xml
<style name="TabButtonTextStyle" parent="android:Widget.Button">
        <item name="android:textSize">12sp</item>
        <item name="android:textColor">@drawable/tab_button_text_selector</item>
</style>
```

Add bottomtablayout view to activity layout:
```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.stfalcon.bottomtablayout_sample.MainActivity">

    <FrameLayout
        android:id="@+id/container"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_above="@+id/bottomTabLayout" />

    <com.stfalcon.bottomtablayout.BottomTabLayout
        android:id="@+id/bottomTabLayout"
        android:layout_width="match_parent"
        android:layout_height="58dp"
        android:layout_alignParentBottom="true"
        android:background="@color/dark" />

</RelativeLayout>
```

In activity class:
```java
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = R.id.container; //Container for fragments

        //Setup button tab layout
        bottomTabLayout = (BottomTabLayout) findViewById(R.id.bottomTabLayout);
        //set button text style
        bottomTabLayout.setButtonTextStyle(R.style.TextGray16);
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
    }
```

For example we can switch fragments in container:

```java
/**
     * Show fragment in container
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
```

For showing bubble count on tab. 
```java
bottomTabLayout.showTabBubbleCount(R.id.menu_button1, 3);
```
You can style bubble with methods:
```java
bottomTabLayout.setTabBubbleColor(ContextCompat.getColor(this, R.color.blue));
bottomTabLayout.setTabBubblePadding(0, 0, 0, 0);
bottomTabLayout.setTabBubbleTextStyle(R.style.TextWhite12);
```

Take a look at the [sample project](sample) for more information.

### License 

```
Copyright 2017 stfalcon.com

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```


[sample]: <https://github.com/stfalcon-studio/BottomTabLayout/tree/master/sample>
