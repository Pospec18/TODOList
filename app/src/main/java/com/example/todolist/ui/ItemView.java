package com.example.todolist.ui;

import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.todolist.R;
import com.example.todolist.data.Item;

public class ItemView extends LinearLayout {
    private Item item;

    public ItemView(Context context, Item item) {
        super(context);
        this.item = item;
        TextView nameView = new TextView(getContext());
        nameView.setText(item.getItemName());
        setBackgroundResource(R.color.red_500);

        TextView countView = new TextView(getContext());
        countView.setText(Integer.toString(item.getIdealCount()));

        Button button = new Button(getContext());
        button.setText("+");

        addView(nameView);
        addView(countView);
        addView(button);
    }
}
