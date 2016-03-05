package com.chipview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.chip.callbacks.OnChipClickListener;
import com.chip.view.ChipView;
import com.chipview.builders.CustomBGChip;
import com.chipview.builders.CustomLayoutChip;
import com.chipview.builders.SimpleChip;

public class MainActivity extends AppCompatActivity implements OnChipClickListener, View.OnClickListener {
    private ChipView chipSimple;
    private ChipView chipCustomBg;
    private ChipView chipCustomLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chipSimple = (ChipView) findViewById(R.id.chip_simple);
        chipCustomBg = (ChipView) findViewById(R.id.chip_custom_bg);
        chipCustomLayout = (ChipView) findViewById(R.id.chip_custom_layout);
        chipSimple.setChipBuilder(new SimpleChip());
        chipCustomBg.setChipBuilder(new CustomBGChip());
        chipCustomBg.setOnChipClickListener(this);
        chipCustomLayout.setChipBuilder(new CustomLayoutChip());
        chipCustomLayout.setDeleteOnClick(true);
        chipCustomLayout.setOnChipClickListener(this);
        findViewById(R.id.btn_getChips).setOnClickListener(this);
    }

    private ChipView getCurrentChip() {
        if (chipSimple.hasFocus())
            return chipSimple;
        else if (chipCustomBg.hasFocus())
            return chipCustomBg;
        else if (chipCustomLayout.hasFocus())
            return chipCustomLayout;
        else
            return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_getChips:
                ChipView chip = getCurrentChip();
                Toast.makeText(getApplicationContext(), TextUtils.join(",", chip.getChips()), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onChipClick(ChipView v, String chip) {
        switch (v.getId()) {
            case R.id.chip_custom_bg:
                Toast.makeText(MainActivity.this, chip, Toast.LENGTH_SHORT).show();
                break;
            case R.id.chip_custom_layout:
                Toast.makeText(MainActivity.this, chip + " was deleted", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}