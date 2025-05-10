package Controlador;
import Modelo.productoModelo;
import Modelo.ventaModelo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
public class ventaControlador {
    private static final String PRODUCTOS_JSON = "productos.json";
    private static final String VENTA_JSON = "venta.json";
    private static final String FOLIO_TXT = "folio.txt";
    private static List<ventaModelo.ProductoSeleccionado> productosSeleccionados = new ArrayList<>();
    public static class VentaRealizada {
        List<ventaModelo.ProductoSeleccionado> productos;
        double total;
        public VentaRealizada(List<ventaModelo.ProductoSeleccionado> productos, double total) {
            this.productos = productos;
            this.total = total;
        }
    }
    private static boolean verificarCajaActiva() {
        if (!cajaControlador.hayCajaActiva()) {
            JOptionPane.showMessageDialog(null, "Debe iniciar la caja antes de realizar la operación.");
            return false;
        }
        return true;
    }
    public static void seleccionarProducto() {
        if (!verificarCajaActiva()) return;
        List<productoModelo> productos = productoControlador.leerProductosJSON(PRODUCTOS_JSON);
        if (productos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay productos.");
            return;
        }
        productos.sort(Comparator.comparing(p -> p.getId()));
        JFrame ventanaSeleccion = new JFrame("Seleccionar Producto");
        ventanaSeleccion.setSize(400, 300);
        ventanaSeleccion.setLocationRelativeTo(null);
        ventanaSeleccion.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel(new BorderLayout());
        String[] columnas = {"ID", "Nombre", "Precio"};
        String[][] datos = new String[productos.size()][3];
        for (int i = 0; i < productos.size(); i++) {
            productoModelo p = productos.get(i);
            datos[i][0] = p.getId().toString();
            datos[i][1] = p.getNombre();
            datos[i][2] = "$" + p.getPrecio();
        }
        JTable tablaProductos = new JTable(datos, columnas);
        JScrollPane scrollPane = new JScrollPane(tablaProductos);
        panel.add(scrollPane, BorderLayout.CENTER);
        JButton seleccionarBtn = new JButton("Seleccionar Producto");
        seleccionarBtn.addActionListener(e -> {
            int filaSeleccionada = tablaProductos.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un producto.");
                return;
            }
            productoModelo productoSeleccionado = productos.get(filaSeleccionada);
            String cantidadStr = JOptionPane.showInputDialog("Ingrese la cantidad de " + productoSeleccionado.getNombre() + ":");
            try {
                int cantidad = Integer.parseInt(cantidadStr);
                if (cantidad > productoSeleccionado.getStock()) {
                    JOptionPane.showMessageDialog(null, "No hay suficiente stock.");
                } else if (cantidad <= 0) {
                    JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor a 0.");
                } else {
                    productosSeleccionados.add(new ventaModelo.ProductoSeleccionado(productoSeleccionado, cantidad));
                    productoSeleccionado.setStock(productoSeleccionado.getStock() - cantidad); // Actualizar stock
                    productoControlador.guardarProductosJSON(productos, PRODUCTOS_JSON); // Guardar el archivo actualizado
                    JOptionPane.showMessageDialog(null, "Producto seleccionado: " + productoSeleccionado.getNombre() + " - Cantidad: " + cantidad);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Cantidad inválida.");
            }
        });
        panel.add(seleccionarBtn, BorderLayout.SOUTH);
        ventanaSeleccion.add(panel);
        ventanaSeleccion.setVisible(true);
    }
    public static void calcularTotal() {
        if (!verificarCajaActiva()) return;
        if (productosSeleccionados.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay productos seleccionados.");
            return;
        }
        double total = 0;
        StringBuilder detalle = new StringBuilder("Productos seleccionados:\n");
        for (ventaModelo.ProductoSeleccionado ps : productosSeleccionados) {
            double totalProducto = ps.getProducto().getPrecio() * ps.getCantidad();
            total += totalProducto;
            detalle.append(ps.getProducto().getNombre())
                    .append(" x")
                    .append(ps.getCantidad())
                    .append(" - $")
                    .append(String.format("%.2f", totalProducto))
                    .append("\n");
        }
        JOptionPane.showMessageDialog(null, detalle + "\nTotal: $" + String.format("%.2f", total));
    }
    public static void pagarVenta() {
        if (!verificarCajaActiva()) return;

        if (productosSeleccionados.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay productos seleccionados.");
            return;
        }
        double total = productosSeleccionados.stream()
                .mapToDouble(ps -> ps.getProducto().getPrecio() * ps.getCantidad())
                .sum();

        double montoCaja = cajaControlador.obtenerMontoCaja();
        if (montoCaja < total) {
            JOptionPane.showMessageDialog(null, "No hay suficiente dinero en la caja para realizar la venta.");
            return;
        }
        String montoStr = JOptionPane.showInputDialog("Total a pagar: $" + String.format("%.2f", total) + "\nIngrese monto para pagar:");
        if (montoStr == null) return;
        try {
            double montoPago = Double.parseDouble(montoStr);
            if (montoPago < total) {
                JOptionPane.showMessageDialog(null, "El monto ingresado es insuficiente.");
                return;
            }
            double cambio = montoPago - total;
            if (cambio > montoCaja) {
                JOptionPane.showMessageDialog(null, "No hay suficiente dinero en la caja para dar el cambio de $" + String.format("%.2f", cambio));
                return;
            }
            JOptionPane.showMessageDialog(null, "Pago realizado con éxito.\nCambio: $" + String.format("%.2f", cambio));
            cajaControlador.actualizarCaja(total);
            List<ventaModelo.ProductoSeleccionado> copiaProductos = new ArrayList<>(productosSeleccionados);
            String ticketPath = generarTicket(copiaProductos, montoPago, cambio);
            File ticketFile = new File(ticketPath);
            if (ticketFile.exists()) {
                JOptionPane.showMessageDialog(null, "Ticket generado: " + ticketPath);
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(ticketFile);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo generar el ticket.");
            }
            productosSeleccionados.clear();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Monto inválido.");
        } catch (DocumentException | IOException e) {
            JOptionPane.showMessageDialog(null, "Error al generar ticket: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static String generarTicket(List<ventaModelo.ProductoSeleccionado> productos, double montoPago, double cambio)
            throws IOException, DocumentException {
        Document documento = new Document(PageSize.A6);
        int folio = obtenerSiguienteFolio();
        String directorio = "tickets/";
        new File(directorio).mkdirs();
        String nombreArchivo = directorio + "ticket_" + folio + ".pdf";
        PdfWriter.getInstance(documento, new FileOutputStream(nombreArchivo));
        documento.open();
        Paragraph header = new Paragraph("POLLO EL SABROSO\nRivera de Champayan S/N, Arboleda\nTampico, Tamaulipas, México\nTel: (833)173-7256\n\n",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10));
        header.setAlignment(Element.ALIGN_CENTER);
        documento.add(header);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        documento.add(new Paragraph("Fecha: " + sdf.format(new Date())));
        documento.add(new Paragraph("ID Venta: " + folio));
        documento.add(new Paragraph("\n"));
        PdfPTable tabla = new PdfPTable(5);
        tabla.setWidths(new float[]{1f, 2f, 4f, 2f, 2f});
        tabla.addCell("ID");
        tabla.addCell("Cant.");
        tabla.addCell("Producto");
        tabla.addCell("P.Unit");
        tabla.addCell("SubTotal");
        double total = 0;
        for (ventaModelo.ProductoSeleccionado ps : productos) {
            double subtotal = ps.getProducto().getPrecio() * ps.getCantidad();
            total += subtotal;
            tabla.addCell(String.valueOf(ps.getProducto().getId()));
            tabla.addCell(String.valueOf(ps.getCantidad()));
            tabla.addCell(ps.getProducto().getNombre());
            tabla.addCell(String.format("$%.2f", ps.getProducto().getPrecio()));
            tabla.addCell(String.format("$%.2f", subtotal));
        }
        documento.add(tabla);
        documento.add(new Paragraph("\nTOTAL: $" + String.format("%.2f", total)));
        documento.add(new Paragraph("RECIBIDO: $" + String.format("%.2f", montoPago)));
        documento.add(new Paragraph("CAMBIO: $" + String.format("%.2f", cambio)));
        documento.close();
        return nombreArchivo;
    }
    private static int obtenerSiguienteFolio() {
        String archivoFolio = "folio.txt";
        int folio = 1;
        try {
            File file = new File(archivoFolio);
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String linea = reader.readLine();
                if (linea != null) {
                    folio = Integer.parseInt(linea.trim()) + 1;
                }
                reader.close();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(String.valueOf(folio));
            writer.close();
        } catch (IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error al manejar el folio: " + e.getMessage());
        }
        return folio;
    }
    public static void rembolsoVenta() {
        if (productosSeleccionados == null || productosSeleccionados.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay productos registrados en esta venta para devolver.");
            return;
        }
        String[] opciones = new String[productosSeleccionados.size()];
        for (int i = 0; i < productosSeleccionados.size(); i++) {
            ventaModelo.ProductoSeleccionado ps = productosSeleccionados.get(i);
            opciones[i] = ps.getProducto().getNombre() + " - Cantidad: " + ps.getCantidad();
        }
        String seleccionado = (String) JOptionPane.showInputDialog(null, "Seleccione el producto a devolver:", "Devolver Producto",
                JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        if (seleccionado == null) return;
        int index = -1;
        for (int i = 0; i < opciones.length; i++) {
            if (opciones[i].equals(seleccionado)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            JOptionPane.showMessageDialog(null, "Error al encontrar el producto seleccionado.");
            return;
        }
        ventaModelo.ProductoSeleccionado psDevolver = productosSeleccionados.get(index);
        String cantidadStr = JOptionPane.showInputDialog("Ingrese la cantidad a devolver de " + psDevolver.getProducto().getNombre() + ":");
        try {
            int cantidadDevolver = Integer.parseInt(cantidadStr);
            if (cantidadDevolver <= 0 || cantidadDevolver > psDevolver.getCantidad()) {
                JOptionPane.showMessageDialog(null, "Cantidad inválida.");
                return;
            }
            List<productoModelo> productos = productoControlador.leerProductosJSON(PRODUCTOS_JSON);
            for (productoModelo p : productos) {
                if (p.getId().equals(psDevolver.getProducto().getId())) {
                    p.setStock(p.getStock() + cantidadDevolver);
                    break;
                }
            }
            productoControlador.guardarProductosJSON(productos, PRODUCTOS_JSON);
            psDevolver.setCantidad(psDevolver.getCantidad() - cantidadDevolver);
            if (psDevolver.getCantidad() == 0) {
                productosSeleccionados.remove(index);
            }
            JOptionPane.showMessageDialog(null, "Devolución realizada con éxito.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Cantidad inválida.");
        }
    }
    public static void guardarVentaEnJSON(VentaRealizada venta) {
        try {
            List<VentaRealizada> ventasExistentes = leerVentasJSON(VENTA_JSON);
            ventasExistentes.add(venta);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileWriter writer = new FileWriter(VENTA_JSON);
            gson.toJson(ventasExistentes, writer);
            writer.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar la venta: " + e.getMessage());
        }
    }
    public static List<VentaRealizada> leerVentasJSON(String archivo) {
        try {
            Gson gson = new Gson();
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            List<VentaRealizada> ventas = gson.fromJson(reader, new TypeToken<List<VentaRealizada>>() {
            }.getType());
            reader.close();
            return (ventas != null) ? ventas : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}