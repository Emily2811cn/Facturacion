package upse.facturacion.general;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static upse.facturacion.general.Mod_general.idiomaActual;

public class Mod_VariablesGlobales {

    public static int num_fact = 0;
    public static String g_nombreUsuario;

    // Generar número de factura con formato XXX-XXX-XXXX
    public static String generarNumeroFactura(int secuencia) {
        String numero = String.format("%010d", secuencia);
        return numero.substring(0, 3) + "-"
                + numero.substring(3, 6) + "-"
                + numero.substring(6, 10);
    }

    // 🔹 Devuelve fecha como String formateada según idioma
    public static String obtenerFechaHoy() {
        LocalDate hoy = LocalDate.now();
        DateTimeFormatter formato;

        if (idiomaActual.getLanguage().equals("en")) {
            formato = DateTimeFormatter.ofPattern("yyyy/MM/dd"); // Inglés
        } else {
            formato = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Español
        }

        return hoy.format(formato);
    }

    // 🔹 Devuelve fecha como LocalDate (sin formato)
    public static LocalDate obtenerFechaHoyLocalDate() {
        return LocalDate.now();
    }

    // 🔹 Convierte un String a LocalDate según idioma
    public static LocalDate parsearFecha(String fechaTexto) {
        try {
            DateTimeFormatter formato;
            if (idiomaActual.getLanguage().equals("en")) {
                formato = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            } else {
                formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            }
            return LocalDate.parse(fechaTexto, formato);
        } catch (Exception e) {
            System.err.println("⚠️ Error al parsear fecha: " + fechaTexto);
            return null;
        }
    }
}
