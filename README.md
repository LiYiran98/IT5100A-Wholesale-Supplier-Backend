# IT5100A-Wholesale-Supplier-Backend

## Instructions
This is a Scala application that implements a wholesale system using TPC-C benchmark.

## Requirements
- MySQL 8.0+
- Scala 3
- sbt

## Usage (Windows)
Create the tables (need to input your MySQL password in console)
```shell
Get-Content .\src\main\resources\sql\create_table.sql | mysql -u root -p
```
---
Show MySQL *secure-file-priv* directory
```mysql
mysql > SHOW VARIABLES LIKE "secure_file_priv";
```
Move the csv files under /src/main/resources/csv to the directory shown above (manually)  

---
Change all path in **import_csv.sql** (manually)
```mysql
-- Change the path below to your file path
LOAD DATA INFILE '../Uploads/customer-1.csv'
    IGNORE
    ...
-- Change the path below to your file path
LOAD DATA INFILE '../Uploads/customer-2.csv'
    IGNORE
    ...
```
Import data in .csv files into **tpcc** databse
```shell
Get-Content .\src\main\resources\sql\import_csv.sql | mysql -u root -p
```
---
Run the wholesale system
```shell
sbt run
```
Test with sample test cases provided in **testcase.txt** 
and feel free to try your own operation in the system!
## Contribution
- Li Yiran
- Jiang Lei
- Xu Chuke
