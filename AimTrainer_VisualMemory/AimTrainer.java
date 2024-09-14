import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;

public class AimTrainer {
    private JFrame frame;
    private long gameTime;
    private int TargetsLeft;

    public AimTrainer(JFrame window) {
        frame = window;
        frame.getContentPane().setBackground(new Color(43, 135, 209));
        frame.setTitle("Aim Trainer");

        JPanel panel = new JPanel();
        panel.setBounds(frame.getContentPane().getBounds());
        panel.setBackground(frame.getContentPane().getBackground());
        panel.setLayout(null);
        panel.setVisible(true);

        // Game Settings
        int numberOfTargets = 30;
        int targetSize = 50;

        TargetsLeft = numberOfTargets;

        JLabel remain = new JLabel("Remaining " + TargetsLeft);
        remain.setBounds(300, 0, 200, 50);
        remain.setFont(new Font("Times New Roman", Font.BOLD, 30));

        JButton target = new JButton();
        target.setBounds(200, 200, targetSize, targetSize);

        target.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                TargetsLeft--;
                if (TargetsLeft == (numberOfTargets - 1))
                    gameTime = System.currentTimeMillis();
                if (TargetsLeft > 0) {
                    target.setLocation((int) (Math.random() * (671 - 50) + 50), (int) (Math.random() * (671 - 50) + 50));
                    remain.setText("Remaining " + TargetsLeft);
                } else {
                    target.setVisible(false);
                    panel.setVisible(false);
                    endGame(numberOfTargets);
                }
            }
        });

        panel.add(target);
        panel.add(remain);
        frame.add(panel);
    }
 
    public void endGame(int numberOfTargets) {
        gameTime = (System.currentTimeMillis() - gameTime) / numberOfTargets;

        JLabel score = new JLabel("Score: " + gameTime + " ms");
        score.setBounds(300, 10, 200, 100);
        score.setFont(new Font("Times New Roman", Font.BOLD, 30));

        JButton back = new JButton("Back");
        back.setFont(new Font("Times New Roman", Font.BOLD, 30));
        back.setBounds(200, 400, 400, 50);
        back.setBackground(Color.white);
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new Homepage();
            }
        });

        frame.add(back);
        frame.add(score);
    }
}