package com.example.todolist;

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

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ItemHolder itemHolder; // TODO: allow multiple lists
    private Item editedItem = null;
    private boolean creatingItem = false;
    private ItemListsHolder lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lists = SaveAndLoad.loadLists(getApplicationContext());
        itemHolder = SaveAndLoad.loadItems(lists.getLastUsedList(), getApplicationContext());
        setMainScreen();
    }

    public void add(View v) {
        editedItem = new Item();
        setEditScreen(true);
    }

    public void done(View v) {

    }

    public void filter(View v) {

    }

    public void editItem(Item item) {
        setEditScreen(false);
        editedItem = item;
        EditText editName = findViewById(R.id.editName);
        EditText editCount = findViewById(R.id.editIdealCount);
        editName.setText(item.getItemName());
        editCount.setText(Integer.toString(item.getIdealCount()));
    }
    
    public void applyEdit(View v) {
        EditText editName = findViewById(R.id.editName);
        EditText editCount = findViewById(R.id.editIdealCount);
        if (editName.getText().length() == 0)
            return;

        try {
            editedItem.setItemName(editName.getText().toString());
            editedItem.setIdealCount(Integer.parseInt(editCount.getText().toString()));
        } catch (NumberFormatException nfe) {
            return;
        }

        if(creatingItem)
            itemHolder.addItem(editedItem);
        creatingItem = false;
        editedItem = null;
        setMainScreen();
        saveItems();
    }

    public void cancelEdit(View v) {
        editedItem = null;
        creatingItem = false;
        setMainScreen();
    }

    public void deleteItem(View v) {
        itemHolder.deleteItem(editedItem);
        editedItem = null;
        setMainScreen();
        saveItems();
    }

    private void setMainScreen() {
        setContentView(R.layout.activity_main);
        setTitle("LIST");

        LinearLayout linearLayout = findViewById(R.id.list);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            for (Item item : itemHolder.filterNoItems())
                linearLayout.addView(new ItemView(linearLayout.getContext(), item, this));
        }
    }

    private void setEditScreen(boolean creating) {
        creatingItem = creating;
        setContentView(R.layout.item_edit);
        if (creating)
            setTitle("ADD ITEM");
        else
            setTitle("EDIT ITEM");

        findViewById(R.id.deleteItem).setVisibility(creating ? View.GONE : View.VISIBLE);
    }

    public void saveItems() {
        SaveAndLoad.saveItems(itemHolder, getApplicationContext());
    }

    public void changeList(View v) {
        setContentView(R.layout.screen_of_lists);
        LinearLayout linearLayout = findViewById(R.id.list);
    }
}