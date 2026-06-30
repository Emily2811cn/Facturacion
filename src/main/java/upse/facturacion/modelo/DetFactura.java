package upse.facturacion.modelo;

public class DetFactura {

    // ── Campos que se guardan en la BD (talleFactura) ──
    private int det_id;
    private int fac_id;
    private int prod_id;
    private String prod_nombre;
    private float cantidad;
    private double prod_pvp;   // precio unitario  → columna prod_pvp
    private double iva;        // valor del IVA    → columna iva
    private double total;      // total con IVA    → columna total

    // ── Campos extra que usa la UI (no se guardan directamente) ──
    private String prod_cod;   // código visible en tabla
    private double subtotal;   // cantidad × prod_pvp (sin IVA)
    private boolean aplicaIva; // checkbox "APLICA IVA"

    // ── Constructor vacío (para filas nuevas en la tabla) ──
    public DetFactura() {
        this.prod_cod    = "";
        this.prod_nombre = "";
        this.cantidad    = 0f;
        this.prod_pvp    = 0.0;
        this.subtotal    = 0.0;
        this.iva         = 0.0;
        this.total       = 0.0;
        this.aplicaIva   = false;
    }

    /**
     * Constructor completo que usa el Controller cuando busca un producto
     * y lo agrega a la tabla.
     *
     * @param det_id      PK (0 si es nuevo)
     * @param prod_cod    código del producto (UI)
     * @param prod_nombre nombre del producto
     * @param cantidad    unidades
     * @param prod_pvp    precio unitario
     * @param aplicaIva   si aplica IVA
     * @param iva         monto IVA calculado
     * @param total       total final
     */
    public DetFactura(int det_id, String prod_cod, String prod_nombre,
                      float cantidad, double prod_pvp,
                      boolean aplicaIva, double iva, double total) {
        this.det_id      = det_id;
        this.prod_cod    = prod_cod;
        this.prod_nombre = prod_nombre;
        this.cantidad    = cantidad;
        this.prod_pvp    = prod_pvp;
        this.aplicaIva   = aplicaIva;
        this.subtotal    = cantidad * prod_pvp;
        this.iva         = iva;
        this.total       = total;
    }

    // ── Getters / Setters BD ────────────────────────────────────────────

    public int getDet_id() { return det_id; }
    public void setDet_id(int det_id) { this.det_id = det_id; }

    public int getFac_id() { return fac_id; }
    public void setFac_id(int fac_id) { this.fac_id = fac_id; }

    public int getProd_id() { return prod_id; }
    public void setProd_id(int prod_id) { this.prod_id = prod_id; }

    public String getProd_nombre() { return prod_nombre; }
    public void setProd_nombre(String prod_nombre) { this.prod_nombre = prod_nombre; }

    public float getCantidad() { return cantidad; }
    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
        recalcular();
    }

    /** Precio unitario — columna prod_pvp en la BD */
    public double getProd_pvp() { return prod_pvp; }
    public void setProd_pvp(double prod_pvp) {
        this.prod_pvp = prod_pvp;
        recalcular();
    }

    public double getIva() { return iva; }
    public void setIva(double iva) { this.iva = iva; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    // ── Getters / Setters UI ────────────────────────────────────────────

    public String getProd_cod() { return prod_cod; }
    public void setProd_cod(String prod_cod) { this.prod_cod = prod_cod; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    public boolean isAplicaIva() { return aplicaIva; }
    public boolean getAplicaIva() { return aplicaIva; }   // PropertyValueFactory necesita getXxx
    public void setAplicaIva(boolean aplicaIva) {
        this.aplicaIva = aplicaIva;
        recalcular();
    }

    /**
     * Alias "precio" → usado por PropertyValueFactory("precio") en col_pvp
     * y por Mad_factura al leer d.getPrecio().
     */
    public double getPrecio() { return prod_pvp; }
    public void setPrecio(double precio) {
        this.prod_pvp = precio;
        recalcular();
    }

    // ── Cálculos ────────────────────────────────────────────────────────

    /**
     * Recalcula subtotal, iva y total a partir de cantidad, prod_pvp y aplicaIva.
     * Llamado automáticamente al cambiar cantidad, precio o aplicaIva.
     */
    private void recalcular() {
        this.subtotal = this.cantidad * this.prod_pvp;
        if (this.aplicaIva) {
            this.iva   = this.subtotal * 0.15;
            this.total = this.subtotal + this.iva;
        } else {
            this.iva   = 0.0;
            this.total = this.subtotal;
        }
    }

    /**
     * Método público para forzar recálculo desde el Controller.
     * (antes era ActualizarTotales en mayúscula — se mantiene el alias)
     */
    public void actualizarTotales() {
        recalcular();
    }

    /** Alias con mayúscula para compatibilidad con el Controller existente */
    public void ActualizarTotales() {
        recalcular();
    }
}