package main.java.com.magdezis.inversionesmagdezis.dto.response.auth;

public class RegisterResponse {

    private boolean exito;
    private String message;

    public RegisterResponse(boolean exito, String message) {
        this.exito = exito;
        this.message = message;
    }

    public void setExito(boolean exito) {
        this.exito = exito;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isExito() {
        return exito;
    }

    public String getMessage() {
        return message;
    }

}
