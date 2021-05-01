package com.aman.dto;

public class SnakeDto {
    private final int head;

    private final int tail;

    public SnakeDto(int head, int tail) {
        this.head = head;
        this.tail = tail;
    }

    public int getHead() {
        return head;
    }

    public int getTail() {
        return tail;
    }
}
