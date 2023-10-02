package com.pospecstudio.todolist.helper;

import java.util.List;

public class Collections {
    public static void move(List<?> list, int from, int to) {
        if(from < to)
            for(int i = from; i < to; i++)
                java.util.Collections.swap(list, i, i + 1);
        else
            for(int i = from; i > to; i--)
                java.util.Collections.swap(list, i, i - 1);
    }
}
