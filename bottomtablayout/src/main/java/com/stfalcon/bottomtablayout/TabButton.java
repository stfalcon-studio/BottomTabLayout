package com.stfalcon.bottomtablayout;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stfalcon.buttontablayout.R;

/**
 * Created by Anton Bevza on 5/5/16.
 */
public class TabButton extends RelativeLayout {
    private Button button;
    private ClickListener listener;
    //Text view for show bubble like unread massage
    private TextView tvBubble;


    public TabButton(Context context) {
        super(context);
        init();
    }

    public TabButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * Initialize tab button
     */
    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.part_view_tab_button, this, true);
        button = (Button) v.findViewById(R.id.button);
        tvBubble = (TextView) v.findViewById(R.id.bubble);
    }

    /**
     * Set button icon. Icon show as drawable top
     *
     * @param drawable drawable
     */
    public void setIcon(Drawable drawable) {
        button.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
    }

    /**
     * Set title text
     *
     * @param title title
     */
    public void setText(String title) {
        button.setText(title);
    }

    /**
     * Text view for show bubble like unread massage
     *
     * @param count count of unread massages
     */
    public void setUnreadCount(int count) {
        tvBubble.setText("" + count);
        tvBubble.setVisibility(count > 0 ? VISIBLE : GONE);
    }


    /**
     * Set selected button
     *
     * @param selected value
     */
    public void setSelected(boolean selected) {
        button.setSelected(selected);
    }

    /**
     * Set button click listener
     *
     * @param clickListener ClickListener interface
     */
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

    /**
     * Set button text style
     *
     * @param res Style res id
     */
    public void setButtonTextStyle(@StyleRes int res) {
        button.setTextAppearance(getContext(), res);
    }

    /**
     * Interface for click listener
     */
    public interface ClickListener {
        void onClick();
    }
}
