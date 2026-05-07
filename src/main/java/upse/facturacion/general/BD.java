package upse.facturacion.general;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import upse.facturacion.modelo.Cliente;
import upse.facturacion.modelo.Productos;
import upse.facturacion.modelo.CabFactura;

public class BD {
    public static ArrayList<Cliente> listaClientes = new ArrayList<>();
    public static ArrayList<Productos> listaProductos = new ArrayList<>();
    public static ArrayList<CabFactura> listaFacturas = new ArrayList<>();

    public static List<String> categorias = Arrays.asList(
        "Bebidas calientes",
        "Bebidas frías",
        "Repostería",
        "Snacks",
        "Especiales"
    );

    public static Cliente recuperarCliente(String cedula) {
        for (Cliente c : listaClientes) {
            if (c.getCedula().equals(cedula)) {
                return c;
            }
        }
        return null;
    }

    public static void guardarCliente(Cliente cliente) {
        listaClientes.add(cliente);
    }

    public static void agregarProducto(Productos producto) {
        for (int i = 0; i < listaProductos.size(); i++) {
            if (listaProductos.get(i).getCodigo().equals(producto.getCodigo())) {
                listaProductos.set(i, producto);
                return;
            }
        }
        listaProductos.add(producto);
    }

    public static Productos buscarProductoPorCodigo(String codigo) {
        for (Productos p : listaProductos) {
            if (p.getCodigo().equals(codigo)) {
                return p;
            }
        }
        return null;
    }

    public static void guardarFactura(CabFactura factura) {
        listaFacturas.add(factura);
    }
}
