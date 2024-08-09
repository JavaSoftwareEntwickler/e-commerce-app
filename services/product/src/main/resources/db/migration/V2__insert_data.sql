-- Cancellazione dei dati esistenti
DELETE FROM product;
DELETE FROM category;

-- Inserimento dati fittizi per la tabella category
INSERT INTO category (id, description, name) VALUES
    (nextval('category_seq'), 'Abbigliamento maschile', 'Abbigliamento'),
    (nextval('category_seq'), 'Scarpe sportive', 'Scarpe'),
    (nextval('category_seq'), 'Elettronica di consumo', 'Elettronica'),
    (nextval('category_seq'), 'Arredamento per la casa', 'Arredamento'),
    (nextval('category_seq'), 'Libri e riviste', 'Libri'),
    (nextval('category_seq'), 'Giocattoli per bambini', 'Giocattoli'),
    (nextval('category_seq'), 'Articoli per la cucina', 'Cucina'),
    (nextval('category_seq'), 'Prodotti per la cura personale', 'Cura personale'),
    (nextval('category_seq'), 'Strumenti musicali', 'Musica'),
    (nextval('category_seq'), 'Prodotti alimentari', 'Alimentari');

-- Inserimento dati fittizi per la tabella product
INSERT INTO product (id, description, name, available_quantity, price, category_id) VALUES
    -- Categoria Abbigliamento
    (nextval('product_seq'), 'Maglietta casual in cotone', 'Maglietta uomo', 100, 29.99, (select id from category where name = 'Abbigliamento')),
    (nextval('product_seq'), 'Jeans slim fit', 'Jeans uomo', 150, 49.99, (select id from category where name = 'Abbigliamento')),
    (nextval('product_seq'), 'Cappotto invernale', 'Cappotto uomo', 80, 99.99, (select id from category where name = 'Abbigliamento')),
    (nextval('product_seq'), 'Scarpe da ginnastica', 'Scarpe da ginnastica', 200, 69.99, (select id from category where name = 'Abbigliamento')),
    (nextval('product_seq'), 'Giacca di pelle', 'Giacca di pelle uomo', 120, 149.99, (select id from category where name = 'Abbigliamento')),
    (nextval('product_seq'), 'Sciarpa di lana', 'Sciarpa uomo', 90, 24.99, (select id from category where name = 'Abbigliamento')),
    (nextval('product_seq'), 'Camicia elegante', 'Camicia uomo', 110, 39.99, (select id from category where name = 'Abbigliamento')),
    (nextval('product_seq'), 'Pantaloni chino', 'Pantaloni chino uomo', 70, 34.99, (select id from category where name = 'Abbigliamento')),
    (nextval('product_seq'), 'Calze sportive', 'Calze uomo', 180, 9.99, (select id from category where name = 'Abbigliamento')),
    (nextval('product_seq'), 'T-shirt basic', 'T-shirt uomo', 95, 14.99, (select id from category where name = 'Abbigliamento')),

    -- Categoria Scarpe
    (nextval('product_seq'), 'Scarpe da corsa leggere', 'Scarpe running', 100, 79.99, (select id from category where name = 'Scarpe')),
    (nextval('product_seq'), 'Stivaletti in pelle', 'Stivaletti donna', 150, 89.99, (select id from category where name = 'Scarpe')),
    (nextval('product_seq'), 'Sandali estivi', 'Sandali unisex', 80, 49.99, (select id from category where name = 'Scarpe')),
    (nextval('product_seq'), 'Scarpe da trekking', 'Scarpe trekking', 200, 129.99, (select id from category where name = 'Scarpe')),
    (nextval('product_seq'), 'Décolleté eleganti', 'Décolleté donna', 120, 69.99, (select id from category where name = 'Scarpe')),
    (nextval('product_seq'), 'Scarpe da ballo', 'Scarpe da ballo', 90, 59.99, (select id from category where name = 'Scarpe')),
    (nextval('product_seq'), 'Pantofole comode', 'Pantofole unisex', 110, 19.99, (select id from category where name = 'Scarpe')),
    (nextval('product_seq'), 'Sneakers moda', 'Sneakers moda', 70, 54.99, (select id from category where name = 'Scarpe')),
    (nextval('product_seq'), 'Ciabatte da casa', 'Ciabatte unisex', 180, 29.99, (select id from category where name = 'Scarpe')),
    (nextval('product_seq'), 'Scarpette da ballo', 'Scarpette da ballo', 95, 39.99, (select id from category where name = 'Scarpe')),

    (nextval('product_seq'), 'Smartphone Android con schermo OLED', 'Smartphone Android', 50, 599.99, (select id from category where name = 'Elettronica')),
    (nextval('product_seq'), 'Laptop ultraleggero', 'Laptop', 30, 999.99, (select id from category where name = 'Elettronica')),
    (nextval('product_seq'), 'Televisore 4K 55 pollici', 'Televisore 4K', 40, 799.99, (select id from category where name = 'Elettronica'));

