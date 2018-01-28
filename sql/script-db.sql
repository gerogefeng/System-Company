﻿CREATE EXTENSION IF NOT EXISTS pgcrypto; --connect to library with functions

DROP TABLE IF EXISTS employees CASCADE;
DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS passports CASCADE;
DROP TABLE IF EXISTS driver_licences CASCADE;
DROP TABLE IF EXISTS category CASCADE;
DROP TABLE IF EXISTS driver_licences_category CASCADE;
DROP TABLE IF EXISTS private_cards CASCADE;
DROP TABLE IF EXISTS log_employee CASCADE;
DROP TABLE IF EXISTS orders CASCADE;
DROP TABLE IF EXISTS reports CASCADE;
DROP TABLE IF EXISTS organizations CASCADE;
DROP TABLE IF EXISTS places CASCADE;
DROP TABLE IF EXISTS posts CASCADE;
DROP TABLE IF EXISTS employee_post CASCADE;
DROP TABLE IF EXISTS departure CASCADE;
DROP TABLE IF EXISTS equipment CASCADE;
DROP TABLE IF EXISTS equipment_departure CASCADE;
DROP TABLE IF EXISTS mark CASCADE;
DROP TABLE IF EXISTS model CASCADE;
DROP TABLE IF EXISTS transports CASCADE;
DROP TABLE IF EXISTS transport_rental CASCADE;

-------------EMPLOYEE----------------

