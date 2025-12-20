-- Working schema.sql (PostgreSQL + R2DBC)
CREATE TABLE IF NOT EXISTS STUDENT (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age INT NOT NULL
);

