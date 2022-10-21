package chess.pieces;

import board.Board;
import board.Position;
import chess.ChessPiece;
import chess.Color;

public class Queen extends ChessPiece {

	public Queen(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "Q";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] m = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position p = new Position(0,0);
		
		//above
		p.setValues(position.getRow() - 1, position.getColumn());
		while(getBoard().positionExists(p) && !getBoard().isPlaced(p)) {
			m[p.getRow()][p.getColumn()] = true;
			p.setRow(p.getRow() - 1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			m[p.getRow()][p.getColumn()] = true;
		}
		
		//below
		p.setValues(position.getRow() + 1, position.getColumn());
		while(getBoard().positionExists(p) && !getBoard().isPlaced(p)) {
			m[p.getRow()][p.getColumn()] = true;
			p.setRow(p.getRow() + 1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			m[p.getRow()][p.getColumn()] = true;
		}
		
		//left
		p.setValues(position.getRow(), position.getColumn() - 1);
		while(getBoard().positionExists(p) && !getBoard().isPlaced(p)) {
			m[p.getRow()][p.getColumn()] = true;
			p.setColumn(p.getColumn() - 1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			m[p.getRow()][p.getColumn()] = true;
		}
		
		//right
		p.setValues(position.getRow(), position.getColumn() + 1);
		while(getBoard().positionExists(p) && !getBoard().isPlaced(p)) {
			m[p.getRow()][p.getColumn()] = true;
			p.setColumn(p.getColumn() + 1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			m[p.getRow()][p.getColumn()] = true;
		}
		
		//upper left
		p.setValues(position.getRow() - 1, position.getColumn() - 1);
		while(getBoard().positionExists(p) && !getBoard().isPlaced(p)) {
			m[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() - 1, p.getColumn() - 1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			m[p.getRow()][p.getColumn()] = true;
		}
		
		//upper right
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		while(getBoard().positionExists(p) && !getBoard().isPlaced(p)) {
			m[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() - 1, p.getColumn() + 1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			m[p.getRow()][p.getColumn()] = true;
		}
		
		//lower left
		p.setValues(position.getRow() + 1, position.getColumn() - 1);
		while(getBoard().positionExists(p) && !getBoard().isPlaced(p)) {
			m[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() + 1, p.getColumn() - 1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			m[p.getRow()][p.getColumn()] = true;
		}
		
		//lower right
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
		while(getBoard().positionExists(p) && !getBoard().isPlaced(p)) {
			m[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() + 1, p.getColumn() + 1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			m[p.getRow()][p.getColumn()] = true;
		}
		
		return m;
	}
}
