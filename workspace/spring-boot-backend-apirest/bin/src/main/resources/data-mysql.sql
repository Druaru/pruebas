-- CREACIÓN DE MODULOS
INSERT INTO module (name, base_path) VALUES ('PRODUCT', '/products');
INSERT INTO module (name, base_path) VALUES ('CATEGORY', '/categories');
INSERT INTO module (name, base_path) VALUES ('CUSTOMER', '/customers');
INSERT INTO module (name, base_path) VALUES ('AUTH', '/auth');

-- CREACIÓN DE OPERACIONES
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_ALL_PRODUCTS','', 'GET', false, 1);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_ONE_PRODUCT','/[0-9]*', 'GET', false, 1);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('CREATE_ONE_PRODUCT','', 'POST', false, 1);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('UPDATE_ONE_PRODUCT','/[0-9]*', 'PUT', false, 1);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('DISABLE_ONE_PRODUCT','/[0-9]*/disabled', 'PUT', false, 1);

INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_ALL_CATEGORIES','', 'GET', false, 2);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_ONE_CATEGORY','/[0-9]*', 'GET', false, 2);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('CREATE_ONE_CATEGORY','', 'POST', false, 2);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('UPDATE_ONE_CATEGORY','/[0-9]*', 'PUT', false, 2);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('DISABLE_ONE_CATEGORY','/[0-9]*/disabled', 'PUT', false, 2);

INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_ALL_CUSTOMERS','', 'GET', false, 3);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('REGISTER_ONE','', 'POST', true, 3);

INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('AUTHENTICATE','/authenticate', 'POST', true, 4);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('VALIDATE-TOKEN','/validate-token', 'GET', true, 4);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_MY_PROFILE','/profile','GET', false, 4);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('LOGOUT','/logout','POST', true, 4);


-- CREACIÓN DE ROLES
INSERT INTO role (name) VALUES ('CUSTOMER');
INSERT INTO role (name) VALUES ('ASSISTANT_ADMINISTRATOR');
INSERT INTO role (name) VALUES ('ADMINISTRATOR');

-- CREACIÓN DE PERMISOS
INSERT INTO granted_permission (role_id, operation_id) VALUES (1, 15);

INSERT INTO granted_permission (role_id, operation_id) VALUES (2, 1);
INSERT INTO granted_permission (role_id, operation_id) VALUES (2, 2);
INSERT INTO granted_permission (role_id, operation_id) VALUES (2, 4);
INSERT INTO granted_permission (role_id, operation_id) VALUES (2, 6);
INSERT INTO granted_permission (role_id, operation_id) VALUES (2, 7);
INSERT INTO granted_permission (role_id, operation_id) VALUES (2, 9);
INSERT INTO granted_permission (role_id, operation_id) VALUES (2, 15);

INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 1);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 2);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 3);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 4);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 5);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 6);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 7);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 8);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 9);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 10);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 15);

-- CREACIÓN DE USUARIOS
INSERT INTO user (username, name, password, role_id) VALUES ('lmarquez', 'luis márquez', '$2a$10$ywh1O2EwghHmFIMGeHgsx.9lMw5IXpg4jafeFS.Oi6nFv0181gHli', 1);
INSERT INTO user (username, name, password, role_id) VALUES ('fperez', 'fulano pérez', '$2a$10$V29z7/qC9wpHfzRMxGOHye5RMAxCid2/MzJalk0dsiA3zZ9CJfub.', 2);
INSERT INTO user (username, name, password, role_id) VALUES ('mhernandez', 'mengano hernández', '$2a$10$TMbMuEZ8utU5iq8MOoxpmOc6QWQuYuwgx1xJF8lSMNkKP3hIrwYFG', 3);
INSERT INTO user (username, name, password, role_id) VALUES ('gcanas', 'guillermo canas', '$2a$10$TMbMuEZ8utU5iq8MOoxpmOc6QWQuYuwgx1xJF8lSMNkKP3hIrwYFG', 3);




INSERT INTO regiones (id, nombre) VALUES (1, 'Sudamérica');
INSERT INTO regiones (id, nombre) VALUES (2, 'Norteamérica');
INSERT INTO regiones (id, nombre) VALUES (3, 'Europa');
INSERT INTO regiones (id, nombre) VALUES (4, 'Asia');
INSERT INTO regiones (id, nombre) VALUES (5, 'África');
INSERT INTO regiones (id, nombre) VALUES (6, 'Oceanía');
INSERT INTO regiones (id, nombre) VALUES (7, 'Antártida');
INSERT INTO regiones (id, nombre) VALUES (8, 'América Central');
INSERT INTO regiones (id, nombre) VALUES (9, 'América del Sur');


INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(1, 'Juan', 'Perez', 'juan.perez@email.com', '2023-09-14');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(2, 'Maria', 'Gomez', 'maria.gomez@email.com', '2023-09-13');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(2, 'Pedro', 'Lopez', 'pedro.lopez@email.com', '2023-09-12');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(3, 'Luisa', 'Martinez', 'luisa.martinez@email.com', '2023-09-11');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(4, 'Ana', 'Rodriguez', 'ana.rodriguez@email.com', '2023-09-10');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(1, 'Carlos', 'Sanchez', 'carlos.sanchez@email.com', '2023-09-09');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(6, 'Laura', 'Fernandez', 'laura.fernandez@email.com', '2023-09-08');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(9, 'Miguel', 'Ramirez', 'miguel.ramirez@email.com', '2023-09-07');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(9, 'Isabel', 'Garcia', 'isabel.garcia@email.com', '2023-09-06');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(5, 'David', 'Hernandez', 'david.hernandez@email.com', '2023-09-05');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(7, 'Elena', 'Lopez', 'elena.lopez@email.com', '2023-09-04');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(7,'Francisco', 'Gonzalez', 'francisco.gonzalez@email.com', '2023-09-03');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(3, 'Elpo', 'Llagorda', 'llagorda.gonzalez@email.com', '2023-09-03');
