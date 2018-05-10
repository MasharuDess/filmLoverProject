package me.mashyrin.filmLovers;

import me.mashyrin.filmLovers.view.activities.Activity;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

import static me.mashyrin.filmLovers.view.Config.NUMBERS.*;

/**
 * Base activity class. Main class calls that activity and set other activities on it
 *
 * @author mashyrin
 */
public class BaseActivity extends JFrame {
    private JPanel mainPanel;
    
    private static Stack<Activity> task = new Stack<>();
    
    /**
     * Base activity constructor
     */
    public BaseActivity() {
        init();
    }
    
    /**
     * Satting new activity on that
     *
     * @param activity
     */
    public void setNewForm( Activity activity ) {
        refresh( activity );
    }
    
    /**
     * Setting last activity
     */
    public void setLastForm() {
        Activity activity = task.pop();
        activity.reinit();
        refresh( activity );
    }
    
    /**
     * Pushing activity in the task
     *
     * @param activity
     */
    public void pushStackFrame( Activity activity ) {
        task.push( activity );
    }
    
    /**
     * Clearing task
     */
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