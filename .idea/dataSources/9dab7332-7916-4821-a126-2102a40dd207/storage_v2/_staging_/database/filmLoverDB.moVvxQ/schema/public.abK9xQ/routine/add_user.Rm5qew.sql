create function add_film_review()
  returns json_each
language plpgsql
as $$

BEGIN
  --{"login": "review", "login2": "review", "login3": "review"}

  SELECT * FROM json_each (
      SELECT review
      FROM film
      WHERE film_id = my_film_id
  );

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

