package Modelo;
public class inicioSesionModelo {
    private int id;
    private String usuario;
    private String contraseña;
    private boolean admin;
    public inicioSesionModelo(int id, String usuario, String contraseña, boolean esAdmin) {
        this.id = id;
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.admin = esAdmin;
    }
    public int getId() {
        return id;
    }
    public String getUsuario() {
        return usuario;
    }
    public boolean esAdmin() {
        return admin;
    }
    public boolean validarContraseña(String contraseña) {
        return this.contraseña.equals(contraseña);
    }
}