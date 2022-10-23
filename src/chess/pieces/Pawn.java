package chess.pieces;

import board.Board;
import board.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {
	private ChessMatch chess;

	public Pawn(Board board, Color color, ChessMatch chess) {
		super(board, color);
		this.chess = chess;
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
			
			//En Passant White
			if(position.getRow() == 3) {
				Position left = new Position(position.getRow(), position.getColumn() - 1);
				if(getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().getPiece(left) == chess.getEnPassantVulnerable()) {
					m[left.getRow() - 1][left.getColumn()] = true;
				}
				Position right = new Position(position.getRow(), position.getColumn() + 1);
				if(getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().getPiece(right) == chess.getEnPassantVulnerable()) {
					m[right.getRow() - 1][right.getColumn()] = true;
				}
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
			
			//En Passant Black
			if(position.getRow() == 4) {
				Position left = new Position(position.getRow(), position.getColumn() - 1);
				if(getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().getPiece(left) == chess.getEnPassantVulnerable()) {
					m[left.getRow() + 1][left.getColumn()] = true;
				}
				Position right = new Position(position.getRow(), position.getColumn() + 1);
				if(getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().getPiece(right) == chess.getEnPassantVulnerable()) {
					m[right.getRow() + 1][right.getColumn()] = true;
				}
			}
		}
		return m;
	}
}
