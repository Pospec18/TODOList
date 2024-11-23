package com.pospecstudio.todolist.ui;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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
    private final ImageButton orderButton;
    private boolean canDelete = true;

    public SortedTypeView(@NonNull @NotNull View itemView, FilterActivity filterActivity) {
        super(itemView);
        this.filterActivity = filterActivity;

        context = itemView.getContext();
        nameText = itemView.findViewById(R.id.name_text);
        background = (GradientDrawable) itemView.getBackground();

        ImageButton deleteButton = itemView.findViewById(R.id.close_button);
        deleteButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_close_icon));
        deleteButton.setOnClickListener(this::deleteType);

        orderButton = itemView.findViewById(R.id.order_button);
        orderButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_asc_icon));
        orderButton.setOnClickListener(this::changeOrder);
    }

    private void deleteType(View view) {
        if (canDelete) {
            canDelete = false;
            filterActivity.deleteSortingType(getLayoutPosition());
        }
    }

    private void changeOrder(View view) {
        sortType.setAscending(!sortType.isAscending());
        filterActivity.save();
        updateUI();
    }

    private void updateUI() {
        if (sortType.isAscending())
            orderButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_asc_icon));
        else
            orderButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_desc_icon));
    }

    public void bind(SortingType sortType) {
        this.sortType = sortType;
        nameText.setText(sortType.getTitle());
        updateUI();
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
