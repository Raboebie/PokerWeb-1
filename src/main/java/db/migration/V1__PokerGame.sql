CREATE TABLE user(
    user_name VARCHAR(100) NOT NULL,
    password VARCHAR(1000) NOT NULL,
    PRIMARY KEY(user_name)
);

CREATE TABLE game(
    game_id INT NOT NULL AUTO_INCREMENT,
    game_name VARCHAR(100) NOT NULL,
    game_date TIMESTAMP NOT NULL,
    PRIMARY KEY(game_name)
);

CREATE TABLE game_user(
    user_name VARCHAR(100) NOT NULL,
    game_id INT NOT NULL,
    hand VARCHAR(100) NOT NULL,
    CONSTRAINT user_name_fk FOREIGN KEY(user_name) REFERENCES user(user_name),
    CONSTRAINT game_id_fk FOREIGN KEY(game_id) REFERENCES game(game_id),
    PRIMARY KEY(user_name, game_id)
);