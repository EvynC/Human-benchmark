import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import org.w3c.dom.ls.LSException;

import java.util.ArrayList;

public class Homepage {
    public static JButton VM;
    public static JButton AIM;
    private JTextArea AIM_How; 
    private JTextArea VM_How;
    public static ArrayList<Component> components = new ArrayList<Component>();

    public Homepage() {
        JFrame window = new JFrame("Benchmark Test - Catton");
        window.setSize(800, 800);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.BLACK);
        window.setLayout(null);

        VM = new JButton("Visual Memory Game");
        VM.setFont(new Font("Times New Roman", Font.BOLD, 20));
        VM.setBounds(20, 30, 360, 180);
        VM.setFocusable(true);
        VM.setFocusPainted(false);

        VM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt){
                hideComponents();
                new VisualMemory(window);
            }
        });

        VM_How = new JTextArea("     Every level, a number of tiles will flash white. Memorize them, and pick them again after the tiles are reset! Levels get progressively more difficult, to challenge your skills. If you miss 3 tiles on a level, you lose one life. You have three lives. Make it as far as you can!");
        VM_How.setBounds(20, 250, 360, 300);
        VM_How.setEditable(false);
        VM_How.setWrapStyleWord(true);
        VM_How.setLineWrap(true);
        VM_How.setFont(new Font("Times New Roman", Font.BOLD, 20));
        VM_How.setFocusable(true);
        VM_How.setMargin(new Insets(10, 10, 10, 10));

        AIM = new JButton("Aim Trainer Game");
        AIM.setBounds(410, 30, 360, 180);
        AIM.setFont(new Font("Times New Roman", Font.BOLD, 20));
        AIM.setFocusable(true);
        AIM.setFocusPainted(false);
        AIM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt){
                hideComponents();
                new AimTrainer(window);
            }
        });

        AIM_How = new JTextArea("     Click the targets as quickly and accurately as you can. This tests reflexes and hand-eye coordination. Once you've clicked 30 targets, your score and average time per target will be displayed. Scores in this test are slower than a simple reaction time test, because you must react and then move the cursor. This test is best taken with a mouse or tablet screen. Trackpads are difficult to score well with.");
        AIM_How.setBounds(410, 250, 360, 300);
        AIM_How.setEditable(false);
        AIM_How.setWrapStyleWord(true);
        AIM_How.setLineWrap(true);
        AIM_How.setFont(new Font("Times New Roman", Font.BOLD, 20));
        AIM_How.setFocusable(true);
        AIM_How.setMargin(new Insets(10, 10, 10, 10));

        
        components.add(AIM);
        components.add(AIM_How);
        components.add(VM);
        components.add(VM_How);
        
        window.add(VM);
        window.add(VM_How);
        window.add(AIM);
        window.add(AIM_How);


        window.setVisible(true);
    }

    public static void main(String[] args) throws InterruptedException {
        new Homepage();
    }

    public void hideComponents() {
        for (int i = 0; i < components.size(); i++) {
            components.get(i).setVisible(false);
        }
    }

    public void showComponents() {
        for (int i = 0; i < components.size(); i++) {
            components.get(i).setVisible(true);
        }
    }
}