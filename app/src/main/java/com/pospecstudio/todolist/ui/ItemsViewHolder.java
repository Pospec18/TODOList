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
import com.pospecstudio.todolist.data.Item;
import org.jetbrains.annotations.NotNull;

public class ItemsViewHolder extends RecyclerView.ViewHolder {
    private final Context context;
    private final TextView nameText;
    private final TextView idealText;
    private final EditText currentText;
    private final ImageButton editButton;
    private final GradientDrawable background;
    private final GradientDrawable currentTextBackground;
    private Item item;

    public ItemsViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        context = itemView.getContext();
        nameText = itemView.findViewById(R.id.nameText);
        idealText = itemView.findViewById(R.id.idealCountText);
        currentText = itemView.findViewById(R.id.currCountText);
        editButton = itemView.findViewById(R.id.editButton);
        editButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_edit_icon));
        ((GradientDrawable)editButton.getBackground()).setColor(ContextCompat.getColor(context, R.color.white));
        background = (GradientDrawable) itemView.getBackground();
        currentTextBackground = (GradientDrawable) currentText.getBackground();
    }

    public void bind(Item item) {
        this.item = item;
        nameText.setText(item.getItemName());
        idealText.setText(Integer.toString(item.getIdealCount()));
        currentText.setText(Integer.toString(item.getCurrCount()));
        updateColors();
    }

    private void updateColors() {
        background.setColor(FilledTypeToColor.primary(item.getFilledType(), context));
        currentText.setText(Integer.toString(item.getCurrCount()));
        currentTextBackground.setColor(FilledTypeToColor.secondary(item.getFilledType(), context));
        editButton.setColorFilter(FilledTypeToColor.primary(item.getFilledType(), context), android.graphics.PorterDuff.Mode.MULTIPLY);
    }

    public void increase(View v) {

    }

    public void decrease(View v) {

    }
}
