create or replace function get_film_review(my_film_id INTEGER)
  returns TABLE(key character varying, value character varying)
language plpgsql
as $$
BEGIN
  RETURN QUERY
  SELECT jsonb_object_keys( review ) as key, jsonb_array_elements( review ) as value
    FROM film
    WHERE my_film_id = film_id;

END;
$$;

