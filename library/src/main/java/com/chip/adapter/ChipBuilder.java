package com.chip.adapter;

import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by Abhishek on 3/5/2016.
 */
public abstract class ChipBuilder {
    private int position;

    public int getViewTypeCount() {
        return 0;
    }

    public final int getPosition() {
        return position;
    }

    public final void setPosition(int position) {
        this.position = position;
    }

    public abstract View getChip(LayoutInflater inflater, String data);
}