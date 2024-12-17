package com.techpalle;

import java.util.Random;
import java.util.Scanner;

public class TicTacToeGameProject {

    public static void main(String[] args) {
        TicTacToe t = new TicTacToe();
        t.dispBoard();

        Scanner sc = new Scanner(System.in);
        System.out.println("Select mode: ");
        System.out.println("1. Human vs Human");
        System.out.println("2. Human vs AI");
        int mode = sc.nextInt();

        if (mode == 1) {
            playHumanVsHuman(t);
        } else if (mode == 2) {
            playHumanVsAI(t);
        } else {
            System.out.println("Invalid choice! Exiting.");
        }
    }

    private static void playHumanVsHuman(TicTacToe t) {
        HumanPlayer p1 = new HumanPlayer("Player 1", 'X');
        HumanPlayer p2 = new HumanPlayer("Player 2", 'O');
        Player currentPlayer = p1;

        while (true) {
            System.out.println(currentPlayer.name + "'s turn");
            currentPlayer.makeMove();

            t.dispBoard();

            if (t.checkColWin() || t.checkRowWin() || t.CheckDiagWin()) {
                System.out.println(currentPlayer.name + " has won!");
                break;
            } else if (t.checkDraw()) {
                System.out.println("The game is a draw!");
                break;
            }

            // Switch players
            currentPlayer = (currentPlayer == p1) ? p2 : p1;
        }
    }

    private static void playHumanVsAI(TicTacToe t) {
        HumanPlayer human = new HumanPlayer("Human", 'X');
        AIPlayer ai = new AIPlayer("AI", 'O');
        Player currentPlayer = human;

        while (true) {
            System.out.println(currentPlayer.name + "'s turn");
            currentPlayer.makeMove();

            t.dispBoard();

            if (t.checkColWin() || t.checkRowWin() || t.CheckDiagWin()) {
                System.out.println(currentPlayer.name + " has won!");
                break;
            } else if (t.checkDraw()) {
                System.out.println("The game is a draw!");
                break;
            }

            // Switch players
            currentPlayer = (currentPlayer == human) ? ai : human;
        }
    }
}

class TicTacToe {
    static char[][] board;

    public TicTacToe() {
        board = new char[3][3];
        initBoard();
    }

    void initBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = ' ';
            }
        }
    }

    void dispBoard() {
        System.out.println(" -------------");
        for (int i = 0; i < board.length; i++) {
            System.out.print(" | ");
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println();
            System.out.println(" -------------");
        }
    }

    static void placeMark(int row, int col, char mark) {
        if (row >= 0 && row <= 2 && col >= 0 && col <= 2) {
            board[row][col] = mark;
        } else {
            System.out.println("Invalid position!");
        }
    }

    boolean checkColWin() {
        for (int j = 0; j <= 2; j++) {
            if (board[0][j] != ' ' && board[0][j] == board[1][j] && board[1][j] == board[2][j]) {
                return true;
            }
        }
        return false;
    }

    boolean checkRowWin() {
        for (int i = 0; i <= 2; i++) {
            if (board[i][0] != ' ' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return true;
            }
        }
        return false;
    }

    boolean CheckDiagWin() {
        if (board[0][0] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return true;
        }
        if (board[0][2] != ' ' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return true;
        }
        return false;
    }

    boolean checkDraw() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }
}

abstract class Player {
    String name;
    char mark;

    abstract void makeMove();

    boolean isValidMove(int row, int col) {
        if (row >= 0 && row <= 2 && col >= 0 && col <= 2) {
            if (TicTacToe.board[row][col] == ' ') {
                return true;
            }
        }
        return false;
    }
}

class HumanPlayer extends Player {
    HumanPlayer(String name, char mark) {
        this.name = name;
        this.mark = mark;
    }

    void makeMove() {
        Scanner sc = new Scanner(System.in);
        int row, col;

        do {
            System.out.println("Enter row and column (0, 1, or 2): ");
            row = sc.nextInt();
            col = sc.nextInt();
        } while (!isValidMove(row, col));

        TicTacToe.placeMark(row, col, mark);
    }
}

class AIPlayer extends Player {
    AIPlayer(String name, char mark) {
        this.name = name;
        this.mark = mark;
    }

    void makeMove() {
        Random r = new Random();
        int row, col;

        do {
            row = r.nextInt(3);
            col = r.nextInt(3);
        } while (!isValidMove(row, col));

        TicTacToe.placeMark(row, col, mark);
    }
}
