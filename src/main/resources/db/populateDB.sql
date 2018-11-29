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

insert into users (name, email, password)
values ('User', 'user@yandex.ru', 'password');

insert into users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

insert into user_roles (role, user_id)
values ('ROLE_USER', 100000);
insert into user_roles (role, user_id)
values ('ROLE_ADMIN', 100001);

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
--delete from meals where id = 100010;
--select * from global_seq;
/*analyze meals;
explain select *
        from users
               join meals m on users.id = m.user_id;

select *
from users
       join meals m on users.id = m.user_id;*/