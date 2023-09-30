package com.pospecstudio.todolist.ui;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.todolist.R;
import com.pospecstudio.todolist.data.Item;
import org.jetbrains.annotations.NotNull;

public class ItemsViewHolder extends RecyclerView.ViewHolder {

    private final TextView nameText;
    private Item item;

    public ItemsViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        nameText = itemView.findViewById(R.id.nameText);
    }

    public void bind(Item item) {
        this.item = item;
        nameText.setText(item.getItemName());
    }

    public Item getItem() {
        return item;
    }
}
