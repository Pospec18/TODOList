package com.pospecstudio.todolist;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.todolist.R;
import com.google.android.material.chip.Chip;
import com.pospecstudio.todolist.data.*;
import com.pospecstudio.todolist.ui.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class FilterActivity extends AppCompatActivity {
    private ItemHolder itemHolder = null;
    private Filter filter = null;
    private Chip fully;
    private Chip partially;
    private Chip empty;
    private Chip optional;

    private RecyclerView recyclerView;

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

        List<SortingType> items = itemHolder.getSortingOrder();
        recyclerView = findViewById(android.R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SortedTypeAdapter adapter = new SortedTypeAdapter(getApplicationContext(), items, this);
        recyclerView.setAdapter(adapter);
        RecycleRowMoveCallback<SortedTypeView> callback = new RecycleRowMoveCallback<>(adapter, SortedTypeView.class);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);

        ImageButton addSortButton = findViewById(R.id.add);
        addSortButton.setOnClickListener(this::addSortType);
    }

    private void addSortType(View view) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            SortingType alphabet = new SortingType("Alphabetically",
                    Comparator.comparing((Function<Item, String> & Serializable)Item::getItemName));
            SortingType idealCount = new SortingType("Ideal count",
                    Comparator.comparing((Function<Item, Integer> & Serializable)Item::getIdealCount));
            SortingType currCount = new SortingType("Current count",
                    Comparator.comparing((Function<Item, Integer> & Serializable)Item::getCurrCount));
            SortingType age = new SortingType("Age",
                    Comparator.comparing((Function<Item, ZonedDateTime> & Serializable)Item::getCreatedTime));
            SortingType lastUsed = new SortingType("Last used",
                    Comparator.comparing((Function<Item, ZonedDateTime> & Serializable)Item::getChangedTime));
            SortingType mostUsed = new SortingType("Most used",
                    Comparator.comparing((Function<Item, Integer> & Serializable)Item::getNumberOfChanges));
            SortingType state = new SortingType("State",
                    Comparator.comparing((Function<Item, FilledType> & Serializable)Item::getFilledType));
            itemHolder.getSortingOrder().add(idealCount);
            recyclerView.getAdapter().notifyItemInserted(itemHolder.getSortingOrder().size() - 1);
            save();
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
        save();
        finish();
    }

    public void cancel(View v) {
        finish();
    }

    public void deleteSortingType(int index) {
        itemHolder.getSortingOrder().remove(index);
        recyclerView.getAdapter().notifyItemRemoved(index);
        save();
    }

    public void save() {
        SaveAndLoad.saveItems(itemHolder, getApplicationContext());
    }
}
