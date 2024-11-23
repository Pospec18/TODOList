package com.pospecstudio.todolist.ui;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.todolist.R;
import com.pospecstudio.todolist.SwitchListActivity;
import com.pospecstudio.todolist.data.FilledType;
import com.pospecstudio.todolist.data.ListNames;
import org.jetbrains.annotations.NotNull;

public class ListViewHolder extends RecyclerView.ViewHolder {
    private final Context context;
    private final TextView nameText;
    private final GradientDrawable background;
    private final SwitchListActivity lists;
    private ListNames listName;

    public ListViewHolder(@NonNull @NotNull View itemView, SwitchListActivity lists) {
        super(itemView);
        this.lists = lists;
        context = itemView.getContext();
        nameText = itemView.findViewById(R.id.name_text);
        background = (GradientDrawable) itemView.getBackground();
        ImageButton editButton = itemView.findViewById(R.id.close_button);

        editButton.setColorFilter(FilledTypeToColor.primary(FilledType.PARTIALLY, context), android.graphics.PorterDuff.Mode.MULTIPLY);

        itemView.setOnClickListener(this::selectList);
        editButton.setOnClickListener(this::editList);
    }

    public void selectList(View view) {
        lists.selectList(listName);
    }

    public void editList(View view) {
        lists.editList(listName);
    }

    public void bind(ListNames listName) {
        this.listName = listName;
        nameText.setText(listName.getListName());
    }

    public void startReordering() {
        background.setColor(FilledTypeToColor.transparent(FilledType.PARTIALLY, context));
        background.setAlpha(220);
    }

    public void endReordering() {
        background.setColor(FilledTypeToColor.primary(FilledType.PARTIALLY, context));
        background.setAlpha(255);
    }
}
