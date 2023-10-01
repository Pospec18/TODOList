package com.pospecstudio.todolist.ui;

import androidx.recyclerview.widget.RecyclerView;

public interface RecycleViewReorderable<T extends RecyclerView.ViewHolder> {
    void onRowMoved(int from, int to);
    void onRowSelected(T view);
    void onRowClear(T view);
}
