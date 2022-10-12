package chess.pieces;

import board.Board;
import board.Position;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece {

	public Rook(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "R";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] m = new boolean[getBoard().getRows()][getBoard().getColumns()];
	
		//above
		Position p = new Position(position.getRow() - 1, position.getColumn());
		while(getBoard().positionExists(p) && !getBoard().isPlaced(p)) {
			m[p.getRow()][p.getColumn()] = true;
			p.setRow(p.getRow() - 1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			m[p.getRow()][p.getColumn()] = true;
		}
		
		//below
		p = new Position(position.getRow() + 1, position.getColumn());
		while(getBoard().positionExists(p) && !getBoard().isPlaced(p)) {
			m[p.getRow()][p.getColumn()] = true;
			p.setRow(p.getRow() + 1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			m[p.getRow()][p.getColumn()] = true;
		}
		
		//left
		p = new Position(position.getRow(), position.getColumn() - 1);
		while(getBoard().positionExists(p) && !getBoard().isPlaced(p)) {
			m[p.getRow()][p.getColumn()] = true;
			p.setColumn(p.getColumn() - 1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			m[p.getRow()][p.getColumn()] = true;
		}
		
		//right
		p = new Position(position.getRow(), position.getColumn() + 1);
		while(getBoard().positionExists(p) && !getBoard().isPlaced(p)) {
			m[p.getRow()][p.getColumn()] = true;
			p.setColumn(p.getColumn() + 1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			m[p.getRow()][p.getColumn()] = true;
		}
		
		return m;
	}
}
