package com.example.todolist;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.example.todolist.data.Item;
import com.example.todolist.data.ItemHolder;
import com.example.todolist.data.ItemListsHolder;
import com.example.todolist.data.SaveAndLoad;
import com.example.todolist.ui.ItemView;
import com.example.todolist.ui.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ItemHolder itemHolder; // TODO: allow multiple lists
    private ItemListsHolder lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lists = SaveAndLoad.loadLists(getApplicationContext());
        setAndShowItemHolder(lists.getLastUsedList());
    }

    @Override
    protected void onResume() {
        super.onResume();
        setAndShowItemHolder(lists.getLastUsedList());
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

    private void setMainScreen() {
        setContentView(R.layout.activity_main);
        setTitle("LIST: " + itemHolder.getListName().toUpperCase());

        LinearLayout linearLayout = findViewById(R.id.list);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            for (Item item : itemHolder.filterNoItems())
                linearLayout.addView(new ItemView(linearLayout.getContext(), item, this));
        }
    }

    public void saveItems() {
        SaveAndLoad.saveItems(itemHolder, getApplicationContext());
    }

    public void changeList(View v) {
        setContentView(R.layout.screen_of_lists);
        setTitle("LIST TO CHOOSE");
        LinearLayout linearLayout = findViewById(R.id.list);
        for (String name : lists.getListsFiles())
            linearLayout.addView(new ListView(linearLayout.getContext(), name, this));
    }

    public void setAndShowItemHolder(String listFileName) {
        itemHolder = SaveAndLoad.loadItems(listFileName, getApplicationContext());
        setMainScreen();
    }
}