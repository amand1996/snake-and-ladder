package com.aman.dto;

public class LadderDto {

    private final int start;

    private final int end;

    public LadderDto(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}
