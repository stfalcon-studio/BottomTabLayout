package com.stfalcon.bottomtablayout;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Anton Bevza on 5/5/16.
 */
public class TabButton extends RelativeLayout {
    private Button button;
    private TextView tvBubble;

    private ClickListener listener;


    public TabButton(Context context) {
        super(context);
        init();
    }

    public TabButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.part_view_tab_button, this, true);
        button = (Button) v.findViewById(R.id.button);
        tvBubble = (TextView) v.findViewById(R.id.bubble);
    }

    public void setIcon(Drawable drawable) {
        button.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
    }

    public void setText(String title) {
        button.setText(title);
    }

    public void setUnreadCount(int count) {
        tvBubble.setText("" + count);
        tvBubble.setVisibility(count > 0 ? VISIBLE : GONE);
    }

    public void setSelected(boolean selected) {
        button.setSelected(selected);
    }

    public void setListener(ClickListener clickListener) {
        listener = clickListener;
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick();
                }
            }
        });
    }

    public interface ClickListener {
        void onClick();
    }
}
