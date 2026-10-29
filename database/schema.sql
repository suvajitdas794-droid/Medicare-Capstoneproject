CREATE DATABASE IF NOT EXISTS medicare_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE medicare_db;

CREATE TABLE IF NOT EXISTS app_users (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(120) NOT NULL,
  email VARCHAR(180) NOT NULL UNIQUE,
  password VARCHAR(100) NOT NULL,
  role VARCHAR(20) NOT NULL DEFAULT 'USER',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS medicines (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(180) NOT NULL,
  description TEXT,
  category VARCHAR(100) NOT NULL,
  brand VARCHAR(100),
  symptoms VARCHAR(500),
  price DECIMAL(10,2) NOT NULL,
  stock INT NOT NULL DEFAULT 0,
  prescription_required BOOLEAN NOT NULL DEFAULT FALSE,
  image_url VARCHAR(500),
  active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS customer_orders (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  status VARCHAR(30) NOT NULL DEFAULT 'PLACED',
  total DECIMAL(10,2) NOT NULL,
  shipping_address VARCHAR(500) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_order_user FOREIGN KEY (user_id) REFERENCES app_users(id)
);

CREATE TABLE IF NOT EXISTS order_items (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  order_id BIGINT NOT NULL,
  medicine_id BIGINT NOT NULL,
  medicine_name VARCHAR(180) NOT NULL,
  quantity INT NOT NULL,
  unit_price DECIMAL(10,2) NOT NULL,
  CONSTRAINT fk_item_order FOREIGN KEY (order_id) REFERENCES customer_orders(id),
  CONSTRAINT fk_item_medicine FOREIGN KEY (medicine_id) REFERENCES medicines(id)
);

INSERT INTO medicines (name, description, category, brand, symptoms, price, stock, prescription_required, image_url, active)
SELECT 'Vitamin C Tablets', 'Illustrative wellness product listing.', 'Vitamins', 'WellLife', 'general wellness', 149.00, 80, FALSE, 'https://images.unsplash.com/photo-1571781926291-c477ebfd024b?w=700', TRUE
WHERE NOT EXISTS (SELECT 1 FROM medicines WHERE name='Vitamin C Tablets');
INSERT INTO medicines (name, description, category, brand, symptoms, price, stock, prescription_required, image_url, active)
SELECT 'Digital Thermometer', 'Digital thermometer for temperature measurement.', 'Healthcare Essentials', 'CarePlus', 'temperature monitoring', 299.00, 35, FALSE, 'https://images.unsplash.com/photo-1584634731339-252c581abfc5?w=700', TRUE
WHERE NOT EXISTS (SELECT 1 FROM medicines WHERE name='Digital Thermometer');
INSERT INTO medicines (name, description, category, brand, symptoms, price, stock, prescription_required, image_url, active)
SELECT 'First Aid Kit', 'Basic first aid supplies for home use.', 'Healthcare Essentials', 'SafeHome', 'first aid', 399.00, 25, FALSE, 'https://images.unsplash.com/photo-1603398938378-e54eab446dde?w=700', TRUE
WHERE NOT EXISTS (SELECT 1 FROM medicines WHERE name='First Aid Kit');
