DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;
ALTER SEQUENCE meal_seq RESTART WITH 1000000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals(datetime, description,calories,user_id) VALUES
  ('2015-05-30 13:00', 'Завтрак', 500,100000),
  ('2015-05-30 14:00', 'Обед', 1000,100000),
  ('2015-05-30 17:00', 'Ужин', 500,100000),
  ('2015-05-31 13:00', 'Завтрак', 500,100000),
  ('2015-05-31 15:00', 'Обед', 1000,100000),
  ('2015-05-31 18:00', 'Ужин', 1000,100000);