CREATE TABLE IF NOT EXISTS employees (
  id            SERIAL NOT NULL PRIMARY KEY,
  name          TEXT   NOT NULL,
  last_name     TEXT   NOT NULL,
  patronymic    TEXT   NOT NULL,
  url_photo     TEXT   NULL,
  delete_status BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS roles (
  id    SERIAL NOT NULL PRIMARY KEY,
  title TEXT   NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
  id            INTEGER NOT NULL PRIMARY KEY,
  login         TEXT    NOT NULL,
  pass          TEXT    NOT NULL,
  id_role       INTEGER REFERENCES roles (id),
  id_employee   INTEGER NULL UNIQUE REFERENCES employees (id),
  delete_status BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS passports (
  id              SERIAL            NOT NULL PRIMARY KEY,
  nationality     TEXT              NOT NULL,
  department      TEXT              NOT NULL,
  serial_passport TEXT DEFAULT 'МС' NULL,
  number_passport TEXT,
  date_out        DATE              NOT NULL,
  date_in         DATE              NOT NULL,
  id_employee     INTEGER REFERENCES employees (id) ON DELETE CASCADE,
  delete_status   BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS driver_licences (
  id            SERIAL NOT NULL PRIMARY KEY,
  department    TEXT   NOT NULL,
  number        TEXT   NULL,
  date_out      DATE,
  date_in       DATE,
  id_employee   INTEGER REFERENCES employees (id) ON DELETE CASCADE,
  delete_status BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS category (
  id    SERIAL NOT NULL PRIMARY KEY,
  title TEXT   NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS driver_licences_category (
  id               SERIAL NOT NULL PRIMARY KEY,
  fk_category      INTEGER REFERENCES category (id),
  fk_driverlicence INTEGER REFERENCES driver_licences (id),
  UNIQUE (fk_category, fk_driverlicence)
);

CREATE TABLE IF NOT EXISTS private_cards (
  id                   SERIAL NOT NULL PRIMARY KEY REFERENCES employees (id) ON DELETE CASCADE,
  data_birth           DATE   NOT NULL,
  private_number_phone TEXT   NULL,
  email                TEXT   NULL,
  delete_status        BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS log_employee (
  id        SERIAL NOT NULL PRIMARY KEY,
  operation TEXT   NOT NULL,
  datetime  DATE   NOT NULL
);

CREATE TABLE IF NOT EXISTS posts (
  id    SERIAL       NOT NULL PRIMARY KEY,
  title VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS employee_post (
  id            SERIAL                 NOT NULL PRIMARY KEY,
  id_employee   INTEGER                NOT NULL REFERENCES employees (id),
  id_post       INTEGER                NOT NULL REFERENCES posts (id),
  date_start    DATE                   NOT NULL,
  date_end      DATE                   NOT NULL,
  delete_status BOOLEAN DEFAULT FALSE  NOT NULL
);

-------------TRANSPORT----------------

CREATE TABLE IF NOT EXISTS mark (
  id   SERIAL       NOT NULL PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);


CREATE TABLE IF NOT EXISTS model (
  id      SERIAL       NOT NULL PRIMARY KEY,
  id_mark INTEGER      NOT NULL REFERENCES mark (id),
  name    VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS transports (
  id            SERIAL                 NOT NULL PRIMARY KEY,
  id_mark       INTEGER                NOT NULL REFERENCES mark (id),
  id_model      INTEGER                NOT NULL REFERENCES model (id),
  engine        VARCHAR(40)            NOT NULL,
  capacity_kg   INTEGER                NOT NULL CHECK (capacity_kg > 0),
  status        INTEGER                NOT NULL,
  seats         INTEGER                NOT NULL CHECK (seats > 0),
  photo_url     VARCHAR(40)            NULL,
  delete_status BOOLEAN DEFAULT FALSE  NOT NULL
);

------------ORGANIZATION-----------------
CREATE TABLE IF NOT EXISTS organizations (
  id            SERIAL       NOT NULL PRIMARY KEY,
  title         VARCHAR(255) NOT NULL,
  delete_status BOOLEAN      NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS reports (
  id                SERIAL  NOT NULL PRIMARY KEY,
  resource_document TEXT    NULL,
  delete_status     BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS places (
  id            SERIAL       NOT NULL PRIMARY KEY,
  title         VARCHAR(255) NOT NULL,
  date_begin    DATE,
  data_end      DATE,
  delete_status BOOLEAN      NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS orders (
  id              SERIAL                                NOT NULL PRIMARY KEY,
  id_place        INTEGER REFERENCES places (id)        NOT NULL,
  id_organization INTEGER REFERENCES organizations (id) NULL NULL,
  id_report       INTEGER REFERENCES reports (id)       NULL,
  delete_status   BOOLEAN DEFAULT FALSE
);

-------------------ACTION-------------------------

CREATE TABLE IF NOT EXISTS departure (
  id               SERIAL                 NOT NULL PRIMARY KEY,
  id_order         INTEGER                NOT NULL REFERENCES orders (id),
  id_employee_post INTEGER                NOT NULL REFERENCES employee_post (id),
  status           INTEGER                NOT NULL DEFAULT 1,
  delete_status    BOOLEAN DEFAULT FALSE  NOT NULL
);

CREATE TABLE IF NOT EXISTS transport_rental (
  id            SERIAL                 NOT NULL PRIMARY KEY,
  id_departure  INTEGER                NOT NULL REFERENCES departure (id),
  id_transport  INTEGER                NOT NULL REFERENCES transports (id),
  date_start    DATE                   NOT NULL,
  date_end      DATE                   NOT NULL,
  delete_status BOOLEAN DEFAULT FALSE  NOT NULL
);
-------------------INSTRUMENTS---------------------

CREATE TABLE IF NOT EXISTS equipment (
  id            SERIAL                 NOT NULL PRIMARY KEY,
  title         VARCHAR(255)           NOT NULL,
  weight        INTEGER                NOT NULL,
  date_buy      DATE                   NOT NULL,
  date_end      DATE                   NOT NULL,
  delete_status BOOLEAN DEFAULT FALSE  NOT NULL
);

CREATE TABLE IF NOT EXISTS equipment_departure (
  id            SERIAL                 NOT NULL PRIMARY KEY,
  id_departure  INTEGER                NOT NULL REFERENCES departure (id),
  id_equipment  INTEGER                NOT NULL REFERENCES equipment (id),
  date_start    DATE                   NOT NULL,
  date_end      DATE                   NULL,
  delete_status BOOLEAN DEFAULT FALSE  NOT NULL
);
-------------------------------------------------

CREATE OR REPLACE FUNCTION delete_departure_function()
  RETURNS TRIGGER AS $$
DECLARE
BEGIN
  IF TG_OP = 'DELETE'
  THEN
    UPDATE employee_post
    SET delete_status = TRUE
    WHERE id = old.id_employee_post;
    UPDATE equipment_departure
    SET delete_status = TRUE
    WHERE id_departure = old.id;
    UPDATE transport_rental
    SET delete_status = TRUE
    WHERE id_departure = old.id;
    UPDATE departure
    SET delete_status = TRUE
    WHERE id = old.id;
    RETURN NULL;
  END IF;
END;
$$
LANGUAGE plpgsql;

CREATE TRIGGER delete_departure_trigger
  BEFORE DELETE
  ON departure
  FOR EACH ROW EXECUTE PROCEDURE delete_departure_function();

CREATE OR REPLACE FUNCTION delete_order_function()
  RETURNS TRIGGER AS $$
DECLARE
BEGIN
  IF TG_OP = 'DELETE'
  THEN
    UPDATE organizations
    SET delete_status = TRUE
    WHERE id = old.id_organization;
    UPDATE reports
    SET delete_status = TRUE
    WHERE id = old.id_report;
    UPDATE places
    SET delete_status = TRUE
    WHERE id = old.id_place;
    DELETE FROM departure
    WHERE id_order = old.id;
    UPDATE orders
    SET delete_status = TRUE
    WHERE id = old.id;
    RETURN NULL;
  END IF;
END;
$$
LANGUAGE plpgsql;

CREATE TRIGGER delete_order_trigger
  BEFORE DELETE
  ON orders
  FOR EACH ROW EXECUTE PROCEDURE delete_order_function();

CREATE OR REPLACE FUNCTION action_employee_function()
  RETURNS TRIGGER AS $$
DECLARE
  mstr   VARCHAR(30);
  astr   VARCHAR(100);
  retstr VARCHAR(254);
BEGIN
  IF TG_OP = 'INSERT'
  THEN
    astr = new.name || ' ' || new.last_name || ' ' || new.patronymic;
    mstr := 'Add new employee ';
    retstr := mstr || astr;
    INSERT INTO log_employee (operation, datetime) VALUES (retstr, NOW());
    RETURN NEW;
  ELSIF TG_OP = 'UPDATE'
    THEN
      astr = new.name || ' ' || new.last_name || ' ' || new.patronymic;
      mstr := 'Update employee ';
      retstr := mstr || astr;
      INSERT INTO log_employee (operation, datetime) VALUES (retstr, NOW());
      RETURN NEW;
  ELSIF TG_OP = 'DELETE'
    THEN
      astr = OLD.name || ' ' || OLD.last_name || ' ' || OLD.patronymic;
      mstr := 'Remove employee ';
      retstr := mstr || astr;
      INSERT INTO log_employee (operation, datetime) VALUES (retstr, NOW());
      RETURN NULL;
  END IF;
END;
$$
LANGUAGE plpgsql;

CREATE TRIGGER action_employee_trigger
  AFTER INSERT OR UPDATE OR DELETE
  ON employees
  FOR EACH ROW EXECUTE PROCEDURE action_employee_function();

CREATE OR REPLACE FUNCTION delete_employee_function()
  RETURNS TRIGGER AS $$
BEGIN
  IF TG_OP = 'DELETE'
  THEN
    UPDATE private_cards
    SET delete_status = TRUE
    WHERE id = old.id;
    UPDATE employee_post
    SET delete_status = TRUE
    WHERE id_employee = old.id;
    UPDATE passports
    SET delete_status = TRUE
    WHERE passports.id_employee = old.id;
    UPDATE driver_licences dlc
    SET delete_status = TRUE
    WHERE dlc.id_employee = old.id;
    UPDATE employees
    SET delete_status = TRUE
    WHERE id = old.id;
    RETURN NULL;
  END IF;
END;
$$
LANGUAGE plpgsql;

CREATE TRIGGER delete_employee_trigger
  BEFORE DELETE
  ON employees
  FOR EACH ROW EXECUTE PROCEDURE delete_employee_function();


CREATE OR REPLACE FUNCTION delete_transport_function() RETURNS TRIGGER AS $$
BEGIN
  IF TG_OP = 'DELETE' THEN
    UPDATE transports SET delete_status = TRUE WHERE id = old.id;
    RETURN NULL;
  END IF;
END;
$$
LANGUAGE plpgsql;

CREATE TRIGGER delete_transport_trigger BEFORE DELETE
ON transports FOR EACH ROW EXECUTE PROCEDURE delete_transport_function();

CREATE OR REPLACE FUNCTION delete_equipment_function() RETURNS TRIGGER AS $$
BEGIN
  IF TG_OP = 'DELETE' THEN
    UPDATE equipment_departure SET delete_status = TRUE WHERE id_equipment = old.id;
    UPDATE equipment SET delete_status = TRUE WHERE id = old.id;
    RETURN NULL;
  END IF;
END;
$$
LANGUAGE plpgsql;

CREATE TRIGGER delete_equipment_trigger BEFORE DELETE
ON equipment FOR EACH ROW EXECUTE PROCEDURE delete_equipment_function();

INSERT INTO category (id, title)
VALUES (1, 'A1'), (2, 'AM'), (3, 'A'), (4, 'B'), (5, 'C'), (6, 'D'), (7, 'BE'), (8, 'CE'), (9, 'DE'), (10, 'I'),
  (11, 'F');

INSERT INTO roles (title) VALUES ('superuser');

INSERT INTO users (id, login, pass, id_role, id_employee)
VALUES (1, 'superuser', crypt('12345', gen_salt('bf', 8)), 1, NULL);

INSERT INTO employees (name, last_name, patronymic, url_photo) VALUES
  ('Павел', 'Талайко', 'Дмитриевич', NULL),
  ('Роман', 'Ратошнюк', 'Анатольевич', NULL),
  ('Алексей', 'Пранько', 'Игоревич', NULL);

INSERT INTO private_cards (id, data_birth, private_number_phone, email) VALUES
  (1, '1997-03-16', '+375299479630', 'jsdeveloper@yahoo.com'),
  (2, '1997-08-14', '+375299479842', 'rratoshnuk@yahoo.com'),
  (3, '1997-03-26', '+375299472354', 'pronko@yahoo.com');

INSERT INTO driver_licences (department, number, date_out, date_in, id_employee) VALUES
  ('МинскГАИ', '551554884', '2019-03-16', '2005-03-16', 1),
  ('МинскГАИ', '55122884', '2019-03-16', '2005-03-16', 2),
  ('МинскГАИ', '55156664884', '2019-03-16', '2005-03-16', 3);

INSERT INTO driver_licences_category (fk_category, fk_driverlicence) VALUES (1, 2), (3, 3), (2, 2), (5, 2), (7, 1);

INSERT INTO mark (id, name)
VALUES (1, 'Acura'), (2, 'Alfa Romeo'), (3, 'Alpina'), (4, 'Aro'), (5, 'Asia'), (6, 'Aston Martin'), (7, 'Audi'),
  (8, 'Austin'), (9, 'Bentley'), (10, 'BMW'), (11, 'Brilliance'), (12, 'Bugatti'), (13, 'Buick'), (14, 'BYD'),
  (15, 'Cadillac'), (16, 'Caterham'), (17, 'ChangFeng'), (18, 'Chery'), (19, 'Chevrolet'), (20, 'Chrysler'),
  (21, 'Citroen'), (22, 'Coggiola'), (23, 'Dacia'), (24, 'Dadi'), (25, 'Daewoo'), (26, 'Daihatsu'), (27, 'Daimler'),
  (28, 'Derways'), (29, 'Dodge'), (30, 'Dong Feng'), (31, 'Doninvest'), (32, 'Donkervoort'), (33, 'Eagle'), (34, 'FAW'),
  (35, 'Ferrari'), (36, 'Fiat'), (37, 'Ford'), (38, 'Geely'), (39, 'Geo'), (40, 'GMC'), (41, 'Great Wall'),
  (42, 'Hafei'), (43, 'Honda'), (44, 'HuangHai'), (45, 'Hummer'), (46, 'Hyundai'), (47, 'Infiniti'),
  (48, 'Iran Khodro'), (49, 'Isuzu'), (50, 'JAC'), (51, 'Jaguar'), (52, 'Jeep'), (53, 'Kia'), (54, 'Koenigsegg'),
  (55, 'Lamborghini'), (56, 'Lancia'), (57, 'Land Rover'), (58, 'Landwind'), (59, 'Lexus'), (60, 'Lifan'),
  (61, 'Lincoln'), (62, 'Lotus'), (63, 'Mahindra'), (64, 'Maruti'), (65, 'Maserati'), (66, 'Maybach'), (67, 'Mazda'),
  (68, 'Mercedes-Benz'), (69, 'Mercury'), (70, 'Metrocab'), (71, 'MG'), (72, 'Microcar'), (73, 'Mini'),
  (74, 'Mitsubishi'), (75, 'Mitsuoka'), (76, 'Morgan'), (77, 'Nissan'), (78, 'Oldsmobile'), (79, 'Opel'),
  (80, 'Pagani'), (81, 'Peugeot'), (82, 'Plymouth'), (83, 'Pontiac'), (84, 'Porsche'), (85, 'Proton'), (86, 'PUCH'),
  (87, 'Renault'), (88, 'Rolls-Royce'), (89, 'Rover'), (90, 'Saab'), (91, 'Saleen'), (92, 'Saturn'), (93, 'Scion'),
  (94, 'SEAT'), (95, 'ShuangHuan'), (96, 'Skoda'), (97, 'Smart'), (98, 'Spyker'), (99, 'SsangYong'), (100, 'Subaru'),
  (101, 'Suzuki'), (102, 'Tatra'), (103, 'Tianma'), (104, 'Tianye'), (105, 'Toyota'), (106, 'Trabant'), (107, 'TVR'),
  (108, 'Vector'), (109, 'Volkswagen'), (110, 'Volvo'), (111, 'Wartburg'), (112, 'Wiesmann'), (113, 'Xin Kai'),
  (114, 'ZX'), (115, 'ВАЗ'), (116, 'Велта'), (117, 'ГАЗ'), (118, 'ЗАЗ'), (119, 'ЗИЛ'), (120, 'ИЖ'), (121, 'КАМАЗ'),
  (122, 'ЛУАЗ'), (123, 'Москвич'), (124, 'СеАЗ'), (125, 'СМЗ'), (126, 'ТагАЗ'), (127, 'УАЗ');

INSERT INTO model (id, id_mark, name)
VALUES (1, 1, 'CL'), (2, 1, 'EL'), (3, 1, 'Integra'), (4, 1, 'MDX'), (5, 1, 'NSX'), (6, 1, 'RDX'), (7, 1, 'RL'),
  (8, 1, 'RSX'), (9, 1, 'TL'), (10, 1, 'TSX'), (11, 2, '33'), (12, 2, '75'), (13, 2, '145'), (14, 2, '146'),
  (15, 2, '147'), (16, 2, '155'), (17, 2, '156'), (18, 2, '159'), (19, 2, '164'), (20, 2, '166'), (21, 2, 'Alfetta'),
  (22, 2, 'Brera'), (23, 2, 'GT'), (24, 2, 'GTV'), (25, 2, 'Giulietta'), (26, 2, 'Spider'), (27, 3, 'B11'),
  (28, 3, 'B12'), (29, 3, 'B3'), (30, 3, 'B6'), (31, 3, 'B7'), (32, 3, 'Roadster S'), (33, 4, '10'), (34, 4, '24'),
  (35, 5, 'Rocsta'), (36, 6, 'DB7'), (37, 6, 'DB9'), (38, 6, 'DBS'), (39, 6, 'V12 Vanquish'), (40, 6, 'V8'),
  (41, 7, '80'), (42, 7, '90'), (43, 7, '100'), (44, 7, '200'), (45, 7, 'A2'), (46, 7, 'A3'), (47, 7, 'A4'),
  (48, 7, 'A5'), (49, 7, 'A6'), (50, 7, 'A8'), (51, 7, 'Allroad'), (52, 7, 'Cabriolet'), (53, 7, 'Coupe'),
  (54, 7, 'Q7'), (55, 7, 'Quattro'), (56, 7, 'R8'), (57, 7, 'RS4'), (58, 7, 'RS6'), (59, 7, 'S2'), (60, 7, 'S3'),
  (61, 7, 'S4'), (62, 7, 'S5'), (63, 7, 'S6'), (64, 7, 'S8'), (65, 7, 'TT'), (66, 7, 'V8 (D11)'), (67, 8, 'Metro'),
  (68, 8, 'Mini MK'), (69, 8, 'Montego'), (70, 9, 'Arnage'), (71, 9, 'Azure'), (72, 9, 'Brooklands'),
  (73, 9, 'Continental'), (74, 9, 'Mulsanne'), (75, 9, 'Turbo R'), (76, 10, '02 (E10)'), (77, 10, '1er 116'),
  (78, 10, '1er 118'), (79, 10, '1er 120'), (80, 10, '1er 130'), (81, 10, '3er 315'), (82, 10, '3er 316'),
  (83, 10, '3er 318'), (84, 10, '3er 320'), (85, 10, '3er 323'), (86, 10, '3er 324'), (87, 10, '3er 325'),
  (88, 10, '3er 328'), (89, 10, '3er 330'), (90, 10, '3er 335'), (91, 10, '5er 518'), (92, 10, '5er 520'),
  (93, 10, '5er 523'), (94, 10, '5er 524'), (95, 10, '5er 525'), (96, 10, '5er 528'), (97, 10, '5er 530'),
  (98, 10, '5er 535'), (99, 10, '5er 540'), (100, 10, '5er 545'), (101, 10, '5er 550'), (102, 10, '6er 628'),
  (103, 10, '6er 630'), (104, 10, '6er 635'), (105, 10, '6er 645'), (106, 10, '6er 650'), (107, 10, '7er 725'),
  (108, 10, '7er 728'), (109, 10, '7er 730'), (110, 10, '7er 732'), (111, 10, '7er 735'), (112, 10, '7er 740'),
  (113, 10, '7er 745'), (114, 10, '7er 750'), (115, 10, '7er 760'), (116, 10, '8er 850'), (117, 10, 'M3'),
  (118, 10, 'M5'), (119, 10, 'M6'), (120, 10, 'X3'), (121, 10, 'X5'), (122, 10, 'X6'), (123, 10, 'Z1'), (124, 10, 'Z3'),
  (125, 10, 'Z3 M'), (126, 10, 'Z4'), (127, 10, 'Z4 M'), (128, 10, 'Z8'), (129, 11, 'M1'), (130, 11, 'M2'),
  (131, 12, '57 SC Atlantic'), (132, 12, 'EB 16.4'), (133, 13, 'Century'), (134, 13, 'Enclave'), (135, 13, 'LE Sabre'),
  (136, 13, 'Park Avenue'), (137, 13, 'Reatta'), (138, 13, 'Rendezvous'), (139, 13, 'Riviera'), (140, 13, 'Roadmaster'),
  (141, 13, 'Skylark'), (142, 14, 'F3'), (143, 14, 'FLYER II'), (144, 15, 'BLS'), (145, 15, 'CTS'), (146, 15, 'Catera'),
  (147, 15, 'DE Ville'), (148, 15, 'Eldorado'), (149, 15, 'Escalade'), (150, 15, 'Fleetwood'), (151, 15, 'SRX'),
  (152, 15, 'STS'), (153, 15, 'Seville'), (154, 15, 'XLR'), (155, 16, 'Super Seven'), (156, 17, 'Flying'),
  (157, 17, 'SUV'), (158, 18, 'Amulet'), (159, 18, 'Flagcloud'), (160, 18, 'Fora'), (161, 18, 'Kimo (A1)'),
  (162, 18, 'Oriental Son'), (163, 18, 'QQ6 (S21)'), (164, 18, 'Sweet (QQ)'), (165, 18, 'Tiggo'), (166, 19, 'Alero'),
  (167, 19, 'Astro'), (168, 19, 'Avalanche'), (169, 19, 'Aveo'), (170, 19, 'Beretta'), (171, 19, 'Blazer'),
  (172, 19, 'C-10'), (173, 19, 'Camaro'), (174, 19, 'Caprice'), (175, 19, 'Captiva'), (176, 19, 'Cavalier'),
  (177, 19, 'Cobalt'), (178, 19, 'Colorado'), (179, 19, 'Corsica'), (180, 19, 'Corvette'), (181, 19, 'Epica'),
  (182, 19, 'Equinox'), (183, 19, 'Evanda'), (184, 19, 'Express'), (185, 19, 'Geo Storm'), (186, 19, 'HHR'),
  (187, 19, 'Impala'), (188, 19, 'Lacetti'), (189, 19, 'Lanos'), (190, 19, 'Lumina'), (191, 19, 'Metro'),
  (192, 19, 'Niva'), (193, 19, 'Prizm'), (194, 19, 'Rezzo'), (195, 19, 'SSR'), (196, 19, 'Savana'),
  (197, 19, 'Silverado'), (198, 19, 'Spark'), (199, 19, 'Starcraft'), (200, 19, 'Suburban'), (201, 19, 'Tahoe'),
  (202, 19, 'Tracker'), (203, 19, 'Trailblazer'), (204, 19, 'Uplander'), (205, 19, 'Van'), (206, 19, 'Venture'),
  (207, 19, 'Viva'), (208, 20, '300C'), (209, 20, '300M'), (210, 20, 'Aspen'), (211, 20, 'Cirrus'),
  (212, 20, 'Concorde'), (213, 20, 'Crossfire'), (214, 20, 'Fifth Avenue'), (215, 20, 'Grand Voyager'),
  (216, 20, 'Intrepid'), (217, 20, 'LE Baron'), (218, 20, 'LHS'), (219, 20, 'NEW Yorker'), (220, 20, 'Neon'),
  (221, 20, 'PT Cruiser'), (222, 20, 'Pacifica'), (223, 20, 'Prowler'), (224, 20, 'Saratoga'), (225, 20, 'Sebring'),
  (226, 20, 'Stratus'), (227, 20, 'Town &amp; Country'), (228, 20, 'Tracker'), (229, 20, 'Vision'),
  (230, 20, 'Voyager'), (231, 21, 'AX'), (232, 21, 'BX'), (233, 21, 'Berlingo'), (234, 21, 'C2'), (235, 21, 'C25'),
  (236, 21, 'C3'), (237, 21, 'C4'), (238, 21, 'C4 Picasso'), (239, 21, 'C5'), (240, 21, 'C6'), (241, 21, 'C8'),
  (242, 21, 'Evasion'), (243, 21, 'Jumper'), (244, 21, 'Jumpy'), (245, 21, 'LNA'), (246, 21, 'Saxo'), (247, 21, 'Visa'),
  (248, 21, 'XM'), (249, 21, 'Xantia'), (250, 21, 'Xsara'), (251, 21, 'Xsara Picasso'), (252, 21, 'ZX'),
  (253, 22, 'T-Rex'), (254, 23, '1410'), (255, 24, 'City Leading'), (256, 24, 'Shuttle'), (257, 25, 'Damas'),
  (258, 25, 'Espero'), (259, 25, 'Evanda'), (260, 25, 'Kalos'), (261, 25, 'LE Mans'), (262, 25, 'Lacetti'),
  (263, 25, 'Lanos'), (264, 25, 'Leganza'), (265, 25, 'Magnus'), (266, 25, 'Matiz'), (267, 25, 'Nexia'),
  (268, 25, 'Nubira'), (269, 25, 'Prince'), (270, 25, 'Racer'), (271, 25, 'Rezzo'), (272, 25, 'Tacuma'),
  (273, 25, 'Tico'), (274, 26, 'Altis'), (275, 26, 'Applause'), (276, 26, 'Atrai/extol'), (277, 26, 'Be-go'),
  (278, 26, 'Boon'), (279, 26, 'Charade'), (280, 26, 'Copen'), (281, 26, 'Cuore'), (282, 26, 'Esse'),
  (283, 26, 'Feroza'), (284, 26, 'Materia'), (285, 26, 'Mira'), (286, 26, 'Move'), (287, 26, 'Pyzar'),
  (288, 26, 'Rocky'), (289, 26, 'Sirion'), (290, 26, 'Storia'), (291, 26, 'Terios'), (292, 26, 'YRV'),
  (293, 27, '2.8 - 5.3'), (294, 27, 'Limousine'), (295, 28, 'Aurora'), (296, 28, 'Cowboy'), (297, 28, 'Plutus'),
  (298, 28, 'Shuttle'), (299, 29, 'Avenger'), (300, 29, 'Caliber'), (301, 29, 'Caravan'), (302, 29, 'Challenger'),
  (303, 29, 'Charger'), (304, 29, 'Dakota'), (305, 29, 'Durango'), (306, 29, 'Grand Caravan'), (307, 29, 'Intrepid'),
  (308, 29, 'Magnum'), (309, 29, 'Neon'), (310, 29, 'Nitro'), (311, 29, 'RAM'), (312, 29, 'Ramcharger'),
  (313, 29, 'Shadow'), (314, 29, 'Spirit'), (315, 29, 'Stealth'), (316, 29, 'Stratus'), (317, 29, 'Viper'),
  (318, 30, 'MPV'), (319, 31, 'Assol'), (320, 31, 'Kondor'), (321, 31, 'Orion'), (322, 32, 'D8'), (323, 33, 'Talon'),
  (324, 33, 'Vision'), (325, 34, 'Admiral'), (326, 34, 'Jinn'), (327, 34, 'Vita'), (328, 35, '360'), (329, 35, '430'),
  (330, 35, '599'), (331, 35, '612 Scaglietti'), (332, 35, 'California'), (333, 35, 'Enzo'), (334, 35, 'F355'),
  (335, 35, 'Maranello'), (336, 35, 'Mondial'), (337, 35, 'Testarossa'), (338, 36, '131'), (339, 36, 'Albea'),
  (340, 36, 'Barchetta'), (341, 36, 'Brava'), (342, 36, 'Bravo'), (343, 36, 'Cinquecento'), (344, 36, 'Coupe'),
  (345, 36, 'Croma'), (346, 36, 'Doblo'), (347, 36, 'Fiorino'), (348, 36, 'Marea'), (349, 36, 'Multipla'),
  (350, 36, 'New 500'), (351, 36, 'Palio'), (352, 36, 'Panda'), (353, 36, 'Punto'), (354, 36, 'Regata'),
  (355, 36, 'Ritmo'), (356, 36, 'Sedici'), (357, 36, 'Seicento'), (358, 36, 'Stilo'), (359, 36, 'Tempra'),
  (360, 36, 'Tipo'), (361, 36, 'UNO'), (362, 36, 'Ulysse'), (363, 36, 'X 1/9'), (364, 37, 'Aerostar'),
  (365, 37, 'Bronco'), (366, 37, 'C-MAX'), (367, 37, 'Capri'), (368, 37, 'Contour'), (369, 37, 'Cougar'),
  (370, 37, 'Crown Victoria'), (371, 37, 'Econoline'), (372, 37, 'Edge'), (373, 37, 'Escape'), (374, 37, 'Escort'),
  (375, 37, 'Excursion'), (376, 37, 'Expedition'), (377, 37, 'Explorer'), (378, 37, 'F-150'), (379, 37, 'Fiesta'),
  (380, 37, 'Fiesta ST'), (381, 37, 'Five Hundred'), (382, 37, 'Focus'), (383, 37, 'Focus ST'), (384, 37, 'Freestyle'),
  (385, 37, 'Fusion'), (386, 37, 'Fusion (USA)'), (387, 37, 'GT'), (388, 37, 'Galaxy'), (389, 37, 'Granada'),
  (390, 37, 'Granada (USA)'), (391, 37, 'KA'), (392, 37, 'Kuga'), (393, 37, 'Maverick'), (394, 37, 'Mondeo'),
  (395, 37, 'Mustang'), (396, 37, 'Orion'), (397, 37, 'Probe'), (398, 37, 'Puma'), (399, 37, 'Ranger'),
  (400, 37, 'S-MAX'), (401, 37, 'Scorpio'), (402, 37, 'Shelby'), (403, 37, 'Sierra'), (404, 37, 'Taunus'),
  (405, 37, 'Taurus'), (406, 37, 'Tempo'), (407, 37, 'Thunderbird'), (408, 37, 'Tourneo Connect'),
  (409, 37, 'Windstar'), (410, 38, 'MK'), (411, 38, 'Otaka'), (412, 39, 'Metro'), (413, 39, 'Prizm'),
  (414, 39, 'Storm'), (415, 39, 'Tracker'), (416, 40, 'Acadia'), (417, 40, 'Envoy'), (418, 40, 'Jimmy'),
  (419, 40, 'Safari'), (420, 40, 'Savana'), (421, 40, 'Sierra'), (422, 40, 'Sonoma'), (423, 40, 'Yukon'),
  (424, 40, 'suburban'), (425, 41, 'Deer'), (426, 41, 'Hover'), (427, 41, 'SUV'), (428, 41, 'Safe'),
  (429, 41, 'Sailor'), (430, 41, 'Socool'), (431, 41, 'Wingle'), (432, 42, 'Brio'), (433, 42, 'Princip'),
  (434, 42, 'Simbo'), (435, 43, 'Accord'), (436, 43, 'Airwave'), (437, 43, 'Avancier'), (438, 43, 'CR-V'),
  (439, 43, 'CRX'), (440, 43, 'Capa'), (441, 43, 'City'), (442, 43, 'Civic'), (443, 43, 'Civic Shuttle'),
  (444, 43, 'Concerto'), (445, 43, 'Domani'), (446, 43, 'Edix'), (447, 43, 'Element'), (448, 43, 'FIT'),
  (449, 43, 'FR-V'), (450, 43, 'Fit Aria'), (451, 43, 'Hr-v'), (452, 43, 'Insight'), (453, 43, 'Inspire'),
  (454, 43, 'Integra'), (455, 43, 'Jazz'), (456, 43, 'Legend'), (457, 43, 'Life'), (458, 43, 'Logo'),
  (459, 43, 'Mobilio'), (460, 43, 'NSX'), (461, 43, 'Odyssey'), (462, 43, 'Orthia'), (463, 43, 'Partner'),
  (464, 43, 'Passport'), (465, 43, 'Pilot'), (466, 43, 'Prelude'), (467, 43, 'Rafaga'), (468, 43, 'Ridgeline'),
  (469, 43, 'S2000'), (470, 43, 'Saber'), (471, 43, 'Shuttle'), (472, 43, 'Sm-x'), (473, 43, 'Stepwgn'),
  (474, 43, 'Stream'), (475, 43, 'Torneo'), (476, 43, 'Vamos'), (477, 43, 'Vigor'), (478, 43, 'Z'),
  (479, 44, 'Antelope'), (480, 45, 'Hummer'), (481, 45, 'Hummer H1'), (482, 45, 'Hummer H2'), (483, 45, 'Hummer H3'),
  (484, 46, 'Accent'), (485, 46, 'Atos'), (486, 46, 'Coupe'), (487, 46, 'Elantra'), (488, 46, 'Galloper'),
  (489, 46, 'Getz'), (490, 46, 'Grandeur'), (491, 46, 'H-1 Starex'), (492, 46, 'H100'), (493, 46, 'Lavita'),
  (494, 46, 'Marcia'), (495, 46, 'Matrix'), (496, 46, 'NF'), (497, 46, 'Pony'), (498, 46, 'S-Coupe'),
  (499, 46, 'Santa FE'), (500, 46, 'Santamo'), (501, 46, 'Sonata'), (502, 46, 'Terracan'), (503, 46, 'Tiburon'),
  (504, 46, 'Trajet'), (505, 46, 'Tucson'), (506, 46, 'Tuscani'), (507, 46, 'Veracruz'), (508, 46, 'Verna'),
  (509, 46, 'XG'), (510, 47, 'EX'), (511, 47, 'FX 35'), (512, 47, 'FX 45'), (513, 47, 'FX 50'), (514, 47, 'G35'),
  (515, 47, 'G37'), (516, 47, 'I30'), (517, 47, 'I35'), (518, 47, 'J30'), (519, 47, 'M35'), (520, 47, 'M45'),
  (521, 47, 'Q45'), (522, 47, 'QX4'), (523, 47, 'QX56'), (524, 48, 'Samand'), (525, 49, 'Ascender'), (526, 49, 'Aska'),
  (527, 49, 'Axiom'), (528, 49, 'Bighorn'), (529, 49, 'Gemini'), (530, 49, 'Impulse'), (531, 49, 'Rodeo'),
  (532, 49, 'Trooper'), (533, 49, 'VehiCross'), (534, 49, 'Wizard'), (535, 50, 'Refine'), (536, 50, 'Rein'),
  (537, 51, 'E-type'), (538, 51, 'S-type'), (539, 51, 'X-type'), (540, 51, 'XF'), (541, 51, 'XJ'), (542, 51, 'XJR'),
  (543, 51, 'XJS Coupe'), (544, 51, 'XJSc Convertible'), (545, 51, 'XK 8'), (546, 51, 'XKR'), (547, 52, 'CJ5 - CJ8'),
  (548, 52, 'Cherokee'), (549, 52, 'Commander'), (550, 52, 'Compass'), (551, 52, 'Grand Cherokee'),
  (552, 52, 'Liberty'), (553, 52, 'Patriot'), (554, 52, 'Wrangler'), (555, 53, 'Avella'), (556, 53, 'Besta'),
  (557, 53, 'Borrego'), (558, 53, 'Capital'), (559, 53, 'Carens'), (560, 53, 'Carnival'), (561, 53, 'Cee&#039;d'),
  (562, 53, 'Cerato'), (563, 53, 'Clarus'), (564, 53, 'Joice'), (565, 53, 'Magentis'), (566, 53, 'Opirus'),
  (567, 53, 'Optima'), (568, 53, 'Picanto'), (569, 53, 'Potentia'), (570, 53, 'Pregio'), (571, 53, 'Pride'),
  (572, 53, 'Retona'), (573, 53, 'Rio'), (574, 53, 'Sedona'), (575, 53, 'Sephia'), (576, 53, 'Shuma'),
  (577, 53, 'Sorento'), (578, 53, 'Spectra'), (579, 53, 'Sportage'), (580, 53, 'Visto'), (581, 54, 'CC'),
  (582, 55, 'Diablo'), (583, 55, 'Espada'), (584, 55, 'Gallardo'), (585, 55, 'Murcielago'), (586, 56, 'Beta'),
  (587, 56, 'Dedra'), (588, 56, 'Delta'), (589, 56, 'Kappa'), (590, 56, 'Lybra'), (591, 56, 'Phedra'),
  (592, 56, 'Thema'), (593, 56, 'Thesis'), (594, 56, 'Y'), (595, 57, '90/110'), (596, 57, 'Defender'),
  (597, 57, 'Discovery'), (598, 57, 'Freelander'), (599, 57, 'Land Rover'), (600, 57, 'Range Rover'),
  (601, 57, 'Range Rover Sport'), (602, 58, 'Double Gate'), (603, 58, 'SUV'), (604, 59, 'ES 300'), (605, 59, 'ES 330'),
  (606, 59, 'ES 350'), (607, 59, 'GS 300'), (608, 59, 'GS 350'), (609, 59, 'GS 400'), (610, 59, 'GS 430'),
  (611, 59, 'GS 450h'), (612, 59, 'GS 460'), (613, 59, 'GX'), (614, 59, 'IS 200'), (615, 59, 'IS 250'),
  (616, 59, 'IS 300'), (617, 59, 'IS 350'), (618, 59, 'IS-F'), (619, 59, 'LS 400'), (620, 59, 'LS 430'),
  (621, 59, 'LS 460'), (622, 59, 'LS 600'), (623, 59, 'LX 450'), (624, 59, 'LX 470'), (625, 59, 'LX 570'),
  (626, 59, 'RX 300'), (627, 59, 'RX 330'), (628, 59, 'RX 350'), (629, 59, 'RX 400h'), (630, 59, 'SC 300'),
  (631, 59, 'SC 400'), (632, 59, 'SC 430'), (633, 60, 'Breez (520)'), (634, 61, 'Aviator'), (635, 61, 'Continental'),
  (636, 61, 'LS'), (637, 61, 'MKX'), (638, 61, 'MKZ'), (639, 61, 'Mark'), (640, 61, 'Mark LT'), (641, 61, 'Navigator'),
  (642, 61, 'Town Car'), (643, 61, 'Zephyr'), (644, 62, 'Elise'), (645, 62, 'Exige'), (646, 62, 'Super 7'),
  (647, 63, 'Marshal'), (648, 64, 'Alto'), (649, 65, '228'), (650, 65, '3200 GT'), (651, 65, '4300 GT Coupe'),
  (652, 65, 'Biturbo'), (653, 65, 'Coupe'), (654, 65, 'GranSport'), (655, 65, 'GranTurismo'), (656, 65, 'Quattroporte'),
  (657, 65, 'Spyder'), (658, 66, 'Maybach 57 S и Maybach 62 S'), (659, 66, 'Maybach 57 и Maybach 62'), (660, 67, '121'),
  (661, 67, '323'), (662, 67, '626'), (663, 67, '929'), (664, 67, 'Atenza'), (665, 67, 'Axela'), (666, 67, 'Az-wagon'),
  (667, 67, 'B-serie'), (668, 67, 'BT-50'), (669, 67, 'Bongo'), (670, 67, 'CX-7'), (671, 67, 'CX-9'),
  (672, 67, 'Capella'), (673, 67, 'Carol'), (674, 67, 'Demio'), (675, 67, 'E 2000,2200 Bus'), (676, 67, 'Eunos 500'),
  (677, 67, 'Eunos Cosmo'), (678, 67, 'Familia'), (679, 67, 'Lantis'), (680, 67, 'Levante'), (681, 67, 'Luce'),
  (682, 67, 'MPV'), (683, 67, 'Mazda 2'), (684, 67, 'Mazda 3'), (685, 67, 'Mazda 3 MPS'), (686, 67, 'Mazda 5'),
  (687, 67, 'Mazda 6'), (688, 67, 'Mazda 6 MPS'), (689, 67, 'Millenia'), (690, 67, 'Mx-3'), (691, 67, 'Mx-5'),
  (692, 67, 'Mx-6'), (693, 67, 'Premacy'), (694, 67, 'Protege'), (695, 67, 'RX 7'), (696, 67, 'Roadster'),
  (697, 67, 'Rx-8'), (698, 67, 'Tribute'), (699, 67, 'Verisa'), (700, 67, 'Xedos 6'), (701, 67, 'Xedos 9'),
  (702, 68, ' Cabrio'), (703, 68, ' Coupe'), (704, 68, ' T-mod.'), (705, 68, '/8'), (706, 68, '190'), (707, 68, '200'),
  (708, 68, '220'), (709, 68, '230'), (710, 68, '240'), (711, 68, '250'), (712, 68, '260'), (713, 68, '280'),
  (714, 68, '300'), (715, 68, '500'), (716, 68, 'A-klasse A 140'), (717, 68, 'A-klasse A 150'),
  (718, 68, 'A-klasse A 160'), (719, 68, 'A-klasse A 170'), (720, 68, 'A-klasse A 180'), (721, 68, 'A-klasse A 190'),
  (722, 68, 'A-klasse A 200'), (723, 68, 'A-klasse A 210'), (724, 68, 'B-klasse B 170'), (725, 68, 'B-klasse B 180'),
  (726, 68, 'B-klasse B 200'), (727, 68, 'C-klasse C 180'), (728, 68, 'C-klasse C 200'), (729, 68, 'C-klasse C 220'),
  (730, 68, 'C-klasse C 230'), (731, 68, 'C-klasse C 240'), (732, 68, 'C-klasse C 250'), (733, 68, 'C-klasse C 270'),
  (734, 68, 'C-klasse C 280'), (735, 68, 'C-klasse C 32 AMG'), (736, 68, 'C-klasse C 320'), (737, 68, 'C-klasse C 350'),
  (738, 68, 'C-klasse C 36 AMG'), (739, 68, 'C-klasse C 55 AMG'), (740, 68, 'C-klasse C 63 AMG'),
  (741, 68, 'CL-Klasse CL 420'), (742, 68, 'CL-Klasse CL 500'), (743, 68, 'CL-Klasse CL 55 AMG'),
  (744, 68, 'CL-Klasse CL 600'), (745, 68, 'CL-Klasse CL 63 AMG'), (746, 68, 'CL-Klasse CL 65 AMG'),
  (747, 68, 'CLC-klasse CLC 220 CDI'), (748, 68, 'CLK-klasse CLK 200'), (749, 68, 'CLK-klasse CLK 220'),
  (750, 68, 'CLK-klasse CLK 230'), (751, 68, 'CLK-klasse CLK 240'), (752, 68, 'CLK-klasse CLK 270'),
  (753, 68, 'CLK-klasse CLK 320'), (754, 68, 'CLK-klasse CLK 350'), (755, 68, 'CLK-klasse CLK 430'),
  (756, 68, 'CLK-klasse CLK 500'), (757, 68, 'CLK-klasse CLK 55 AMG'), (758, 68, 'CLK-klasse CLK 63 AMG'),
  (759, 68, 'CLS-klasse CLS 320'), (760, 68, 'CLS-klasse CLS 350'), (761, 68, 'CLS-klasse CLS 500'),
  (762, 68, 'CLS-klasse CLS 55 AMG'), (763, 68, 'CLS-klasse CLS 63 AMG'), (764, 68, 'E-klasse E 200'),
  (765, 68, 'E-klasse E 220'), (766, 68, 'E-klasse E 230'), (767, 68, 'E-klasse E 240'), (768, 68, 'E-klasse E 250'),
  (769, 68, 'E-klasse E 270'), (770, 68, 'E-klasse E 280'), (771, 68, 'E-klasse E 290'), (772, 68, 'E-klasse E 300'),
  (773, 68, 'E-klasse E 320'), (774, 68, 'E-klasse E 350'), (775, 68, 'E-klasse E 400'), (776, 68, 'E-klasse E 420'),
  (777, 68, 'E-klasse E 430'), (778, 68, 'E-klasse E 50 AMG'), (779, 68, 'E-klasse E 500'),
  (780, 68, 'E-klasse E 55 AMG'), (781, 68, 'E-klasse E 63 AMG'), (782, 68, 'G-Klasse G 230'),
  (783, 68, 'G-Klasse G 250'), (784, 68, 'G-Klasse G 270'), (785, 68, 'G-Klasse G 280'), (786, 68, 'G-Klasse G 290'),
  (787, 68, 'G-Klasse G 300'), (788, 68, 'G-Klasse G 320'), (789, 68, 'G-Klasse G 350'), (790, 68, 'G-Klasse G 400'),
  (791, 68, 'G-Klasse G 500'), (792, 68, 'G-Klasse G 55 AMG'), (793, 68, 'GL-klasse GL 320'),
  (794, 68, 'GL-klasse GL 420'), (795, 68, 'GL-klasse GL 450'), (796, 68, 'GL-klasse GL 500'),
  (797, 68, 'GL-klasse GL 550'), (798, 68, 'M-klasse ML 230'), (799, 68, 'M-klasse ML 270'),
  (800, 68, 'M-klasse ML 280'), (801, 68, 'M-klasse ML 320'), (802, 68, 'M-klasse ML 350'),
  (803, 68, 'M-klasse ML 400'), (804, 68, 'M-klasse ML 430'), (805, 68, 'M-klasse ML 500'),
  (806, 68, 'M-klasse ML 55 AMG'), (807, 68, 'M-klasse ML 63 AMG'), (808, 68, 'Pullmann'), (809, 68, 'R-klasse'),
  (810, 68, 'R-klasse R 320'), (811, 68, 'R-klasse R 350'), (812, 68, 'R-klasse R 500'), (813, 68, 'S-klasse S 260'),
  (814, 68, 'S-klasse S 280'), (815, 68, 'S-klasse S 300'), (816, 68, 'S-klasse S 320'), (817, 68, 'S-klasse S 350'),
  (818, 68, 'S-klasse S 380'), (819, 68, 'S-klasse S 400'), (820, 68, 'S-klasse S 420'), (821, 68, 'S-klasse S 430'),
  (822, 68, 'S-klasse S 450'), (823, 68, 'S-klasse S 500'), (824, 68, 'S-klasse S 55 AMG'), (825, 68, 'S-klasse S 550'),
  (826, 68, 'S-klasse S 560'), (827, 68, 'S-klasse S 600'), (828, 68, 'S-klasse S 65 AMG'),
  (829, 68, 'SL-klasse SL 280'), (830, 68, 'SL-klasse SL 300'), (831, 68, 'SL-klasse SL 320'),
  (832, 68, 'SL-klasse SL 350'), (833, 68, 'SL-klasse SL 380'), (834, 68, 'SL-klasse SL 420'),
  (835, 68, 'SL-klasse SL 450'), (836, 68, 'SL-klasse SL 500'), (837, 68, 'SL-klasse SL 55 AMG'),
  (838, 68, 'SL-klasse SL 600'), (839, 68, 'SL-klasse SL 63 AMG'), (840, 68, 'SL-klasse SL 65 AMG'),
  (841, 68, 'SLK-klasse SLK 200'), (842, 68, 'SLK-klasse SLK 230'), (843, 68, 'SLK-klasse SLK 280'),
  (844, 68, 'SLK-klasse SLK 32 AMG'), (845, 68, 'SLK-klasse SLK 320'), (846, 68, 'SLK-klasse SLK 350'),
  (847, 68, 'SLK-klasse SLK 55 AMG'), (848, 68, 'SLR McLaren'), (849, 68, 'V-klasse V 200'),
  (850, 68, 'V-klasse V 220'), (851, 68, 'V-klasse V 230'), (852, 68, 'V-klasse V 280'), (853, 68, 'Vaneo'),
  (854, 68, 'Viano'), (855, 69, 'Capri'), (856, 69, 'Cougar'), (857, 69, 'Grand Marquis'), (858, 69, 'Mariner'),
  (859, 69, 'Montego'), (860, 69, 'Mountaineer'), (861, 69, 'Mystique'), (862, 69, 'Sable'), (863, 69, 'Topaz'),
  (864, 69, 'Tracer'), (865, 69, 'Villager'), (866, 70, 'Taxi  (II -series)'), (867, 71, 'MGB'), (868, 71, 'MGF'),
  (869, 71, 'TF'), (870, 71, 'ZR'), (871, 71, 'ZT'), (872, 72, 'MC1'), (873, 73, 'Clubman'), (874, 73, 'Cooper'),
  (875, 73, 'One'), (876, 74, '3000 GT'), (877, 74, 'Airtrek'), (878, 74, 'Aspire'), (879, 74, 'Carisma'),
  (880, 74, 'Challenger'), (881, 74, 'Chariot'), (882, 74, 'Colt'), (883, 74, 'Debonair'), (884, 74, 'Delica'),
  (885, 74, 'Diamante'), (886, 74, 'Dingo'), (887, 74, 'Dion'), (888, 74, 'EK Wagon'), (889, 74, 'Eclipse'),
  (890, 74, 'Emeraude'), (891, 74, 'Endeavor'), (892, 74, 'FTO'), (893, 74, 'GTO'), (894, 74, 'Galant'),
  (895, 74, 'Grandis'), (896, 74, 'L 200'), (897, 74, 'Lancer'), (898, 74, 'Lancer'), (899, 74, 'Lancer Cedia'),
  (900, 74, 'Lancer Evolution'), (901, 74, 'Legnum'), (902, 74, 'Libero'), (903, 74, 'Minica'), (904, 74, 'Mirage'),
  (905, 74, 'Montero'), (906, 74, 'Montero Sport'), (907, 74, 'Outlander'), (908, 74, 'Pajero'),
  (909, 74, 'Pajero Sport'), (910, 74, 'RVR'), (911, 74, 'Sapporo'), (912, 74, 'Sigma'), (913, 74, 'Space Gear'),
  (914, 74, 'Space Runner'), (915, 74, 'Space Star'), (916, 74, 'Space Wagon'), (917, 74, 'Starion'),
  (918, 74, 'Town BOX'), (919, 74, 'i'), (920, 75, 'Galue'), (921, 75, 'Le-Sayde'), (922, 75, 'Le-Seyde'),
  (923, 76, 'Aero 8'), (924, 77, '100 NX'), (925, 77, '200 SX'), (926, 77, '300 ZX'), (927, 77, '350Z'),
  (928, 77, 'AD'), (929, 77, 'Almera'), (930, 77, 'Almera Classic'), (931, 77, 'Almera Tino'), (932, 77, 'Altima'),
  (933, 77, 'Armada'), (934, 77, 'Avenir'), (935, 77, 'Bassara'), (936, 77, 'Bluebird'), (937, 77, 'Cedric'),
  (938, 77, 'Cefiro'), (939, 77, 'Cherry'), (940, 77, 'Cima'), (941, 77, 'Cube'), (942, 77, 'Datsun'),
  (943, 77, 'Elgrand'), (944, 77, 'Expert'), (945, 77, 'Fairlady'), (946, 77, 'Frontier'), (947, 77, 'Fuga'),
  (948, 77, 'GT-R'), (949, 77, 'Gloria'), (950, 77, 'King Cab'), (951, 77, 'Lafesta'), (952, 77, 'Largo'),
  (953, 77, 'Laurel'), (954, 77, 'Leopard'), (955, 77, 'Liberty'), (956, 77, 'Lucino'), (957, 77, 'March'),
  (958, 77, 'Maxima'), (959, 77, 'Micra'), (960, 77, 'Mistral'), (961, 77, 'Moco'), (962, 77, 'Murano'),
  (963, 77, 'Navara'), (964, 77, 'Note'), (965, 77, 'Pathfinder'), (966, 77, 'Patrol'), (967, 77, 'Pick UP'),
  (968, 77, 'Prairie'), (969, 77, 'Presage'), (970, 77, 'Presea'), (971, 77, 'Primera'), (972, 77, 'Pulsar'),
  (973, 77, 'Qashqai'), (974, 77, 'Quest'), (975, 77, 'R Nessa'), (976, 77, 'Rasheen'), (977, 77, 'Rogue'),
  (978, 77, 'Safari'), (979, 77, 'Sentra'), (980, 77, 'Serena'), (981, 77, 'Silvia'), (982, 77, 'Skyline'),
  (983, 77, 'Stagea'), (984, 77, 'Stanza'), (985, 77, 'Sunny'), (986, 77, 'Teana'), (987, 77, 'Terrano'),
  (988, 77, 'Tiida'), (989, 77, 'Tino'), (990, 77, 'Titan'), (991, 77, 'Urvan'), (992, 77, 'Vanette'),
  (993, 77, 'Wingroad'), (994, 77, 'X-Terra'), (995, 77, 'X-Trail'), (996, 78, 'Achieva'), (997, 78, 'Alero'),
  (998, 78, 'Aurora'), (999, 78, 'Bravada'), (1000, 78, 'Cutlass'), (1001, 78, 'Silhouette'), (1002, 79, 'Agila'),
  (1003, 79, 'Antara'), (1004, 79, 'Ascona'), (1005, 79, 'Astra'), (1006, 79, 'Astra OPC'), (1007, 79, 'Calibra'),
  (1008, 79, 'Campo'), (1009, 79, 'Combo'), (1010, 79, 'Commodore'), (1011, 79, 'Corsa'), (1012, 79, 'Corsa OPC'),
  (1013, 79, 'Frontera'), (1014, 79, 'GT'), (1015, 79, 'Kadett'), (1016, 79, 'Manta'), (1017, 79, 'Meriva'),
  (1018, 79, 'Monterey'), (1019, 79, 'Movano'), (1020, 79, 'Omega'), (1021, 79, 'Rekord'), (1022, 79, 'Senator'),
  (1023, 79, 'Signum'), (1024, 79, 'Sintra'), (1025, 79, 'Speedster'), (1026, 79, 'Tigra'), (1027, 79, 'Vectra'),
  (1028, 79, 'Vita'), (1029, 79, 'Zafira'), (1030, 80, 'Zonda C12'), (1031, 81, '106'), (1032, 81, '107'),
  (1033, 81, '205'), (1034, 81, '206'), (1035, 81, '207'), (1036, 81, '305'), (1037, 81, '306'), (1038, 81, '307'),
  (1039, 81, '308'), (1040, 81, '309'), (1041, 81, '405'), (1042, 81, '406'), (1043, 81, '407'), (1044, 81, '605'),
  (1045, 81, '607'), (1046, 81, '806'), (1047, 81, '807'), (1048, 81, '1007'), (1049, 81, '4007'),
  (1050, 82, 'Acclaim'), (1051, 82, 'Breeze'), (1052, 82, 'Grand Voyager'), (1053, 82, 'Laser'), (1054, 82, 'Neon'),
  (1055, 82, 'Prowler'), (1056, 82, 'Sundance'), (1057, 82, 'Voyager'), (1058, 83, '6000'), (1059, 83, 'Aztec'),
  (1060, 83, 'Bonneville'), (1061, 83, 'Firebird'), (1062, 83, 'GTO'), (1063, 83, 'Grand AM'), (1064, 83, 'Grand Prix'),
  (1065, 83, 'Phoenix'), (1066, 83, 'Solstige'), (1067, 83, 'Sunbird'), (1068, 83, 'Sunfire'),
  (1069, 83, 'Trans Sport'), (1070, 83, 'Vibe'), (1071, 84, '911'), (1072, 84, '924'), (1073, 84, '928'),
  (1074, 84, '944'), (1075, 84, '968'), (1076, 84, 'Boxster'), (1077, 84, 'Carrera GT'), (1078, 84, 'Cayenne'),
  (1079, 84, 'Cayman'), (1080, 85, 'Persona 300 Compact'), (1081, 85, 'Persona 400'), (1082, 86, 'G-modell'),
  (1083, 86, 'Pinzgauer'), (1084, 87, '5'), (1085, 87, '9'), (1086, 87, '11'), (1087, 87, '18'), (1088, 87, '19'),
  (1089, 87, '20'), (1090, 87, '21'), (1091, 87, '25'), (1092, 87, 'Avantime'), (1093, 87, 'Clio'),
  (1094, 87, 'Clio Symbol'), (1095, 87, 'Espace'), (1096, 87, 'Fuego'), (1097, 87, 'Grand Scenic'),
  (1098, 87, 'Kangoo Express'), (1099, 87, 'Kangoo Passenger'), (1100, 87, 'Laguna'), (1101, 87, 'Logan'),
  (1102, 87, 'Master'), (1103, 87, 'Megane'), (1104, 87, 'Modus'), (1105, 87, 'Rapid'), (1106, 87, 'Safrane'),
  (1107, 87, 'Scenic'), (1108, 87, 'Scenic RX'), (1109, 87, 'Super 5'), (1110, 87, 'Symbol'), (1111, 87, 'Trafic'),
  (1112, 87, 'Twingo'), (1113, 87, 'Vel Satis'), (1114, 88, 'Corniche Cabrio'), (1115, 88, 'Phantom'),
  (1116, 88, 'Phantom Drophead Coupe'), (1117, 88, 'Silver Seraph'), (1118, 88, 'Silver Spur'), (1119, 89, '25'),
  (1120, 89, '45'), (1121, 89, '75'), (1122, 89, '200'), (1123, 89, '400'), (1124, 89, '600'), (1125, 89, '800'),
  (1126, 89, 'Maestro'), (1127, 89, 'Mini MK'), (1128, 89, 'Montego'), (1129, 90, '9-2X'), (1130, 90, '9-3'),
  (1131, 90, '9-5'), (1132, 90, '9-7X'), (1133, 90, '99'), (1134, 90, '900'), (1135, 90, '9000'), (1136, 91, 'S7'),
  (1137, 92, 'ION'), (1138, 92, 'LS'), (1139, 92, 'SC'), (1140, 92, 'SL'), (1141, 92, 'VUE'), (1142, 93, 'tC'),
  (1143, 93, 'xA'), (1144, 93, 'xB'), (1145, 94, 'Alhambra'), (1146, 94, 'Altea'), (1147, 94, 'Arosa'),
  (1148, 94, 'Cordoba'), (1149, 94, 'Ibiza'), (1150, 94, 'Leon'), (1151, 94, 'Malaga'), (1152, 94, 'Ronda'),
  (1153, 94, 'Toledo'), (1154, 95, 'Sceo'), (1155, 96, 'Fabia'), (1156, 96, 'Favorit'), (1157, 96, 'Felicia'),
  (1158, 96, 'Octavia'), (1159, 96, 'Octavia Scout'), (1160, 96, 'Roomster'), (1161, 96, 'Superb'),
  (1162, 97, 'Crossblade'), (1163, 97, 'Forfour'), (1164, 97, 'Fortwo'), (1165, 97, 'Roadster'), (1166, 98, 'C8'),
  (1167, 99, 'Actyon'), (1168, 99, 'Chairman'), (1169, 99, 'Family'), (1170, 99, 'Istana'), (1171, 99, 'Korando'),
  (1172, 99, 'Kyron'), (1173, 99, 'Musso'), (1174, 99, 'Rexton'), (1175, 99, 'Rodius'), (1176, 99, 'Tager'),
  (1177, 100, 'Baja'), (1178, 100, 'Forester'), (1179, 100, 'Impreza'), (1180, 100, 'Impreza WRX'),
  (1181, 100, 'Justy'), (1182, 100, 'Legacy'), (1183, 100, 'Leone'), (1184, 100, 'Libero'), (1185, 100, 'Outback'),
  (1186, 100, 'Pleo'), (1187, 100, 'R2'), (1188, 100, 'SVX'), (1189, 100, 'Stella'), (1190, 100, 'Traviq'),
  (1191, 100, 'Tribeca'), (1192, 100, 'Vivio'), (1193, 100, 'XT'), (1194, 101, 'Aerio'), (1195, 101, 'Alto'),
  (1196, 101, 'Baleno'), (1197, 101, 'Cultus Wagon'), (1198, 101, 'Escudo'), (1199, 101, 'Every'),
  (1200, 101, 'Every Landy'), (1201, 101, 'Forenza'), (1202, 101, 'Grand Vitara'), (1203, 101, 'Ignis'),
  (1204, 101, 'Jimny'), (1205, 101, 'Kei'), (1206, 101, 'Liana'), (1207, 101, 'MR Wagon'), (1208, 101, 'SX4'),
  (1209, 101, 'Samurai'), (1210, 101, 'Sidekick'), (1211, 101, 'Swift'), (1212, 101, 'Verona'), (1213, 101, 'Vitara'),
  (1214, 101, 'Wagon R+'), (1215, 101, 'X-90'), (1216, 101, 'XL7'), (1217, 102, 'T613'), (1218, 103, 'Century'),
  (1219, 104, 'Admiral'), (1220, 105, '4runner'), (1221, 105, 'Allex'), (1222, 105, 'Allion'), (1223, 105, 'Alphard'),
  (1224, 105, 'Altezza'), (1225, 105, 'Aristo'), (1226, 105, 'Aurion'), (1227, 105, 'Auris'), (1228, 105, 'Avalon'),
  (1229, 105, 'Avensis'), (1230, 105, 'Avensis Verso'), (1231, 105, 'BB'), (1232, 105, 'Brevis'),
  (1233, 105, 'Caldina'), (1234, 105, 'Cami'), (1235, 105, 'Camry'), (1236, 105, 'Camry Solara'), (1237, 105, 'Carib'),
  (1238, 105, 'Carina'), (1239, 105, 'Carina E'), (1240, 105, 'Carina ED'), (1241, 105, 'Cavalier'),
  (1242, 105, 'Celica'), (1243, 105, 'Chaser'), (1244, 105, 'Corolla'), (1245, 105, 'Corolla'),
  (1246, 105, 'Corolla Ceres'), (1247, 105, 'Corolla FX'), (1248, 105, 'Corolla Fielder'), (1249, 105, 'Corolla Levin'),
  (1250, 105, 'Corolla Rumion'), (1251, 105, 'Corolla RunX'), (1252, 105, 'Corolla Spacio'),
  (1253, 105, 'Corolla Verso'), (1254, 105, 'Corona'), (1255, 105, 'Corsa'), (1256, 105, 'Cressida'),
  (1257, 105, 'Cresta'), (1258, 105, 'Crown'), (1259, 105, 'Crown'), (1260, 105, 'Crown Athlete'),
  (1261, 105, 'Crown Majesta'), (1262, 105, 'Curren'), (1263, 105, 'Cynos'), (1264, 105, 'Duet'), (1265, 105, 'Echo'),
  (1266, 105, 'Estima'), (1267, 105, 'FJ Cruiser'), (1268, 105, 'Fortuner'), (1269, 105, 'Funcargo'),
  (1270, 105, 'Gaia'), (1271, 105, 'Grand Hiace'), (1272, 105, 'Granvia'), (1273, 105, 'Harrier'), (1274, 105, 'Hiace'),
  (1275, 105, 'Highlander'), (1276, 105, 'Hilux Pick Up'), (1277, 105, 'Hilux Surf'), (1278, 105, 'ISis'),
  (1279, 105, 'Ipsum'), (1280, 105, 'Ist'), (1281, 105, 'Kluger'), (1282, 105, 'Land Cruiser'),
  (1283, 105, 'Land Cruiser (120) Prado'), (1284, 105, 'Land Cruiser (90) Prado'), (1285, 105, 'Land Cruiser 100'),
  (1286, 105, 'Land Cruiser 200'), (1287, 105, 'Land Cruiser 70'), (1288, 105, 'Land Cruiser 80'),
  (1289, 105, 'Lite Ace'), (1290, 105, 'MR 2'), (1291, 105, 'MR-S'), (1292, 105, 'Mark II'), (1293, 105, 'Mark X'),
  (1294, 105, 'MasterAce'), (1295, 105, 'Matrix'), (1296, 105, 'Nadia'), (1297, 105, 'Noah'), (1298, 105, 'Opa'),
  (1299, 105, 'Origin'), (1300, 105, 'Paseo'), (1301, 105, 'Passo'), (1302, 105, 'Picnic'), (1303, 105, 'Platz'),
  (1304, 105, 'Porte'), (1305, 105, 'Premio'), (1306, 105, 'Previa'), (1307, 105, 'Prius'), (1308, 105, 'Probox'),
  (1309, 105, 'Progres'), (1310, 105, 'Pronard'), (1311, 105, 'RAV 4'), (1312, 105, 'Ractis'), (1313, 105, 'Raum'),
  (1314, 105, 'Regius'), (1315, 105, 'Regius'), (1316, 105, 'Regius Ace'), (1317, 105, 'Scepter'),
  (1318, 105, 'Sequoia'), (1319, 105, 'Sera'), (1320, 105, 'Sienna'), (1321, 105, 'Sienta'), (1322, 105, 'Soarer'),
  (1323, 105, 'Solara'), (1324, 105, 'Sparky'), (1325, 105, 'Sprinter'), (1326, 105, 'Starlet'), (1327, 105, 'Succeed'),
  (1328, 105, 'Supra'), (1329, 105, 'Tacoma'), (1330, 105, 'Tercel'), (1331, 105, 'Tundra'), (1332, 105, 'Vellfire'),
  (1333, 105, 'Venza'), (1334, 105, 'Verossa'), (1335, 105, 'Vista'), (1336, 105, 'Vitz'), (1337, 105, 'Voltz'),
  (1338, 105, 'Voxy'), (1339, 105, 'Will'), (1340, 105, 'Windom'), (1341, 105, 'Wish'), (1342, 105, 'Yaris'),
  (1343, 105, 'Yaris Verso'), (1344, 106, 'P 601'), (1345, 107, 'Griffith'), (1346, 108, 'W8 Twin Turbo'),
  (1347, 109, 'Bora'), (1348, 109, 'Caddy'), (1349, 109, 'Corrado'), (1350, 109, 'Eos'), (1351, 109, 'Fox'),
  (1352, 109, 'Golf'), (1353, 109, 'Golf Plus'), (1354, 109, 'Jetta'), (1355, 109, 'Kaefer'), (1356, 109, 'Lupo'),
  (1357, 109, 'Multivan'), (1358, 109, 'NEW Beetle'), (1359, 109, 'Passat'), (1360, 109, 'Phaeton'),
  (1361, 109, 'Pointer'), (1362, 109, 'Polo'), (1363, 109, 'Santana'), (1364, 109, 'Scirocco'), (1365, 109, 'Sharan'),
  (1366, 109, 'Taro'), (1367, 109, 'Tiguan'), (1368, 109, 'Touareg'), (1369, 109, 'Touran'), (1370, 109, 'Vento'),
  (1371, 110, '164'), (1372, 110, '240'), (1373, 110, '340-360'), (1374, 110, '440 K'), (1375, 110, '460 L'),
  (1376, 110, '480 E'), (1377, 110, '740'), (1378, 110, '760'), (1379, 110, '780 Bertone'), (1380, 110, '850'),
  (1381, 110, '940'), (1382, 110, '960'), (1383, 110, 'C30'), (1384, 110, 'C70'), (1385, 110, 'S40'),
  (1386, 110, 'S60'), (1387, 110, 'S70'), (1388, 110, 'S80'), (1389, 110, 'S90'), (1390, 110, 'V40 Kombi'),
  (1391, 110, 'V50'), (1392, 110, 'V70'), (1393, 110, 'XC70'), (1394, 110, 'XC90'), (1395, 111, '353'),
  (1396, 112, 'GT'), (1397, 112, 'Roadster'), (1398, 113, 'PICKUP X3'), (1399, 113, 'SR-V X3'), (1400, 113, 'SUV X3'),
  (1401, 114, 'GrandTiger'), (1402, 114, 'Landmark'), (1403, 115, '1111 Ока 1111'), (1404, 115, '1111 Ока 11113'),
  (1405, 115, '2101'), (1406, 115, '2102'), (1407, 115, '2103'), (1408, 115, '2104'), (1409, 115, '2105'),
  (1410, 115, '2106'), (1411, 115, '2107'), (1412, 115, '2108'), (1413, 115, '2109'), (1414, 115, '2110'),
  (1415, 115, '2111'), (1416, 115, '2112'), (1417, 115, '2113'), (1418, 115, '2114'), (1419, 115, '2115'),
  (1420, 115, '2123'), (1421, 115, '2129'), (1422, 115, '2131'), (1423, 115, '2329'), (1424, 115, '21043'),
  (1425, 115, '21047'), (1426, 115, '21053'), (1427, 115, '21054'), (1428, 115, '21055'), (1429, 115, '21061'),
  (1430, 115, '21063'), (1431, 115, '21065'), (1432, 115, '21071'), (1433, 115, '21072'), (1434, 115, '21073'),
  (1435, 115, '21074'), (1436, 115, '21079'), (1437, 115, '21081'), (1438, 115, '21083'), (1439, 115, '21086'),
  (1440, 115, '2108i'), (1441, 115, '21093'), (1442, 115, '21099'), (1443, 115, '21099i'), (1444, 115, '2109i'),
  (1445, 115, '21101'), (1446, 115, '21102'), (1447, 115, '21103'), (1448, 115, '21104'), (1449, 115, '21108'),
  (1450, 115, '21111'), (1451, 115, '21112'), (1452, 115, '21113'), (1453, 115, '21121'), (1454, 115, '21122'),
  (1455, 115, '21123'), (1456, 115, '21124'), (1457, 115, '2115 Wankel'), (1458, 115, '2115i'),
  (1459, 115, '2120 Надежда'), (1460, 115, '2121 4x4 2121'), (1461, 115, '2121 4x4 21213'),
  (1462, 115, '2121 4x4 21214'), (1463, 115, '2121 4x4 21217'), (1464, 115, '2121 4x4 21218'),
  (1465, 115, 'Kalina Hatchback'), (1466, 115, 'Kalina Sedan'), (1467, 115, 'Kalina Wagon'), (1468, 115, 'Priora'),
  (1469, 116, 'Автокам'), (1470, 117, '13'), (1471, 117, '14'), (1472, 117, '20'), (1473, 117, '21'), (1474, 117, '22'),
  (1475, 117, '24'), (1476, 117, '69'), (1477, 117, '3102'), (1478, 117, '3105'), (1479, 117, '3110'),
  (1480, 117, '3111'), (1481, 117, '31022'), (1482, 117, '31026'), (1483, 117, '31029'), (1484, 117, '3102i'),
  (1485, 117, '31105'), (1486, 117, '310221'), (1487, 117, '311055'), (1488, 117, '3110i'), (1489, 117, 'М1'),
  (1490, 118, '965'), (1491, 118, '968'), (1492, 118, '1102'), (1493, 118, '1103'), (1494, 118, '1105'),
  (1495, 118, '1140'), (1496, 118, 'Sens'), (1497, 119, '114'), (1498, 119, '117'), (1499, 119, '4104'),
  (1500, 120, '412'), (1501, 120, '2125'), (1502, 120, '2126'), (1503, 120, '2715'), (1504, 120, '2717'),
  (1505, 120, '21261'), (1506, 120, '27171'), (1507, 121, 'Ока'), (1508, 122, '967'), (1509, 122, '968'),
  (1510, 122, '969'), (1511, 123, '400'), (1512, 123, '401'), (1513, 123, '402'), (1514, 123, '403'),
  (1515, 123, '406'), (1516, 123, '407'), (1517, 123, '408'), (1518, 123, '412'), (1519, 123, '2125'),
  (1520, 123, '2140'), (1521, 123, '2141'), (1522, 123, '2335'), (1523, 123, '423 Kombi'), (1524, 123, '5846'),
  (1525, 123, 'Aslk 2137 Kombi'), (1526, 123, 'Aslk 2138'), (1527, 123, 'Aslk 2140'), (1528, 123, 'Князь Владимир'),
  (1529, 123, 'Святогор'), (1530, 123, 'Юрий Долгорукий'), (1531, 124, '1111'), (1532, 125, 'Мотоколяска'),
  (1533, 126, 'Tager'), (1534, 127, '469'), (1535, 127, '2206'), (1536, 127, '3151'), (1537, 127, '3153'),
  (1538, 127, '3159'), (1539, 127, '3160'), (1540, 127, '3162'), (1541, 127, '3163'), (1542, 127, '31512'),
  (1543, 127, '31514'), (1544, 127, '31519'), (1545, 127, '315195');

INSERT INTO places (title, date_begin, data_end) VALUES
  ('г. Минск парк Горького', '2018-03-16', '2018-03-18'),
  ('г. Полоцк Полоцкий коллегиум', '2018-03-19', '2018-03-20');

INSERT INTO organizations (title) VALUES ('Artezio'), ('PSU.BY');

INSERT INTO reports (resource_document) VALUES ('report.pdf'), ('report.docx');

INSERT INTO orders (id_place, id_organization, id_report) VALUES (1, 1, 1), (2, 2, 2);

INSERT INTO transports (id_mark, id_model, engine, capacity_kg, status, seats, photo_url) VALUES
  (1, 2, 'newEngine', 545, 1, 8, NULL),
  (1, 4, 'engine V8', 545, 1, 8, NULL),
  (1, 8, 'TDI 2.0', 545, 1, 8, NULL);

INSERT INTO equipment (title, weight, date_buy, date_end) VALUES
  ('Батут "Дракон"', 255, '2000-03-16', '2019-03-16'),
  ('Батут "Счастье"', 125, '2000-03-16', '2019-03-16'),
  ('Батут "Дракон"', 255, '2000-03-16', '2019-03-16');