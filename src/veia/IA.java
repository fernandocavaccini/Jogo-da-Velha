package veia;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

public class IA {

	public class InterfaceGame implements ActionListener {

	    static int      moveCount = 0;
	    static JFrame   BoardFrame;
	    static JButton  board[][] = new JButton[3][3];
	    static int      IA = 0;               
	    static int      PLAYER = 1;

	    List<Move> freeMoves;
	    List<Classification> childNode;
	    Move computer;
	    Classification classification;

	    private void initialize() {
	        BoardFrame = new JFrame();
	        BoardFrame.setResizable(false);
	        BoardFrame.setBounds(100, 100, 400, 400);
	        BoardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        BoardFrame.getContentPane().setLayout(null);
	        BoardFrame.getContentPane().setBackground(Color.black);

	        for (int i = 0; i < 3; i++) {
	            for (int j = 0; j < 3; j++) {
	                board[i][j] = new JButton("");
	                BoardFrame.getContentPane().add(board[i][j]);
	                board[i][j].addActionListener(this);
	            }
	        }

	        board[0][0].setBounds(38, 43, 103, 91);
	        board[0][1].setBounds(151, 43, 103, 91);
	        board[0][2].setBounds(264, 43, 103, 91);
	        board[1][0].setBounds(38, 145, 103, 91);
	        board[1][1].setBounds(151, 145, 103, 91);
	        board[1][2].setBounds(264, 145, 103, 91);
	        board[2][0].setBounds(38, 247, 103, 91);
	        board[2][1].setBounds(151, 247, 103, 91);
	        board[2][2].setBounds(264, 247, 103, 91);
	        
	        BoardFrame.setVisible(true);
	    }
	    
	    public InterfaceGame() {
	        initialize();
	    }
	    
	    private void newGame() {
	        for (int i = 0; i < 3; i++) {
	            for (int j = 0; j < 3; j++) {
	                board[i][j].setText("");
	                board[i][j].setEnabled(true);
	                moveCount = 0;
	            }
	        }
	        System.out.println("-------- NovoJogo --------");
	    }

	    private boolean losses() {
	        if (board[0][0].getText().equals("O") && board[0][1].getText().equals("O") && board[0][2].getText().equals("O")
	                || board[1][0].getText().equals("O") && board[1][1].getText().equals("O") && board[1][2].getText().equals("O")
	                || board[2][0].getText().equals("O") && board[2][1].getText().equals("O") && board[2][2].getText().equals("O")
	                || board[0][0].getText().equals("O") && board[1][0].getText().equals("O") && board[2][0].getText().equals("O")
	                || board[0][1].getText().equals("O") && board[1][1].getText().equals("O") && board[2][1].getText().equals("O")
	                || board[0][2].getText().equals("O") && board[1][2].getText().equals("O") && board[2][2].getText().equals("O")
	                || board[0][0].getText().equals("O") && board[1][1].getText().equals("O") && board[2][2].getText().equals("O")
	                || board[0][2].getText().equals("O") && board[1][1].getText().equals("O") && board[2][0].getText().equals("O")) {

	            return (true);
	        }
	        return false;
	    }

	    private boolean wins() {
	        if (board[0][0].getText().equals("X") && board[0][1].getText().equals("X") && board[0][2].getText().equals("X")
	                || board[1][0].getText().equals("X") && board[1][1].getText().equals("X") && board[1][2].getText().equals("X")
	                || board[2][0].getText().equals("X") && board[2][1].getText().equals("X") && board[2][2].getText().equals("X")
	                || board[0][0].getText().equals("X") && board[1][0].getText().equals("X") && board[2][0].getText().equals("X")
	                || board[0][1].getText().equals("X") && board[1][1].getText().equals("X") && board[2][1].getText().equals("X")
	                || board[0][2].getText().equals("X") && board[1][2].getText().equals("X") && board[2][2].getText().equals("X")
	                || board[0][0].getText().equals("X") && board[1][1].getText().equals("X") && board[2][2].getText().equals("X")
	                || board[0][2].getText().equals("X") && board[1][1].getText().equals("X") && board[2][0].getText().equals("X")) {

	            return true;
	        }
	        return false;
	    }

	    private boolean draws() {
	        if (moveCount == 9) {
	            return true;
	        }
	        return false;
	    }
	    
