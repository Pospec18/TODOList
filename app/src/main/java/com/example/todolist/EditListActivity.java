package com.example.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.example.todolist.data.*;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.util.List;

public class EditListActivity extends AppCompatActivity {
    private boolean creatingList;
    private ItemListsHolder lists;
    private ListNames list;
    private ActivityResultLauncher<Intent> onImportCSV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            creatingList = extras.getBoolean("creatingList");
            lists = (ItemListsHolder) extras.getSerializable("lists");

            if (!creatingList){
                int index = extras.getInt("itemIdx");
                list = lists.getListsData().get(index);
            }
        }

        setContentView(R.layout.list_edit);
        if (creatingList)
            setTitle("ADD LIST");
        else {
            setTitle("EDIT LIST");
            EditText editName = findViewById(R.id.editName);
            editName.setText(list.getListName());
        }

        findViewById(R.id.deleteItem).setVisibility(creatingList ? View.GONE : View.VISIBLE);
        findViewById(R.id.exportToCsv).setVisibility(creatingList ? View.GONE : View.VISIBLE);

        onImportCSV = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data == null)
                            return;
                        saveEdit();
                        ItemHolder holder = SaveAndLoad.loadItems(list.getFileName(), getApplicationContext());
                        SaveAndLoad.importListFromCSV(getContentResolver(), data.getData(), holder);
                        SaveAndLoad.saveItems(holder, getApplicationContext());
                        finish();
                    }
                }
        );
    }

    public void applyEdit(View v) {
        saveEdit();
        finish();
    }

    private void saveEdit() {
        EditText editName = findViewById(R.id.editName);
        if (editName.getText().length() == 0)
            return;

        if(creatingList) {
            ListNames newData = new ListNames(editName.getText().toString(), lists.getNextListName());
            lists.getListsData().add(newData);
        }
        else
            list.setListName(editName.getText().toString());
        SaveAndLoad.saveLists(lists, getApplicationContext());
    }

    public void cancelEdit(View v) {
        finish();
    }

    public void deleteList(View v) {
        lists.getListsData().remove(list);
        SaveAndLoad.saveLists(lists, getApplicationContext());
        SaveAndLoad.deleteFile(list.getFileName(), getApplicationContext());
        finish();
    }

    public void exportToCSV(View v) {
        ItemHolder holder = SaveAndLoad.loadItems(list.getFileName(), getApplicationContext());
        SaveAndLoad.exportListToCSV(holder, list.getListName());
    }

    public void importCSV(View v) {
        ItemHolder holder;
        Intent intent = new Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT);;
        onImportCSV.launch(intent);
    }
}
