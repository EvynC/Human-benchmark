import java.awt.Color;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import java.awt.event.MouseEvent; 

public class ButtonHoverListener implements MouseListener {

    private JButton button;
    private Color enter, exit; 

    public ButtonHoverListener(JButton button, Color enter, Color exit) {
        this.button = button;
        this.enter = enter; 
        this.exit = exit; 
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        button.setBackground(enter);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        button.setBackground(exit);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Do nothing.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Do nothing.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Do nothing.
    }
}