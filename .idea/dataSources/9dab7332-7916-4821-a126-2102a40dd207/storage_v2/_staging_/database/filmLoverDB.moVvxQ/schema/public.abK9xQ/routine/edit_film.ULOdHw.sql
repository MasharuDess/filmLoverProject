create or replace function edit_film(my_release_date date, my_budget integer, my_comment character varying, my_genre_id integer, my_country_id integer, my_name character varying, my_film_id integer)
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

