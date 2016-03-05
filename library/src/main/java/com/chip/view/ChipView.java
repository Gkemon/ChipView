package com.chip.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.MovementMethod;
import android.text.style.CharacterStyle;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.chip.adapter.ChipBuilder;
import com.chip.callbacks.OnChipClickListener;
import com.chip.callbacks.OnClickCallback;
import com.chip.engine.ChipClickSpan;
import com.chip.engine.ChipViewMovementMethod;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Abhishek on 3/4/2016.
 */
public class ChipView extends EditText {
    private LayoutInflater inflater;
    private boolean deleteOnClick;
    private ChipBuilder chipBuilder;
    private OnChipClickListener chipClickListener;


    public ChipView(Context context) {
        this(context, null);
    }

    public ChipView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChipView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFocusableInTouchMode(true);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    protected MovementMethod getDefaultMovementMethod() {
        return ChipViewMovementMethod.getInstance();
    }

    public void setOnChipClickListener(OnChipClickListener l) {
        chipClickListener = l;
    }

    public boolean isDeleteOnClick() {
        return deleteOnClick;
    }

    public void setDeleteOnClick(boolean delete) {
        deleteOnClick = delete;
    }

    public ChipBuilder getChipBuilder() {
        return chipBuilder;
    }

    public void setChipBuilder(ChipBuilder builder) {
        this.chipBuilder = builder;
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (lengthAfter > 0 && (text.charAt(start) == ' ' || text.charAt(start) == ','))
            createChips();
    }

    public List<String> getChips() {
        Set<String> chips = new LinkedHashSet<>();
        String text = getText().toString();
        Spannable spannable = getText();
        for (CharacterStyle span : spannable.getSpans(0, text.length(), CharacterStyle.class))
            chips.add(text.substring(spannable.getSpanStart(span), spannable.getSpanEnd(span)));
        return new ArrayList<>(chips);
    }

    public void createChips() {
        String text = getText().toString();
        if (text.contains(" ") || text.contains(",")) {
            String[] chips = text.split("( )|(,)");
            SpannableStringBuilder builder = new SpannableStringBuilder(getText());
            int index = 0;
            int pos = -1;
            for (String chip : chips)
                index = buildChip(chip, builder, index, chipBuilder.getViewTypeCount() == 0 ? 0 : (pos = (pos + 1) % chipBuilder.getViewTypeCount()));
            setText(builder);
            setSelection(text.length());
        }
    }

    private int buildChip(String chip, SpannableStringBuilder builder, int index, int pos) {
        chipBuilder.setPosition(pos);
        View view = chipBuilder.getChip(inflater, chip);
        int spec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        view.measure(spec, spec);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        Bitmap bmp = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        canvas.translate(-view.getScrollX(), -view.getScrollY());
        view.draw(canvas);
        view.setDrawingCacheEnabled(true);
        Bitmap cache = view.getDrawingCache();
        Bitmap bitmap = cache.copy(Bitmap.Config.ARGB_8888, true);
        view.destroyDrawingCache();
        BitmapDrawable drawable = new BitmapDrawable(getResources(), bitmap);
        drawable.setBounds(0, 0, view.getWidth(), view.getHeight());
        builder.setSpan(new ImageSpan(drawable), index, index + chip.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ChipClickSpan(callback), index, index + chip.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return index + chip.length() + 1;
    }

    private OnClickCallback callback = new OnClickCallback() {
        @Override
        public void onClick(String chip, int startPos, int endPos) {
            if (chipClickListener != null) {
                if (deleteOnClick)
                    getText().replace(startPos, endPos, "");
                chipClickListener.onChipClick(ChipView.this, chip);
            }
        }
    };
}