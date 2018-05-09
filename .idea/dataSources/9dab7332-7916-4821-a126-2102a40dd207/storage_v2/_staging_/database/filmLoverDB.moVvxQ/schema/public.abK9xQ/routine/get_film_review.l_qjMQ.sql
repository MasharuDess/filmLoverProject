create or replace function get_film_review(my_film_id integer)
  returns TABLE(key text, value text)
language plpgsql
as $$
BEGIN
  RETURN QUERY
  SELECT jsonb_object_keys(review) as keys,
    (review -> jsonb_object_keys(review))::TEXT as value
  FROM film
  WHERE film_id = my_film_id;
END;
$$;

