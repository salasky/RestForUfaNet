DROP TABLE IF EXISTS clients;

CREATE TABLE clients
(
    id    BIGINT NOT NULL,
    name  VARCHAR(255),
    email VARCHAR(255),
    phone VARCHAR(255),
    CONSTRAINT pk_clients PRIMARY KEY (id)
);

ALTER TABLE clients
    ADD CONSTRAINT uc_clients_email UNIQUE (email);

ALTER TABLE clients
    ADD CONSTRAINT uc_clients_phone UNIQUE (phone);

CREATE TABLE orders
(
    id      BIGINT NOT NULL,
    date    VARCHAR(255),
    time    VARCHAR(255),
    user_id BIGINT,
    CONSTRAINT pk_orders PRIMARY KEY (id)
);

ALTER TABLE orders
    ADD CONSTRAINT FK_ORDERS_ON_USER FOREIGN KEY (user_id) REFERENCES clients (id);