package board;

public class Board {
	private int rows;
	private int columns;
	private Piece[][] pieces;
	
	public Board(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}
	
	public Piece getPiece(int row, int column) {
		if(!positionExists(row, column)) {
			throw new BoardException("Posição inválida: " + row + "," + column);
		}
		return pieces[row][column];
	}
	
	public Piece getPiece(Position position) {
		return pieces[position.getRow()][position.getColumn()];
	}
	
	public void placePiece(Piece piece, Position position) {
		if(isPlaced(position)) {
			throw new BoardException("Posição ocupada: " + position.getRow() + "," + position.getColumn());
		}
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}
	
	public Piece removePiece(Position position) {
		if(!positionExists(position)) {
			throw new BoardException("Posição inválida: " + position.getRow() + "," + position.getColumn());
		}
		if(getPiece(position) == null) {
			return null;
		}
		
		Piece temp = getPiece(position);
		temp.position = null;
		pieces[position.getRow()][position.getColumn()] = null;
		return temp;
	}
	
	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}
	
	private boolean positionExists(int row, int column) {
		return row >= 0 && row < rows && column >= 0 && column < columns;
	}
	
	public boolean isPlaced(Position position) {
		if(!positionExists(position.getRow(), position.getColumn())) {
			throw new BoardException("Posição inválida: " + position.getRow() + "," + position.getColumn());
		}
		return getPiece(position) != null;
	}
}
