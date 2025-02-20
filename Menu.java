package Capitulo6;

import java.io.IOException;

public class Menu {
	
    public static void MenuPuntoVenta(String[][] ventas, String idticket, String[][] productos) throws IOException {
        String opcion, membrete;
        Boolean pago = false;
        int tamticket = 50;
        String[][] Vticket = new String[tamticket][4];
        idticket = Ticket.IdTicketSiguiente(idticket);
        String fechadia = Principal.Fecha();
        do {
            membrete = "Fecha del Dia " + fechadia + " Ticket No " + idticket + "\n-----------------------------------------------------\n";
            String Tickettexto = Mostrar.MostrarTicket(Vticket).trim();
            if (!Tickettexto.isEmpty()) {
                membrete += "\n" + Tickettexto + "\n";
            }
            String[] datosmenu = {"1.-Agregar ", "2.-Eliminar ", "3.-Listado ", "4.-Pagar ", "5.-Salida "};
            opcion = DesplegarMenu(membrete + "\nMenu de Punto de Venta", datosmenu);
            if (opcion != null) {
                switch (opcion) {
                    case "1":
                        Producto.CapturaVentaProducto(Vticket, productos, idticket, tamticket);
                        break;
                    case "2":
                    	Principal.Eliminar(Vticket, tamticket);
                    	break;
                    case "3":
                        if (Principal.ObtenerUltimaPosicion(Vticket) > -1) {
                            System.out.println(Mostrar.MostrarTicket(Vticket));
                        }
                        break;
                    case "4":
                        System.out.println(Mostrar.MostrarTicketVenta(Vticket, idticket, Principal.fecha).trim());
                        Principal.Pagar(idticket, ventas, Vticket);
                        pago = true;
                        opcion = "5";
                        break;
                    case "5":
                        System.out.println("Salida del Ventas ");
                        if (!pago) {
                            System.out.println("No pago el ticket ");
                            System.out.println("Eliminando ticket " + idticket);
                        }
                        break;
                    default:
                        System.out.println("No existe esta opcion");
                        break;
                }
            } else {
                System.out.println("Dato incorrecto introducido");
            }
        } while (!"5".equals(opcion));
    }
    public static void MenuInventario(String[][] vproductos) throws IOException {
        String[] datosmenuinventario = {"1.-Listado ", "2.-Agregar ", "3.-Salida "};
        String opcion = "0";
        
        do {
            opcion = DesplegarMenu("Opciones de Inventarios", datosmenuinventario);
            if (opcion == null) {
                System.out.println("opcion incorrecta ");
            } else {
                switch (opcion) {
                    case "1":
                        System.out.println(Mostrar.MostrarLista(vproductos));
                        break;
                    case "2":
                        Principal.AgregarStock(vproductos);
                        break;
                    case "3":
                        System.out.println("Salida del Sistema ");
                        break;
                    default:
                        System.out.println("No existe esta opcion ");
                        break;
                }
            }
        } while (opcion.compareTo("3") != 0);
    }
    public static String DesplegarMenu(String Titulo1, String[] menu) throws IOException {
        String cadena = Titulo1 + "\n\n" + Mostrar.MostrarMenu(menu) + "\nQue opcion deseas ";
        return Principal.Dialogo(cadena);
    }
    public static void MenuProductos(String[][] vproductos) throws IOException {
        String[] datosmenuproductos = {"1.-Modificar ", "2.-Listado ", "3.-Salida "};
        String opcion;
        do {
            opcion = DesplegarMenu("Opciones de Productos", datosmenuproductos);
            if (opcion != null) {
                switch (opcion) {
                    case "1":
                        Producto.ModificarProducto(vproductos);
                        break;
                    case "2":
                        System.out.println(Mostrar.MostrarLista(vproductos));
                        break;
                    case "3":
                        System.out.println("Salida del Sistema ");
                        break;
                    default:
                        System.out.println("No existe esta opcion ");
                        break;
                }
            } else {
                System.out.println("Opcion incorrecta ");
            }
        } while (!"3".equals(opcion));
    }
    public static void MenuPrincipal(String[][] vproductos, String[][] vventas) throws IOException {
        String[] datosmenuprincipal = {"1.-Productos ", "2.-Punto de Venta ", "3.- Inventario", "4.-Ventas", "5.-Salir"};
        String opcion = "0";
        String idticket;
        
        do {
            idticket = Principal.ObtenerUltimoValorVentas(vventas);
            opcion = DesplegarMenu("Menu de Punto de Tienda de Abarrotes la Pequeña", datosmenuprincipal);
            
            if (opcion == null) {
                System.out.println("opcion incorrecta ");
            } else {
                switch (opcion) {
                    case "1":
                        MenuProductos(vproductos);
                        break;
                    case "2":
                        MenuPuntoVenta(vventas, idticket, vproductos);
                        break;
                    case "3":
                        MenuInventario(vproductos);
                        break;
                    case "4":
                        System.out.println(Mostrar.MostrarListaVentas(vventas));
                        break;
                    case "5":
                        System.out.println("Salida del Sistema ");
                        break;
                    default:
                        System.out.println("No existe esta opcion ");
                        break;
                }
            }
        } while (opcion.compareTo("5") != 0);
    }
}