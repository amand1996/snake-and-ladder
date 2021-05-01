package com.aman.dto;

public class PlayerDto {
    private static int counter = 0;

    private int id;
    private final String name;

    public PlayerDto(String name) {
        this.name = name;
        this.id = counter;
        counter++;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
