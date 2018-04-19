CREATE TABLE country
(
  country_id   SERIAL      NOT NULL
    CONSTRAINT country_pkey
    PRIMARY KEY,
  country_name VARCHAR(64) NOT NULL
    CONSTRAINT country_name
    CHECK ((country_name) :: TEXT ~ '(^([ A-Za-zА-Яа-я0-9-]+)$)' :: TEXT)
);

CREATE TABLE film
(
  release_date DATE,
  budget       INTEGER
    CONSTRAINT budget
    CHECK (budget > 0),
  score        DOUBLE PRECISION
    CONSTRAINT score
    CHECK ((score >= (0) :: DOUBLE PRECISION) AND (score <= (10) :: DOUBLE PRECISION)),
  critic_score DOUBLE PRECISION,
  comment      VARCHAR(256),
  film_id      SERIAL                                               NOT NULL
    CONSTRAINT film_pkey
    PRIMARY KEY,
  genre_id     INTEGER                                              NOT NULL,
  country_id   INTEGER
    CONSTRAINT r_29
    REFERENCES country,
  name         VARCHAR(256) DEFAULT 'Filmname' :: CHARACTER VARYING NOT NULL
    CONSTRAINT name
    CHECK ((name) :: TEXT ~ '(^([.'' A-Za-zА-Яа-я0-9-]+)$)' :: TEXT),
  comments     JSON,
  CONSTRAINT critic_score
  CHECK ((critic_score >= (0) :: DOUBLE PRECISION) AND (score <= (10) :: DOUBLE PRECISION))
);

CREATE TABLE filmworker
(
  name          VARCHAR(64) NOT NULL
    CONSTRAINT name
    CHECK ((name) :: TEXT ~ '(^([ A-Za-zА-Яа-я-]+)$)' :: TEXT),
  surname       VARCHAR(64) NOT NULL
    CONSTRAINT surname
    CHECK ((surname) :: TEXT ~ '(^([ A-Za-zА-Яа-я-]+)$)' :: TEXT),
  birthday      INTEGER
    CONSTRAINT birthday
    CHECK (birthday >= 0),
  filmworker_id SERIAL      NOT NULL
    CONSTRAINT filmworker_pkey
    PRIMARY KEY,
  country_id    INTEGER
    CONSTRAINT r_28
    REFERENCES country
);

CREATE TABLE filmworkers_role
(
  role_id       INTEGER NOT NULL,
  filmworker_id INTEGER NOT NULL
    CONSTRAINT r_17
    REFERENCES filmworker,
  film_id       INTEGER NOT NULL
    CONSTRAINT r_18
    REFERENCES film,
  CONSTRAINT filmworkers_role_pkey
  PRIMARY KEY (role_id, filmworker_id, film_id)
);

CREATE TABLE genre
(
  genre_id SERIAL NOT NULL
    CONSTRAINT genre_pkey
    PRIMARY KEY,
  genre    VARCHAR(64)
    CONSTRAINT genre
    CHECK ((genre) :: TEXT ~ '(^([ A-Za-zА-Яа-я0-9-]+)$)' :: TEXT)
);

ALTER TABLE film
  ADD CONSTRAINT r_26
FOREIGN KEY (genre_id) REFERENCES genre;

CREATE TABLE role
(
  role_name VARCHAR(64) NOT NULL
    CONSTRAINT role_name
    CHECK ((role_name) :: TEXT ~ '(^([ A-Za-zА-Яа-я-]+)$)' :: TEXT),
  role_id   SERIAL      NOT NULL
    CONSTRAINT role_pkey
    PRIMARY KEY
);

ALTER TABLE filmworkers_role
  ADD CONSTRAINT r_16
FOREIGN KEY (role_id) REFERENCES role;

CREATE TABLE score_film_to_user
(
  film_id INTEGER     NOT NULL
    CONSTRAINT r_21
    REFERENCES film,
  score   INTEGER     NOT NULL
    CONSTRAINT score
    CHECK ((score >= 0) AND (score <= 10)),
  login   VARCHAR(64) NOT NULL,
  role    VARCHAR(20) NOT NULL,
  CONSTRAINT score_film_to_user_pkey
  PRIMARY KEY (film_id, login)
);

CREATE TABLE film_user
(
  name     VARCHAR(64),
  surname  VARCHAR(64),
  birthday INTEGER
    CONSTRAINT birthday
    CHECK (birthday > 0),
  login    VARCHAR(32)                                 NOT NULL
    CONSTRAINT film_user_pkey
    PRIMARY KEY
    CONSTRAINT login
    CHECK ((login) :: TEXT ~ '(^([A-Za-zА-Яа-я0-9]+)$)' :: TEXT),
  password VARCHAR(128)                                NOT NULL
    CONSTRAINT password
    CHECK ((password) :: TEXT ~ '(^([A-Za-zА-Яа-я0-9]+)$)' :: TEXT),
  role     VARCHAR(1) DEFAULT 'U' :: CHARACTER VARYING NOT NULL
);

