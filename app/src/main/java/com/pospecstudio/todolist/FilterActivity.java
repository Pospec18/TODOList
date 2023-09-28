package com.pospecstudio.todolist;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.todolist.R;
import com.google.android.material.chip.Chip;
import com.pospecstudio.todolist.data.FilledType;
import com.pospecstudio.todolist.data.Filter;
import com.pospecstudio.todolist.data.ItemHolder;
import com.pospecstudio.todolist.data.SaveAndLoad;

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
        filter = ItemHolder.getFilter();

        setContentView(R.layout.filter);

        fully = findViewById(R.id.fully);
        fully.setChecked(filter.canShow(FilledType.FULLY));

        partially = findViewById(R.id.partially);
        partially.setChecked(filter.canShow(FilledType.PARTIALLY));

        empty = findViewById(R.id.empty);
        empty.setChecked(filter.canShow(FilledType.EMPTY));

        optional = findViewById(R.id.optional);
        optional.setChecked(filter.canShow(FilledType.OPTIONAL));
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
        finish();
    }

    public void cancel(View v) {
        finish();
    }
}
