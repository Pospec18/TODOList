package com.pospecstudio.todolist.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;

public class RecycleRowMoveCallback<T extends RecyclerView.ViewHolder> extends ItemTouchHelper.Callback {
    private RecycleViewReorderable<T> reorderable;
    private final Class<T> tClass;

    public RecycleRowMoveCallback(RecycleViewReorderable<T> reorderable, Class<T> tClass) {
        this.reorderable = reorderable;
        this.tClass = tClass;
    }

    public void setReorderable(RecycleViewReorderable<T> reorderable) {
        this.reorderable = reorderable;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    @Override
    public void onSelectedChanged(@Nullable @org.jetbrains.annotations.Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        if(actionState != ItemTouchHelper.ACTION_STATE_IDLE)
        {
            if(viewHolder != null && tClass.isAssignableFrom(viewHolder.getClass())){
                T myViewHolder = (T)viewHolder;
                reorderable.onRowSelected(myViewHolder);
            }
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        if(tClass.isAssignableFrom(viewHolder.getClass())){
            T myViewHolder = (T)viewHolder;
            reorderable.onRowClear(myViewHolder);
        }
    }

    @Override
    public int getMovementFlags(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder) {
        int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        return makeMovementFlags(dragFlag,0);
    }

    @Override
    public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
        this.reorderable.onRowMoved(viewHolder.getAdapterPosition(),target.getAdapterPosition());
        return false;
    }

    @Override
    public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {

    }
}