ALTER TABLE score_film_to_user
  ADD CONSTRAINT r_22
FOREIGN KEY (login) REFERENCES film_user;

CREATE TABLE user_role
(
  role VARCHAR(1) NOT NULL
    CONSTRAINT user_role_role_pk
    PRIMARY KEY,
  name VARCHAR(64)
);

CREATE FUNCTION add_user(username CHARACTER VARYING, pass CHARACTER VARYING)
  RETURNS CHARACTER VARYING
LANGUAGE plpgsql
AS $$
DECLARE id VARCHAR(64);

BEGIN
  INSERT INTO public.film_user (login, password)
  VALUES (username, pass)
  RETURNING username
    INTO id;
  RETURN id;

  EXCEPTION
  WHEN not_null_violation
    THEN
      RAISE EXCEPTION 'Field cant be NULL';
      RETURN NULL;

  WHEN unique_violation
    THEN
      RAISE EXCEPTION 'Username must be unique';
      RETURN NULL;

  WHEN check_violation
    THEN
      RAISE EXCEPTION 'Wrong format of field';
      RETURN NULL;

  WHEN OTHERS
    THEN
      RAISE EXCEPTION 'Unknown error';
      RETURN NULL;
END;
$$;

CREATE FUNCTION add_film(film_name CHARACTER VARYING, genre INTEGER)
  RETURNS INTEGER
LANGUAGE plpgsql
AS $$
DECLARE id INTEGER;

BEGIN
  INSERT INTO public.film (name, genre_id)
  VALUES (film_name, genre)
  RETURNING film_id
    INTO id;
  RETURN id;

  EXCEPTION
  WHEN not_null_violation
    THEN
      RAISE EXCEPTION 'Field cant be NULL';
      RETURN NULL;

  WHEN unique_violation
    THEN
      RAISE EXCEPTION 'Username must be unique';
      RETURN NULL;

  WHEN check_violation
    THEN
      RAISE EXCEPTION 'Wrong format of field';
      RETURN NULL;

  WHEN OTHERS
    THEN
      RAISE EXCEPTION 'Unknown error';
      RETURN NULL;
END;
$$;

CREATE FUNCTION add_filmworker(filmworker_name CHARACTER VARYING, filmworker_surname CHARACTER VARYING)
  RETURNS INTEGER
LANGUAGE plpgsql
AS $$
DECLARE id INTEGER;

BEGIN
  INSERT INTO public.filmworker (name, surname)
  VALUES (filmworker_name, filmworker_surname)
  RETURNING filmworker_id
    INTO id;
  RETURN id;

  EXCEPTION
  WHEN not_null_violation
    THEN
      RAISE EXCEPTION 'Field cant be NULL';
      RETURN NULL;

  WHEN unique_violation
    THEN
      RAISE EXCEPTION 'Username must be unique';
      RETURN NULL;

  WHEN check_violation
    THEN
      RAISE EXCEPTION 'Wrong format of field';
      RETURN NULL;

  WHEN OTHERS
    THEN
      RAISE EXCEPTION 'Unknown error';
      RETURN NULL;
END;
$$;

CREATE FUNCTION delete_user(username CHARACTER VARYING)
  RETURNS VOID
LANGUAGE plpgsql
AS $$
BEGIN

  DELETE FROM public.film_user
  WHERE login = username;

  EXCEPTION
  WHEN not_null_violation
    THEN
      RAISE EXCEPTION 'Field cant be NULL';
      RETURN;

  WHEN unique_violation
    THEN
      RAISE EXCEPTION 'Username must be unique';
      RETURN;

  WHEN check_violation
    THEN
      RAISE EXCEPTION 'Wrong format of field';
      RETURN;

  WHEN OTHERS
    THEN
      RAISE EXCEPTION 'Unknown error';
      RETURN;
END;
$$;

CREATE FUNCTION delete_film(my_film_id INTEGER)
  RETURNS VOID
LANGUAGE plpgsql
AS $$
BEGIN

  DELETE FROM public.film
  WHERE film_id = my_film_id;

  EXCEPTION
  WHEN not_null_violation
    THEN
      RAISE EXCEPTION 'Field cant be NULL';
      RETURN;

  WHEN unique_violation
    THEN
      RAISE EXCEPTION 'Username must be unique';
      RETURN;

  WHEN check_violation
    THEN
      RAISE EXCEPTION 'Wrong format of field';
      RETURN;

  WHEN OTHERS
    THEN
      RAISE EXCEPTION 'Unknown error';
      RETURN;
END;
$$;

CREATE FUNCTION delete_filmworker(my_filmworker_id INTEGER)
  RETURNS VOID
