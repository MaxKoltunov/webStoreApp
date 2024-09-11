
CREATE TABLE IF NOT EXISTS mainschema.levelsOfLoyalty (
    id BIGINT PRIMARY KEY DEFAULT nextval('mainschema.levelsOfLoyalty_id_seq'),
    level_name VARCHAR(255) NOT NULL UNIQUE
);

INSERT INTO mainschema.levelsOfLoyalty (level_name)
VALUES ('default'),
    ('bronze'),
    ('silver'),
    ('gold');

CREATE TABLE IF NOT EXISTS mainschema.roles (
    id BIGINT PRIMARY KEY DEFAULT nextval('mainschema.roles_id_seq'),
    role_name VARCHAR(255) NOT NULL UNIQUE
);

INSERT INTO mainschema.roles (role_name)
VALUES ('ROLE_USER'),
    ('ROLE_ADMIN');

CREATE TABLE IF NOT EXISTS mainschema.discounts (
    id BIGINT PRIMARY KEY DEFAULT nextval('mainschema.discounts_id_seq'),
    name VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    product_type VARCHAR(255) NOT NULL,
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
	amount BIGINT NOT NULL,
    FOREIGN KEY (discount_id) REFERENCES mainschema.discounts(id)
);

CREATE TABLE IF NOT EXISTS mainschema.users (
    id BIGINT PRIMARY KEY DEFAULT nextval('mainschema.users_id_seq'),
    name VARCHAR(255) NOT NULL,
    birth_day DATE NOT NULL,
    phone_number VARCHAR(255) NOT NULL UNIQUE,
    level_name VARCHAR(255) NOT NULL,
	existing BOOLEAN NOT NULL,
	role_name VARCHAR(255) NOT NULL,
	password VARCHAR(255) NOT NULL,
    FOREIGN KEY (level_name) REFERENCES mainschema.levelsOfLoyalty(level_name),
    FOREIGN KEY (role_name) REFERENCES mainschema.roles(role_name)
);

CREATE TABLE IF NOT EXISTS mainschema.cart (
    user_id BIGINT NOT NULL,
	product_id BIGINT NOT NULL,
	amount BIGINT NOT NULL,
	PRIMARY KEY (user_id, product_id),
	FOREIGN KEY (user_id) REFERENCES mainschema.users(id),
	FOREIGN KEY (product_id) REFERENCES mainschema.products(id)
);