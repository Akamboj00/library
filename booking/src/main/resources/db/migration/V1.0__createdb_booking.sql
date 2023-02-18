CREATE SEQUENCE hibernate_sequence START WITH 6 INCREMENT BY 1;

CREATE TABLE booking (
    booking_id BIGINT NOT NULL,
    origin_lib_id BIGINT NOT NULL,
    destination_lib_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,
    PRIMARY KEY (booking_id)
);

INSERT INTO booking(BOOKING_ID, ORIGIN_LIB_ID, DESTINATION_LIB_ID, BOOK_ID)
VALUES
    (0, 1, 2, 3),
    (1, 2, 3, 4),
    (2, 3, 4, 5),
    (3, 4, 5, 6),
    (4, 5, 6, 7);
