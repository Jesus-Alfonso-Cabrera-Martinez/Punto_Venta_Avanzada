package Capitulo6;
import java.io.IOException;
public class Producto {
	public static String Leer(String texto) throws IOException{ 
	String cadena = ""; 
	cadena = Principal.Dialogo(texto); 
	if (cadena != null) 
	{ 
		cadena = cadena.trim(); 
	if (cadena.isEmpty())
	cadena=null;
	}
	else
	cadena = null; 
	return cadena; 
	}
    public static String[][] CargarProductos() {
    	String[][] producto = {
            {"001", "Arroz 1kg", "35", "10"},
            {"002", "Azúcar 1kg", "25", "10"},
            {"003", "Harina 1kg", "28", "10"},
            {"004", "Aceite 1L", "50", "10"},
            {"005", "Leche 1L", "35", "10"},
            {"006", "Huevos 12 unidades", "45", "10"},
            {"007", "Fideos 500g", "20", "10"},
            {"008", "Sal 1kg", "15", "10"},
            {"009", "Pasta de tomate 400g", "25", "10"},
            {"010", "Atún lata 170g", "35", "10"}
        };
        return producto;
    }
    public static int ExisteProducto(String codigo, String[][] vproductos) {
    	int enc, pos, tam, ciclo;
    	enc = -1;
    	pos = 0;
    	tam = vproductos.length;
    	for (ciclo = 0; ciclo < tam; ciclo++) {
    		if (vproductos[ciclo][0].compareTo(codigo.trim()) == 0) {
    			enc = pos;
    			}
		pos++;
		}
	return enc;
}
    public static void ModificarProducto(String[][] vproductos) throws IOException {
        String codigo = Leer(Mostrar.MostrarLista(vproductos) + "\nIntroduce el codigo del producto a modificar");
        if (codigo != null) {
            int posicion = ExisteProducto(codigo, vproductos);
            if (posicion > -1) {
                String precio =Leer("\nIntroduce el precio de " + Mostrar.MostrarProducto(vproductos[posicion]) + " ");
                if (precio != null && (Principal.EvaluarNumerico(precio, 2) || Principal.EvaluarNumerico(precio, 1))) {
                    vproductos[posicion][2] = precio;
                } else {
                    System.out.println("No es un valor numerico");
                }
            } else {
                System.out.println("No existe el codigo");
            }
        } else {
            System.out.println("Dato nulo");
        }
    }
    public static void CapturaVentaProducto(String[][] mticket, String[][] mproductos, String idticket, int tamticket) throws IOException {
        String codigo = Leer(Mostrar.MostrarListaProductosVenta(mproductos) + "\nIntroduce el codigo del producto");
        if (codigo != null) {
            int posp = ExisteProducto(codigo.trim(), mproductos);
            if (posp > -1) {
                if (Integer.parseInt(mproductos[posp][3]) > 0) {
                    String[] producto = mproductos[posp].clone();
                    System.out.println(Mostrar.MostrarProducto(producto));
                    String[] venta = {producto[0], producto[1], producto[2], "1"};
                    if (!Ticket.InsertarProductoTicket(mticket, venta, tamticket)) {
                        System.out.println("El arreglo esta lleno");
                    }
                } else {
                    System.out.println("No hay productos para venta");
                }
            } else {
                System.out.println("El codigo no existe no se puede agregar");
            }
        } else {
            System.out.println("Dato nulo");
        }
    }
    public static void AgregarProductoAVenta(String[][] mticket, String[][] mventa, String idticket) {
        int posventas = Principal.ObtenerUltimaPosicion(mventa);
        int posticket = Principal.ObtenerUltimaPosicion(mticket);
        for (int i = 0; i <= posticket; i++) {
            if (mticket[i][0] != null) {
                posventas++;
                mventa[posventas][0] = idticket;
                mventa[posventas][1] = mticket[i][0];
                mventa[posventas][2] = mticket[i][1];
                mventa[posventas][3] = mticket[i][2];
                mventa[posventas][4] = mticket[i][3];
            }
        }
    }
    public static String TotalProducto(String precio, String cantidad) {
        double total = Double.parseDouble(precio) * Double.parseDouble(cantidad);
        return String.format("%.2f", total);
    }
}