CREATE SEQUENCE hibernate_sequence START WITH 6 INCREMENT BY 1;

CREATE TABLE library (
library_id BIGINT NOT NULL,
name VARCHAR(255) NOT NULL,
city VARCHAR(255) NOT NULL,
PRIMARY KEY (library_id)
);

INSERT INTO library(library_id, name, city)
VALUES
(0, 'Deichman', 'Oslo'),
(1, 'Papirbredden', 'Drammen'),
(2, 'Tønsberg bibliotek', 'Tønsberg'),
(3, 'Asker bibliotek', 'Asker'),
(4, 'Bergen bibliotek', 'Bergen');