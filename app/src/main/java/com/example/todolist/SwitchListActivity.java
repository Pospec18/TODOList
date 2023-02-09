package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.todolist.data.ItemListsHolder;
import com.example.todolist.data.SaveAndLoad;
import com.example.todolist.ui.ListView;

public class SwitchListActivity  extends AppCompatActivity {

    private ItemListsHolder lists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setup();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setup();
    }

    private void setup() {
        lists = SaveAndLoad.loadLists(getApplicationContext());
        setContentView(R.layout.screen_of_lists);
        setTitle("LISTS TO CHOOSE");
        LinearLayout linearLayout = findViewById(R.id.list);
        for (String name : lists.getListsFiles())
            linearLayout.addView(new ListView(linearLayout.getContext(), name, this));
    }

    public void selectList(String fileName) {
        lists.setLastUsedListIdx(lists.getListsFiles().indexOf(fileName));
        SaveAndLoad.saveLists(lists, getApplicationContext());
        finish();
    }

    public void editList(String fileName) {
        Intent intent = new Intent(this, EditListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("creatingList", false);
        bundle.putSerializable("lists", lists);
        bundle.putString("listName", fileName);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void addList(View v) {
        Intent intent = new Intent(this, EditListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("creatingList", true);
        bundle.putSerializable("lists", lists);
        bundle.putString("listName", null);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
