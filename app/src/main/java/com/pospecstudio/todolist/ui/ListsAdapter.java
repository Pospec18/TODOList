package com.pospecstudio.todolist.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.todolist.R;
import com.pospecstudio.todolist.SwitchListActivity;
import com.pospecstudio.todolist.data.ListNames;
import com.pospecstudio.todolist.helper.Collections;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ListsAdapter extends RecyclerView.Adapter<ListViewHolder> implements RecycleViewReorderable<ListViewHolder> {

    private final Context context;
    private final List<ListNames> lists;
    private final SwitchListActivity listsPage;

    public ListsAdapter(Context context, List<ListNames> lists, SwitchListActivity listsPage) {
        this.context = context;
        this.lists = lists;
        this.listsPage = listsPage;
    }

    @NonNull
    @NotNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_holder, parent, false);
        return new ListViewHolder(view, listsPage);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ListViewHolder holder, int position) {
        ListNames list = lists.get(position);
        holder.bind(list);
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    @Override
    public void onRowMoved(int from, int to) {
        ListNames toMove = lists.get(from);
        int toStayIndex = to;
        if (from < to)
            toStayIndex++;
        ListNames toStay = toStayIndex >= lists.size() ? null : lists.get(toStayIndex);
        listsPage.moveAboveItem(toMove, toStay);

        Collections.move(lists, from, to);
        notifyItemMoved(from, to);
    }

    @Override
    public void onRowSelected(ListViewHolder view) {
        view.startReordering();
    }

    @Override
    public void onRowClear(ListViewHolder view) {
        view.endReordering();
    }
}
