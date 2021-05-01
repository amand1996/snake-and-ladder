package com.aman;

import com.aman.dto.GameBoardDto;
import com.aman.dto.LadderDto;
import com.aman.dto.PlayerDto;
import com.aman.dto.SnakeDto;
import com.aman.exceptions.BadInputException;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("Welcome to the Snake & Ladder game!");
        GameBoardDto game = new GameBoardDto();

        initPlayers(game);
        initLadders(game);
        initSnakes(game);

        startGame(game);
    }

    private static void startGame(GameBoardDto game) {
        while (!game.gameOver()) {
            game.rollDie();
            System.out.println("Press any key to roll the die:");
            scanner.next();
        }
    }

    private static void initSnakes(GameBoardDto game) {
        System.out.println("enter the number of snakes: ");
        int numSnakes = scanner.nextInt();
        scanner.nextLine();

        while (numSnakes > 0) {
            System.out.println("enter head and tail of snake (space-separated): ");
            int head = scanner.nextInt();
            int tail = scanner.nextInt();
            try {
                game.registerSnake(new SnakeDto(head, tail));
                numSnakes--;
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
            }
        }
        game.printSnakes();
    }

    private static void initLadders(GameBoardDto game) {
        System.out.println("enter the number of ladders: ");
        int numLadders = scanner.nextInt();
        scanner.nextLine();

        while (numLadders > 0) {
            System.out.println("enter start and end of ladder (space-separated): ");
            int start = scanner.nextInt();
            int end = scanner.nextInt();
            try {
                game.registerLadder(new LadderDto(start, end));
                numLadders--;
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
            }
        }
        game.printLadders();
    }

    private static void initPlayers(GameBoardDto game) {
        System.out.println("enter the number of players: ");

        int numPlayers = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < numPlayers; i++) {
            System.out.println("enter name of player " + (i + 1) + ": ");
            String name = scanner.nextLine();
            while (name == null || name.trim() == "") {
                System.out.println("invalid input");
                System.out.println("enter name of player " + (i + 1) + ": ");
                name = scanner.nextLine();
            }
            game.registerPlayer(new PlayerDto(name));
        }
    }
}
