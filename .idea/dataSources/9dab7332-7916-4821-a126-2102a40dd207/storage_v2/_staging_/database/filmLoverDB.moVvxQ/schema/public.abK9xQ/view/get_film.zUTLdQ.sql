create or replace view get_film as
  SELECT film.release_date,
    film.budget,
    film.score,
    film.critic_score,
    film.comment,
    film.film_id,
    film.genre_id,
    film.country_id,
    film.name
   FROM film;

