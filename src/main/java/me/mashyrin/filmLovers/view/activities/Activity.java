package me.mashyrin.filmLovers.view.activities;

import javax.swing.*;

/**
 * Activity interface
 *
 * @author mashyrin
 */
public interface Activity {
    /**
     * @return root panel
     */
    JPanel getRootPanel();
    
    /**
     * code that must be reinitialised
     */
    void reinit();
}
