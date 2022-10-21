package chess.pieces;

import board.Board;
import board.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

	public Pawn(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "P";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] m = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position p = new Position(0,0);
		
		if(getColor() == Color.WHITE) {
			p.setValues(position.getRow() - 1, position.getColumn());
			if(getBoard().positionExists(p) && !getBoard().isPlaced(p)) {
				m[p.getRow()][p.getColumn()] = true;
				
				//Verificar primeiro movimento do peão
				p.setValues(position.getRow() - 2, position.getColumn());
				if(getBoard().positionExists(p) && !getBoard().isPlaced(p) && getMoveCount() == 0) {
					m[p.getRow()][p.getColumn()] = true;
				}
			}
			
			//captura
			p.setValues(position.getRow() - 1, position.getColumn() - 1);
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				m[p.getRow()][p.getColumn()] = true;
			}
			
			p.setValues(position.getRow() - 1, position.getColumn() + 1);
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				m[p.getRow()][p.getColumn()] = true;
			}
		}
		else {
			p.setValues(position.getRow() + 1, position.getColumn());
			if(getBoard().positionExists(p) && !getBoard().isPlaced(p)) {
				m[p.getRow()][p.getColumn()] = true;
				
				//Verificar primeiro movimento do peão
				p.setValues(position.getRow() + 2, position.getColumn());
				if(getBoard().positionExists(p) && !getBoard().isPlaced(p) && getMoveCount() == 0) {
					m[p.getRow()][p.getColumn()] = true;
				}
			}
			
			//captura
			p.setValues(position.getRow() + 1, position.getColumn() - 1);
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				m[p.getRow()][p.getColumn()] = true;
			}
			
			p.setValues(position.getRow() + 1, position.getColumn() + 1);
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				m[p.getRow()][p.getColumn()] = true;
			}
		}
		return m;
	}
}
