package com.pospecstudio.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.todolist.R;
import com.pospecstudio.todolist.data.*;
import com.pospecstudio.todolist.ui.InfoDialogFragment;
import com.pospecstudio.todolist.ui.ItemView;
import com.pospecstudio.todolist.ui.ItemsAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ItemHolder itemHolder;
    private ItemView selected = null;

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

        RecyclerView linearLayout = findViewById(android.R.id.list);
        if (itemHolder == null || linearLayout == null)
            return;

        linearLayout.setLayoutManager(new LinearLayoutManager(this));
        linearLayout.setAdapter(new ItemsAdapter(getApplicationContext(), itemHolder));

        for (Item item : itemHolder.getFilteredItems()) {
            // ItemView v = new ItemView(linearLayout.getContext(), item, this);
            // linearLayout.addView(v);
            // if (itemHolder.isEditedItem(item))
                // linearLayout.requestChildFocus(v, v);
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

    public void selectItem(ItemView item) {
        if (selected != null)
            selected.deselect();

        if (item != null)
            item.select();
        selected = item;
    }

    public void showMessage(String message) {
        InfoDialogFragment.showMessage(message, getSupportFragmentManager());
    }
}