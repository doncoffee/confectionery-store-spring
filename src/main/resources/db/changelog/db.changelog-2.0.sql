--liquibase formatted sql

--changeset doncoffee:insert-values-into-address-table-liquibase
INSERT INTO address (name)
VALUES ('439 Marvon Street Victoria, TX 77904'),
       ('7891 Summit Street Ponte Vedra Beach, FL 32082'),
       ('Wilhelminenstrasse 6, A-1170 Vienna, Austria'),
       ('440 North County Ave. Land O Lakes, FL 34639'),
       ('8 Ann St. Greenville, NC 27834'),
       ('156 Sussex Ave. Bloomington, IN 47401'),
       ('9237 Columbia Street Hartford, CT 06106');

--changeset doncoffee:insert-values-into-phone_number-table-liquibase
INSERT INTO phone_number (number)
VALUES ('+1 (443) 312-6875'),
       ('+1 (803) 366-2953'),
       ('+1 (318) 872-6052'),
       ('+1 (281) 719-0423'),
       ('+1 (641) 322-5266'),
       ('+1 (732) 747-7815'),
       ('+1 (315) 843-5125'),
       ('+1 (407) 396-9184');

--changeset doncoffee:insert-values-into-brand-table-liquibase
INSERT INTO brand (name)
VALUES ('Dove'),
       ('Mars'),
       ('Hersheys'),
       ('Nestlé'),
       ('Chips Ahoy Cookies'),
       ('Belvita'),
       ('Oreo'),
       ('Mars Wrigley Confectionery'),
       ('General Mills'),
       ('Mondelez International'),
       ('Pepperidge Farm Milano Dark Chocolate');

--changeset doncoffee:insert-values-into-store-table-liquibase
INSERT INTO store (address_id, phone_number_id)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4);

--changeset doncoffee:insert-values-into-supplier-table-liquibase
INSERT INTO supplier (contact_person, name, address_id, phone_number_id)
VALUES ('John Connor', 'Vanilla Food Company', 4, 5),
       ('Pamella Fillipson', 'Stover & Company', 5, 6),
       ('Robert Anderson', 'DELICERT DE CHOCOLAT & MaryJane''s', 6, 7),
       ('Fillipo Anchellotti', 'JOSEF MANNER & COMP. AG', 7, 8);

--changeset doncoffee:insert-values-into-chocolate-table-liquibase
INSERT INTO chocolate (composition, price, type, weight, brand_id, store_id, supplier_id)
VALUES ('Energy value 525 kcal/2198.1 kJ
  Fat 25 g saturated 12 g Carbohydrates 48 g sugar
  44 g Protein 8 g Salt 0.2 g', 2, 'milk', 100, 1, 1, 1),
       ('Energy value 1989 kJ / 477 kcal
  Fat 25.82 g saturated 18.11 g Carbohydrates 51.24 g
  sugar 19.76 g Protein 2.06 g Salt 0.18 g', 5, 'dark', 200, 2, 2, 2),
       ('Energy value 567 kcal/2198.1 kJ
  Fat 25 g saturated 12 g Carbohydrates 48 g sugar 44 g
  Protein 8 g Salt 0.2 g', 3, 'white', 70, 3, 3, 3),
       ('Energy value 1989 kJ / 477 kcal
  Fat 25.82 g saturated 18.11 g Carbohydrates 51.24 g
  sugar 19.76 g Protein 2.06 g Salt 0.18 g', 2, 'milk', 100, 4, 4, 4);

--changeset doncoffee:insert-values-into-cookie-table-liquibase
INSERT INTO cookie (composition, price, type, weight, brand_id, store_id, supplier_id)
VALUES ('Energy value 525 kcal/2198.1 kJ Fat 25 g saturated 12 g Carbohydrates 48 g sugar 44 g Protein 8 g Salt 0.2 g',
        2, 'oatmeal', 200, 1, 1, 1),
       ('Energy value 1989 kJ / 477 kcal Fat 25.82 g saturated 18.11 g Carbohydrates 51.24 g sugar 19.76 g Protein 2.06 g Salt 0.18 g',
        5, 'chocolate', 500, 2, 2, 2),
       ('Energy value 567 kcal/2198.1 kJ Fat 25 g saturated 12 g Carbohydrates 48 g sugar 44 g Protein 8 g Salt 0.2 g',
        3, 'gingersnap', 120, 3, 3, 3),
       ('Energy value 1989 kJ / 477 kcal Fat 25.82 g saturated 18.11 g Carbohydrates 51.24 g sugar 19.76 g Protein 2.06 g Salt 0.18 g',
        2, 'cottage cheese', 340, 4, 4, 4);


--changeset doncoffee:insert-values-into-sweets-table-liquibase
INSERT INTO sweets (composition, price, type, weight, brand_id, store_id, supplier_id)
VALUES ('Energy value 525 kcal/2198.1 kJ Fat 25 g saturated 12 g Carbohydrates 48 g sugar 44 g Protein 8 g Salt 0.2 g',
        2, 'caramel', 200, 1, 1, 1),
       ('Energy value 1989 kJ / 477 kcal Fat 25.82 g saturated 18.11 g Carbohydrates 51.24 g sugar 19.76 g Protein 2.06 g Salt 0.18 g',
        5, 'chocolate', 500, 2, 2, 2),
       ('Energy value 567 kcal/2198.1 kJ Fat 25 g saturated 12 g Carbohydrates 48 g sugar 44 g Protein 8 g Salt 0.2 g',
        3, 'gummies', 120, 3, 3, 3),
       ('Energy value 1989 kJ / 477 kcal Fat 25.82 g saturated 18.11 g Carbohydrates 51.24 g sugar 19.76 g Protein 2.06 g Salt 0.18 g',
        2, 'candy', 340, 4, 4, 4);
