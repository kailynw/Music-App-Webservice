CREATE TABLE songs(
    song_id PRIMARY KEY GENERATED ALWAYS as IDENTITY,
    song_name VARCHAR(60),
    image_uri_location VARCHAR(200),
    song_uri_location VARCHAR(200),
    number_of_likes INTEGER,
    posted_by VARCHAR(60) FOREIGN
    posted_date TIMESTAMP
)

CREATE TABLE user(
    user_id IDENTITY PRIMARY,
    song_name VARCHAR(60),
    image_uri_location VARCHAR(200),
    song_uri_location VARCHAR(200),
    number_of_likes INTEGER,
    posted_by VARCHAR(60) FOREIGN
    posted_date TIMESTAMP
)