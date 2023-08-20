package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.example.todolist.data.ItemListsHolder;
import com.example.todolist.data.ListNames;
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
        setTitle("CLICK ON LIST TO SELECT IT");
        LinearLayout linearLayout = findViewById(R.id.list);
        for (ListNames data : lists.getListsData())
            linearLayout.addView(new ListView(linearLayout.getContext(), data, this));
    }

    public void selectList(ListNames list) {
        lists.setLastUsedListIdx(lists.getListsData().indexOf(list));
        SaveAndLoad.saveLists(lists, getApplicationContext());
        finish();
    }

    public void editList(ListNames list) {
        Intent intent = new Intent(this, EditListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("creatingList", false);
        bundle.putSerializable("lists", lists);
        bundle.putInt("itemIdx", lists.getListsData().indexOf(list));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void addList(View v) {
        Intent intent = new Intent(this, EditListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("creatingList", true);
        bundle.putSerializable("lists", lists);
        bundle.putInt("itemIdx", 0);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
