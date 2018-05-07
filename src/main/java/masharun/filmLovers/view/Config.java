package masharun.filmLovers.view;

import masharun.filmLovers.Main;

public final class Config {
    
    public static final class SIZE {
        public static final int WINDOW_WIDTH = 720;
        public static final int WINDOW_HEIGHT = 480;
    }
    
    public static final class TITLES {
        public static final String MAIN_FORM_TITLE = null;
        public static final String MAIN_TITLE = "КиноРофликс";
        public static final String ACTORS_EDIT_FORM_TITLE = "Настройка актеров";
        public static final String ACTORS_ADD_FORM_TITLE = "Добавление актеров";
        public static final String ACTORS_FORM_TITLE = "Актеры";
        public static final String ALL_ACTORS_FORM_TITLE = "Все актеры";
        public static final String ADMIN_FORM_TITLE = "Пользователи";
        public static final String AUTHENTICATION_FORM_TITLE = "Аутентификация";
        public static final String FILM_EDIT_FORM_TITLE = "Настройка фильмов";
        public static final String FILM_ADD_FORM_TITLE = "Добавление фильмов";
        public static final String FILMS_FORM_TITLE = "Фильмы";
        public static final String PERSONAL_AREA_FORM_TITLE = "Личный кабинет";
        public static final String REGISTRATION_FORM_TITLE = "Регистрация";
        public static final String REVIEW_FORM_TITLE = "Рецензии";
        public static final String UPDATE_USER_FORM_TITLE = "Настройка профиля";
        public static final String GRAPHIC_FORM_TITLE = "График";
        
        public static void setTitle( String title ) {
            if( title == null ) {
                Main.setTitle( MAIN_TITLE );
            } else {
                Main.setTitle( MAIN_TITLE + " - " + title );
            }
        }
    }
    
    public static final int CURRENT_YEAR = 2018;
    public static final int YEAR_COUNT = 118;
}
