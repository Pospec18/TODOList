package com.pospecstudio.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.todolist.R;
import com.pospecstudio.todolist.data.*;
import com.pospecstudio.todolist.ui.*;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ItemHolder itemHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setup();

        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            e.printStackTrace();
            showMessage("Unexpected error occurred");
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setup();
    }

    public void add(View v) {
        Intent intent = new Intent(this, EditItemActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("creatingItem", true);
        bundle.putInt("itemIdx", 0);
        bundle.putSerializable("itemHolder", itemHolder);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void done(View v) {

    }

    public void filter(View v) {
        Intent intent = new Intent(this, FilterActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("itemHolder", itemHolder);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void editItem(Item item) {
        Intent intent = new Intent(this, EditItemActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("creatingItem", false);
        bundle.putInt("itemIdx", itemHolder.indexOf(item));
        bundle.putSerializable("itemHolder", itemHolder);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void setup() {
        setContentView(R.layout.activity_main);

        ItemListsHolder lists = SaveAndLoad.loadLists(getApplicationContext());
        ListNames listNames = lists.getLastUsedList();
        if (listNames == null)
            return;
        itemHolder = SaveAndLoad.loadItems(listNames.getFileName(), getApplicationContext());

        setTitle("LIST: " + listNames.getListName().toUpperCase());

        RecyclerView recyclerView = findViewById(android.R.id.list);
        SwipeRefreshLayout swipe = findViewById(R.id.swipe);
        if (itemHolder == null || recyclerView == null)
            return;

        List<Item> items = itemHolder.getFilteredItems();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemsAdapter adapter = new ItemsAdapter(getApplicationContext(), items, this);
        recyclerView.setAdapter(adapter);
        RecycleRowMoveCallback<ItemsViewHolder> callback = new RecycleRowMoveCallback<>(adapter, ItemsViewHolder.class);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);

        swipe.setOnRefreshListener(() -> {
            ItemsAdapter newAdapter = new ItemsAdapter(getApplicationContext(), itemHolder.getFilteredItems(), this);
            recyclerView.setAdapter(newAdapter);
            callback.setReorderable(newAdapter);
            swipe.setRefreshing(false);
        });

        for (int i = 0; i < items.size(); i++) {
            if (itemHolder.isEditedItem(items.get(i)))
                recyclerView.scrollToPosition(i);
        }

        itemHolder.forgetIndexOfEditedItem();
        SaveAndLoad.saveItems(itemHolder, getApplicationContext());
    }

    public void saveItems() {
        SaveAndLoad.saveItems(itemHolder, getApplicationContext());
    }

    public void changeList(View v) {
        Intent intent = new Intent(this, SwitchListActivity.class);
        startActivity(intent);
    }

    public void moveAboveItem(Item itemToMove, Item itemToStay) {
        itemHolder.moveAboveItem(itemToMove, itemToStay);
        saveItems();
    }

    public void showMessage(String message) {
        InfoDialogFragment.showMessage(message, getSupportFragmentManager());
    }
}