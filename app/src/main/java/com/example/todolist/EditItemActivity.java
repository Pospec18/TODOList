package com.example.todolist;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.todolist.data.Item;
import com.example.todolist.data.ItemHolder;
import com.example.todolist.data.SaveAndLoad;
import com.example.todolist.ui.InfoDialogFragment;

public class EditItemActivity extends AppCompatActivity {
    private boolean creatingItem;
    private Item editedItem;
    private ItemHolder itemHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            creatingItem = extras.getBoolean("creatingItem");
            itemHolder = (ItemHolder) extras.getSerializable("itemHolder");

            if (creatingItem)
                editedItem = new Item();
            else
                editedItem = itemHolder.getItem(extras.getInt("itemIdx"));
        }

        setContentView(R.layout.item_edit);
        if (creatingItem)
            setTitle("ADD ITEM");
        else {
            setTitle("EDIT ITEM");
            EditText editName = findViewById(R.id.editName);
            EditText editCount = findViewById(R.id.editIdealCount);
            editName.setText(editedItem.getItemName());
            editCount.setText(Integer.toString(editedItem.getIdealCount()));
        }

        findViewById(R.id.deleteItem).setVisibility(creatingItem ? View.GONE : View.VISIBLE);
    }

    private void showMessage(String message) {
        InfoDialogFragment.showMessage(message, getSupportFragmentManager());
    }

    public void applyEdit(View v) {
        EditText editName = findViewById(R.id.editName);
        EditText editCount = findViewById(R.id.editIdealCount);
        if (editName.getText().length() == 0) {
            showMessage("Name must be filled");
            return;
        }

        try {
            editedItem.setItemName(editName.getText().toString());
            editedItem.setIdealCount(Integer.parseInt(editCount.getText().toString()));
        } catch (NumberFormatException nfe) {
            showMessage("Ideal count must be number.");
            return;
        }

        if(creatingItem)
            itemHolder.addItem(editedItem);
        saveItems();
        finish();
    }

    public void cancelEdit(View v) {
        finish();
    }

    public void deleteItem(View v) {
        itemHolder.deleteItem(editedItem);
        saveItems();
        finish();
    }

    public void saveItems() {
        SaveAndLoad.saveItems(itemHolder, getApplicationContext());
    }
}
