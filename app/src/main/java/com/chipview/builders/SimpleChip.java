package com.chipview.builders;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chip.adapter.ChipBuilder;
import com.chipview.R;

/**
 * Created by Abhishek on 3/5/2016.
 */
public class SimpleChip extends ChipBuilder {

    @Override
    public View getChip(LayoutInflater inflater, String data) {
        View view = inflater.inflate(R.layout.view_chip_simple, null);
        TextView txtChip = (TextView) view.findViewById(R.id.txt_chip);
        txtChip.setText(data);
        return view;
    }
}