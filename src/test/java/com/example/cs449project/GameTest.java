package com.example.cs449project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game game;
    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    void setGameMode() {
        game.setGameMode(Game.GameMode.General);
        assertEquals(game.getGameMode(), Game.GameMode.General);
    }

    @Test
    void getGameMode() {
        assertEquals(game.getGameMode(), Game.GameMode.Simple);
    }

    @Test
    void getPlayers() {
        Player[] players = game.getPlayers();
        assertEquals(players.length, 2);
        assertEquals(players[0].getName(), "Blue player");
        assertEquals(players[1].getName(), "Red player");
    }

    @Test
    void getCurrentPlayer() {
        assertEquals(game.getCurrentPlayer().getName(), "Blue player");
    }

    @Test
    void getBoardSize() {
        assertEquals(game.getBoardSize(), Game.INITIAL_BOARD_SIZE);
    }

    @Test
    void setBoardSize() {
        game.setBoardSize(5);
        assertEquals(game.getBoardSize(), 5);
        game.setBoardSize(2);
        assertEquals(game.getBoardSize(), 5);
        game.setBoardSize(13);
        assertEquals(game.getBoardSize(), 5);
    }

    @Test
    void makeMove() {
        assert(game.makeMove(0));
        game.setCellState(0);
        assertFalse(game.makeMove(0));
    }

    @Test
    void changeCurrentPlayer() {
        assertEquals(game.getCurrentPlayer().getName(), "Blue player");
        game.changeCurrentPlayer();
        assertEquals(game.getCurrentPlayer().getName(), "Red player");
    }

    @Test
    void isGameStarted() {
        assertFalse(game.isGameStarted());
    }

    @Test
    void setGameStarted() {
        assertFalse(game.isGameStarted());
        game.setGameStarted(true);
        assert(game.isGameStarted());
    }

    @Test
    void getCellState() {
        assertEquals(game.getCellState(0), null);
    }

    @Test
    void setCellState() {
        assertEquals(game.getCellState(0), null);
        game.setCellState(0);
        assertEquals(game.getCellState(0), "S");
    }
}