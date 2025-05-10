package Modelo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class ventaModelo {
    private int idVenta;
    private Date fechaVenta;
    private List<ProductoSeleccionado> productosSeleccionados;
    private double total;
    public ventaModelo(int idVenta, Date fechaVenta) {
        this.idVenta = idVenta;
        this.fechaVenta = fechaVenta;
        this.productosSeleccionados = new ArrayList<>();
        this.total = 0.0;
    }
    public static class ProductoSeleccionado {
        private productoModelo producto;
        private int cantidad;
        public ProductoSeleccionado(productoModelo producto, int cantidad) {
            this.producto = producto;
            this.cantidad = cantidad;
        }

        public productoModelo getProducto() {
            return producto;
        }
        public void setProducto(productoModelo producto) {
            this.producto = producto;
        }
        public int getCantidad() {
            return cantidad;
        }

        public void setCantidad(int cantidad) {
            this.cantidad = cantidad;
        }
    }
    public void agregarProducto(ProductoSeleccionado productoSeleccionado) {
        this.productosSeleccionados.add(productoSeleccionado);
    }
    public double getTotal() {
        total = 0.0;
        for (ProductoSeleccionado ps : productosSeleccionados) {
            total += ps.getProducto().getPrecio() * ps.getCantidad(); // Asegúrate de que getPrecio() esté en ProductoModelo
        }
        return total;
    }
    public int getIdVenta() {
        return idVenta;
    }
    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }
    public Date getFechaVenta() {
        return fechaVenta;
    }
    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }
    public List<ProductoSeleccionado> getProductosSeleccionados() {
        return productosSeleccionados;
    }
    public void setProductosSeleccionados(List<ProductoSeleccionado> productosSeleccionados) {
        this.productosSeleccionados = productosSeleccionados;
    }
    public void setTotal(double total) {
        this.total = total;
    }
}