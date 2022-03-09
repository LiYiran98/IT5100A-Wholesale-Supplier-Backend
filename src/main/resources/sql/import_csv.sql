USE tpcc;
-- customer
# DELETE FROM customer;
LOAD DATA INFILE '../Uploads/customer-1.csv'
    IGNORE
    INTO TABLE customer
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n';

LOAD DATA INFILE '../Uploads/customer-2.csv'
    IGNORE
    INTO TABLE customer
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n';

LOAD DATA INFILE '../Uploads/customer-3.csv'
    IGNORE
    INTO TABLE customer
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n';

LOAD DATA INFILE '../Uploads/customer-4.csv'
    IGNORE
    INTO TABLE customer
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n';

-- district
# DELETE FROM district;

LOAD DATA INFILE '../Uploads/district.csv'
    IGNORE
    INTO TABLE district
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n';

-- item
LOAD DATA INFILE '../Uploads/item.csv'
    IGNORE
    INTO TABLE item
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n';

-- order
LOAD DATA INFILE '../Uploads/order.csv'
    IGNORE
    INTO TABLE order_
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n';

-- order-line
LOAD DATA INFILE '../Uploads/order-line-1.csv'
    IGNORE
    INTO TABLE order_line
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n';


LOAD DATA INFILE '../Uploads/order-line-2.csv'
    IGNORE
    INTO TABLE order_line
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n';

LOAD DATA INFILE '../Uploads/order-line-3.csv'
    IGNORE
    INTO TABLE order_line
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n';

LOAD DATA INFILE '../Uploads/order-line-4.csv'
    IGNORE
    INTO TABLE order_line
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n';

LOAD DATA INFILE '../Uploads/order-line-5.csv'
    IGNORE
    INTO TABLE order_line
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n';

-- stock
LOAD DATA INFILE '../Uploads/stock-1.csv'
    IGNORE
    INTO TABLE stock
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n';

LOAD DATA INFILE '../Uploads/stock-2.csv'
    IGNORE
    INTO TABLE stock
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n';

LOAD DATA INFILE '../Uploads/stock-3.csv'
    IGNORE
    INTO TABLE stock
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n';

LOAD DATA INFILE '../Uploads/stock-4.csv'
    IGNORE
    INTO TABLE stock
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n';

LOAD DATA INFILE '../Uploads/stock-5.csv'
    IGNORE
    INTO TABLE stock
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n';

-- warehouse
LOAD DATA INFILE '../Uploads/warehouse.csv'
    IGNORE
    INTO TABLE warehouse
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n';
