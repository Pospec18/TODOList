package com.pospecstudio.todolist.ui;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.todolist.R;
import com.pospecstudio.todolist.FilterActivity;
import com.pospecstudio.todolist.data.FilledType;
import com.pospecstudio.todolist.data.SortingType;
import org.jetbrains.annotations.NotNull;

public class SortedTypeView extends RecyclerView.ViewHolder {
    private SortingType sortType;
    private final Context context;
    private final TextView nameText;
    private final GradientDrawable background;
    private final FilterActivity filterActivity;

    public SortedTypeView(@NonNull @NotNull View itemView, FilterActivity filterActivity) {
        super(itemView);
        this.filterActivity = filterActivity;

        context = itemView.getContext();
        nameText = itemView.findViewById(R.id.name_text);
        background = (GradientDrawable) itemView.getBackground();

        ImageButton deleteButton = itemView.findViewById(R.id.close_button);
        deleteButton.setOnClickListener(this::deleteType);
    }

    private void deleteType(View view) {
        filterActivity.deleteSortingType(getLayoutPosition());
    }

    public void bind(SortingType sortType) {
        this.sortType = sortType;
        nameText.setText(sortType.getTitle());
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
