CREATE TABLE IF NOT EXISTS Task (
    id SERIAL PRIMARY KEY,
    title VARCHAR(250) NOT NULL,
    status VARCHAR(20) NOT NULL,
    user_id INT NOT NULL
);

CREATE TABLE IF NOT EXISTS MyUser (
    user_id SERIAL PRIMARY KEY ,
    username VARCHAR(250) NOT NULL,
    password VARCHAR(250) NOT NULL
);

