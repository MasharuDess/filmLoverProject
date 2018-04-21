CREATE FUNCTION add_film(film_name character varying, genre integer)
  RETURNS integer
LANGUAGE plpgsql
AS $$
DECLARE id INTEGER;

    BEGIN
        INSERT INTO public.film ( name, genre_id )
        VALUES( film_name, genre )
        RETURNING film_id INTO id;
        RETURN id;

        EXCEPTION
            WHEN not_null_violation THEN
                RAISE EXCEPTION 'Field cant be NULL';
                RETURN NULL;

            WHEN unique_violation THEN
                RAISE EXCEPTION 'Username must be unique';
                RETURN NULL;

            WHEN check_violation THEN
                RAISE EXCEPTION 'Wrong format of field';
                RETURN NULL;

            WHEN others THEN
                RAISE EXCEPTION 'Unknown error';
                RETURN NULL;
    END;
$$;