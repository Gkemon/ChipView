package com.chip.adapter;

import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by Abhishek on 3/5/2016.
 */
public interface ChipBuilder {
    int getViewTypeCount();
    View getChip(LayoutInflater inflater, int position, String data);
}