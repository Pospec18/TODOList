package com.pospecstudio.todolist.helper;

public class Numeric {
    public static boolean isBetween(int number, int rangeEdge1, int rangeEdge2) {
        int rangeStart = Math.min(rangeEdge1, rangeEdge2);
        int rangeEnd = Math.max(rangeEdge1, rangeEdge2);

        return rangeStart < number && number < rangeEnd;
    }

    public static boolean isBetweenOrEqual(int number, int rangeEdge1, int rangeEdge2) {
        int rangeStart = Math.min(rangeEdge1, rangeEdge2);
        int rangeEnd = Math.max(rangeEdge1, rangeEdge2);

        return rangeStart <= number && number <= rangeEnd;
    }
}
