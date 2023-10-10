package com.pospecstudio.todolist;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.todolist.R;
import com.pospecstudio.todolist.data.Item;
import com.pospecstudio.todolist.data.ItemHolder;
import com.pospecstudio.todolist.data.SaveAndLoad;
import com.pospecstudio.todolist.ui.InfoDialogFragment;

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

            if (Build.VERSION.SDK_INT >= 33)
                itemHolder = extras.getSerializable("itemHolder", ItemHolder.class);
            else
                itemHolder = (ItemHolder) extras.getSerializable("itemHolder");

            if (creatingItem)
                editedItem = new Item();
            else
                editedItem = itemHolder.getItemToEdit(extras.getInt("itemIdx"));
        }

        setContentView(R.layout.item_edit);
        if (creatingItem)
            setTitle("ADD ITEM");
        else {
            setTitle("EDIT ITEM");
            EditText editName = findViewById(R.id.edit_name);
            EditText editCount = findViewById(R.id.edit_ideal_count);
            CheckBox optional = findViewById(R.id.optional);

            editName.setText(editedItem.getItemName());
            editCount.setText(Integer.toString(editedItem.getIdealCount()));
            optional.setChecked(editedItem.isOptional());
        }

        findViewById(R.id.delete_item).setVisibility(creatingItem ? View.GONE : View.VISIBLE);
    }

    private void showMessage(String message) {
        InfoDialogFragment.showMessage(message, getSupportFragmentManager());
    }

    public void applyEdit(View v) {
        EditText editName = findViewById(R.id.edit_name);
        EditText editCount = findViewById(R.id.edit_ideal_count);
        CheckBox optional = findViewById(R.id.optional);

        if (editName.getText().length() == 0) {
            showMessage("Name must be filled");
            return;
        }

        try {
            editedItem.setItemName(editName.getText().toString());
            editedItem.setIdealCount(Integer.parseInt(editCount.getText().toString()));
            editedItem.setOptional(optional.isChecked());
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
