import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class NumberMemory {
    private String number;
    private int level, durationInSeconds;
    private boolean isDone, buttonPressed;
    private JLabel answerLabel2, numberLabel2, levelLabel, numberLabel, answerLabel, numsVal;
    private JPanel numberPanel, guessPanel, scorePanel, gameOverPanel;
    private JButton next, submit;
    private JProgressBar progressBar;
    private JTextField numsArea;

    public NumberMemory() {
        buttonPressed = false;
        isDone = false;
        level = 1;
        number = "";
        durationInSeconds = level + 1;

        buildGame();

        waitForButton();

        gameLoop();
    }

    public void gameLoop(){
        while (!isDone) {

            // First; show number w progress bar
            numberScreen(); // Calls next when timer is done

            // Second; allow user to guess
            guessScreen(); // Calls when button is pressed
            waitForButton();

            // Third; score the guess
            scoreScreen();
            waitForButton();

            // Set for next iteration
            level++;
            numsArea.setText("");
        }

        gameEnd(); // GameEnd Screen + try again because you suck 
    }

    public void gameEnd() { // Ugly but it gets the job done 

        gameOverPanel.setVisible(true);

        String guess = numsArea.getText();

        String strikedString = "<html>";

        int size = guess.length(); 

        if (guess.length() > number.length()){
            size = number.length(); 
        } 

        for (int i = 0; i < size; i++) {
            if (guess.charAt(i) != number.charAt(i)) {
                strikedString += "<strike><font color='black'>" + guess.charAt(i) + "</font></strike>";
            } else {
                strikedString += guess.charAt(i);
            }
        }
        
        if (guess.length() > number.length()){
            strikedString += "<strike><font color='black'>" + guess.substring(size) +  "</font></strike>"; 
        } 

        strikedString += "</html>";
        numberLabel2.setText(number);
        answerLabel2.setText(strikedString);
    }

    public void waitForButton() { // Pauses running till buttons are pressed 
        while (!buttonPressed) {
            try {
                Thread.sleep(100); // Sleep for some time before checking again
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        buttonPressed = false;
    }

    public void numberScreen() { // When the timer and number comes up 

        // Update number
        number = createNum();

        System.out.println("Doon't cheat but this it the magic number: " + number);

        numberPanel.setVisible(true);

        numsVal.setText(number);

        durationInSeconds = level + 1; // Calculate the timer length 2 

        int maxProgress = durationInSeconds * 1000; // the size of the progressBar (Ticks)
        progressBar.setMaximum(maxProgress);

        final Timer[] timer = { null };

        timer[0] = new Timer(10, new ActionListener() {

            int timeLeft = durationInSeconds * 1000;

            @Override
            public void actionPerformed(ActionEvent e) {

                timeLeft -= 15;

                progressBar.setValue(timeLeft);

                if (timeLeft <= 0) { // When timer is up
                    timer[0].stop();
                }
            }
        });

        timer[0].start(); // Starts timer

        while (timer[0].isRunning()) {
            try {
                Thread.sleep(10); // Sleep for some time before checking again
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void guessScreen() { // Controls the screens changing 

        numberPanel.setVisible(false);

        guessPanel.setVisible(true);

        numsArea.requestFocus();
    }

    public void scoreScreen() { // After you guess screen pops up 

        // Set visibility
        guessPanel.setVisible(false);

        // Grab your guess
        String guess = numsArea.getText();

        // Set values
        answerLabel.setText(guess);
        numberLabel.setText(number);
        levelLabel.setText("Level " + level);

        if ((number.strip().toLowerCase()).equals(guess.strip().toLowerCase())) {
            isDone = false;
            scorePanel.setVisible(true);

        } else {
            isDone = true;
            gameEnd();
        }
    }

    public String createNum() { // Creates a random number as a string because long breaks after 11 digits
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < level; i++) {
            output.append((int) (Math.random() * 10));
        }

        return output.toString();
    }

    public JFrame buildGame() { // Lots of styling to make it all pretty 
        ArrayList<Component> comps = new ArrayList<>();

        // Homepage
        JFrame window = new JFrame("Number Memory Game");
        window.setSize(1200, 800);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(new Color(43, 135, 209));
        window.setLayout(null);

        // Load Icon
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("assets/icon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JLabel label = new JLabel(new ImageIcon(image));
        label.setBounds((window.getWidth() / 2) - (200 / 2), 100, 200, 200);

        JLabel title = new JLabel("Number Memory");
        title.setFont(new Font("Arial", Font.PLAIN, 75));
        title.setBounds((window.getWidth() / 2) - (800 / 2), 275, 800, 125);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.CENTER);
        title.setForeground(Color.WHITE);

        JLabel sub = new JLabel("The average person can remember 7 numbers at once. Can you do more?");
        sub.setFont(new Font("Arial", Font.PLAIN, 23));
        sub.setBounds((window.getWidth() / 2) - (750 / 2), 380, 750, 50);
        sub.setHorizontalAlignment(SwingConstants.CENTER);
        sub.setVerticalAlignment(SwingConstants.CENTER);
        sub.setForeground(Color.WHITE);

        JButton numGame = new JButton("Start");
        numGame.setFont(new Font("Arial", Font.BOLD, 20));
        numGame.setBounds((window.getWidth() / 2) - (100 / 2), 450, 100, 50);
        numGame.setFocusable(true);
        numGame.setFocusPainted(false);
        numGame.setBackground(new Color(255, 209, 84));
        numGame.setHorizontalAlignment(SwingConstants.CENTER);
        numGame.setVerticalAlignment(SwingConstants.CENTER);
        numGame.setBorder(null);

        // Number Panel

        numberPanel = new JPanel();
        numberPanel.setBounds(0, 0, window.getWidth(), window.getHeight());
        numberPanel.setBackground(new Color(43, 135, 209));
        numberPanel.setLayout(null);

        numsVal = new JLabel();
        numsVal.setFont(new Font("Arial", Font.BOLD, 75));
        numsVal.setBounds((window.getWidth() / 2) - (800 / 2), (window.getWidth() / 2) - (800 / 2), 800, 160);
        numsVal.setHorizontalAlignment(SwingConstants.CENTER);
        numsVal.setVerticalAlignment(SwingConstants.CENTER);
        numsVal.setForeground(Color.WHITE);
        numberPanel.add(numsVal);

        progressBar = new JProgressBar(0);
        progressBar.setStringPainted(false);
        progressBar.setBackground(new Color(34, 108, 187));
        progressBar.setForeground(Color.WHITE);
        progressBar.setBounds((window.getWidth() / 2) - (150 / 2), (window.getWidth() / 2) - (800 / 2) + 150, 150, 10);
        progressBar.setBorderPainted(false);
        numberPanel.add(progressBar);

        numberPanel.setVisible(false);
        window.add(numberPanel);

        // Guess Screen

        guessPanel = new JPanel();
        guessPanel.setBounds(0, 0, window.getWidth(), window.getHeight());
        guessPanel.setBackground(new Color(43, 135, 209));
        guessPanel.setLayout(null);

        JLabel question = new JLabel("What was the number?");
        question.setFont(new Font("Arial", Font.BOLD, 30));
        question.setBounds((window.getWidth() / 2) - (800 / 2), 225, 800, 75);
        question.setHorizontalAlignment(SwingConstants.CENTER);
        question.setVerticalAlignment(SwingConstants.CENTER);
        question.setForeground(Color.WHITE);
        guessPanel.add(question);

        JLabel submitLabel = new JLabel("Press enter to submit");
        submitLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        submitLabel.setBounds((window.getWidth() / 2) - (800 / 2), 275, 800, 50);
        submitLabel.setHorizontalAlignment(SwingConstants.CENTER);
        submitLabel.setVerticalAlignment(SwingConstants.CENTER);
        submitLabel.setForeground(new Color(150, 186, 224));
        guessPanel.add(submitLabel);

        numsArea = new JTextField();
        numsArea.setFont(new Font("Arial", Font.BOLD, 50));
        numsArea.setBounds((window.getWidth() / 2) - (800 / 2), 350, 800, 80);
        numsArea.setBackground(new Color(34, 108, 187));
        numsArea.setHorizontalAlignment(JTextField.CENTER);
        numsArea.setBorder(null);
        numsArea.setForeground(Color.WHITE);
        numsArea.setCaretColor(Color.WHITE);
        guessPanel.add(numsArea);

        submit = new JButton("Submit");
        submit.setFont(new Font("Arial", Font.BOLD, 20));
        submit.setBounds((window.getWidth() / 2) - (100 / 2), 450, 100, 50);
        submit.setFocusable(true);
        submit.setFocusPainted(false);
        submit.setBackground(new Color(255, 209, 84));
        submit.setHorizontalAlignment(SwingConstants.CENTER);
        submit.setVerticalAlignment(SwingConstants.CENTER);
        guessPanel.add(submit);

        window.add(guessPanel);
        guessPanel.setVisible(false);

        // Score Screen

        scorePanel = new JPanel();
        scorePanel.setBounds(0, 0, window.getWidth(), window.getHeight());
        scorePanel.setBackground(new Color(43, 135, 209));
        scorePanel.setLayout(null);

        JLabel numberHeader = new JLabel("Number:");
        numberHeader.setFont(new Font("Arial", Font.PLAIN, 20));
        numberHeader.setBounds((window.getWidth() / 2) - (800 / 2), 75, 800, 75);
        numberHeader.setHorizontalAlignment(SwingConstants.CENTER);
        numberHeader.setVerticalAlignment(SwingConstants.CENTER);
        numberHeader.setForeground(new Color(150, 186, 224));
        scorePanel.add(numberHeader);

        numberLabel = new JLabel();
        numberLabel.setFont(new Font("Arial", Font.PLAIN, 40));
        numberLabel.setBounds((window.getWidth() / 2) - (800 / 2), 125, 800, 75);
        numberLabel.setHorizontalAlignment(SwingConstants.CENTER);
        numberLabel.setVerticalAlignment(SwingConstants.CENTER);
        numberLabel.setForeground(Color.WHITE);
        scorePanel.add(numberLabel);

        JLabel answerHeader = new JLabel("Your Answer");
        answerHeader.setFont(new Font("Arial", Font.PLAIN, 20));
        answerHeader.setBounds((window.getWidth() / 2) - (800 / 2), 200, 800, 75);
        answerHeader.setHorizontalAlignment(SwingConstants.CENTER);
        answerHeader.setVerticalAlignment(SwingConstants.CENTER);
        answerHeader.setForeground(new Color(150, 186, 224));
        scorePanel.add(answerHeader);

        answerLabel = new JLabel();
        answerLabel.setFont(new Font("Arial", Font.PLAIN, 40));
        answerLabel.setBounds((window.getWidth() / 2) - (800 / 2), 250, 800, 75);
        answerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        answerLabel.setVerticalAlignment(SwingConstants.CENTER);
        answerLabel.setForeground(Color.WHITE);
        scorePanel.add(answerLabel);

        levelLabel = new JLabel();
        levelLabel.setFont(new Font("Arial", Font.PLAIN, 60));
        levelLabel.setBounds((window.getWidth() / 2) - (800 / 2), 350, 800, 75);
        levelLabel.setHorizontalAlignment(SwingConstants.CENTER);
        levelLabel.setVerticalAlignment(SwingConstants.CENTER);
        levelLabel.setForeground(Color.WHITE);
        scorePanel.add(levelLabel);

        next = new JButton("Next");
        next.setFont(new Font("Arial", Font.BOLD, 20));
        next.setBounds((window.getWidth() / 2) - (100 / 2), 450, 100, 50);
        next.setFocusable(true);
        next.setFocusPainted(false);
        next.setBackground(new Color(255, 209, 84));
        next.setHorizontalAlignment(SwingConstants.CENTER);
        next.setVerticalAlignment(SwingConstants.CENTER);
        next.setBorder(null);
        scorePanel.add(next);

        window.add(scorePanel);
        scorePanel.setVisible(false);

        // Game End Panel 

        gameOverPanel = new JPanel();
        gameOverPanel.setBounds(0, 0, window.getWidth(), window.getHeight());
        gameOverPanel.setBackground(new Color(43, 135, 209));
        gameOverPanel.setLayout(null);

        JButton tryAgain = new JButton("Try again");
        tryAgain.setFont(new Font("Arial", Font.BOLD, 20));
        tryAgain.setBounds((window.getWidth() / 2) - (100 / 2), 450, 100, 50);
        tryAgain.setFocusable(true);
        tryAgain.setFocusPainted(false);
        tryAgain.setBackground(new Color(255, 209, 84));
        tryAgain.setHorizontalAlignment(SwingConstants.CENTER);
        tryAgain.setVerticalAlignment(SwingConstants.CENTER);
        tryAgain.setBorder(null);

        JLabel numberHeader2 = new JLabel("Number:");
        numberHeader2.setFont(new Font("Arial", Font.PLAIN, 20));
        numberHeader2.setBounds((window.getWidth() / 2) - (800 / 2), 75, 800, 75);
        numberHeader2.setHorizontalAlignment(SwingConstants.CENTER);
        numberHeader2.setVerticalAlignment(SwingConstants.CENTER);
        numberHeader2.setForeground(new Color(150, 186, 224));

        numberLabel2 = new JLabel();
        numberLabel2.setFont(new Font("Arial", Font.PLAIN, 40));
        numberLabel2.setBounds((window.getWidth() / 2) - (800 / 2), 125, 800, 75);
        numberLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        numberLabel2.setVerticalAlignment(SwingConstants.CENTER);
        numberLabel2.setForeground(Color.WHITE);

        JLabel answerHeader2 = new JLabel("Your Answer");
        answerHeader2.setFont(new Font("Arial", Font.PLAIN, 20));
        answerHeader2.setBounds((window.getWidth() / 2) - (800 / 2), 200, 800, 75);
        answerHeader2.setHorizontalAlignment(SwingConstants.CENTER);
        answerHeader2.setVerticalAlignment(SwingConstants.CENTER);
        answerHeader2.setForeground(new Color(150, 186, 224));

        answerLabel2 = new JLabel();
        answerLabel2.setFont(new Font("Arial", Font.PLAIN, 40));
        answerLabel2.setBounds((window.getWidth() / 2) - (800 / 2), 250, 800, 75);
        answerLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        answerLabel2.setVerticalAlignment(SwingConstants.CENTER);
        answerLabel2.setForeground(Color.WHITE);

        JLabel levelLabel2 = new JLabel();
        levelLabel2.setFont(new Font("Arial", Font.PLAIN, 60));
        levelLabel2.setBounds((window.getWidth() / 2) - (800 / 2), 350, 800, 75);
        levelLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        levelLabel2.setVerticalAlignment(SwingConstants.CENTER);
        levelLabel2.setForeground(Color.WHITE);

        gameOverPanel.add(tryAgain);
        gameOverPanel.add(numberHeader2);
        gameOverPanel.add(numberLabel2);
        gameOverPanel.add(answerHeader2);
        gameOverPanel.add(levelLabel2);
        gameOverPanel.add(answerLabel2);

        gameOverPanel.setVisible(false);
        window.add(gameOverPanel);

        // ---------------------- EVENT LISTENERS ---------------------- \\

        next.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mousePressed(java.awt.event.MouseEvent evt) {
                scorePanel.setVisible(false);
                buttonPressed = true;
            }
        });

        tryAgain.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mousePressed(java.awt.event.MouseEvent evt) {
                gameOverPanel.setVisible(false);
                for(Component c : comps){
                    c.setVisible(true); 
                }
                level = 0; 
                buttonPressed = false; 
                durationInSeconds = level + 1; 
                isDone = false; 
            }
        });

        submit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                if (!numsArea.getText().equals("")) {
                    buttonPressed = true;
                }
            }
        });

        numGame.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                for (Component c : comps) {
                    c.setVisible(false);
                }
                buttonPressed = true;
            }
        });

        numsArea.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && !numsArea.getText().equals("") && guessPanel.isVisible()) {
                    buttonPressed = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // TODO Auto-generated method stub
            }

        });

        ButtonHoverListener nextListener = new ButtonHoverListener(next, new Color(255, 255, 255),
                next.getBackground());
        next.addMouseListener(nextListener);

        ButtonHoverListener tryAgainListener = new ButtonHoverListener(tryAgain, new Color(255, 255, 255),
                tryAgain.getBackground());
        tryAgain.addMouseListener(tryAgainListener);

        ButtonHoverListener subListen = new ButtonHoverListener(submit, new Color(255, 255, 255),
                submit.getBackground());
        submit.addMouseListener(subListen);

        ButtonHoverListener startListener = new ButtonHoverListener(numGame, new Color(255, 255, 255),
                numGame.getBackground());
        numGame.addMouseListener(startListener);

        comps.add(label);
        comps.add(title);
        comps.add(sub);
        comps.add(numGame);

        window.add(label);
        window.add(title);
        window.add(sub);
        window.add(numGame);

        window.setVisible(true);

        return window;
    }

    public static void main(String[] args) {
        new NumberMemory();
    }
}