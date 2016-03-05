package com.chip.engine;

import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.ArrowKeyMovementMethod;
import android.text.method.MovementMethod;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by Abhishek on 3/4/2016.
 */
public class ChipViewMovementMethod extends ArrowKeyMovementMethod {
    private static ChipViewMovementMethod instance;

    public static MovementMethod getInstance() {
        if (instance == null)
            instance = new ChipViewMovementMethod();
        return instance;
    }

    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            x -= widget.getTotalPaddingLeft();
            y -= widget.getTotalPaddingTop();
            x += widget.getScrollX();
            y += widget.getScrollY();
            Layout layout = widget.getLayout();
            int line = layout.getLineForVertical(y);
            int off = layout.getOffsetForHorizontal(line, x);
            ChipClickSpan[] link = buffer.getSpans(off, off, ChipClickSpan.class);
            if (link.length != 0) {
                if (action == MotionEvent.ACTION_UP) {
                    link[0].onClick(widget);
                    Selection.removeSelection(buffer);
                } else if (action == MotionEvent.ACTION_DOWN)
                    Selection.setSelection(buffer, buffer.getSpanStart(link[0]), buffer.getSpanEnd(link[0]));
                return true;
            }
        }
        return super.onTouchEvent(widget, buffer, event);
    }
}