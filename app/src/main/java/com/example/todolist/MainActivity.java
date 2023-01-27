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

    private ItemHolder itemHolder = new ItemHolder(SaveAndLoad.loadItems());
    private Item editedItem = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainScreen();
    }

    public void add(View v) {
        setContentView(R.layout.item_edit);
        setTitle("ADD ITEM");
        editedItem = new Item();
    }

    public void done(View v) {

    }

    public void filter(View v) {

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
        itemHolder.addItem(editedItem);
        editedItem = null;
        setMainScreen();
    }

    public void cancelEdit(View v) {
        editedItem = null;
        setMainScreen();
    }

    private void setMainScreen() {
        setContentView(R.layout.activity_main);
        setTitle("LIST");

        LinearLayout linearLayout = findViewById(R.id.list);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            for (Item item : itemHolder.filterNoItems())
                linearLayout.addView(new ItemView(linearLayout.getContext(), item));
        }
    }
}