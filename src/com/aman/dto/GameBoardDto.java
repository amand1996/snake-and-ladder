package com.aman.dto;

import com.aman.exceptions.BadInputException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GameBoardDto {
    private final Map<PlayerDto, Integer> players = new HashMap<>();

    private final Map<Integer, SnakeDto> snakes = new HashMap<>();

    private final Map<Integer, LadderDto> ladders = new HashMap<>();

    private PlayerDto currentlyPlaying;

    private static final Random random = new Random();

    public void registerPlayer(PlayerDto player) {
        this.players.put(player, 0);
        if (currentlyPlaying == null) {
            currentlyPlaying = player;
        }
    }

    public void registerSnake(SnakeDto snake) throws BadInputException {
        if (snake.getHead() <= snake.getTail()) {
            throw new BadInputException("Snake tail cannot be above head");
        }
        if (this.snakes.containsKey(snake.getHead())) {
            throw new BadInputException("Snake already exists at Head " + snake.getHead());
        }
        this.snakes.put(snake.getHead(), snake);
    }

    public void registerLadder(LadderDto ladder) throws BadInputException {
        if (ladder.getEnd() <= ladder.getStart()) {
            throw new BadInputException("Ladder Start cannot be above End");
        }
        if (this.ladders.containsKey(ladder.getStart())) {
            throw new BadInputException("Ladder already exists at Start " + ladder.getStart());
        }
        this.ladders.put(ladder.getStart(), ladder);
    }

    public void rollDie() {
        int current = players.get(currentlyPlaying);
        while (current == 100) {
            changeCurrentlyPlaying();
            current = players.get(currentlyPlaying);
        }

        int steps = random.nextInt(6) + 1;
        do {
            System.out.println(currentlyPlaying.getName() + " rolls the die: " + steps);
            move(currentlyPlaying, steps);
            steps = random.nextInt(6) + 1;
        } while (steps == 6);
        changeCurrentlyPlaying();
    }

    private void changeCurrentlyPlaying() {
        int total = players.size();
        int id = currentlyPlaying.getId();
        int newId = id + 1 == total ? 0 : id + 1;
        for (PlayerDto player : players.keySet()) {
            if (player.getId() == newId) {
                currentlyPlaying = player;
                break;
            }
        }
    }

    private void move(PlayerDto player, int steps) {
        int current = players.get(player);
        if (current + steps <= 100) {
            current = current + steps;
            if (ladders.containsKey(current)) {
                LadderDto ladder = ladders.get(current);
                current = ladder.getEnd();
                System.out.println(player.getName() + " got a ladder from " + ladder.getStart() + " to " + ladder.getEnd());
            }
            if (snakes.containsKey(current)) {
                SnakeDto snake = snakes.get(current);
                current = snake.getTail();
                System.out.println(player.getName() + " got bitten by a snake from " + snake.getHead() + " to " + snake.getTail());
            }
            players.put(player, current);
        }
        if (current == 100) {
            System.out.println(player.getName() + " wins!");
        }
        printLeaderBoard();
    }

    private void printLeaderBoard() {
        System.out.println();
        System.out.println("LEADERBOARD");
        System.out.println("====================================");
        for (Map.Entry<PlayerDto, Integer> entry : players.entrySet()) {
            PlayerDto player = entry.getKey();
            Integer position = entry.getValue();
            System.out.println(player.getName() + " : " + position);
        }
        System.out.println("====================================");
    }

    public boolean gameOver() {
        return players.values().stream().allMatch(p -> p == 100);
    }

    public void printLadders() {
        System.out.println("Ladders: ");
        this.ladders.values().forEach(l -> System.out.println(l.getStart() + " to " + l.getEnd()));
    }

    public void printSnakes() {
        System.out.println("Snakes: ");
        this.snakes.values().forEach(l -> System.out.println(l.getHead() + " to " + l.getTail()));
    }
}
