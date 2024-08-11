package com.pospecstudio.todolist;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.todolist.R;
import com.google.android.material.chip.Chip;
import com.pospecstudio.todolist.data.*;

import java.util.Comparator;

public class FilterActivity extends AppCompatActivity {
    private ItemHolder itemHolder = null;
    private Filter filter = null;
    private Chip fully;
    private Chip partially;
    private Chip empty;
    private Chip optional;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (Build.VERSION.SDK_INT >= 33)
                itemHolder = extras.getSerializable("itemHolder", ItemHolder.class);
            else
                itemHolder = (ItemHolder) extras.getSerializable("itemHolder");
        }

        if (itemHolder == null) {
            finish();
            return;
        }

        filter = itemHolder.getFilter();
        setContentView(R.layout.filter);

        fully = findViewById(R.id.fully);
        fully.setChecked(filter.canShow(FilledType.FULLY));

        partially = findViewById(R.id.partially);
        partially.setChecked(filter.canShow(FilledType.PARTIALLY));

        empty = findViewById(R.id.empty);
        empty.setChecked(filter.canShow(FilledType.EMPTY));

        optional = findViewById(R.id.optional);
        optional.setChecked(filter.canShow(FilledType.OPTIONAL));

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            SortingType alphabet = new SortingType("Alphabetically", Comparator.comparing(Item::getItemName));
            SortingType idealCount = new SortingType("Ideal count", Comparator.comparing(Item::getIdealCount));
            SortingType currCount = new SortingType("Current count", Comparator.comparing(Item::getCurrCount));
            SortingType age = new SortingType("Age", Comparator.comparing(Item::getCreatedTime));
            SortingType lastUsed = new SortingType("Last used", Comparator.comparing(Item::getChangedTime));
            SortingType mostUsed = new SortingType("Most used", Comparator.comparing(Item::getNumberOfChanges));
            SortingType state = new SortingType("State", Comparator.comparing(Item::getFilledType));
        }
    }

    private void applyFilledType(Chip chip, FilledType filledType) {
        if (chip.isChecked())
            filter.addShowedType(filledType);
        else
            filter.removeShowedType(filledType);
    }

    public void apply(View v) {
        applyFilledType(fully, FilledType.FULLY);
        applyFilledType(partially, FilledType.PARTIALLY);
        applyFilledType(empty, FilledType.EMPTY);
        applyFilledType(optional, FilledType.OPTIONAL);
        SaveAndLoad.saveItems(itemHolder, getApplicationContext());
        finish();
    }

    public void cancel(View v) {
        finish();
    }
}
