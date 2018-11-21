DROP TABLE IF EXISTS USER_ROLES;
drop table if exists MEALS;
DROP TABLE IF EXISTS USERS;
DROP SEQUENCE IF EXISTS GLOBAL_SEQ;

CREATE SEQUENCE GLOBAL_SEQ START 100000;

CREATE TABLE users
(
  id         INTEGER PRIMARY KEY DEFAULT nextval('GLOBAL_SEQ'),
  name       VARCHAR,
  email      VARCHAR NOT NULL,
  password   VARCHAR NOT NULL,
  registered TIMESTAMP           DEFAULT now(),
  enabled    BOOLEAN             DEFAULT TRUE,
  calories_per_day INTEGER DEFAULT 2000 NOT NULL
);

CREATE UNIQUE INDEX unique_email ON users (email);

CREATE TABLE USER_ROLES
(
  user_id INTEGER NOT NULL,
  role    VARCHAR,
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

create table meals(
  id          INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  dateTime    TIMESTAMP,
  description TEXT    NOT NULL,
  calories    INTEGER NOT NULL,
  user_id     INTEGER NOT NULL,
  CONSTRAINT "fk_m.user_id __users" FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
CREATE INDEX idx_meals_userId_dateTime ON meals using btree(user_id, dateTime DESC);
