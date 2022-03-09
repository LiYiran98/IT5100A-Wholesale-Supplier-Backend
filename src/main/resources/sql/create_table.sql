-- Create Database
DROP DATABASE tpcc;

CREATE DATABASE tpcc;

USE tpcc;

-- Create tables

-- Warehouse
CREATE TABLE IF NOT EXISTS warehouse (
                                         W_ID INT NOT NULL,
                                         W_NAME VARCHAR(10),
    W_STREET_1 VARCHAR(20),
    W_STREET_2 VARCHAR(20),
    W_CITY VARCHAR(20),
    W_STATE CHAR(2),
    W_ZIP CHAR(9),
    W_TAX DECIMAL(4,4),
    W_YTD DECIMAL(12,2),

    PRIMARY KEY (W_ID)
    );


-- District
CREATE TABLE IF NOT EXISTS district (
                                        D_W_ID INT NOT NULL,
                                        D_ID INT NOT NULL,
                                        D_NAME VARCHAR(10),
    D_STREET_1 VARCHAR(20),
    D_STREET_2 VARCHAR(20),
    D_CITY VARCHAR(20),
    D_STATE CHAR(2),
    D_ZIP CHAR(9),
    D_TAX DECIMAL(4,4),
    D_YTD DECIMAL(12,2),
    D_NEXT_O_ID INT,

    PRIMARY KEY (D_W_ID, D_ID)
    );

-- Customer
CREATE TABLE IF NOT EXISTS customer (
                                        C_W_ID INT NOT NULL,
                                        C_D_ID INT NOT NULL,
                                        C_ID INT NOT NULL,
                                        C_FIRST VARCHAR(16),
    C_MIDDLE CHAR(2),
    C_LAST VARCHAR(16),
    C_STREET_1 VARCHAR(20),
    C_STREET_2 VARCHAR(20),
    C_CITY VARCHAR(20),
    C_STATE CHAR(2),
    C_ZIP CHAR(9),
    C_PHONE CHAR(16),
    C_SINCE TIMESTAMP,
    C_CREDIT CHAR(2),
    C_CREDIT_LIM DECIMAL(12,2),
    C_DISCOUNT DECIMAL(4,4),
    C_BALANCE DECIMAL(12,2),
    C_YTD_PAYMENT FLOAT,
    C_PAYMENT_CNT INT,
    C_DELIVERY_CNT INT,
    C_DATA VARCHAR(500),

    PRIMARY KEY (C_W_ID, C_D_ID, C_ID)
    );

-- Order
CREATE TABLE IF NOT EXISTS order_ (
                                      O_W_ID INT NOT NULL,
                                      O_D_ID INT NOT NULL,
                                      O_ID INT NOT NULL,
                                      O_C_ID INT,
                                      O_CARRIER_ID INT,
                                      O_OL_CNT DECIMAL(2,0),
    O_ALL_LOCAL DECIMAL(1,0),
    O_ENTRY_D TIMESTAMP,

    PRIMARY KEY (O_W_ID, O_D_ID, O_ID)
    );

-- Item
CREATE TABLE IF NOT EXISTS item (
                                    I_ID INT NOT NULL,
                                    I_NAME VARCHAR(24),
    I_PRICE DECIMAL(5,2),
    I_IM_ID INT,
    I_DATA VARCHAR(50),

    PRIMARY KEY (I_ID)
    );

-- Order-Line
CREATE TABLE IF NOT EXISTS order_line (
                                          OL_W_ID INT NOT NULL,
                                          OL_D_ID INT NOT NULL,
                                          OL_O_ID INT NOT NULL,
                                          OL_NUMBER INT NOT NULL,
                                          OL_I_ID INT,
                                          OL_DELIVERY_D TIMESTAMP,
                                          OL_AMOUNT DECIMAL(6,2),
    OL_SUPPLY_W_ID INT,
    OL_QUANTITY DECIMAL(2,0),
    OL_DIST_INFO CHAR(24),

    PRIMARY KEY (OL_W_ID, OL_D_ID, OL_O_ID, OL_NUMBER)
    );

-- Stock
CREATE TABLE IF NOT EXISTS stock
(
    S_W_ID       INT NOT NULL,
    S_I_ID       INT NOT NULL,
    S_QUANTITY   DECIMAL(4, 0),
    S_YTD        DECIMAL(8, 2),
    S_ORDER_CNT  INT,
    S_REMOTE_CNT INT,
    S_DIST_01    CHAR(24),
    S_DIST_02    CHAR(24),
    S_DIST_03    CHAR(24),
    S_DIST_04    CHAR(24),
    S_DIST_05    CHAR(24),
    S_DIST_06    CHAR(24),
    S_DIST_07    CHAR(24),
    S_DIST_08    CHAR(24),
    S_DIST_09    CHAR(24),
    S_DIST_10    CHAR(24),
    S_DATA       VARCHAR(50),

    PRIMARY KEY (S_W_ID, S_I_ID)
    );
-- SHOW TABLES;

-- 创建索引
CREATE INDEX balance_idx ON customer (C_BALANCE);	-- only for Workload A
CREATE INDEX item_idx ON order_line (OL_I_ID);		-- for both Workload A and Workload B

-- 用于NewOrderTransaction的临时表

CREATE TABLE IF NOT EXISTS newOrderInfoTable (
                                                 NO_DRIVER_ID INT NOT NULL,
                                                 NO_XACT_ID INT NOT NULL,
                                                 NO_ROW_COUNT INT NOT NULL,
                                                 NO_ITEM_NUMBER INT,				-- I_ID of each ordered item
                                                 NO_SUPPLIER_WAREHOUSE INT,		-- W_ID of supplier warehouse for each ordered item
                                                 NO_QUANTITY DECIMAL(2,0),		-- OL_QUANTITY of each ordered item
    NO_W_ID INT,					-- W_ID of the customer who created this order
    NO_D_ID INT,					-- D_ID of the customer who created this order
    NO_C_ID INT,					-- C_ID of the customer who created this order

    PRIMARY KEY (NO_DRIVER_ID, NO_XACT_ID, NO_ROW_COUNT)
    );
    
 -- 用于Delivery的临时表
 
 CREATE TABLE IF NOT EXISTS tmp_delivery
 (
     o_w_id INT NOT NULL,
     o_d_id INT NOT NULL,
     o_c_id INT NOT NULL,
     sum_amount DECIMAL(20,2),
     
     PRIMARY KEY (O_W_ID, O_D_ID, O_C_ID)
 );