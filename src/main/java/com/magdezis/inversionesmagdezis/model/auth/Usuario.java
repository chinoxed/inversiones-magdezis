package main.java.com.magdezis.inversionesmagdezis.model.auth;

public class Usuario {
    
    /* ==================================
            ATRIBUTOS
        =================================== */
    private String nombre;
    private String apellido;
    private String email;
    private String psswrd;
    private int idRol;
    
    /* ==================================
            CONSTRUCTOR
        =================================== */

    public Usuario(String nombre, String apellido, String email, String psswrd, int idRol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.psswrd = psswrd;
        this.idRol = idRol;
    }
    
    /* ==================================
            GETTERS&SETTERS
        =================================== */
    
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPsswrd() {
        return psswrd;
    }

    public void setPsswrd(String psswrd) {
        this.psswrd = psswrd;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }
}
