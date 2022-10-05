package chess;

import board.Board;
import chess.pieces.*;

public class ChessMatch {
	private Board board;
	
	public ChessMatch() {
		board = new Board(8, 8);
		initialSetup();
	}
	
	public ChessPiece[][] getPieces(){
		ChessPiece[][] pieces = new ChessPiece[board.getRows()][board.getColumns()];
		for(int i = 0; i < board.getRows(); ++i) {
			for(int j = 0; j < board.getColumns(); ++j) {
				pieces[i][j] = (ChessPiece) board.getPiece(i, j);
			}
		}
		return pieces;
	}
	
	private void placeChessPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
	}
	
	public void initialSetup() {
		placeChessPiece('a', 8, new Rook(board, Color.BLACK));
		placeChessPiece('e', 8, new King(board, Color.BLACK));
	}
}
