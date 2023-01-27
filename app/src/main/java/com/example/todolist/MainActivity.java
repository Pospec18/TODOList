package com.example.todolist;

import android.os.Build;
import android.os.Bundle;
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.example.todolist.data.Item;
import com.example.todolist.data.ItemHolder;
import com.example.todolist.data.SaveAndLoad;
import com.example.todolist.ui.ItemView;

public class MainActivity extends AppCompatActivity {

    private final ItemHolder itemHolder = new ItemHolder(SaveAndLoad.loadItems());
    private Item editedItem = null;
    private boolean creatingItem = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainScreen();
    }

    public void add(View v) {
        setContentView(R.layout.item_edit);
        setTitle("ADD ITEM");
        editedItem = new Item();
        creatingItem = true;
    }

    public void done(View v) {

    }

    public void filter(View v) {

    }

    public void editItem(Item item) {
        setContentView(R.layout.item_edit);
        setTitle("EDIT ITEM");
        creatingItem = false;
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
    }

    public void cancelEdit(View v) {
        editedItem = null;
        creatingItem = false;
        setMainScreen();
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
}