package com.pospecstudio.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.todolist.R;
import com.pospecstudio.todolist.data.ItemListsHolder;
import com.pospecstudio.todolist.data.ListNames;
import com.pospecstudio.todolist.data.SaveAndLoad;
import com.pospecstudio.todolist.ui.*;

import java.util.List;

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

        List<ListNames> filteredLists = lists.getFilteredLists();
        RecyclerView recyclerView = findViewById(android.R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ListsAdapter adapter = new ListsAdapter(getApplicationContext(), filteredLists, this);
        recyclerView.setAdapter(adapter);
        RecycleRowMoveCallback<ListViewHolder> callback = new RecycleRowMoveCallback<>(adapter, ListViewHolder.class);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);

        int lastUsedListIndex = filteredLists.indexOf(lists.getLastUsedList());
        if (lastUsedListIndex >= 0)
            recyclerView.scrollToPosition(lastUsedListIndex);
    }

    public void selectList(ListNames list) {
        lists.setLastUsedList(list);
        SaveAndLoad.saveLists(lists, getApplicationContext());
        finish();
    }

    public void editList(ListNames list) {
        Intent intent = new Intent(this, EditListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("creatingList", false);
        bundle.putSerializable("lists", lists);
        bundle.putInt("itemIdx", lists.indexOf(list));
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

    public void moveAboveItem(ListNames itemToMove, ListNames itemToStay) {
        lists.moveAboveItem(itemToMove, itemToStay);
        SaveAndLoad.saveLists(lists, getApplicationContext());
    }
}
