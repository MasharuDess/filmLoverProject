create or replace function delete_film(my_film_id integer)
  returns void
language plpgsql
as $$
BEGIN

  DELETE FROM score_film_to_user
  WHERE film_id = my_film_id;

  DELETE FROM filmworkers_role
  WHERE film_id = my_film_id;

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

  --WHEN OTHERS
  --  THEN
  --    RAISE EXCEPTION 'Unknown error';
  --    RETURN;
END;
$$;

