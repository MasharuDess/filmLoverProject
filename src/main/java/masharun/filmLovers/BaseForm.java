package masharun.filmLovers;

import masharun.filmLovers.view.forms.Form;
import javax.swing.*;
import java.awt.*;
import java.util.Stack;

import static masharun.filmLovers.view.Config.MAIN_TITLE;
import static masharun.filmLovers.view.Config.SIZE.*;

public class BaseForm extends JFrame {
    private JPanel mainPanel;
    
    private static Stack<Form> formStack = new Stack<>();
    
    public BaseForm() {
        init();
    }
    
    public void setNewForm( Form form ) {
        refresh( form );
    }
    
    public void setLastForm() {
        Form form = formStack.pop();
        form.reinit();
        refresh( form );
    }
    
    public void pushStackFrame( Form form ) {
        formStack.push( form );
    }
    
    public void clearStackFrame() {
        formStack.clear();
    }
    
    private void init() {
        this.setTitle( MAIN_TITLE );
        this.setDefaultCloseOperation( EXIT_ON_CLOSE );
        this.setMinimumSize( new Dimension( WINDOW_WIDTH, WINDOW_HEIGHT ));
        setLocationRelativeTo( null );
        pack();
    }
    
    private void refresh ( Form form ) {
        setContentPane( form.getRootPanel() );
        revalidate();
        repaint();
    }
    
}