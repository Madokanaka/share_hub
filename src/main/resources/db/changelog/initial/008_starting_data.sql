INSERT INTO users (email, password, role)
VALUES ('user@example.com', 'password123', 'USER');

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