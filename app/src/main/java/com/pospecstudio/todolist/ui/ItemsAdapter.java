package com.pospecstudio.todolist.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.todolist.R;
import com.pospecstudio.todolist.data.Item;
import com.pospecstudio.todolist.data.ItemHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsViewHolder> {

    Context context;
    List<Item> items;

    public ItemsAdapter(Context context, ItemHolder itemHolder) {
        this.context = context;
        this.items = itemHolder.getFilteredItems();
    }

    @NonNull
    @NotNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ItemsViewHolder(LayoutInflater.from(context).inflate(R.layout.item_holder, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ItemsViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
