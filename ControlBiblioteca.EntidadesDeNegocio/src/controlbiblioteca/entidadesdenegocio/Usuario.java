
package controlbiblioteca.entidadesdenegocio;

import java.time.LocalDate;


public class Usuario {
    private int id;
    private int idRol;
    private String nombre;
    private String apellido;
    private String login;
    private String password;
    private byte estatus;
    private LocalDate FechaRegistro;
    private int top_aux;
    private String confirmPasswor_aux;
    private Rol rol;

    public Usuario() {
    }

    public Usuario(int id, int idRol, String nombre, String apellido, String login, String password, byte estatus, LocalDate FechaRegistro) {
        this.id = id;
        this.idRol = idRol;
        this.nombre = nombre;
        this.apellido = apellido;
        this.login = login;
        this.password = password;
        this.estatus = estatus;
        this.FechaRegistro = FechaRegistro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte getEstatus() {
        return estatus;
    }

    public void setEstatus(byte estatus) {
        this.estatus = estatus;
    }

    public LocalDate getFechaRegistro() {
        return FechaRegistro;
    }

    public void setFechaRegistro(LocalDate FechaRegistro) {
        this.FechaRegistro = FechaRegistro;
    }

    public int getTop_aux() {
        return top_aux;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }

    public String getConfirmPasswor_aux() {
        return confirmPasswor_aux;
    }

    public void setConfirmPasswor_aux(String confirmPasswor_aux) {
        this.confirmPasswor_aux = confirmPasswor_aux;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
    public class EstatusUsuario{
        public static final byte ACTIVO = 1;
        public static final byte INACTIVO = 2;
    }
    
    
    
    
}
