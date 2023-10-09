package com.example.cs449project;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameBoard extends Application {
    private Game game;
    private Scene scene;
    private BorderPane rootBorderPane;
    private HBox headingHBox;
    private HBox gameModeHBox;
    private Label gameLabel;
    private ToggleGroup gameModeToggleGroup;
    private RadioButton simpleGameModeRadioButton;
    private RadioButton generalGameModeRadioButton;
    private Label boardSizeLabel;
    private TextField boardSizeTextField;
    private Label[] playerLabels;
    private VBox[] playerSymbolsBoxes;
    private ToggleGroup[] playerSymbolToggleGroups;
    private RadioButton[] playerSRadioButtons;
    private RadioButton[] playerORadioButtons;
    private HBox footerHBox;
    private Button startGameButton;
    private Button cancelGameButton;
    GridPane gameBoard;

    public GameBoard() {
        game = new Game();
        // The root node to hold all the game nodes
        rootBorderPane = new BorderPane();
        rootBorderPane.setPadding(new javafx.geometry.Insets(20));
        // Set the heading box
        setHeading();
        // Set the player vbox-es
        setPlayerVBox(game.getPlayers());
        // Set the footer box
        setFooter();
        // Set the board grid
        setBoard();
    }
    private void setHeading() {
        // HBox to hold the game label, game mode toggles and board size input
        headingHBox = new HBox(30);
        headingHBox.setAlignment(Pos.CENTER);
        // Create the game label
        gameLabel = new Label("SOS");
        // Create a ToggleGroup for radio buttons to hold the game mode radio buttons
        gameModeToggleGroup = new ToggleGroup();
        // Create radio buttons for game type selection
        simpleGameModeRadioButton = new RadioButton(Game.GameMode.Simple.name() + " game");
        simpleGameModeRadioButton.setUserData(Game.GameMode.Simple);
        simpleGameModeRadioButton.setSelected(true);
        generalGameModeRadioButton = new RadioButton(Game.GameMode.General.name() + " game");
        generalGameModeRadioButton.setUserData(Game.GameMode.General);
        // Add radio buttons to the ToggleGroup
        simpleGameModeRadioButton.setToggleGroup(gameModeToggleGroup);
        generalGameModeRadioButton.setToggleGroup(gameModeToggleGroup);

        gameModeToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {

                if (gameModeToggleGroup.getSelectedToggle() != null) {
                    try {
                        Game.GameMode selectedMode = Game.GameMode.valueOf(new_toggle.getUserData().toString());
                        game.setGameMode(selectedMode);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        // Add game label and game mode radio buttons to the game mode HBox
        gameModeHBox = new HBox(10, gameLabel, simpleGameModeRadioButton, generalGameModeRadioButton);
        // Create a label and a text input field for board size
        boardSizeLabel = new Label("Board Size");
        boardSizeTextField = new TextField();
        // Set the width to 2 digits
        boardSizeTextField.setPrefColumnCount(2);
        boardSizeTextField.setText(String.valueOf(game.getBoardSize()));

        // Set event listener to reset board when board size changes
        // Add an event listener to the text field to select its value when the user enters a value
        boardSizeTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                boardSizeTextField.selectAll();
                try {
                    game.setBoardSize(Integer.parseInt(newValue));
                } catch (Exception e) {
                    // e.printStackTrace();
                }
                setBoard();
            }
        });

        // Add game mode box, board size label and textfield to heading box
        headingHBox.getChildren().addAll(gameModeHBox, boardSizeLabel, boardSizeTextField);
        // Add heading to top of border pane
        rootBorderPane.setTop(headingHBox);
    }
    private void setPlayerVBox(Player[] players) {
        int noOfPlayers = players.length;
        playerLabels = new Label[noOfPlayers];
        playerSymbolsBoxes = new VBox[noOfPlayers];
        playerSymbolToggleGroups = new ToggleGroup[noOfPlayers];
        playerSRadioButtons = new RadioButton[noOfPlayers];
        playerORadioButtons = new RadioButton[noOfPlayers];
        for (int index = 0; index < players.length; index++) {
            // Create player label
            playerLabels[index] = new Label(players[index].getName());
            playerSymbolsBoxes[index] = new VBox(10);
            // Create a ToggleGroup for radio buttons to ensure exclusive selection
            playerSymbolToggleGroups[index] = new ToggleGroup();
            // Create radio buttons for game type selection
            playerSRadioButtons[index] = new RadioButton(Player.Symbol.S.name());
            playerSRadioButtons[index].setSelected(true);
            playerSRadioButtons[index].setUserData(Player.Symbol.S);
            playerORadioButtons[index] = new RadioButton(Player.Symbol.O.name());
            playerORadioButtons[index].setUserData(Player.Symbol.O);
            // Add radio buttons to the ToggleGroup
            playerSRadioButtons[index].setToggleGroup(playerSymbolToggleGroups[index]);
            playerORadioButtons[index].setToggleGroup(playerSymbolToggleGroups[index]);

            // Add on click listeners
            int finalIndex = index;
            playerSymbolToggleGroups[index].selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
                public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {

                    if (playerSymbolToggleGroups[finalIndex].getSelectedToggle() != null) {
                        try {
                            Player.Symbol selectedSymbol = Player.Symbol.valueOf(new_toggle.getUserData().toString());
                            game.getPlayers()[finalIndex].setCurrentSymbol(selectedSymbol);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            });

            // Add to VBox
            playerSymbolsBoxes[index].getChildren().addAll(playerLabels[index], playerSRadioButtons[index], playerORadioButtons[index]);
        }
        rootBorderPane.setLeft(playerSymbolsBoxes[0]);
        rootBorderPane.setRight(playerSymbolsBoxes[1]);
    }
    private void setFooter() {
        footerHBox = new HBox(50);
        footerHBox.setAlignment(Pos.CENTER);
        // Show the current player name
        Label currentPlayer = new Label("Current turn: " + game.getCurrentPlayer().getName());

        // Game control buttons to start and stop the game
        VBox gameControlButtonsVBox = new VBox(10);
        gameControlButtonsVBox.setAlignment(Pos.CENTER);
        startGameButton = new Button("Start Game");
        cancelGameButton = new Button("Cancel Game");

        // Create an EventHandler to handle the button click event
        EventHandler<ActionEvent> startGameButtonClickHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Code to be executed when the button is clicked
                game.setGameStarted(true);
            }
        };
        startGameButton.setOnAction(startGameButtonClickHandler);
        EventHandler<ActionEvent> cancelGameButtonClickHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Code to be executed when the button is clicked
                game.setGameStarted(false);
                game.resetGame();
                setBoard();
            }
        };
        cancelGameButton.setOnAction(cancelGameButtonClickHandler);

        gameControlButtonsVBox.getChildren().addAll(startGameButton, cancelGameButton);
        footerHBox.getChildren().addAll(currentPlayer, gameControlButtonsVBox);

        // Add to root border pane bottom
        rootBorderPane.setBottom(footerHBox);
    }
    private void setBoard() {
        int size = game.getBoardSize();
        gameBoard = new GridPane();
        gameBoard.setAlignment(Pos.CENTER);
        // Check that the board size is between 3 & 12, inclusive
        if (size >= Game.MIN_BOARD_SIZE && size <= Game.MAX_BOARD_SIZE) {
            // Create an array of TextFields for the game board cells
            TextField[] cellTextFields = new TextField[size * size];
            for (int i = 0; i < size * size; i++) {
                TextField cell = new TextField();
                // Display a single column
                cell.setPrefColumnCount(1);
                // Make the TextField read-only
                cell.setEditable(false);
                cell.setAlignment(Pos.CENTER);
                // Add on click listener to set the cell symbol
                int finalI = i;
                cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        // HANDLE IN GAME LOGIC
                        if (game.isGameStarted() && game.makeMove(finalI)) {
                            // Update the game board state and change the current player
                            game.setCellState(finalI);
                            game.changeCurrentPlayer();
                            // Update board and current player by calling these methods
                            setBoard();
                            setFooter();
                        }
                    }
                });
                // Set the cells corresponding value from the game board state
                cell.setText(game.getCellState(i));
                cellTextFields[i] = cell;
                gameBoard.add(cell, i % size, i / size);
            }
        }
        // Create a BorderPane to add padding around the HBox
        rootBorderPane.setCenter(gameBoard);
    }
    @Override
    public void start(Stage primaryStage) {
        // Create a scene with the rootBorderPane as the root node
        scene = new Scene(rootBorderPane, 720, 560);
        // Set the scene for the primary stage
        primaryStage.setScene(scene);
        // Set the stage title
        primaryStage.setTitle("SOS Game");
        // Show the stage
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
