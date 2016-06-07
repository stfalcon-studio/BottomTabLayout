package com.stfalcon.bottomtablayout_sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ColoredFragment extends Fragment {
    private static final String ARG_COLOR = "color";
    private static final String ARG_TITLE = "title";
    private String title;
    private int color;


    public ColoredFragment() {
        // Required empty public constructor
    }

    public static ColoredFragment newInstance(int param1, String param2) {
        ColoredFragment fragment = new ColoredFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLOR, param1);
        args.putString(ARG_TITLE, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            color = getArguments().getInt(ARG_COLOR);
            title = getArguments().getString(ARG_TITLE);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setBackgroundColor(getResources().getColor(color));
        ((TextView) view.findViewById(R.id.title)).setText(title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_colored, container, false);
    }
}
