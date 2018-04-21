CREATE VIEW get_country AS
  SELECT country.country_name,
    country.country_id
  FROM country
  ORDER BY country_id;

