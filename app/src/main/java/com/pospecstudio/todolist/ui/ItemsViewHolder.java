package com.pospecstudio.todolist.ui;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.todolist.R;
import com.pospecstudio.todolist.MainActivity;
import com.pospecstudio.todolist.data.Item;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class ItemsViewHolder extends RecyclerView.ViewHolder {
    private final Context context;
    private final TextView nameText;
    private final TextView idealText;
    private final EditText currentText;
    private final ImageButton editButton;
    private final GradientDrawable background;
    private final GradientDrawable currentTextBackground;
    private final MainActivity main;
    private final View editPanel;
    private Item item;

    public ItemsViewHolder(@NonNull @NotNull View itemView, MainActivity main) {
        super(itemView);
        this.main = main;
        context = itemView.getContext();
        nameText = itemView.findViewById(R.id.nameText);
        idealText = itemView.findViewById(R.id.idealCountText);
        currentText = itemView.findViewById(R.id.currCountText);
        editPanel = itemView.findViewById(R.id.editPanel);
        editPanel.setVisibility(View.GONE);
        editButton = itemView.findViewById(R.id.editButton);
        editButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_edit_icon));
        ((GradientDrawable)editButton.getBackground()).setColor(ContextCompat.getColor(context, R.color.white));
        background = (GradientDrawable) itemView.getBackground();
        currentTextBackground = (GradientDrawable) currentText.getBackground();

        editButton.setOnClickListener(this::editItem);
        itemView.findViewById(R.id.increase).setOnClickListener(this::increase);
        itemView.findViewById(R.id.decrease).setOnClickListener(this::decrease);
    }

    public void bind(Item item) {
        this.item = item;
        nameText.setText(item.getItemName());
        idealText.setText(String.format(Locale.ENGLISH, "%d", item.getIdealCount()));
        currentText.setText(String.format(Locale.ENGLISH, "%d", item.getCurrCount()));
        updateColors();
    }

    private void updateColors() {
        background.setColor(FilledTypeToColor.primary(item.getFilledType(), context));
        currentText.setText(String.format(Locale.ENGLISH, "%d", item.getCurrCount()));
        currentTextBackground.setColor(FilledTypeToColor.secondary(item.getFilledType(), context));
        editButton.setColorFilter(FilledTypeToColor.primary(item.getFilledType(), context), android.graphics.PorterDuff.Mode.MULTIPLY);
    }

    public void increase(View v) {
        item.increaseCurrCount();
        main.saveItems();
        updateColors();
    }

    public void decrease(View v) {
        if (item.getCurrCount() <= 0)
            return;

        item.decreaseCurrCount();
        main.saveItems();
        updateColors();
    }

    public void editItem(View v) {
        main.editItem(item);
    }

    public void deselect() {
        editPanel.setVisibility(View.GONE);
    }

    public void select() {
        editPanel.setVisibility(View.VISIBLE);
    }
}
