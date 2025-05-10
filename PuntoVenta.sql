-- PRODUCTOS TABLA --
CREATE TABLE producto (
    nombre_producto VARCHAR(50) NOT NULL,
    id_producto BIGINT PRIMARY KEY NOT NULL,          
    precio_producto FLOAT NOT NULL,
    tamaño_producto VARCHAR(50),
    cantidad_producto INT NOT NULL,
    stock_producto INT NOT NULL,
    proveedor_producto VARCHAR(50) NOT NULL
);

-- VENTAS TABLA --
CREATE TABLE venta (
    id_venta INT PRIMARY KEY NOT NULL,
    fecha_venta DATE NOT NULL,
    id_producto BIGINT NOT NULL, 
    cantidad_producto INT NOT NULL,
    precio_producto FLOAT NOT NULL, 
    subtotal_venta FLOAT NOT NULL,
    FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
);

-- CAJA TABLA --
CREATE TABLE cajaVenta (
    id_caja INT PRIMARY KEY NOT NULL,
    id_venta INT NOT NULL,
    abierto_caja DATETIME NOT NULL,
    cerrado_caja DATETIME,
    montoinicial_caja FLOAT NOT NULL,
    montofinal_caja FLOAT,
    FOREIGN KEY (id_venta) REFERENCES venta(id_venta)
);

-- EMPLEADOS TABLA --
CREATE TABLE inicioSesion (
    id_inicioSesion INT PRIMARY KEY NOT NULL,
    usuario_inicioSesion VARCHAR(50) NOT NULL,
    contraseña_inicioSesion VARCHAR(50) NOT NULL,
    admin_inicioSesion BIT NOT NULL
);

-- INSERTS DE EJEMPLO --

-- Producto ejemplo
INSERT INTO producto (
    nombre_producto, id_producto, precio_producto, tamaño_producto, 
    cantidad_producto, stock_producto, proveedor_producto
) VALUES (
    'Producto A', 1, 100.0, 'Gr', 1000, 20, 'Granja Juanito'
);

-- Venta ejemplo
INSERT INTO venta (
    id_venta, fecha_venta, id_producto, cantidad_producto, 
    precio_producto, subtotal_venta
) VALUES (
    101, '2025-05-09', 1, 5, 100.0, 500.0
);

-- CajaVenta ejemplo
INSERT INTO cajaVenta (
    id_caja, id_venta, abierto_caja, cerrado_caja, 
    montoinicial_caja, montofinal_caja
) VALUES (
    1, 101, '2025-05-09 08:00:00', '2025-05-09 18:00:00', 1000.0, 1500.0
);

-- Empleados ejemplo
INSERT INTO inicioSesion (
    id_inicioSesion, usuario_inicioSesion, contraseña_inicioSesion, admin_inicioSesion
) VALUES
(1, 'Alfonso', '1', 1),  -- Admin
(2, 'Cabrera', '2', 0);  -- No admin
-- SELECTS
SELECT * FROM producto;
SELECT * FROM venta;
SELECT * FROM cajaVenta;
SELECT * FROM inicioSesion;
-- DELETES (por si quieres limpiar)
DELETE FROM producto WHERE id_producto = 1;
DELETE FROM venta WHERE id_venta = 101;
DELETE FROM cajaVenta WHERE id_caja = 1;
DELETE FROM inicioSesion WHERE id_inicioSesion IN (1, 2);