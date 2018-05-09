package me.mashyrin.filmLovers.view;

import me.mashyrin.filmLovers.Main;

public final class Config {
    
    public static final class NUMBERS {
        public static final int WINDOW_WIDTH = 720;
        public static final int WINDOW_HEIGHT = 480;
        public static final int CURRENT_YEAR = 2018;
        public static final int YEAR_COUNT = 118;
    }
    
    public static final class TITLES {
        public static final String MAIN_FORM_TITLE = null;
        public static final String CONGRATULATION_TITLE = "Поздравляем";
        public static final String CHART_NAME_TITLE = "Количество оценок";
        public static final String MAIN_TITLE = "КиноРофликс";
        
        public static final String ACTORS_EDIT_ACTIVITY_TITLE = "Настройка актеров";
        public static final String ACTORS_ADD_ACTIVITY_TITLE = "Добавление актеров";
        public static final String ACTORS_ACTIVITY_TITLE = "Актеры";
        public static final String ALL_ACTORS_ACTIVITY_TITLE = "Все актеры";
        public static final String ADMIN_ACTIVITY_TITLE = "Пользователи";
        public static final String AUTHENTICATION_ACTIVITY_TITLE = "Аутентификация";
        public static final String FILM_EDIT_ACTIVITY_TITLE = "Настройка фильмов";
        public static final String FILM_ADD_FORM_ACTIVITY = "Добавление фильмов";
        public static final String FILMS_ACTIVITY_TITLE = "Фильмы";
        public static final String PERSONAL_AREA_ACTIVITY_TITLE = "Личный кабинет";
        public static final String REGISTRATION_ACTIVITY_TITLE = "Регистрация";
        public static final String REVIEW_ACTIVITY_TITLE = "Рецензии";
        public static final String UPDATE_USER_ACTIVITY_TITLE = "Настройка профиля";
        public static final String GRAPHIC_ACTIVITY_TITLE = "График";
        public static final String ADD_REVIEW_ACTIVITY_TITLE = "Добавление рецензии";
        
        public static void setTitle( String title ) {
            if( title == null ) {
                Main.setTitle( MAIN_TITLE );
            } else {
                Main.setTitle( MAIN_TITLE + " - " + title );
            }
        }
    }
    
    public static final class ERRORS {
        public static final String ERROR_TITLE = "Ошибка";
        public static final String SORRY_TITLE = "Извините";
        public static final String NO_REVIEW_ERROR = " К этому фильму пока нет рецензий. " +
                "Пожалуйста, дождитесь их появления.";
    }
}
