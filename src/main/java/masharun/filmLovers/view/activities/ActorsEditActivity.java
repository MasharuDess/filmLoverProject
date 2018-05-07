package masharun.filmLovers.view.activities;

import masharun.filmLovers.Main;
import masharun.filmLovers.model.DAO.CountryDAO;
import masharun.filmLovers.model.DAO.FilmworkerDAO;
import masharun.filmLovers.model.entities.Country;
import masharun.filmLovers.model.entities.Filmworker;
import masharun.filmLovers.view.Config;
import masharun.filmLovers.view.OptionPane;

import javax.swing.*;

import java.sql.SQLException;
import java.util.ArrayList;

import static masharun.filmLovers.view.Config.CURRENT_YEAR;
import static masharun.filmLovers.view.Config.TITLES.ACTORS_ADD_FORM_TITLE;
import static masharun.filmLovers.view.Config.TITLES.ACTORS_EDIT_FORM_TITLE;
import static masharun.filmLovers.view.Config.TITLES.MAIN_TITLE;
import static masharun.filmLovers.view.Config.YEAR_COUNT;

public class ActorsEditActivity implements Activity {
    private JPanel rootPanel;
    private JPanel footerPanel;
    private JPanel headerPanel;
    private JLabel mainTitle;
    private JButton editButton;
    private JButton exitButton;
    private JButton backButton;
    private JTextField nameField;
    private JTextField surnameField;
    private JComboBox countryBox;
    private JComboBox birthdayBox;
    private JLabel nameLabel;
    private JLabel surnameLabel;
    private JLabel birthdayLabel;
    private JLabel countryLabel;
    private Filmworker filmworker;
    
    public ActorsEditActivity() {
        mainTitle.setText( MAIN_TITLE );
        Config.TITLES.setTitle( ACTORS_ADD_FORM_TITLE );
        editButton.setText( "Добавить" );
        initBoxes();
        initListeners();
    }
    
    public ActorsEditActivity( Filmworker filmworker ) {
        this.filmworker = filmworker;
        mainTitle.setText( MAIN_TITLE );
        Config.TITLES.setTitle( ACTORS_EDIT_FORM_TITLE );
        editButton.setText( "Изменить" );
        initBoxes();
        initListeners();
    }
    
    private void initBoxes() {
        try {
            ArrayList<Country> countryList = new CountryDAO().selectAll();
            for( Country country : countryList ) {
                countryBox.addItem( country.getName() );
            }
            if( filmworker != null ) {
                countryBox.setSelectedIndex( filmworker.getCountryId() );
            }
        } catch( SQLException e ) {
            OptionPane.showMessage( "Ошибка загрузки списка стран", "Ошибка" );
            System.err.println( e.toString() );
        }
        
        if( filmworker != null ) {
            nameField.setText( filmworker.getName() );
            surnameField.setText( filmworker.getSurname() );
            birthdayBox.setSelectedIndex( YEAR_COUNT - filmworker.getBirthday() );
        } else {
            birthdayBox.setSelectedIndex( -1 );
            countryBox.setSelectedIndex( -1 );
        }
    }
    
    private void initListeners() {
        backButton.addActionListener( eventListeners -> Main.getMainActivity().setLastForm() );
        
        exitButton.addActionListener( eventListeners -> {
            Main.setCurrentUser( null );
            Main.getMainActivity().clearStackFrame();
            Main.getMainActivity().setNewForm( new MainActivity() );
        } );
        
        editButton.addActionListener( eventListener -> {
            if( nameField.getText().equals( "" ) || surnameField.getText().equals( "" ) ) {
                OptionPane.showMessage( "Вы не ввели имя/фамилию", "Ошибка" );
            } else if( countryBox.getSelectedIndex() == -1 || birthdayBox.getSelectedIndex() == -1 ) {
                OptionPane.showMessage( "Вы не выбрали страну/возраст", "Ошибка" );
            } else {
                try {
                    if( filmworker == null ) {
                        filmworker = new Filmworker();
                        filmworker.setName( nameField.getText() );
                        filmworker.setSurname( surnameField.getText() );
                        filmworker.setBirthday( CURRENT_YEAR - Integer.parseInt(
                                birthdayBox.getItemAt( birthdayBox.getSelectedIndex() ).toString() ) );
                        filmworker.setCountryId( countryBox.getSelectedIndex() );
                        new FilmworkerDAO().insert( filmworker );
                        OptionPane.showMessage( "Вы успешно добавили актера", "Поздравляем" );
                    } else {
                        filmworker.setName( nameField.getText() );
                        filmworker.setSurname( surnameField.getText() );
                        filmworker.setBirthday( CURRENT_YEAR - Integer.parseInt(
                                birthdayBox.getItemAt( birthdayBox.getSelectedIndex() ).toString() ) );
                        filmworker.setCountryId( countryBox.getSelectedIndex() );
                        new FilmworkerDAO().update( filmworker );
                        OptionPane.showMessage( "Вы успешно изменили актера", "Поздравляем" );
                    }
                    Main.getMainActivity().setLastForm();
                } catch( SQLException e ) {
                    OptionPane.showMessage( "Ошибка добавления актера", "Ошибка" );
                    System.err.println( e.toString() );
                }
            }
        } );
    }
    
    @Override
    public JPanel getRootPanel() {
        return rootPanel;
    }
    
    @Override
    public void reinit() {
        if( filmworker == null ) {
            Config.TITLES.setTitle( ACTORS_ADD_FORM_TITLE );
        } else {
            Config.TITLES.setTitle( ACTORS_EDIT_FORM_TITLE );
        }
    }
}
