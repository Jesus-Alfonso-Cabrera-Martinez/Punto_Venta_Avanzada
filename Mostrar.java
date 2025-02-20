package Capitulo6;

public class Mostrar {
    public static String MostrarMenu(String[] opciones) {
        StringBuilder cadena = new StringBuilder();
        for (String info : opciones) {
            cadena.append(info).append("\n");
        }
        return cadena.toString();
    }
    public static String MostrarProducto(String[] vproducto) {
        String codigo = Principal.RellenarEspacios(vproducto[0], 5);
        String producto = Principal.RellenarEspacios(vproducto[1], 30);
        String precio = Principal.RellenarEspacios(vproducto[2], 10);
        String cantidad = Principal.RellenarEspacios(vproducto[3], 10);
        return codigo.concat(producto + precio + cantidad);
    }
    public static String MostrarLista(String[][] vproductos) { 
        String salida = "";
        for (int ciclo = 0; ciclo < vproductos.length; ciclo++) { 
            if (vproductos[ciclo][0] != null) {
                String[] vproducto = {vproductos[ciclo][0], vproductos[ciclo][1], vproductos[ciclo][2], vproductos[ciclo][3]};
                String cadena = MostrarProducto(vproducto); 
                salida = salida.concat(cadena + "\n"); 
            }
        }
        return salida; 
    }
    public static String MostrarProductoTicket(String[][] mticket, int pos) {
        String codigo = Principal.RellenarEspacios(mticket[pos][0], 5);
        String producto = Principal.RellenarEspacios(mticket[pos][1], 30);
        String precio = Principal.RellenarEspacios(mticket[pos][2], 10);
        String cantidad = Principal.RellenarEspacios(mticket[pos][3], 5);
        String totalproducto = Principal.RellenarEspacios(Producto.TotalProducto(mticket[pos][2], mticket[pos][3]), 10);
        return codigo.concat(producto + precio + cantidad + totalproducto);
    }
    public static String MostrarTicket(String[][] mticket) {
        StringBuilder salida = new StringBuilder();
        int pos = Principal.ObtenerUltimaPosicion(mticket);
        for (int i = 0; i <= pos; i++) {
            salida.append(MostrarProductoTicket(mticket, i)).append("\n");
        }
        return salida.toString();
    }
    public static String MostrarListaProductosVenta(String[][] vproductos) {
        StringBuilder salida = new StringBuilder();
        for (String[] vproducto : vproductos) {
            if (Integer.parseInt(vproducto[3]) > 0) {
                salida.append(MostrarProducto(vproducto)).append("\n");
            }
        }
        return salida.toString();
    }
    public static String MostrarTicketVenta(String[][] mticket, String idticket, String fecha) {
        String subtotal = String.format("%.2f", Ticket.SubTotalTicket(mticket));
        String iva = String.format("%.2f", Ticket.IvaTicket(mticket));
        String total = String.format("%.2f", Ticket.TotalTicket(mticket));
        return "Fecha " + fecha + " Ticket No." + idticket + "\n" + MostrarTicket(mticket) + "\n\nEl total sin iva " + subtotal + "\nEl iva total es " + iva + "\nEl total de la venta fue " + total;
    }
    public static String MostrarVenta(String[] venta) {
        String idticket = Principal.RellenarEspacios(venta[0], 6);
        String codigo = Principal.RellenarEspacios(venta[1], 5);
        String producto = Principal.RellenarEspacios(venta[2], 30);
        String precio = Principal.RellenarEspacios(venta[3], 10);
        String cantidad = Principal.RellenarEspacios(venta[4], 10);
        return idticket.concat(codigo + producto + precio + cantidad);
    } 
    public static String MostrarListaVentas(String[][] ventas) {
        int posventas = Principal.ObtenerUltimaPosicion(ventas);
        String salida = "";
        for (int ciclo = 0; ciclo <= posventas; ciclo++) {
            String[] venta = {ventas[ciclo][0], ventas[ciclo][1], ventas[ciclo][2], ventas[ciclo][3]};
            String cadena = MostrarVenta(venta);
            salida = salida.concat(cadena + "\n");
        }
        return salida;
    }
    
}