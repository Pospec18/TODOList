package com.pospecstudio.todolist.ui;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import com.pospecstudio.todolist.MainActivity;
import com.example.todolist.R;
import com.pospecstudio.todolist.data.Item;

public class ItemView extends LinearLayout {
    private final Item item;
    private final EditText currentCountView;

    public ItemView(Context context, Item item, MainActivity main) {
        super(context);
        this.item = item;
        setBackgroundResource(R.drawable.item_background);

        LayoutParams a = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        a.setMargins(10, 10, 10, 10);
        a.gravity = Gravity.CENTER;
        setLayoutParams(a);

        ContextThemeWrapper textThemeWrapper = new ContextThemeWrapper(context, R.style.text);

        TextView nameView = new TextView(textThemeWrapper, null, R.style.text);
        nameView.setText(item.getItemName());
        nameView.setGravity(Gravity.CENTER_VERTICAL);
        nameView.setEllipsize(TextUtils.TruncateAt.END);
        nameView.setMaxLines(1);
        LayoutParams nameLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 100);
        nameLayoutParams.gravity = Gravity.CENTER;
        nameLayoutParams.setMargins(50, 10, 0, 10);
        nameView.setLayoutParams(nameLayoutParams);

        LayoutParams countLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 0);
        countLayoutParams.gravity = Gravity.CENTER;
        countLayoutParams.setMargins(10, 10, 10, 10);

        TextView countView = new TextView(textThemeWrapper, null, R.style.text);
        countView.setGravity(Gravity.CENTER);
        countView.setText(Integer.toString(item.getIdealCount()));
        countView.setLayoutParams(countLayoutParams);

        currentCountView = new EditText(textThemeWrapper, null, R.style.text);
        currentCountView.setLayoutParams(countLayoutParams);
        currentCountView.setBackgroundResource(R.drawable.item_background);
        currentCountView.setGravity(Gravity.CENTER);
        currentCountView.setInputType(InputType.TYPE_CLASS_NUMBER);
        currentCountView.setOnEditorActionListener((view, actionId, keyEvent) -> {
            if (actionId != EditorInfo.IME_ACTION_DONE)
                return false;
            try {
                item.setCurrCount(Integer.parseInt(currentCountView.getText().toString()));
            } catch (NumberFormatException nfe) {
                main.showMessage("Ideal count must be number.");
                return false;
            }
            return true;
        });
    }
}
