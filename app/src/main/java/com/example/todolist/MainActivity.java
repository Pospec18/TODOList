package com.example.todolist;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.example.todolist.data.Item;
import com.example.todolist.data.ItemHolder;
import com.example.todolist.data.SaveAndLoad;
import com.example.todolist.ui.ItemView;

public class MainActivity extends AppCompatActivity {

    private ItemHolder itemHolder = new ItemHolder(SaveAndLoad.loadItems());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout linearLayout = findViewById(R.id.list);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            for (Item item : itemHolder.filterNoItems())
                linearLayout.addView(new ItemView(linearLayout.getContext(), item));
            System.out.println("AAAAA");
        }
        System.out.println("BBBB");
    }
}