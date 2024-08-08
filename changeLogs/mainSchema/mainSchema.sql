CREATE TABLE IF NOT EXISTS mainschema.discounts (
    id BIGINT PRIMARY KEY DEFAULT nextval('mainschema.discounts_id_seq'),
    name VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL
);


CREATE TABLE IF NOT EXISTS mainschema.products (
    id BIGINT PRIMARY KEY DEFAULT nextval('mainschema.products_id_seq'),
    name VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    brand VARCHAR(255) NOT NULL,
    cost BIGINT NOT NULL,
    arrival_date TIMESTAMP NOT NULL,
    discount_id BIGINT,
    FOREIGN KEY (discount_id) REFERENCES mainschema.discounts(id)
);



CREATE TABLE IF NOT EXISTS mainschema.users (
    id BIGINT PRIMARY KEY DEFAULT nextval('mainschema.users_id_seq'),
    name VARCHAR(255) NOT NULL,
    birth_day DATE NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    level_name_id BIGINT NOT NULL,
    FOREIGN KEY (level_name_id) REFERENCES mainschema.levelsOfLoyalty(id)
);


CREATE TABLE IF NOT EXISTS mainschema.levelsOfLoyalty (
    id BIGINT PRIMARY KEY DEFAULT nextval('mainschema.levelsOfLoyalty_id_seq'),
    level_name VARCHAR(255) NOT NULL UNIQUE
);

INSERT INTO mainschema.levelsOfLoyalty (level_name)
VALUES ('default'),
    ('bronze'),
    ('silver'),
    ('gold');
