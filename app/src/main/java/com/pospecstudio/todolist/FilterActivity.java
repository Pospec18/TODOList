package com.pospecstudio.todolist;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
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
        addSortButton.setOnClickListener(this::showSortTypeOptions);
    }

    private void showSortTypeOptions(View view) {
        Context wrapper = new ContextThemeWrapper(this, R.style.popup_menu);
        PopupMenu popup = new PopupMenu(wrapper, view);
        popup.getMenuInflater().inflate(R.menu.sort_type_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(this::addSortType);
        popup.show();
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

    private boolean addSortType(@NonNull MenuItem item) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N)
            return false;

        SortingType toAdd;
        Context ctx = getApplicationContext();
        int id = item.getItemId();
        if (id == R.id.alpha)
            toAdd = new SortingType(ctx.getString(R.string.alphabetically),
                    Comparator.comparing((Function<Item, String> & Serializable) Item::getItemName));
        else if (id == R.id.current)
            toAdd = new SortingType(ctx.getString(R.string.current_value),
                    Comparator.comparing((Function<Item, Integer> & Serializable)Item::getCurrCount));
        else if (id == R.id.ideal)
            toAdd = new SortingType(ctx.getString(R.string.ideal_value),
                    Comparator.comparing((Function<Item, Integer> & Serializable)Item::getIdealCount));
        else if (id == R.id.age)
            toAdd = new SortingType(ctx.getString(R.string.age),
                    Comparator.comparing((Function<Item, ZonedDateTime> & Serializable)Item::getCreatedTime));
        else if (id == R.id.last_used)
            toAdd = new SortingType(ctx.getString(R.string.last_used),
                    Comparator.comparing((Function<Item, ZonedDateTime> & Serializable)Item::getChangedTime));
        else if (id == R.id.frequency)
            toAdd = new SortingType(ctx.getString(R.string.most_used),
                    Comparator.comparing((Function<Item, Integer> & Serializable)Item::getNumberOfChanges));
        else if (id == R.id.state)
            toAdd = new SortingType(ctx.getString(R.string.filled_type),
                    Comparator.comparing((Function<Item, FilledType> & Serializable)Item::getFilledType));
        else
            return false;

        itemHolder.getSortingOrder().add(toAdd);
        recyclerView.getAdapter().notifyItemInserted(itemHolder.getSortingOrder().size() - 1);
        save();
        return true;
    }
}