	    private boolean checkGameCondition() {
	        if (wins()) {
	            System.out.println("VOCÊ GANHOU");
	            newGame();
	            return true;
	            
	        } else if (losses()) { 
	            System.out.println("VOCÊ PERDEU");
	            newGame();
	            return true;
	            
	        } else if (draws()) {
	            System.out.println("Empate");
	            newGame();
	            return true;
	            
	        }

	        return false;
	    }

	    List<Move> getAvailableMoves() {
	        freeMoves = new ArrayList<>();
	        for (int i = 0; i < 3; i++) {
	            for (int j = 0; j < 3; j++) {
	                if (board[i][j].getText().equals("")) {
	                    freeMoves.add(new Move(i, j));
	                }
	            }
	        }

	        return freeMoves;
	    }

	    void makePlayComputer(Move move) {
	        board[move.i][move.j].setText("O");
	        board[move.i][move.j].setEnabled(false);
	        moveCount += 1;
	    }

	  
	    void makePlayComputerFalse(Move move) {
	        board[move.i][move.j].setText("O");
	    }

	    void makePlayPersonFalse(Move move) {
	        board[move.i][move.j].setText("X");
	    }

	    Move returnBestplay() {
	        int max = Integer.MIN_VALUE;
	        int best = -1;

	        for (int i = 0; i < childNode.size(); i++) {
	            if (max < childNode.get(i).score) {
	            	max = childNode.get(i).score;
	            	best = i;
	            }
	        }

	        return childNode.get(best).move;
	    }

	    public int returnMinimum(List<Integer> points) {
	        int min = Integer.MAX_VALUE;
	        int position = -1;

	        for (int i = 0; i < points.size(); i++) {
	            if (points.get(i) < min) {
	            	min = points.get(i);
	            	position = i;
	            }
	        }

	        return points.get(position);
	    }
	   
	    public int returnMaximum(List<Integer> points) {
	        int max = Integer.MIN_VALUE;
	        int position = -1;

	        for (int i = 0; i < points.size(); i++) {
	            if (points.get(i) > max) {
	            	max = points.get(i);
	            	position = i;
	            }
	        }

	        return points.get(position);
	    }

	    public int minimax(int depth, int turn) {
	       
	        if (wins()) {
	            return -1;
	        }else if (losses()) {
	            return +1;
	        }
	        
	        List<Move> moves = getAvailableMoves();
	        if (moves.isEmpty()) {
	            return 0;
	        }
	        List<Integer> score = new ArrayList<>();

	        for (int i = 0; i < moves.size(); i++) {
	        	Move currentPlay = moves.get(i);
	            
	            if (turn == IA) {
	                makePlayComputerFalse(currentPlay);
	                int currentScore = minimax(depth + 1, PLAYER);
	                score.add(currentScore);

	                if (depth == 0) {
	                    childNode.add(new Classification(currentScore, currentPlay));
	                }
	                
	            }else if (turn == PLAYER) {
	            	makePlayPersonFalse(currentPlay);
	                score.add((minimax(depth + 1, IA)));
	            }
	            board[currentPlay.i][currentPlay.j].setText("");
	        }

	        return turn == IA ? returnMaximum(score) : returnMinimum(score);
	    }

	    @Override
	    public void actionPerformed(ActionEvent e) {
	    
	        for (int i = 0; i < 3; i++) {
	            for (int j = 0; j < 3; j++) {
	                if (e.getSource() == board[i][j]) {
	                    board[i][j].setText("X");
	                    board[i][j].setEnabled(false);
	                    moveCount += 1;
	                }
	            }
	        }
	        
	        if (checkGameCondition()) {
	            return;
	        }

	        childNode = new ArrayList<>();
	        minimax(0, IA);
	        Move jogadaIA = returnBestplay();
	        makePlayComputer(jogadaIA);


	        childNode.stream().forEach((obj) -> {
	            System.out.println("Jogada: " + obj.move + " Pontuacao: " + obj.score);
	        });
	        
	        checkGameCondition();
	    }
	    
	    
	}
	
	public void interfaceVeia() {
		
		InterfaceGame interfaceGame = new InterfaceGame();
		interfaceGame.initialize();
		
	}
}
