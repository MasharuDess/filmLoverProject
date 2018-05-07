package masharun.filmLovers.view;

import javax.swing.*;

public class OptionPane extends JOptionPane {
    
    public static void showMessage( String text, String title ) {
        OptionPane.showMessageDialog( new JPanel(), text, title, JOptionPane.DEFAULT_OPTION );
    }
    
}
