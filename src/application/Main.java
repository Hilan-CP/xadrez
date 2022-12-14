package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Main {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		ChessMatch chess = new ChessMatch();
		List<ChessPiece> captured = new ArrayList<>();
		
		while(!chess.getCheckMate()) {
			try {
				UI.clearScreen();
				UI.printMatch(chess, captured);
				System.out.println();
				System.out.print("Source: ");
				ChessPosition source = UI.readChessPosition(scan);
				
				boolean[][] moves = chess.possibleMoves(source);
				UI.clearScreen();
				UI.printBoard(chess.getPieces(), moves);
				
				System.out.print("Target: ");
				ChessPosition target = UI.readChessPosition(scan);
				ChessPiece capturedPiece = chess.performChessMove(source, target);
				
				if(capturedPiece != null) {
					captured.add(capturedPiece);
				}
				
				if(chess.getPromoted() != null) {
					System.out.print("Escolha uma peca para a promocao (Q/R/H/B): ");
					String type = scan.nextLine().toUpperCase();
					while(!type.equals("Q") && !type.equals("H") && !type.equals("B") && !type.equals("R")) {
						System.out.print("Escolha uma peca para a promocao (Q/R/H/B): ");
						type = scan.nextLine().toUpperCase();
					}
					chess.replacePromotedPiece(type);
				}
			}
			catch(ChessException e) {
				System.out.println(e.getMessage());
				scan.nextLine();
			}
			catch(InputMismatchException e) {
				System.out.println(e.getMessage());
				scan.nextLine();
			}
		}
		scan.close();
		UI.clearScreen();
		UI.printMatch(chess, captured);
	}
}