package masharun.filmLovers;

import masharun.filmLovers.view.activities.Activity;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

import static masharun.filmLovers.view.Config.SIZE.*;

public class BaseActivity extends JFrame {
    private JPanel mainPanel;
    
    private static Stack<Activity> task = new Stack<>();
    
    public BaseActivity() {
        init();
    }
    
    public void setNewForm( Activity activity ) {
        refresh( activity );
    }
    
    public void setLastForm() {
        Activity activity = task.pop();
        activity.reinit();
        refresh( activity );
    }
    
    public void pushStackFrame( Activity activity ) {
        task.push( activity );
    }
    
    public void clearStackFrame() {
        task.clear();
    }
    
    private void init() {
        this.setDefaultCloseOperation( EXIT_ON_CLOSE );
        this.setMinimumSize( new Dimension( WINDOW_WIDTH, WINDOW_HEIGHT ) );
        setLocationRelativeTo( null );
        pack();
    }
    
    private void refresh( Activity activity ) {
        setContentPane( activity.getRootPanel() );
        revalidate();
        repaint();
    }
    
}