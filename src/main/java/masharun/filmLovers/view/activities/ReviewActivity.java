package masharun.filmLovers.view.activities;

import masharun.filmLovers.Main;
import masharun.filmLovers.view.Config;

import javax.swing.*;

import static masharun.filmLovers.view.Config.TITLES.MAIN_TITLE;
import static masharun.filmLovers.view.Config.TITLES.REVIEW_FORM_TITLE;

public class ReviewActivity implements Activity {
    private JPanel rootPanel;
    private JPanel headerPanel;
    private JPanel footerPanel;
    private JButton exitButton;
    private JButton backButton;
    private JLabel mainTitle;
    
    public ReviewActivity() {
        mainTitle.setText( MAIN_TITLE );
        Config.TITLES.setTitle( REVIEW_FORM_TITLE );
        initListeners();
    }
    
    private void initListeners() {
        backButton.addActionListener( eventListeners -> Main.getMainActivity().setLastForm() );
        
        exitButton.addActionListener( eventListeners -> {
            Main.setCurrentUser( null );
            Main.getMainActivity().clearStackFrame();
            Main.getMainActivity().setNewForm( new MainActivity() );
        } );
    }
    
    @Override
    public JPanel getRootPanel() {
        return rootPanel;
    }
    
    @Override
    public void reinit() {
        Config.TITLES.setTitle( REVIEW_FORM_TITLE );
    }
}
