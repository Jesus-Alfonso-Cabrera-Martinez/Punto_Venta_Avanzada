package Capitulo6;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Principal {
    public static void main(String[] args) throws IOException {
        String[][] productos = Producto.CargarProductos();
        String[][] ventas = CrearVenta();
        Menu.MenuPrincipal(productos, ventas);
    }
    static String[][] productos;
    static String[][] ventas;
    static int tamventas = 100;
    static String fecha;
    public static boolean EsNumeroEntero(String dato) {
        for (char c : dato.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
    public static boolean EsNumeroDouble(String dato) {
        boolean valido = false;
        for (char c : dato.toCharArray()) {
            if (!Character.isDigit(c)) {
                if (c == '.' && !valido) {
                    valido = true;
                } else {
                    return false;
                }
            }
        }
        return valido;
    }
    public static boolean EvaluarNumerico(String dato, int tipo) {
        boolean valido = false;
        switch (tipo) {
            case 1:
                valido = EsNumeroEntero(dato);
                break;
            case 2:
                valido = EsNumeroDouble(dato);
                break;
        }
        return valido;
    }
    public static String Dialogo(String texto) throws IOException {
        System.out.println(texto + " : ");
        BufferedReader lectura = new BufferedReader(new InputStreamReader(System.in));
        return lectura.readLine();
    }
    public static String RellenarEspacios(String dato, int tamano) {
        return String.format("%1$-" + tamano + "s", dato);
    }
    public static String Fecha() {
        Date fecha = new Date();
        SimpleDateFormat formatodia = new SimpleDateFormat("dd-MM-yyyy");
        return formatodia.format(fecha);
    }
    public static int ObtenerUltimaPosicion(String[][] matriz) {
        for (int i = matriz.length - 1; i >= 0; i--) {
            if (matriz[i][0] != null && !matriz[i][0].isEmpty()) {
                return i;
            }
        }
        return -1;
    }
    public static String[][] CrearVenta() {
        return new String[tamventas][5];
    }
    public static void Eliminar(String[][] mticket, int tamt) throws IOException {
        String codigo = Producto.Leer(Mostrar.MostrarTicket(mticket) + "\nIntroduce el codigo del producto");
        if (codigo != null) {
            int pos = Ticket.ExisteTicketCodigo(mticket, codigo);
            if (pos > -1) {
                Ticket.EliminarProductoTicket(mticket, pos);
            }
        } else {
            System.out.println("Dato nulo");
        }
    }
    public static void Pagar(String idticket, String[][] mventa, String[][] mticket) {
        int posventas = ObtenerUltimaPosicion(mventa);
        int post = ObtenerUltimaPosicion(mticket);
        if ((posventas + post) < 100) {
            Producto.AgregarProductoAVenta(mticket, mventa, idticket);
        } else {
            System.out.println("Desbordamiento de Memoria de ventas");
        }
    }
    public static void AgregarStock(String[][] vproductos) throws IOException {
        String codigo, cantidad;
        int posicion;
        String info = Mostrar.MostrarLista(vproductos);
        codigo = Producto.Leer(info + "\nIntroduce el codigo del producto a modificar");
        
        if (codigo != null) {
            posicion = Producto.ExisteProducto(codigo, vproductos);
            if (posicion > -1) {
                String[] vproducto = {vproductos[posicion][0], vproductos[posicion][1], vproductos[posicion][3]};
                cantidad = Producto.Leer("\nIntroduce la Cantidad de Stock a Agregar" + Mostrar.MostrarProducto(vproducto));
                
                if (cantidad != null) {
                    if (EvaluarNumerico(cantidad, 2) || EvaluarNumerico(cantidad, 1)) {
                        String nuevacantidad = String.valueOf(Integer.valueOf(cantidad) + Integer.valueOf(vproductos[posicion][3]));
                        vproductos[posicion][3] = nuevacantidad;
                    } else {
                        System.out.println("dato nulo");
                    }
                }
            } else {
                System.out.println("no existe el codigo");
            }
        } else {
            System.out.println("dato nulo");
        }
    }
    public static String ObtenerUltimoValorVentas(String[][] ventas) {
        String ultimoValor = "000";
        
        for (int i = ventas.length - 1; i >= 0; i--) {
            if (ventas[i][0] != null && !ventas[i][0].isEmpty()) {
                ultimoValor = ventas[i][0];
                break;
            }
        }
        return ultimoValor;
    		}
	}