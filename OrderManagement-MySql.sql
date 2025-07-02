create database user_managementdb;
use user_managementdb;
-- Users Table
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
-- Roles Table
CREATE TABLE roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
-- User Roles Table (Many-to-Many Relationship)
CREATE TABLE user_roles (
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);
-- User Addresses Table (One-to-Many Relationship)
CREATE TABLE user_addresses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    address_line1 VARCHAR(255) NOT NULL,
    address_line2 VARCHAR(255),
    city VARCHAR(255) NOT NULL,
    postal_code VARCHAR(20) NOT NULL,
    country VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
 drop database user_managementdb;
create database product_management;
use product_management;
-- Products Table
CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    category_id INT,
    name VARCHAR(255) NOT NULL,
    description varchar(100),
    price DECIMAL(10, 2) NOT NULL,
    quantity int,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
-- Product Categories Table
CREATE TABLE product_categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
-- Product Details Table (One-to-One Relationship)
CREATE TABLE product_details (
    product_id INT PRIMARY KEY,
    weight DECIMAL(10, 2),
    dimensions VARCHAR(100),
    color VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);
 drop database product_management;
 drop table users; 
 create database order_managementdb;
 use order_managementdb;
-- Orders Table
CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10, 2) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    shipping_address VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    
);
-- Order Items Table (One-to-Many Relationship with Orders)
CREATE TABLE order_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
   
);
-- Payments Table (One-to-One Relationship with Orders)
CREATE TABLE payments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    payment_method VARCHAR(50),
    amount DECIMAL(10, 2) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);
-- Shipping Info Table (One-to-One Relationship with Orders)
drop table shipping_info;
-- Order Status Table
CREATE TABLE order_status (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    status VARCHAR(50) NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);
drop database order_managementdb;
create database transaction_management;
CREATE TABLE invoices (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    invoice_number VARCHAR(100) UNIQUE NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    generated_date DATETIME NOT NULL,
    status VARCHAR(50) -- e.g., GENERATED   
);
drop database shipping_management;
create database shipping_management;
use shipping_management;
CREATE TABLE shipments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    tracking_number VARCHAR(255),
    tracking_status VARCHAR(50),          
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE DATABASE review_rating;
USE review_rating;

CREATE TABLE review (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    product_id INT,
    rating INT ,
    review_text VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
drop database reviews_rating;


create database promotions_management;
use promotions_management;
CREATE TABLE promotions (
    id INT AUTO_INCREMENT PRIMARY KEY,   
    code VARCHAR(50) UNIQUE,
    product_id INT,
    discount_percentage DECIMAL(5,2),
    expiration_date DATE,
    

    discount_amount DECIMAL(10,2),
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
drop database promotions_management;



