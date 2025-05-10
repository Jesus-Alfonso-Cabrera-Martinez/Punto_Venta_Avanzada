package Modelo;
import java.math.BigInteger;
public class productoModelo {
    private String nombre;
    private BigInteger id;
    private double precio;
    private String tamaño;
    private int cantidad;
    private int stock;
    private String proveedor;
    public productoModelo(String nombre, BigInteger id, double precio, String tamaño, int cantidad, int stock, String proveedor) {
        this.nombre = nombre;
        this.id = id;
        this.precio = precio;
        this.tamaño = tamaño;
        this.cantidad = cantidad;
        this.stock = stock;
        this.proveedor = proveedor;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public BigInteger getId() {
        return id;
    }
    public void setId(BigInteger id) {
        this.id = id;
    }
    public double getPrecio() {
        return precio;
    }
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    public String getTamaño() {
        return tamaño;
    }
    public void setTamaño(String tamaño) {
        this.tamaño = tamaño;
    }
    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public String getProveedor() {
        return proveedor;
    }
    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }
}