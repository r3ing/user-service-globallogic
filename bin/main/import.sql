
INSERT INTO users (id, name, password, email, active, created, last_login) VALUES ('ff8c620e-1147-42dc-8a8f-976129bfc867', 'Administrador', '$2a$10$BbsMolrXnH71CSie01nM.eHNBOLdE7jqGr3/QVXJbg5byYgGG2gaK','admin@system.com', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO phones (user_id ,number, citycode, contrycode) VALUES ('ff8c620e-1147-42dc-8a8f-976129bfc867', '1234567', '1', '57');


INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (name) VALUES ('ROLE_USER');

INSERT INTO users_roles (user_id, role_id) VALUES ('ff8c620e-1147-42dc-8a8f-976129bfc867', 1);