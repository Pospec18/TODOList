package com.pospecstudio.todolist.data;

import java.io.Serializable;
import java.util.*;

public class Filter implements Serializable {
    private final Set<FilledType> showTypes;
    private boolean showSkipped = false;

    public Filter() {
        showTypes = new HashSet<>();
        showTypes.add(FilledType.EMPTY);
        showTypes.add(FilledType.PARTIALLY);
        showTypes.add(FilledType.FULLY);
        showTypes.add(FilledType.OPTIONAL);
    }

    public Filter(Set<FilledType> showTypes, boolean showSkipped) {
        this.showTypes = showTypes;
        this.showSkipped = showSkipped;
    }

    public boolean canShow(Item item) {
        if (item.isHide() && !showSkipped)
            return false;
        return canShow(item.getFilledType());
    }

    public boolean canShow(FilledType type) {
        return showTypes.contains(type);
    }

    public void addShowedType(FilledType type) {
        showTypes.add(type);
    }

    public void removeShowedType(FilledType type) {
        showTypes.remove(type);
    }

    public boolean isShowSkipped() {
        return showSkipped;
    }

    public void setShowSkipped(boolean showSkipped) {
        this.showSkipped = showSkipped;
    }
}
