package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Main {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		ChessMatch chess = new ChessMatch();
		
		while(true) {
			try {
				UI.printBoard(chess.getPieces());
				System.out.println();
				System.out.print("Source: ");
				ChessPosition source = UI.readChessPosition(scan);
				
				boolean[][] moves = chess.possibleMoves(source);
				UI.clearScreen();
				UI.printBoard(chess.getPieces(), moves);
				
				System.out.print("Target: ");
				ChessPosition target = UI.readChessPosition(scan);
				ChessPiece capturedPiece = chess.performChessMove(source, target);
				UI.clearScreen();
			}
			catch(ChessException e) {
				System.out.println(e.getMessage());
				scan.nextLine();
			}
			catch(InputMismatchException e) {
				System.out.println(e.getMessage());
				scan.nextLine();
			}
			finally {
				UI.clearScreen();
			}
		}
	}
}