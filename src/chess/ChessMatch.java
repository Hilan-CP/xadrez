package chess;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import board.Piece;
import board.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {
	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;
	private boolean checkMate;
	
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
	
	public boolean getCheckMate() {
		return checkMate;
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
		
		if(isCheckMate(opponent(currentPlayer))) {
			checkMate = true;
		}
		else {
			nextTurn();
		}
		
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
		ChessPiece p = (ChessPiece) board.removePiece(source);
		p.increaseMoveCount();
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		
		if(capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		
		//right castling
		if(p instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceRook = new Position(source.getRow(), source.getColumn() + 3);
			Position targetRook = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece) board.removePiece(sourceRook);
			board.placePiece(rook, targetRook);
			rook.increaseMoveCount();
		}
		
		//left castling
		if(p instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceRook = new Position(source.getRow(), source.getColumn() - 4);
			Position targetRook = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece) board.removePiece(sourceRook);
			board.placePiece(rook, targetRook);
			rook.increaseMoveCount();
		}
		
		return capturedPiece;
	}
	
	private void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece p = (ChessPiece) board.removePiece(target);
		p.decreaseMoveCount();
		board.placePiece(p, source);
		if(capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
		
		//right castling
		if(p instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceRook = new Position(source.getRow(), source.getColumn() + 3);
			Position targetRook = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece) board.removePiece(targetRook);
			board.placePiece(rook, sourceRook);
			rook.decreaseMoveCount();
		}
		
		//left castling
		if(p instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceRook = new Position(source.getRow(), source.getColumn() - 4);
			Position targetRook = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece) board.removePiece(targetRook);
			board.placePiece(rook, sourceRook);
			rook.decreaseMoveCount();
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
	
	private boolean isCheckMate(Color color) {
		if(!isChecked(color)) {
			return false;
		}
		List<Piece> list = piecesOnTheBoard.stream().filter(piece -> ((ChessPiece)piece).getColor() == color).toList();
		for(Piece p : list) {
			boolean[][] m = p.possibleMoves();
			for(int i = 0; i < board.getRows(); ++i) {
				for(int j = 0; j < board.getColumns(); ++j) {
					if(m[i][j]) {
						Position source = ((ChessPiece)p).getChessPosition().toPosition();
						Position target = new Position(i,j);
						Piece capturedPiece = move(source, target);
						boolean testCheck = isChecked(color);
						undoMove(source, target, capturedPiece);
						if(!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	public void initialSetup() {
		placeChessPiece('a', 7, new Pawn(board, Color.BLACK));
		placeChessPiece('b', 7, new Pawn(board, Color.BLACK));
		placeChessPiece('c', 7, new Pawn(board, Color.BLACK));
		placeChessPiece('d', 7, new Pawn(board, Color.BLACK));
		placeChessPiece('e', 7, new Pawn(board, Color.BLACK));
		placeChessPiece('f', 7, new Pawn(board, Color.BLACK));
		placeChessPiece('g', 7, new Pawn(board, Color.BLACK));
		placeChessPiece('h', 7, new Pawn(board, Color.BLACK));
		placeChessPiece('c', 8, new Bishop(board, Color.BLACK));
		placeChessPiece('f', 8, new Bishop(board, Color.BLACK));
		placeChessPiece('b', 8, new Knight(board, Color.BLACK));
		placeChessPiece('g', 8, new Knight(board, Color.BLACK));
		placeChessPiece('a', 8, new Rook(board, Color.BLACK));
		placeChessPiece('h', 8, new Rook(board, Color.BLACK));
		placeChessPiece('d', 8, new Queen(board, Color.BLACK));
		placeChessPiece('e', 8, new King(board, Color.BLACK, this));
		
		placeChessPiece('a', 2, new Pawn(board, Color.WHITE));
		placeChessPiece('b', 2, new Pawn(board, Color.WHITE));
		placeChessPiece('c', 2, new Pawn(board, Color.WHITE));
		placeChessPiece('d', 2, new Pawn(board, Color.WHITE));
		placeChessPiece('e', 2, new Pawn(board, Color.WHITE));
		placeChessPiece('f', 2, new Pawn(board, Color.WHITE));
		placeChessPiece('g', 2, new Pawn(board, Color.WHITE));
		placeChessPiece('h', 2, new Pawn(board, Color.WHITE));
		placeChessPiece('c', 1, new Bishop(board, Color.WHITE));
		placeChessPiece('f', 1, new Bishop(board, Color.WHITE));
		placeChessPiece('b', 1, new Knight(board, Color.WHITE));
		placeChessPiece('g', 1, new Knight(board, Color.WHITE));
		placeChessPiece('a', 1, new Rook(board, Color.WHITE));
		placeChessPiece('h', 1, new Rook(board, Color.WHITE));
		placeChessPiece('d', 1, new Queen(board, Color.WHITE));
		placeChessPiece('e', 1, new King(board, Color.WHITE, this));
	}
}
