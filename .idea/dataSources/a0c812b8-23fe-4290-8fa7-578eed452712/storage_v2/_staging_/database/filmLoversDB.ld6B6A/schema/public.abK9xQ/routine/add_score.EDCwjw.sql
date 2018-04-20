CREATE FUNCTION delete_score(user_name character varying, my_film_id integer)
  RETURNS void
LANGUAGE plpgsql
AS $$
DECLARE id INTEGER;

    BEGIN
        DELETE FROM score_film_to_user
        WHERE film_id = my_film_id AND login = user_name;

        IF my_role = 'C' THEN
            UPDATE public.film
            SET critic_score = (
                SELECT AVG( critic_score )
                FROM score_film_to_user
                WHERE film_id = my_film_id
            ) WHERE film_id = my_film_id;
        ELSE
            UPDATE public.film
            SET score = (
                SELECT AVG( score )
                FROM score_film_to_user
                WHERE film_id = my_film_id
            ) WHERE film_id = my_film_id;
        END IF;

        EXCEPTION
            WHEN not_null_violation THEN
                RAISE EXCEPTION 'Field cant be NULL';
                RETURN;

            WHEN unique_violation THEN
                RAISE EXCEPTION 'Username must be unique';
                RETURN;

            WHEN check_violation THEN
                RAISE EXCEPTION 'Wrong format of field';
                RETURN;

            WHEN others THEN
                RAISE EXCEPTION 'Unknown error';
                RETURN;
    END;
$$;

