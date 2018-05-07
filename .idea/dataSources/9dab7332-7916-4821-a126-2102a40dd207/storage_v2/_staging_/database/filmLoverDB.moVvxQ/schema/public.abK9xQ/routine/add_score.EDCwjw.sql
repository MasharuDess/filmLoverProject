create or replace function add_score(user_name character varying, my_film_id integer, my_score double precision, my_role character varying)
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

