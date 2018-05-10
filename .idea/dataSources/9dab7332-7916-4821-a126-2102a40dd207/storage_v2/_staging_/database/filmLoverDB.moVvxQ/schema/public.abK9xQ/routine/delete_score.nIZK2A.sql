create or replace function delete_score(user_name character varying, my_film_id integer)
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
      SELECT AVG(score_film_to_user.score)
      FROM score_film_to_user
      WHERE film_id = my_film_id AND role = 'C'
    )
    WHERE film_id = my_film_id;
  ELSE
    UPDATE public.film
    SET score = (
      SELECT AVG(score_film_to_user.score)
      FROM score_film_to_user
      WHERE film_id = my_film_id AND role <> 'C'
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

  --WHEN OTHERS
  --  THEN
  --    RAISE EXCEPTION 'Unknown error';
  --    RETURN;
END;
$$;