LANGUAGE plpgsql
AS $$
BEGIN

  DELETE FROM public.filmworker
  WHERE filmworker_id = my_filmworker_id;

  EXCEPTION
  WHEN not_null_violation
    THEN
      RAISE EXCEPTION 'Field cant be NULL';
      RETURN;

  WHEN unique_violation
    THEN
      RAISE EXCEPTION 'Username must be unique';
      RETURN;

  WHEN check_violation
    THEN
      RAISE EXCEPTION 'Wrong format of field';
      RETURN;

  WHEN OTHERS
    THEN
      RAISE EXCEPTION 'Unknown error';
      RETURN;
END;
$$;

CREATE FUNCTION edit_filmworker(my_name       CHARACTER VARYING, my_surname CHARACTER VARYING, my_birthday INTEGER,
                                my_country_id CHARACTER VARYING, my_filmworker_id INTEGER)
  RETURNS VOID
LANGUAGE plpgsql
AS $$
BEGIN
  UPDATE public.film_filmworker
  SET (name, surname, birthday, country_id) =
  (my_name, my_surname, my_birthday, my_country_id)
  WHERE filmworker_id = my_filmworker_id;

  EXCEPTION
  WHEN not_null_violation
    THEN
      RAISE EXCEPTION 'Field cant be NULL';
      RETURN;

  WHEN unique_violation
    THEN
      RAISE EXCEPTION 'Username must be unique';
      RETURN;

  WHEN check_violation
    THEN
      RAISE EXCEPTION 'Wrong format of field';
      RETURN;

  WHEN OTHERS
    THEN
      RAISE EXCEPTION 'Unknown error';
      RETURN;
END;
$$;

CREATE FUNCTION edit_user(my_name  CHARACTER VARYING, my_surname CHARACTER VARYING, my_birthday INTEGER,
                          username CHARACTER VARYING, pass CHARACTER VARYING, my_role CHARACTER VARYING)
  RETURNS VOID
LANGUAGE plpgsql
AS $$
BEGIN
  UPDATE public.film_user
  SET (password, name, surname, role, birthday) =
  (pass, my_name, my_surname, my_role, my_birthday)
  WHERE login = username;

  EXCEPTION
  WHEN not_null_violation
    THEN
      RAISE EXCEPTION 'Field cant be NULL';
      RETURN;

  WHEN unique_violation
    THEN
      RAISE EXCEPTION 'Username must be unique';
      RETURN;

  WHEN check_violation
    THEN
      RAISE EXCEPTION 'Wrong format of field';
      RETURN;

  WHEN OTHERS
    THEN
      RAISE EXCEPTION 'Unknown error';
      RETURN;
END;
$$;

CREATE FUNCTION edit_film(my_release_date DATE, my_budget INTEGER, my_comment CHARACTER VARYING, my_genre_id INTEGER,
                          my_country_id   INTEGER, my_name CHARACTER VARYING, my_film_id INTEGER)
  RETURNS VOID
LANGUAGE plpgsql
AS $$
BEGIN
  UPDATE public.film
  SET (release_date, budget, comment, genre_id, country_id, name) =
  (my_release_date, my_budget, my_comment, my_genre_id, my_country_id, my_name)
  WHERE film_id = my_film_id;

  EXCEPTION
  WHEN not_null_violation
    THEN
      RAISE EXCEPTION 'Field cant be NULL';
      RETURN;

  WHEN unique_violation
    THEN
      RAISE EXCEPTION 'Username must be unique';
      RETURN;

  WHEN check_violation
    THEN
      RAISE EXCEPTION 'Wrong format of field';
      RETURN;

  WHEN OTHERS
    THEN
      RAISE EXCEPTION 'Unknown error';
      RETURN;
END;
$$;

CREATE FUNCTION add_score(user_name CHARACTER VARYING, my_film_id INTEGER, my_score DOUBLE PRECISION,
                          my_role   CHARACTER VARYING)
  RETURNS VOID
LANGUAGE plpgsql
AS $$
DECLARE id INTEGER;

BEGIN
  INSERT INTO public.score_film_to_user (login, film_id, score, role)
  VALUES (user_name, my_film_id, my_score, my_role);

  IF my_role = 'C'
  THEN
    UPDATE public.film
    SET critic_score = (
      SELECT AVG(critic_score)
      FROM film
    )
    WHERE film_id = my_film_id;
  ELSE
    UPDATE public.film
    SET score = (
      SELECT AVG(score)
      FROM film
    )
    WHERE film_id = my_film_id;
  END IF;

  EXCEPTION
  WHEN not_null_violation
    THEN
      RAISE EXCEPTION 'Field cant be NULL';
      RETURN;

  WHEN unique_violation
    THEN
      RAISE EXCEPTION 'Username must be unique';
      RETURN;

  WHEN check_violation
    THEN
      RAISE EXCEPTION 'Wrong format of field';
      RETURN;

  WHEN OTHERS
    THEN
      RAISE EXCEPTION 'Unknown error';
      RETURN;
END;
$$;


