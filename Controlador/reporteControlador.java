package Controlador;
import Modelo.cajaModelo;
import Modelo.productoModelo;
import Modelo.ventaModelo;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import static Controlador.ventaControlador.leerVentasJSON;
public class reporteControlador {
    public static void generarReporteProductos() {
        Document documento = new Document();
        try {
            String nombreArchivo = "reporte_productos_" + System.currentTimeMillis() + ".pdf";
            PdfWriter.getInstance(documento, new FileOutputStream(nombreArchivo));
            documento.open();
            Paragraph titulo = new Paragraph("REPORTE DE PRODUCTOS\n\n",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);
            PdfPTable tabla = new PdfPTable(7);
            tabla.setWidthPercentage(100);
            tabla.setSpacingBefore(10f);
            tabla.setSpacingAfter(10f);
            String[] encabezados = {"ID", "Nombre", "Precio", "Unidad de Medida", "Cantidad", "Stock", "Proveedor"};
            for (String encabezado : encabezados) {
                PdfPCell celda = new PdfPCell(new Phrase(encabezado,
                        FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
                celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                celda.setBackgroundColor(Color.LIGHT_GRAY);
                tabla.addCell(celda);
            }
            List<productoModelo> productos = productoControlador.getListaProductos();
            if (productos != null && !productos.isEmpty()) {
                Collections.sort(productos, Comparator.comparing(productoModelo::getId));
                for (productoModelo p : productos) {
                    tabla.addCell(p.getId().toString());
                    tabla.addCell(p.getNombre());
                    tabla.addCell(String.format("$%.2f", p.getPrecio()));
                    tabla.addCell(p.getTamaño());
                    tabla.addCell(String.valueOf(p.getCantidad()));
                    tabla.addCell(String.valueOf(p.getStock()));
                    tabla.addCell(p.getProveedor());
                }
                documento.add(tabla);
                documento.add(new Paragraph("Total de productos: " + productos.size()));
            } else {
                documento.add(new Paragraph("No se encontraron productos registrados."));
            }
            System.out.println("PDF generado correctamente en: " + nombreArchivo);
        } catch (DocumentException | IOException e) {
            System.err.println("Error al generar el reporte PDF:");
            e.printStackTrace();
        } finally {
            documento.close();
        }
    }
    public static void generarReporteVentas() {
        Document documento = new Document();
        try {
            String nombreArchivo = "reporte_ventas_" + System.currentTimeMillis() + ".pdf";
            PdfWriter.getInstance(documento, new FileOutputStream(nombreArchivo));
            documento.open();
            Paragraph titulo = new Paragraph("REPORTE DE VENTAS\n\n", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);
            PdfPTable tabla = new PdfPTable(5);
            tabla.setWidthPercentage(100);
            tabla.setSpacingBefore(10f);
            tabla.setSpacingAfter(10f);
            String[] encabezados = {"Folio", "Fecha", "Productos", "Total", "Estado"};
            for (String encabezado : encabezados) {
                PdfPCell celda = new PdfPCell(new Phrase(encabezado, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
                celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                celda.setBackgroundColor(Color.LIGHT_GRAY);
                tabla.addCell(celda);
            }
            List<ventaControlador.VentaRealizada> ventas = leerVentasJSON("venta.json");
            if (ventas != null && !ventas.isEmpty()) {
                for (ventaControlador.VentaRealizada venta : ventas) {
                    StringBuilder productosDetalle = new StringBuilder();
                    double totalVenta = 0;
                    for (ventaModelo.ProductoSeleccionado ps : venta.productos) {
                        if (ps.getProducto() != null) {
                            double subtotal = ps.getProducto().getPrecio() * ps.getCantidad();
                            totalVenta += subtotal;
                            productosDetalle.append(ps.getProducto().getNombre()).append(" x").append(ps.getCantidad()).append("\n");
                        } else {
                            System.out.println("Producto nulo encontrado en la venta");
                        }
                    }
                    tabla.addCell(String.valueOf(obtenerFolioDeVenta(venta)));
                    tabla.addCell(obtenerFechaDeVenta(venta));
                    tabla.addCell(productosDetalle.toString());
                    tabla.addCell(String.format("$%.2f", totalVenta));
                    tabla.addCell("Completada");
                }
                documento.add(tabla);
                documento.add(new Paragraph("Total de ventas: " + ventas.size()));
            } else {
                documento.add(new Paragraph("No se encontraron ventas registradas."));
            }
            System.out.println("PDF generado correctamente en: " + nombreArchivo);
        } catch (DocumentException | IOException e) {
            System.err.println("Error al generar el reporte PDF:");
            e.printStackTrace();
        } finally {
            documento.close();
        }
    }
    private static String obtenerFechaDeVenta(ventaControlador.VentaRealizada venta) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(new Date());
    }
    private static int obtenerFolioDeVenta(ventaControlador.VentaRealizada venta) {
        String archivoFolio = "folio2.txt";
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
    public static void generarReporteCaja() {
        Document documento = new Document();
        try {
            String nombreArchivo = "reporte_caja_" + System.currentTimeMillis() + ".pdf";
            PdfWriter.getInstance(documento, new FileOutputStream(nombreArchivo));
            documento.open();
            Paragraph titulo = new Paragraph("REPORTE DE CAJA\n\n",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);
            PdfPTable tabla = new PdfPTable(5);
            tabla.setWidthPercentage(100);
            tabla.setSpacingBefore(10f);
            tabla.setSpacingAfter(10f);
            String[] encabezados = {"Monto Inicial", "Monto Final", "Fecha Inicio", "Fecha Cierre", "Estado"};
            for (String encabezado : encabezados) {
                PdfPCell celda = new PdfPCell(new Phrase(encabezado,
                        FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
                celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                celda.setBackgroundColor(Color.LIGHT_GRAY);
                tabla.addCell(celda);
            }
            List<cajaModelo> historial = cajaControlador.leerHistorialCaja();
            if (historial != null && !historial.isEmpty()) {
                for (cajaModelo caja : historial) {
                    tabla.addCell(String.format("$%.2f", caja.getMontoInicial()));
                    tabla.addCell(caja.isEsCierre() ? String.format("$%.2f", caja.getMontoActual()) : "—");
                    tabla.addCell(caja.getFechaHoraInicio() != null ? caja.getFechaHoraInicio() : "—");
                    tabla.addCell(caja.isEsCierre() ? caja.getFechaHoraInicio() : "—");
                    tabla.addCell(caja.isEsCierre() ? "Cerrada" : "Abierta");
                }
                documento.add(tabla);
                documento.add(new Paragraph("Total de registros: " + historial.size()));
            } else {
                documento.add(new Paragraph("No se encontró historial de caja."));
            }
            System.out.println("PDF de reporte de caja generado correctamente en: " + nombreArchivo);
        } catch (DocumentException | IOException e) {
            System.err.println("Error al generar el reporte de caja:");
            e.printStackTrace();
        } finally {
            documento.close();
        }
    }
}