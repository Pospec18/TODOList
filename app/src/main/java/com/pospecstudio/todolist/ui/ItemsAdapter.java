package com.pospecstudio.todolist.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.todolist.R;
import com.pospecstudio.todolist.MainActivity;
import com.pospecstudio.todolist.data.Item;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsViewHolder> implements RecycleViewReorderable<ItemsViewHolder> {

    private final Context context;
    private List<Item> items;
    private final MainActivity main;

    private Item selected = null;
    private ItemsViewHolder selectedHolder = null;

    public ItemsAdapter(Context context, List<Item> items, MainActivity main) {
        this.context = context;
        this.items = items;
        this.main = main;
    }

    public void resetList(List<Item> items) {
        this.items = items;
    }

    @NonNull
    @NotNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_holder, parent, false);
        ItemsViewHolder holder = new ItemsViewHolder(view, main);
        view.setOnClickListener(v -> changeSelectItem(holder));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ItemsViewHolder holder, int position) {
        Item item = items.get(position);
        if (selectedHolder == holder && item != selected) {
            selectedHolder.deselect();
            selectedHolder = null;
        }

        if (item == selected) {
            selectedHolder = holder;
            selectedHolder.select();
        }
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void changeSelectItem(ItemsViewHolder view) {
        Item item = items.get(view.getAdapterPosition());
        if (selected == item) {
            if (selectedHolder != null)
                selectedHolder.deselect();
            selectedHolder = null;
            selected = null;
            return;
        }

        if (selectedHolder != null)
            selectedHolder.deselect();

        selectedHolder = view;
        selected = item;
        view.select();
    }

    @Override
    public void onRowMoved(int from, int to) {
        if(from < to)
        {
            for(int i = from; i < to; i++)
            {
                Collections.swap(items, i, i+1);
            }
        }
        else
        {
            for(int i = from; i > to; i--)
            {
                Collections.swap(items, i, i-1);
            }
        }
        notifyItemMoved(from, to);
    }

    @Override
    public void onRowSelected(ItemsViewHolder view) {
        view.startReordering();
    }

    @Override
    public void onRowClear(ItemsViewHolder view) {
        view.endReordering();
    }
}
