CREATE DATABASE IF NOT EXISTS ria_dbp;

USE ria_dbp;

CREATE TABLE IF NOT EXISTS clientes(
	RFC CHAR(13) PRIMARY KEY,
	nombre VARCHAR(50) NOT NULL,
	telefono VARCHAR(12) NOT NULL,
	correo VARCHAR(50) NOT NULL,
	direccion VARCHAR(60) NULL)ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS productos(
	codigo_barras CHAR(16) PRIMARY KEY,
	nombre_p VARCHAR(30) NOT NULL,
	marca VARCHAR(20) NOT NULL,
	descripcion TEXT NOT NULL,
	tipo ENUM('comida','limpieza','electronica') NOT NULL)ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO clientes(RFC,nombre,telefono,correo,direccion)
VALUES
	('BOPD971212','Diego Bolaños Pardo','7757510151','diego@correo.com','Mariano Escobedo 10 Col. Dina 43995'),
	('SUM990914','Mónica Aimeé Suárez Escorcia','7751374714','aimee@correo.com','Morelos Poniente 200 Col. Centro esquina Luis Ponce 43600'),
	('BOCP670721','Pedro Bolaños Pardo','7221234567','pedro@correo.com','');

INSERT INTO productos(codigo_barras,nombre_p,marca,descripcion,tipo)
VALUES ('7501295600126','leche','santa clara','leche entera 1lt','comida'),
('7501035905054','limpiador','ajax','limpiador liquido 1lt','limpieza'),
('642632410095','tensiometro','microlife','tensiometro digital automatico modelo BP3AG1','electronica');

SHOW TABLES;

SELECT * FROM clientes;
SELECT *FROM productos;

DESCRIBE clientes;
DESCRIBE productos;

CREATE USER 'ria'@'localhost' IDENTIFIED BY 'ria.2019';
GRANT ALL PRIVILEGES ON ria_dbp.* TO 'ria'@'localhost';
FLUSH PRIVILEGES;
