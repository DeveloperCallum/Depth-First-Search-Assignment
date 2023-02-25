package com.callum.squareboard;

public class Evaluate {
    private int size1D; //Renamed size -> size1D to match play class variable *Improving readability*
    private int tilesToWin;
    private int maxLevels;
    private char[][] gameBoard;

    private boolean compHasWon;
    private boolean humanHasWon;

    //Renamed size -> size1D to match play class variable *Improving readability*
    public Evaluate(int size1D, int tilesToWin, int maxLevels) {
        this.size1D = size1D;
        this.tilesToWin = (tilesToWin - 1);
        this.maxLevels = maxLevels;

        System.out.println("Needed to win: " + (this.tilesToWin + 1));

        int spaces = size1D * size1D;
        gameBoard = new char[size1D][];

        for (int row = 0; row < size1D; row++) {
            gameBoard[row] = new char[size1D];

            for (int col = 0; col < size1D; col++) {
                gameBoard[row][col] = 'e';
            }
        }
    }

    /**
     * //Creates empty directory
     * @return the directory that was created
     */
    public Dictionary createDictionary() {
        return new Dictionary(size1D);
    }

    /**
     * Check if a state was repeated
     * @param dict the Dictionary( that to check
     * @return the record that was stored, null if non.
     */
    public Record repeatedState(Dictionary dict) {
        return dict.get(getKey());
    }

    /**
     * Insert a state to the dictionary
     * @param dict The dictionary to update
     * @param score the score to add
     * @param level the level to add.
     */
    public void insertState(Dictionary dict, int score, int level) {
        dict.put(new Record(getKey(), score, level));
    }

    /**
     * Update the current game
     * @param row row changed
     * @param col column changed
     * @param symbol what it was changed to, c or h
     */
    public void storePlay(int row, int col, char symbol) {
        if (symbol != 'c' && symbol != 'h' && symbol != 'e' && symbol != 'u') {
            return;
        }

        gameBoard[row][col] = symbol;
    }

    /**
     * If the square is empty
     * @param row the row of the square
     * @param col the column of the square
     * @return true, if empty else false.
     */
    public boolean squareIsEmpty(int row, int col) {
        return gameBoard[row][col] == 'e';
    }

    /**
     * If the square is the computers
     * @param row the row of the square
     * @param col the column of the square
     * @return true, if computers square else false.
     */
    public boolean tileOfComputer(int row, int col) {
        return gameBoard[row][col] == 'c';
    }

    /**
     * If the square is the human
     * @param row the row of the square
     * @param col the column of the square
     * @return true, if human square else false.
     */
    public boolean tileOfHuman(int row, int col) {
        return gameBoard[row][col] == 'h';
    }

    /**
     * Check if the game has been won
     * @param symbol play who has won.
     * @return true, if symbol has won.
     */
    public boolean wins(char symbol) {
        for (int row = 0; row < size1D; row++) {
            for (int col = 0; col < size1D; col++) {

                char current = gameBoard[row][col];

                if (current == symbol) {
                    if (checkWin(row, col, symbol)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * checks if the  game is a draw.
     * @return true if the game is a draw
     */
    public boolean isDraw() {
        return !hasEmptySpaces();
    }

    /**
     * checks if the game has empty spaces
     * @return true if empty spaces are present.
     */
    private boolean hasEmptySpaces() {
        for (int row = 0; row < size1D; row++) {
            for (int col = 0; col < size1D; col++) {

                char current = gameBoard[row][col];

                if (current == 'e') {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Evaluate the current game state.
     * @return int representing the game state.
     */
    public int evalBoard() {
        if (wins('h')) {
            return 0;
        }

        if (wins('c')) {
            return 3;
        }

        if (isDraw()) {
            return 2;
        }

        return 1;
    }

    /**
     * Check if the player has won at pos
     * @param y row pos
     * @param x col pos
     * @param symbol char if this char has won.
     * @return true if symbol has won
     */
    protected boolean checkWin(int y, int x, char symbol) {
        int topX = 0;
        int bottomX = 0;
        int rightX = 0;
        int leftX = 0;
        int topRightX = 0;
        int bottomRightX = 0;
        int topLeftX = 0;
        int bottomLeftX = 0;

        for (int i = 1; i <= this.tilesToWin; i++) {
            int minY = y - i;
            int maxY = y + i;
            int minX = x - i;
            int maxX = x + i;

            boolean validMinY = minY >= 0;
            boolean validMaxY = maxY <= size1D - 1;
            boolean validMinX = minX >= 0;
            boolean validMaxX = maxX <= size1D - 1;

            //Don't Check TOP
            if (validMinY) {
                char top = gameBoard[minY][x];

                if (top == symbol) {
                    topX++;
                }
            }

            if (validMaxY) {
                char bottom = gameBoard[maxY][x];

                if (bottom == symbol) {
                    bottomX++;
                }
            }

            if (validMaxX) {
                char right = gameBoard[y][maxX];

                if (right == symbol) {
                    rightX++;
                }
            }

            if (validMinX) {
                char left = gameBoard[y][minX];

                if (left == symbol) {
                    leftX++;
                }
            }

            if (validMinY && validMaxX) {
                char topRight = gameBoard[minY][maxX];

                if (topRight == symbol) {
                    topRightX++;
                }
            }

            if (validMaxY && validMaxX) {
                char bottomRight = gameBoard[maxY][maxX];

                if (bottomRight == symbol) {
                    bottomRightX++;
                }
            }

            if (validMinY && validMinX) {
                char topLeft = gameBoard[minY][minX];

                if (topLeft == symbol) {
                    topLeftX++;
                }
            }

            if (validMaxY && validMinX) {
                char bottomLeft = gameBoard[maxY][minX];

                if (bottomLeft == symbol) {
                    bottomLeftX++;
                }
            }
        }

        return ((topX >= tilesToWin) || (bottomX >= tilesToWin) || (rightX >= tilesToWin) || (leftX >= tilesToWin) || (topRightX >= tilesToWin) || (bottomRightX >= tilesToWin) || (topLeftX >= tilesToWin) || (bottomLeftX >= tilesToWin));
    }

    /**
     * Get the key of this state.
     * @return the key
     */
    private String getKey() {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < size1D; i++) {
            char[] c = gameBoard[i];

            for (int j = 0; j < c.length; j++) {
                str.append(c[j]);
            }
        }

        return str.toString();
    }
}
