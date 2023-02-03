package com.example.todolist.ui;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.example.todolist.MainActivity;
import com.example.todolist.R;
import com.example.todolist.data.Item;

public class ItemView extends LinearLayout {
    private final Item item;
    private final TextView currentCountView;
    private final ImageButton editButton;

    public ItemView(Context context, Item item, MainActivity main) {
        super(context);
        this.item = item;
        setBackgroundResource(R.drawable.item_background);

        LayoutParams a = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        a.setMargins(10, 10, 10, 10);
        a.gravity = Gravity.CENTER;
        setLayoutParams(a);

        ContextThemeWrapper textThemeWrapper = new ContextThemeWrapper(context, R.style.text);
        ContextThemeWrapper changeCountThemeWrapper = new ContextThemeWrapper(context, R.style.changeCount);

        TextView nameView = new TextView(textThemeWrapper, null, R.style.text);
        nameView.setText(item.getItemName());
        nameView.setGravity(Gravity.CENTER_VERTICAL);
        LayoutParams nameLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 10);
        nameLayoutParams.gravity = Gravity.CENTER;
        nameLayoutParams.setMargins(50, 10, 0, 10);
        nameView.setLayoutParams(nameLayoutParams);

        LayoutParams countLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        countLayoutParams.gravity = Gravity.CENTER;
        countLayoutParams.setMargins(0, 10, 0, 10);

        TextView countView = new TextView(textThemeWrapper, null, R.style.text);
        countView.setGravity(Gravity.CENTER);
        countView.setText(Integer.toString(item.getIdealCount()));
        countView.setLayoutParams(countLayoutParams);

        currentCountView = new TextView(textThemeWrapper, null, R.style.text);
        currentCountView.setLayoutParams(countLayoutParams);
        currentCountView.setBackgroundResource(R.drawable.item_background);
        currentCountView.setGravity(Gravity.CENTER);

        Button increase = new Button(changeCountThemeWrapper, null, R.style.changeCount);
        increase.setText("+");
        increase.setOnClickListener(v -> {
            item.increaseCurrCount();
            main.saveItems();
            updateColors();
        });

        Button decrease = new Button(changeCountThemeWrapper, null, R.style.changeCount);
        decrease.setText("-");
        decrease.setOnClickListener(v -> {
            if (item.getCurrCount() <= 0)
                return;

            item.decreaseCurrCount();
            main.saveItems();
            updateColors();
        });

        editButton = new ImageButton(context);
        editButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_edit_icon));
        editButton.setBackgroundResource(R.drawable.item_background);
        ((GradientDrawable)editButton.getBackground()).setColor(ContextCompat.getColor(context, R.color.white));
        editButton.setLayoutParams(countLayoutParams);
        editButton.setOnClickListener(v -> main.editItem(item));

        LinearLayout changeCount = new LinearLayout(context);
        changeCount.setOrientation(VERTICAL);
        LayoutParams changeCountLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        changeCountLayoutParams.setMargins(20, 10, 20, 10);
        countLayoutParams.gravity = Gravity.CENTER;
        changeCount.setLayoutParams(changeCountLayoutParams);
        changeCount.addView(increase);
        changeCount.addView(decrease);

        addView(nameView);
        addView(countView);
        addView(currentCountView);
        addView(changeCount);
        addView(editButton);
        updateColors();
    }

    private void updateColors() {
        Context context = getContext();
        ((GradientDrawable)getBackground()).setColor(FilledTypeToColor.primary(item.getFilledType(), context));
        currentCountView.setText(Integer.toString(item.getCurrCount()));
        ((GradientDrawable)currentCountView.getBackground()).setColor(FilledTypeToColor.secondary(item.getFilledType(), context));
        editButton.setColorFilter(FilledTypeToColor.primary(item.getFilledType(), context), android.graphics.PorterDuff.Mode.MULTIPLY);
    }
}
