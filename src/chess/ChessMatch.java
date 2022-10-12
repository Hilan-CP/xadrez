package chess;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import board.Piece;
import board.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
	
	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.WHITE;
		check = false;
		initialSetup();
	}
	
	public int getTurn() {
		return turn;
	}
	
	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	public boolean getCheck() {
		return check;
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
		
		if(isChecked(currentPlayer)) {
			undoMove(source, target, capturedPiece);
			throw new ChessException("Voce nao pode ficar em xeque");
		}
		
		if(isChecked(opponent(currentPlayer))) {
			check = true;
		}
		else {
			check = false;
		}
		
		nextTurn();
		return (ChessPiece) capturedPiece;
	}
	
	private void placeChessPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}
	
	private void validateSourcePosition(Position position) {
		if(!board.isPlaced(position)) {
			throw new ChessException("Posicao invalida");
		}
		if(currentPlayer != ((ChessPiece)board.getPiece(position)).getColor()) {
			throw new ChessException("A peca pertence ao adversario");
		}
		if(!board.getPiece(position).isThereAnyPossibleMove()) {
			throw new ChessException("Nao existe movimento possivel para essa peca");
		}
	}
	
	private void validateTargetPosition(Position source, Position target) {
		if(!board.getPiece(source).possibleMove(target)) {
			throw new ChessException("Destino invalido para a peca escolhida");
		}
	}
	
	private Piece move(Position source, Position target) {
		Piece p = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		if(capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		return capturedPiece;
	}
	
	private void undoMove(Position source, Position target, Piece capturedPiece) {
		Piece p = board.removePiece(target);
		board.placePiece(p, source);
		if(capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
	}
	
	private void nextTurn() {
		++turn;
		if(currentPlayer == Color.WHITE) {
			currentPlayer = Color.BLACK;
		}
		else {
			currentPlayer = Color.WHITE;
		}
	}
	
	private Color opponent(Color color) {
		if(color == Color.WHITE) {
			return Color.BLACK;
		}
		else {
			return Color.WHITE;
		}
	}
	
	private ChessPiece king(Color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(piece -> ((ChessPiece)piece).getColor() == color).toList();
		for(Piece p : list) {
			if(p instanceof King) {
				return (ChessPiece) p;
			}
		}
		throw new IllegalStateException("Nenhum rei " + color + " encontrado");
	}
	
	private boolean isChecked(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(piece -> ((ChessPiece)piece).getColor() == opponent(color)).toList();
		for(Piece p : opponentPieces) {
			boolean[][] m = p.possibleMoves();
			if(m[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
		}
		return false;
	}
	
	public void initialSetup() {
		placeChessPiece('a', 8, new Rook(board, Color.BLACK));
		placeChessPiece('h', 8, new Rook(board, Color.BLACK));
		placeChessPiece('e', 8, new King(board, Color.BLACK));
		placeChessPiece('h', 1, new Rook(board, Color.WHITE));
		placeChessPiece('e', 5, new King(board, Color.WHITE));
	}
}
