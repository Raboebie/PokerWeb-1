CREATE TABLE user(
    user_name VARCHAR(100) NOT NULL,
    password VARCHAR(1000) NOT NULL,
    PRIMARY KEY(user_name)
);

CREATE TABLE game(
    game_name VARCHAR(100) NOT NULL,
    game_date DATE NOT NULL,
    PRIMARY KEY(game_name)
);

CREATE TABLE game_user(
    user_name VARCHAR(100) NOT NULL,
    game_name VARCHAR(100) NOT NULL,
    hand VARCHAR(100) NOT NULL,
    CONSTRAINT user_name_fk FOREIGN KEY(user_name) REFERENCES user(user_name),
    CONSTRAINT game_name_fk FOREIGN KEY(game_name) REFERENCES game(game_name),
    PRIMARY KEY(user_name, game_name)
);