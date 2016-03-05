# ChipView

![ChipView](/images/ChipView.png)

ChipView helps you in creating attractive [Chips] for your application. ChipView provides you a flexiable way of creating and organizing your chip by following ways:

  - Adding a background or text color to your chip
  - Creating custom layout for your chip
  - Can even create multi color chips

## Using ChipView
Add ChipView in your ``layout`` and initialize in your ``Activity``

```
<com.chip.view.ChipView
    android:id="@+id/chipView"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:padding="5dp"
    android:textSize="20sp" />
```
```
ChipView chipView = (ChipView) findViewById(R.id.chipView);
```

## ChipBuilder
After initializing ``ChipView`` a **Chip** needs to be created for our ChipView.
With the help of ``ChipBuilder``  a Chip can be created.

> A ``ChipBuilder`` is a view builder which helps in creating Chips for ChipView.
```
public void getViewTypeCount();
public View getChip(LayoutInflater inflater, String data);
```

#### ``getViewTypeCount();``
> This method will helps in managing views for our Chip.

#### ``getChip(LayoutInflater inflater, String data)``
> This method will return the View that will become the Chip of ChipView.

## Creating a Simple Chip
```
public class Chip implements ChipBuilder {

    @Override
    public View getChip(LayoutInflater inflater, String data) {
        View view = inflater.inflate(R.layout.view_chip, null);
        TextView txtChip = (TextView) view.findViewById(R.id.txt_chip);
        txtChip.setText(data);
        return view;
    }
}
```
![SmallChip](/images/SimpleChip.png)

## Creating MultiColor Chip
```
public class Chip extends ChipBuilder {
    private int[] resIds = {R.drawable.chip_red, R.drawable.chip_green, R.drawable.chip_blue};

    @Override
    public int getViewTypeCount() {
        return resIds.length;
    }

    @Override
    public View getChip(LayoutInflater inflater, String data) {
        View view = inflater.inflate(R.layout.view_chip, null);
        TextView txtChip = (TextView) view.findViewById(R.id.txt_chip);
        txtChip.setText(data);
        txtChip.setBackgroundResource(resIds[getPosition()]);
        return view;
    }
}
```
![MultiColorChip](/images/MultiColorChip.png)

After creating ``ChipBuilder``, provide the ChipBuilder to ``ChipView``
```
chipView.setChipBuilder(new Chip());
```

### ChipView click and delete listener
ChipView also provides the functionality to handle the click events along with the feature to delete the chip at the same time. Following is the code.

```
chipCustomLayout.setDeleteOnClick(true);
```
```
chipCustomLayout.setOnChipClickListener(new OnChipClickListener() {
    @Override
    public void onChipClick(ChipView v, String chip) {
                
    }
});
```

### Version
1.0
