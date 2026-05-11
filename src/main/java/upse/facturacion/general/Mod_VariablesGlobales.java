/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package upse.facturacion.general;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static upse.facturacion.general.Mod_general.idiomaActual;

/**
 *
 * @author EMILY CRUZ
 */
public class Mod_VariablesGlobales {

    public static int num_fact = 0;

    public static String generarNumeroFactura() {
        num_fact++;
        String numero = String.format("%010d", num_fact);
        return numero.substring(0, 3) + "-"
                + numero.substring(3, 6) + "-"
                + numero.substring(6, 10);
    }

    public static String obtenerFechaHoy() {
        LocalDate hoy = LocalDate.now();
        DateTimeFormatter formato;

        if (idiomaActual.getLanguage().equals("en")) {
            // Formato inglés YYYY/MM/DD
            formato = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        } else {
            // Formato español DD/MM/YYYY
            formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        }

        return hoy.format(formato);

    }
}
