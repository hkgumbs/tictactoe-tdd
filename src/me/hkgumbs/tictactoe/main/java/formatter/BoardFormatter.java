package me.hkgumbs.tictactoe.main.java.formatter;

import me.hkgumbs.tictactoe.main.java.board.Board;

public interface BoardFormatter {

    String print(Board board);

    void setPadding(int padding);
}
