package me.mashyrin.filmLovers.view;

import javax.swing.*;

public class OptionPane extends JOptionPane {
    
    public static void showMessage( String text, String title ) {
        new Thread( () -> {
            OptionPane.showMessageDialog( new JPanel(), text, title, JOptionPane.DEFAULT_OPTION );
        } ).start();
    }
}
