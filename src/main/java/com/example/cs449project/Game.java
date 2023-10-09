package com.example.cs449project;

import java.util.Arrays;

public class Game {
    final static int NO_OF_PLAYERS = 2;
    static int INITIAL_BOARD_SIZE = 4;
    final static int MIN_BOARD_SIZE = 3;
    final static int MAX_BOARD_SIZE = 12;

    public enum GameMode {
        Simple,
        General
    }
    private Player[] players;
    private Player currentPlayer;
    private GameMode gameMode;
    private int boardSize;
    private String[] boardState;
    private boolean isGameStarted;

    public Game() {
        startGame();
    }

    public void startGame() {
        players = new Player[NO_OF_PLAYERS];
        players[0] = new Player(Player.PlayerType.Blue);
        players[1] = new Player(Player.PlayerType.Red);
        currentPlayer = players[0];
        gameMode = GameMode.Simple;
        boardSize = INITIAL_BOARD_SIZE;
        boardState = new String[INITIAL_BOARD_SIZE*INITIAL_BOARD_SIZE];
        isGameStarted = false;
    }

    public void setGameMode(GameMode type) {
        gameMode = type;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public Player[] getPlayers() {
        return players;
    }
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        if (boardSize >= MIN_BOARD_SIZE && boardSize <= MAX_BOARD_SIZE) {
            this.boardSize = boardSize;
            this.boardState = new String[boardSize * boardSize];
        }
    }

    public boolean makeMove(int index) {
        boolean isValidMove = false;
        if (index < this.boardSize*this.boardSize && this.boardState[index] == null) {
            this.boardState[index] = this.currentPlayer.getCurrentSymbol();
            isValidMove = true;
        }
        return isValidMove;
    }

    public void changeCurrentPlayer() {
        this.currentPlayer = this.currentPlayer == players[0] ? players[1] : players[0];
    }

    public boolean isGameStarted() {
        return isGameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        isGameStarted = gameStarted;
    }

    public String getCellState(int index) {
        return this.boardState[index];
    }

    public void setCellState(int index) {
        this.boardState[index] = currentPlayer.getCurrentSymbol();
    }

    public void resetGame() {
        startGame();
    }

    public Player getWinner() {
        // Implement logic to check for SOS and determine the winner
        return null;
    }
}

