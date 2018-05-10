package me.mashyrin.filmLovers.view;

import javax.swing.*;

/**
 * Option pane class. Used to show message dialogs
 *
 * @author mashyrin
 */
public class OptionPane extends JOptionPane {
    
    /**
     * Showing message dialog in other thread
     *
     * @param text
     * @param title
     */
    public static void showMessage( String text, String title ) {
        new Thread( () -> OptionPane.showMessageDialog(
                new JPanel(), text, title, JOptionPane.DEFAULT_OPTION ) ).start();
    }
}
