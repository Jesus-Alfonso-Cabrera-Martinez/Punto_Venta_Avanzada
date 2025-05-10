package Modelo;
public class cajaModelo {
    private static final String ARCHIVO_CAJA = "caja.json";
    public double montoInicial;
    public double montoActual;
    public String fechaHoraInicio;
    public boolean esCierre;
    private static inicioSesionModelo empleadoActual;
    public cajaModelo(double montoInicial, String fechaHoraInicio, boolean esCierre) {
        this.montoInicial = montoInicial;
        this.montoActual = montoInicial;
        this.fechaHoraInicio = fechaHoraInicio;
        this.esCierre = esCierre;
    }
    public double getMontoInicial() {
        return montoInicial;
    }
    public void setMontoInicial(double montoInicial) {
        this.montoInicial = montoInicial;
    }
    public static inicioSesionModelo getEmpleadoActual() {
        return empleadoActual;
    }
    public static void setEmpleadoActual(inicioSesionModelo empleadoActual) {
        cajaModelo.empleadoActual = empleadoActual;
    }
    public boolean isEsCierre() {
        return esCierre;
    }
    public void setEsCierre(boolean esCierre) {
        this.esCierre = esCierre;
    }
    public String getFechaHoraInicio() {
        return fechaHoraInicio;
    }
    public void setFechaHoraInicio(String fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }
    public double getMontoActual() {
        return montoActual;
    }
    public void setMontoActual(double montoActual) {
        this.montoActual = montoActual;
    }
}