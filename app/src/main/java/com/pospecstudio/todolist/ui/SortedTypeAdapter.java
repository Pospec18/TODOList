package com.pospecstudio.todolist.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.todolist.R;
import com.pospecstudio.todolist.FilterActivity;
import com.pospecstudio.todolist.SwitchListActivity;
import com.pospecstudio.todolist.data.ListNames;
import com.pospecstudio.todolist.data.SortingType;
import com.pospecstudio.todolist.helper.Collections;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SortedTypeAdapter extends RecyclerView.Adapter<SortedTypeView> implements RecycleViewReorderable<SortedTypeView> {

    private final Context context;
    private final List<SortingType> types;
    private final FilterActivity typesPage;

    public SortedTypeAdapter(Context context, List<SortingType> types, FilterActivity typesPage) {
        this.context = context;
        this.types = types;
        this.typesPage = typesPage;
    }

    @NonNull
    @NotNull
    @Override
    public SortedTypeView onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sort_holder, parent, false);
        return new SortedTypeView(view, typesPage);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SortedTypeView holder, int position) {
        SortingType type = types.get(position);
        holder.bind(type);
    }

    @Override
    public int getItemCount() {
        return types.size();
    }

    @Override
    public void onRowMoved(int from, int to) {
        SortingType toMove = types.get(from);
        int toStayIndex = to;
        if (from < to)
            toStayIndex++;
        SortingType toStay = toStayIndex >= types.size() ? null : types.get(toStayIndex);
        Collections.move(types, from, to);
        typesPage.save();
        notifyItemMoved(from, to);
    }

    @Override
    public void onRowSelected(SortedTypeView view) {
        view.startReordering();
    }

    @Override
    public void onRowClear(SortedTypeView view) {
        view.endReordering();
    }
}
