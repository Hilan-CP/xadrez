package chess;

import board.Board;
import board.Piece;
import board.Position;
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
	
	public boolean[][] possibleMoves(ChessPosition source){
		Position p = source.toPosition();
		validateSourcePosition(p);
		return board.getPiece(p).possibleMoves();
	}
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece capturedPiece = move(source, target);
		return (ChessPiece) capturedPiece;
	}
	
	private void placeChessPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
	}
	
	private void validateSourcePosition(Position position) {
		if(!board.isPlaced(position)) {
			throw new ChessException("Posição inválida");
		}
		if(!board.getPiece(position).isThereAnyPossibleMove()) {
			throw new ChessException("Não existe movimento possível para essa peça");
		}
	}
	
	private void validateTargetPosition(Position source, Position target) {
		if(!board.getPiece(source).possibleMove(target)) {
			throw new ChessException("Destino inválido para a peça escolhida");
		}
	}
	
	private Piece move(Position source, Position target) {
		Piece p = board.removePiece(source);
		Piece captured = board.removePiece(target);
		board.placePiece(p, target);
		return captured;
	}
	
	public void initialSetup() {
		placeChessPiece('a', 8, new Rook(board, Color.BLACK));
		placeChessPiece('h', 8, new Rook(board, Color.BLACK));
		placeChessPiece('e', 8, new King(board, Color.BLACK));
		placeChessPiece('h', 1, new Rook(board, Color.WHITE));
	}
}
