package com.project.kn.scrumsimulator.boardview;

public enum ColumnName {
    IN_PROGRESS(2),
    PROBLEM(0),
    SOLUTION(1);

    public int columnNumber;

    ColumnName(int columnNumber) {
        this.columnNumber = columnNumber;
    }
}
