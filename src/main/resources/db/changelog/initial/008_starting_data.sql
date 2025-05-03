INSERT INTO users (email, password, role)
VALUES ('user@example.com', '$2y$10$rCnVmAMe.lOW536A0FCXHOA3kIqRRI1cWlSnYvo1cxvOcdlIk6igK', 'USER');

INSERT INTO users (email, password, role)
VALUES ('admin@example.com', '$2y$10$rCnVmAMe.lOW536A0FCXHOA3kIqRRI1cWlSnYvo1cxvOcdlIk6igK', 'ADMIN');


INSERT INTO categories (name)
VALUES ('Documents');

INSERT INTO categories (name)
VALUES ('Images');

INSERT INTO files (filename, is_public, download_key, download_count, upload_date, user_id, category_id)
VALUES (
           'some_upload.pdf',
           TRUE,
           NULL,
           0,
           CURRENT_TIMESTAMP,
           (SELECT id FROM users WHERE email = 'user@example.com'),
           (SELECT id FROM categories WHERE name = 'Documents')
       );

INSERT INTO files (filename, is_public, download_key, download_count, upload_date, user_id, category_id)
VALUES (
           'private_image.jpg',
           FALSE,
           NULL,
           0,
           CURRENT_TIMESTAMP,
           (SELECT id FROM users WHERE email = 'user@example.com'),
           (SELECT id FROM categories WHERE name = 'Images')
       );
INSERT INTO usr_roles (usr_id, role_id)
values (
        (select id from users where email = 'user@example.com'),
        (select id from roles r where  r.role = 'USER')
       );

INSERT INTO usr_roles (usr_id, role_id)
values (
           (select id from users where email = 'admin@example.com'),
           (select id from roles r where  r.role = 'ADMIN')
       );