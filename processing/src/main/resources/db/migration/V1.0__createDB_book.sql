CREATE SEQUENCE hibernate_sequence START WITH 12 INCREMENT BY 1;

CREATE TABLE book (
book_id BIGINT NOT NULL,
name VARCHAR(255) NOT NULL,
author VARCHAR(255) NOT NULL,
number_of_copies INTEGER NOT NULL CHECK (number_of_copies >= 1 ),
PRIMARY KEY (book_id)
);

INSERT INTO book (book_id, name, author, number_of_copies) VALUES
(0, 'To Kill a Mockingbird', 'Harper Lee', 3),
(1, '1984','George Orwell', 10),
(2, 'Pride and Prejudice', 'Jane Austen', 2),
(3, 'The Great Gatsby', 'F. Scott Fitzgerald', 1),
(4, 'One Hundred Years of Solitude', 'Gabriel García Márquez', 13),
(5, 'The Catcher in the Rye','J.D. Salinger', 3),
(6, 'The Lord of the Rings','J.R.R. Tolkien', 14),
(7, 'The Hitchhikers Guide to the Galaxy','Douglas Adams', 12),
(8, 'The Adventures of Sherlock Holmes','Arthur Conan Doyle', 10),
(9, 'Brave New World','Aldous Huxley', 1);

