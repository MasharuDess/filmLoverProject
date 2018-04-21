CREATE VIEW get_genre AS
  SELECT genre.genre,
    genre.genre_id
  FROM genre
  ORDER BY genre_id;

