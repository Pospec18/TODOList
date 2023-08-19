package com.example.todolist.data;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Filter {
    private final Set<FilledType> showTypes;
    private boolean showSkipped = false;

    public Filter() {
        showTypes = new HashSet<>();
        showTypes.add(FilledType.EMPTY);
        showTypes.add(FilledType.PARTIALLY);
        showTypes.add(FilledType.FULLY);
    }

    public Filter(Set<FilledType> showTypes, boolean showSkipped) {
        this.showTypes = showTypes;
        this.showSkipped = showSkipped;
    }

    public boolean canShow(Item item) {
        if (item.isHide() && !showSkipped)
            return false;
        return  showTypes.contains(item.getFilledType());
    }

    public void addShowedType(FilledType type) {
        showTypes.add(type);
    }

    public void removeShowedType(FilledType type) {
        showTypes.remove(type);
    }

    public Iterator<FilledType> getShowedTypes() {
        return showTypes.iterator();
    }

    public boolean isShowSkipped() {
        return showSkipped;
    }

    public void setShowSkipped(boolean showSkipped) {
        this.showSkipped = showSkipped;
    }
}
