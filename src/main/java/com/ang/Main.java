package com.ang;

import com.ang.Opponent.Engine;

public class Main {
    public static void main(String[] args) {
        Engine opponent = new Engine(500);
        Game game = new Game(opponent);
        game.start();
    }
}
