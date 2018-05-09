-- we don't know how to generate database filmLoverDB (class Database) :(
create table country
(
  country_id   serial      not null
    constraint country_pkey
    primary key,
  country_name varchar(64) not null
    constraint country_name
    check ((country_name) :: text ~ '(^([ A-Za-zА-Яа-я0-9-]+)$)' :: text)
);

create table filmworker
(
  name          varchar(64) not null
    constraint name
    check ((name) :: text ~ '(^([ A-Za-zА-Яа-я-]+)$)' :: text),
  surname       varchar(64) not null
    constraint surname
    check ((surname) :: text ~ '(^([ A-Za-zА-Яа-я-]+)$)' :: text),
  birthday      integer
    constraint birthday
    check (birthday >= 0),
  filmworker_id serial      not null
    constraint filmworker_pkey
    primary key,
  country_id    integer
    constraint r_28
    references country
);

create table genre
(
  genre_id serial not null
    constraint genre_pkey
    primary key,
  genre    varchar(64)
    constraint genre
    check ((genre) :: text ~ '(^([ A-Za-zА-Яа-я0-9-]+)$)' :: text)
);

create table film
(
  release_date date,
  budget       integer
    constraint budget
    check (budget > 0),
  score        double precision
    constraint score
    check ((score >= (0) :: double precision) AND (score <= (10) :: double precision)),
  critic_score double precision,
  comment      varchar(256),
  film_id      serial                                               not null
    constraint film_pkey
    primary key,
  genre_id     integer                                              not null
    constraint r_26
    references genre,
  country_id   integer
    constraint r_29
    references country,
  name         varchar(256) default 'Filmname' :: character varying not null
    constraint name
    check ((name) :: text ~ '(^([.'' A-Za-zА-Яа-я0-9-]+)$)' :: text),
  review       jsonb default '{}' :: jsonb,
  constraint critic_score
  check ((critic_score >= (0) :: double precision) AND (score <= (10) :: double precision))
);

create table role
(
  role_name varchar(64) not null
    constraint role_name
    check ((role_name) :: text ~ '(^([ A-Za-zА-Яа-я-]+)$)' :: text),
  role_id   serial      not null
    constraint role_pkey
    primary key
);

create table filmworkers_role
(
  role_id       integer not null
    constraint r_16
    references role,
  filmworker_id integer not null
    constraint r_17
    references filmworker,
  film_id       integer not null
    constraint r_18
    references film,
  constraint filmworkers_role_pkey
  primary key (role_id, filmworker_id, film_id)
);

create table film_user
(
  name     varchar(64),
  surname  varchar(64),
  birthday integer
    constraint birthday
    check (birthday > 0),
  login    varchar(32)                                 not null
    constraint film_user_pkey
    primary key
    constraint login
    check ((login) :: text ~ '(^([A-Za-zА-Яа-я0-9]+)$)' :: text),
  password varchar(128)                                not null
    constraint password
    check ((password) :: text ~ '(^([A-Za-zА-Яа-я0-9]+)$)' :: text),
  role     varchar(1) default 'U' :: character varying not null
);

create table score_film_to_user
(
  film_id integer     not null
    constraint r_21
    references film,
  score   integer     not null
    constraint score
    check ((score >= 0) AND (score <= 10)),
  login   varchar(64) not null
    constraint r_22
    references film_user,
  role    varchar(20) not null,
  constraint score_film_to_user_pkey
  primary key (film_id, login)
);

create table user_role
(
  role varchar(1) not null
    constraint user_role_role_pk
    primary key,
  name varchar(64)
);

create view get_filmworkers_role as
  SELECT
    filmworkers_role.role_id,
    filmworkers_role.filmworker_id,
    filmworkers_role.film_id
  FROM filmworkers_role;

create view get_role as
  SELECT
    role.role_name,
    role.role_id
  FROM role
  ORDER BY role.role_id;

create view get_score_film_to_user as
  SELECT
    score_film_to_user.film_id,
    score_film_to_user.score,
    score_film_to_user.login,
    score_film_to_user.role
  FROM score_film_to_user;

create view get_user_role as
  SELECT
    user_role.role,
    user_role.name
  FROM user_role;

create view get_film_user as
  SELECT
    film_user.name,
    film_user.surname,
    film_user.birthday,
    film_user.login,
    film_user.password,
    film_user.role
  FROM film_user;

create view get_country as
  SELECT
    country.country_name,
    country.country_id
  FROM country
  ORDER BY country.country_id;

create view get_genre as
  SELECT
    genre.genre,
    genre.genre_id
  FROM genre
  ORDER BY genre.genre_id;

create view get_filmworker as
  SELECT
    filmworker.name,
    filmworker.surname,
    filmworker.birthday,
    filmworker.filmworker_id,
    filmworker.country_id
  FROM filmworker;

