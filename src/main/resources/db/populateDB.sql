-- noinspection SqlWithoutWhereForFile
-- truncate table meals;
-- truncate TABLE user_roles;
-- truncate table users;
delete
from users;
delete
from meals;
delete
from user_roles;
alter sequence global_seq restart with 100000;

insert into users (name, email, password, calories_per_day)
values ('User', 'user@yandex.ru', 'password', 2005);

insert into users (name, email, password, calories_per_day)
VALUES ('Admin', 'admin@gmail.com', 'admin', 1900);;

insert into user_roles (role, user_id)
values ('ROLE_USER', 100000),
       ('ROLE_ADMIN', 100001),
       ('ROLE_USER', 100001);

insert into meals(datetime, description, calories, user_id)
values ('2018-11-18 13:30:00', 'обед', 800, 100000),
       ('2018-11-18 21:25:00', 'ужин', 500, 100000),
       ('2018-11-18 08:00:00', 'завтрак', 600, 100000),
       ('2018-11-17 07:30:00', 'завтрак', 1000, 100000),
       ('2018-11-17 13:15:00', 'обед', 1000, 100000),
       ('2018-11-17 20:45:00', 'ужин', 1000, 100000),
       ('2018-11-19 08:00:00', 'завтрак', 600, 100001),
       ('2018-11-19 14:35:00', 'обед', 800, 100001),
       ('2018-11-20 07:35:00', 'завтрак', 600, 100001);
