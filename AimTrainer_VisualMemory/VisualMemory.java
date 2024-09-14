import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.TimerTask;
import java.util.Timer;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class VisualMemory {     // Instance Variables
    private int level, selectedCounter, lives, cycleCount, boxLength, goodGuess, strikes;
    private boolean twoCycle, canPlay;
    private final Color deSelCol = new Color(37, 115, 193), Col = new Color(255, 255, 255),wrongCol = new Color(21, 67, 104);
    private GameBox[][] boxes;
    private JFrame frame;
    private JPanel mainframe;
    private JLabel levels, lifes;

    public VisualMemory(JFrame frame) {
        this.frame = frame;
        frame.getContentPane().setBackground(new Color(43, 135, 209));
        frame.setTitle("Visual Memory Game");
        mainframe = new JPanel(new GridBagLayout()); // panel for the buttons w/ gridlayout
        mainframe.setBounds(150, 100, 500, 500);

        selectedCounter = 3; // boxes Selected / round
        boxLength = 3; // Number of boxes in round
        level = 1; // Level of round
        lives = 3; // Lives Left
        twoCycle = true; // Cycles for # of boxes calc
        cycleCount = 0; // Keeps track when cycle's switch
        goodGuess = 0; // Counts the correct guesses
        strikes = 0; // Number of strikes
        canPlay = false; // Checks if player is able to play

        lifes = new JLabel("Lives: " + lives);
        lifes.setBounds(0, 10, 400, 30);
        lifes.setForeground(Color.WHITE);
        lifes.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        lifes.setHorizontalAlignment(SwingConstants.CENTER);
        lifes.setVerticalAlignment(SwingConstants.CENTER);
        frame.add(lifes);

        levels = new JLabel("Level: " + level);
        levels.setBounds(400, 10, 400, 30);
        levels.setForeground(Color.WHITE);
        levels.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        levels.setHorizontalAlignment(SwingConstants.CENTER);
        levels.setVerticalAlignment(SwingConstants.CENTER);
        frame.add(levels);

        startLevel(); // Initializes The Game
    }

    public void startLevel() {
        if (lives > 0) { // Round Loop
            canPlay = false; // sets up the game before allowing player to play
            boxes = new GameBox[boxLength][boxLength];

            GridBagConstraints constraints = new GridBagConstraints();
            constraints.insets = new Insets(10, 10, 10, 10);
            constraints.weightx = 1.0;
            constraints.weighty = 1.0;
            constraints.fill = GridBagConstraints.BOTH;
            
            for (int i = 0; i < boxLength; i++) { // Populates Boxes & uses GridBagConstraints
                for (int j = 0; j < boxLength; j++) {
                    boxes[i][j] = new GameBox();
                    int x = i;
                    int y = j;
                    constraints.gridx = j;
                    constraints.gridy = i;

                    mainframe.add(boxes[i][j], constraints);

                    boxes[i][j].addMouseListener(new java.awt.event.MouseAdapter() { // Click Event
                        public void mousePressed(java.awt.event.MouseEvent evt) {
                            if (canPlay && !boxes[x][y].hasBeenGuessed()) { // if it's not been guessed already
                                if (boxes[x][y].isSelected()) {
                                    goodGuess++;
                                    boxes[x][y].setGuessed();
                                    boxes[x][y].setBackground(Col);
                                    if (goodGuess == selectedCounter) // If all have been found
                                        nextTurn(boxes);
                                } else {
                                    strikes++;
                                    boxes[x][y].setGuessed();
                                    boxes[x][y].setBackground(wrongCol);
                                    if (strikes == 3) { // If you get 3 wrong subs lives
                                        lives--;
                                        lifes.setText("Lives: " + lives);
                                        nextTurn(boxes);
                                    }
                                }
                            }
                        }
                    });
                }
            }

            frame.add(mainframe);

            for (int l = 0; l < selectedCounter; l++) { // Randomly Selects boxes
                int x = (int) ((Math.random() * boxLength));
                int y = (int) ((Math.random() * boxLength));
                if (boxes[x][y].isSelected()) // Makes sure no duplicates exist
                    l--;
                else {
                    boxes[x][y].setSelected(true);
                }
            }

            new Timer().schedule(new TimerTask() { // Waits 3 seconds for player to remember buttons
                @Override
                public void run() {
                    for (int w = 0; w < boxLength; w++) { // Re-hides the buttons
                        for (int g = 0; g < boxLength; g++) {
                            boxes[w][g].setBackground(deSelCol);
                        }
                    }
                    canPlay = true; // allows player to play the game
                }

            }, 3000);
        } else { // When Lives is less than 3 (Game End)
            mainframe.setVisible(false);

            JLabel GameEndScreen = new JLabel("Game Over, you got to level " + level + "!");
            GameEndScreen.setBounds(0, 150, 800, 50);
            GameEndScreen.setFont(new Font("Times New Roman", Font.BOLD, 30));
            GameEndScreen.setHorizontalAlignment(SwingConstants.CENTER);
            GameEndScreen.setVerticalAlignment(SwingConstants.CENTER);
            GameEndScreen.setForeground(Color.WHITE);

            JButton back = new JButton("Back");
            back.setBounds(200, 400, 400, 50);
            back.setFont(new Font("Times New Roman", Font.BOLD, 30));
            back.setBackground(Color.white);
            back.setFocusPainted(false);
            
            frame.add(GameEndScreen);
            frame.add(back);
            frame.getContentPane().setBackground(Color.BLACK);
            levels.setVisible(false);
            lifes.setVisible(false);

            back.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    frame.dispose(); // Closes The Current Tab
                    new Homepage(); // Makes a new Instance
                }
            });
        }
    }

    public void nextTurn(GameBox[][] boxes) { // Changes variables and restarts the turn
        if (strikes != 3) {
            level++;
            selectedCounter++;
            cycleCount++;

            if (twoCycle) { // If 2 cycle it continues
                if (cycleCount == 2) {
                    twoCycle = false;
                    cycleCount = 0;
                    boxLength++;
                }
            } else { // If 4 cycle it continues
                if (cycleCount == 4) {
                    twoCycle = true;
                    cycleCount = 0;
                    boxLength++;
                }
            }
        }

        for (int i = 0; i < boxes.length; i++)
            for (int j = 0; j < boxes[i].length; j++)
                boxes[i][j].setVisible(false);

        strikes = 0;
        goodGuess = 0;
        levels.setText("Level: " + level);
        startLevel();

    }
}

class GameBox extends JButton {
    private boolean justGuessed;
    private boolean selected;

    public GameBox() {
        new JButton();
        setFocusPainted(false);
        setBackground(new Color(37, 115, 193));
    }

    // accessors
    public boolean isSelected() {
        return selected;
    }

    public boolean hasBeenGuessed() {
        return justGuessed;
    }

    // mutators
    public void setSelected(boolean selected) {
        this.selected = selected;
        setBackground(new Color(255, 255, 255)); 
    }

    public void setGuessed() {
        justGuessed = true;
    }
}