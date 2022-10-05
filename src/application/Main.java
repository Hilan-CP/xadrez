package application;

import chess.ChessMatch;

public class Main {

	public static void main(String[] args) {
		ChessMatch chess = new ChessMatch();
		UI.printBoard(chess.getPieces());
	}

}
