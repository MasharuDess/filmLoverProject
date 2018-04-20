CREATE OR REPLACE VIEW get_film_user AS
  SELECT name, surname, birthday, login, password, role
  FROM film_user;