create view get_film as
  SELECT
    film.release_date,
    film.budget,
    film.score,
    film.critic_score,
    film.comment,
    film.film_id,
    film.genre_id,
    film.country_id,
    film.name
  FROM film;

create function add_user(username character varying, pass character varying)
  returns character varying
language plpgsql
as $$
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

create function add_film(film_name character varying, genre integer)
  returns integer
language plpgsql
as $$
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

create function delete_user(username character varying)
  returns void
language plpgsql
as $$
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

create function delete_film(my_film_id integer)
  returns void
language plpgsql
as $$
BEGIN

  DELETE FROM score_film_to_user
  WHERE film_id = film_id;

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

create function edit_user(my_name  character varying, my_surname character varying, my_birthday integer,
                          username character varying, pass character varying, my_role character varying)
  returns void
language plpgsql
as $$
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

create function add_score(user_name character varying, my_film_id integer, my_score double precision,
                          my_role   character varying)
  returns void
language plpgsql
as $$
DECLARE id INTEGER;

BEGIN
  INSERT INTO public.score_film_to_user (login, film_id, score, role)
  VALUES (user_name, my_film_id, my_score, my_role);

  IF my_role = 'C'
  THEN
    UPDATE public.film
    SET critic_score = (
      SELECT AVG(score)
      FROM score_film_to_user
      WHERE film_id = my_film_id
    )
    WHERE film_id = my_film_id;
  ELSE
    UPDATE public.film
    SET score = (
      SELECT AVG(score)
      FROM score_film_to_user
      WHERE film_id = my_film_id
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

create function delete_score(user_name character varying, my_film_id integer)
  returns void
language plpgsql
as $$
DECLARE id INTEGER;

BEGIN
  DELETE FROM score_film_to_user
  WHERE film_id = my_film_id AND login = user_name;

  IF (
       SELECT role
       FROM film_user
       WHERE login = user_name
     ) = 'C'
  THEN
    UPDATE public.film
    SET critic_score = (
      SELECT AVG(critic_score)
      FROM score_film_to_user
      WHERE film_id = my_film_id
    )
    WHERE film_id = my_film_id;
  ELSE
    UPDATE public.film
    SET score = (
      SELECT AVG(score)
      FROM score_film_to_user
      WHERE film_id = my_film_id
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

create function edit_film(my_release_date date, my_budget integer, my_comment character varying, my_genre_id integer,
                          my_country_id   integer, my_name character varying, my_film_id integer)
  returns void
language plpgsql
as $$
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

create function edit_filmworker(my_name       character varying, my_surname character varying, my_birthday integer,
                                my_country_id integer, my_filmworker_id integer)
  returns void
language plpgsql
as $$
BEGIN
  UPDATE public.filmworker
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

create function delete_filmworkers_role(my_role_id integer, my_filmworker_id integer, my_film_id integer)
  returns void
language plpgsql
as $$
BEGIN

  DELETE FROM public.filmworkers_role
  WHERE filmworker_id = my_filmworker_id
        AND role_id = my_role_id
        AND film_id = my_film_id;

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

create function delete_filmworker(my_filmworker_id integer)
  returns void
language plpgsql
as $$
BEGIN

  DELETE FROM public.filmworkers_role
  WHERE filmworker_id = my_filmworker_id;

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

create function add_filmworker(filmworker_name     character varying, filmworker_surname character varying,
                               filmworker_birthday integer, filmworker_country_id integer)
  returns integer
language plpgsql
as $$
DECLARE id INTEGER;

BEGIN
  INSERT INTO public.filmworker (name, surname, birthday, country_id)
  VALUES (filmworker_name, filmworker_surname, filmworker_birthday, filmworker_country_id)
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

create function add_filmworkers_role(my_role_id integer, my_filmworker_id integer, my_film_id integer)
  returns void
language plpgsql
as $$
BEGIN

  INSERT INTO public.filmworkers_role (role_id, filmworker_id, film_id)
  VALUES (my_role_id, my_filmworker_id, my_film_id);

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

create function add_film_review(my_film_id integer, my_login character varying, my_review character varying)
  returns void
language plpgsql
as $$
BEGIN
  IF (
       SELECT review
       FROM film
       WHERE film_id = my_film_id
     ) -> my_login IS NULL
  THEN
    UPDATE film
    SET review = review || jsonb_build_object(my_login, my_review)
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

create function delete_film_review(my_film_id integer, my_login character varying)
  returns void
language plpgsql
as $$
BEGIN
  UPDATE film
  SET review = review - my_login
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

create function get_film_review(my_film_id integer)
  returns TABLE(key text, value text)
language plpgsql
as $$
BEGIN
  RETURN QUERY
  SELECT
    jsonb_object_keys(review)                     as keys,
    (review -> jsonb_object_keys(review)) :: TEXT as value
  FROM film
  WHERE film_id = my_film_id;
END;
$$;


