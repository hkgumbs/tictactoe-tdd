package me.hkgumbs.tictactoe.main.java.simulation;

import me.hkgumbs.tictactoe.main.java.board.Board;
import me.hkgumbs.tictactoe.main.java.player.Human;
import me.hkgumbs.tictactoe.main.java.player.Minimax;
import me.hkgumbs.tictactoe.main.java.player.Player;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class PlayersConfiguration implements Configuration {

    private static final String GO_FIRST = "Would you like to go first? (y/n) ";

    private static final String HUMANS_ONLY_KEY = "--humans";
    private static final String MINIMAX_ONLY_KEY = "--minimax";

    private InputStream inputStream;
    private OutputStream outputStream;

    public PlayersConfiguration(
            InputStream inputStream, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    @Override
    public void apply(List<String> args, Simulation simulation)
            throws CannotApplyException {
        Player[] players;
        if (args.contains(MINIMAX_ONLY_KEY)) {
            players = getTwoMinimax();
        } else if (args.contains(HUMANS_ONLY_KEY)) {
            players = getTwoHumans();
        } else {
            players = getHumanAndMinimax();
        }
        simulation.setPlayers(players);
    }

    private Player[] getTwoMinimax() {
        Player x = new Minimax(Board.Mark.X);
        Player o = new Minimax(Board.Mark.O);
        return new Player[]{x, o};
    }

    private Player[] getTwoHumans() {
        Player x = new Human(Board.Mark.X, inputStream, outputStream);
        Player o = new Human(Board.Mark.O, inputStream, outputStream);
        return new Player[]{x, o};
    }

    private Player[] getHumanAndMinimax() {
        Human human = new Human(Board.Mark.X, inputStream, outputStream);
        Player minimax = new Minimax(Board.Mark.O);
        int humanPosition = human.respondYesOrNo(GO_FIRST) ? 0 : 1;
        Player[] players = new Player[2];
        players[humanPosition] = human;
        players[1 - humanPosition] = minimax;
        return players;
    }
}