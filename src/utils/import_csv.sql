USE tpcc;
LOAD DATA INFILE 'E:/Program Files/MySQL/MySQL Server 8.0/Data/Uploads/customer-1.csv'
    INTO TABLE customer
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;

LOAD DATA INFILE 'E:/Program Files/MySQL/MySQL Server 8.0/Data/Uploads/customer-2.csv'
    INTO TABLE customer
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;

LOAD DATA INFILE 'E:/Program Files/MySQL/MySQL Server 8.0/Data/Uploads/customer-3.csv'
    INTO TABLE customer
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;

LOAD DATA INFILE 'E:/Program Files/MySQL/MySQL Server 8.0/Data/Uploads/customer-4.csv'
    INTO TABLE customer
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;

LOAD DATA INFILE 'E:/Program Files/MySQL/MySQL Server 8.0/Data/Uploads/customer-4.csv'
    INTO TABLE customer
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;

LOAD DATA INFILE 'E:/Program Files/MySQL/MySQL Server 8.0/Data/Uploads/customer-5.csv'
    INTO TABLE customer
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;

-- district
LOAD DATA INFILE 'E:/Program Files/MySQL/MySQL Server 8.0/Data/Uploads/district.csv'
    INTO TABLE district
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;

-- item
LOAD DATA INFILE 'E:/Program Files/MySQL/MySQL Server 8.0/Data/Uploads/item.csv'
    INTO TABLE item
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;

-- order
LOAD DATA INFILE 'E:/Program Files/MySQL/MySQL Server 8.0/Data/Uploads/order.csv'
    INTO TABLE order_
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;

-- order-line
LOAD DATA INFILE 'E:/Program Files/MySQL/MySQL Server 8.0/Data/Uploads/order-line-1.csv'
    INTO TABLE order_line
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;


LOAD DATA INFILE 'E:/Program Files/MySQL/MySQL Server 8.0/Data/Uploads/order-line-2.csv'
    INTO TABLE order_line
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;

LOAD DATA INFILE 'E:/Program Files/MySQL/MySQL Server 8.0/Data/Uploads/order-line-3.csv'
    INTO TABLE order_line
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;

LOAD DATA INFILE 'E:/Program Files/MySQL/MySQL Server 8.0/Data/Uploads/order-line-4.csv'
    INTO TABLE order_line
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;

LOAD DATA INFILE 'E:/Program Files/MySQL/MySQL Server 8.0/Data/Uploads/order-line-5.csv'
    INTO TABLE order_line
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;

-- stock
LOAD DATA INFILE 'E:/Program Files/MySQL/MySQL Server 8.0/Data/Uploads/stock-1.csv'
    INTO TABLE stock
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;

LOAD DATA INFILE 'E:/Program Files/MySQL/MySQL Server 8.0/Data/Uploads/stock-2.csv'
    INTO TABLE stock
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;

LOAD DATA INFILE 'E:/Program Files/MySQL/MySQL Server 8.0/Data/Uploads/stock-3.csv'
    INTO TABLE stock
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;

LOAD DATA INFILE 'E:/Program Files/MySQL/MySQL Server 8.0/Data/Uploads/stock-4.csv'
    INTO TABLE stock
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;

LOAD DATA INFILE 'E:/Program Files/MySQL/MySQL Server 8.0/Data/Uploads/stock-5.csv'
    INTO TABLE stock
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;

-- warehouse
LOAD DATA INFILE 'E:/Program Files/MySQL/MySQL Server 8.0/Data/Uploads/warehouse.csv'
    INTO TABLE warehouse
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;
