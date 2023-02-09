package com.example.todolist;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.todolist.data.ItemListsHolder;
import com.example.todolist.data.ListNames;
import com.example.todolist.data.SaveAndLoad;

public class EditListActivity extends AppCompatActivity {
    private boolean creatingList;
    private ItemListsHolder lists;
    private ListNames list;

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
    }

    public void applyEdit(View v) {
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
        finish();
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
}
