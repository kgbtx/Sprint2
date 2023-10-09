package com.example.cs449project;

public class Player {
    public enum PlayerType {
        Blue,
        Red
    }

    public enum Symbol {
        S,
        O
    }
    private PlayerType type;
    private Symbol currentSymbol;

    public Player(PlayerType type) {
        this.type = type;
        this.currentSymbol = Symbol.S;
    }

    public String getName() {
        return this.type.name() + " player";
    }

    public String getCurrentSymbol() {
        return this.currentSymbol.name();
    }

    public void setCurrentSymbol(Symbol symbol) {
        this.currentSymbol = symbol;
    }

}
