package com.chip.engine;

import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.chip.callbacks.OnClickCallback;

/**
 * Created by Abhishek on 3/4/2016.
 */
public class ChipClickSpan extends ClickableSpan {
    private OnClickCallback callback;

    public ChipClickSpan(OnClickCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onClick(View widget) {
        CharSequence text = ((TextView) widget).getText();
        Spanned s = (Spanned) text;
        if (callback != null)
            callback.onClick(text.subSequence(s.getSpanStart(this), s.getSpanEnd(this)).toString(), s.getSpanStart(this), s.getSpanEnd(this));
    }
}
