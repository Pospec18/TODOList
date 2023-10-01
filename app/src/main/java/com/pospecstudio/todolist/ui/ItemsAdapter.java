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

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsViewHolder> {

    private final Context context;
    private final List<Item> items;
    private final MainActivity main;

    private Item selected = null;
    private ItemsViewHolder selectedHolder = null;

    public ItemsAdapter(Context context, List<Item> items, MainActivity main) {
        this.context = context;
        this.items = items;
        this.main = main;
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
}
