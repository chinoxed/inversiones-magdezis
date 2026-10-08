package main.java.com.magdezis.inversionesmagdezis.controller.auth;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert; // <-- Importación necesaria para la alerta
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.com.magdezis.inversionesmagdezis.dto.request.auth.LoginRequest;
import main.java.com.magdezis.inversionesmagdezis.dto.response.auth.LoginResponse;
import main.java.com.magdezis.inversionesmagdezis.repository.auth.UsuarioRepository;
import main.java.com.magdezis.inversionesmagdezis.service.auth.UsuarioService;
import main.java.com.magdezis.inversionesmagdezis.utils.SceneManager;

public class LoginController implements Initializable {

    private SceneManager stage;
    private UsuarioService userService;

    public LoginController() {
        UsuarioRepository usuarioRepository = new UsuarioRepository();
        this.userService = new UsuarioService(usuarioRepository);
    }

    public LoginController(SceneManager stage, UsuarioService userService) {
        this.stage = stage;
        this.userService = userService;
    }

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label lblMensaje;

    @FXML
    private Button btnLogin;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (this.userService == null) {
            UsuarioRepository usuarioRepository = new UsuarioRepository();
            this.userService = new UsuarioService(usuarioRepository);
        }
    }

    @FXML
    private void handleLogin() {
        try {
            String email = txtEmail.getText().trim();
            String password = txtPassword.getText();

            if (email.isEmpty() || password.isEmpty()) {
                lblMensaje.setText("Todos los campos son obligatorios.");
                lblMensaje.setStyle("-fx-text-fill: red;");
                return;
            }

            LoginRequest request = new LoginRequest(email, password);
            LoginResponse response = userService.login(request);

            if (response.isExito()) {
                limpiarCampos();
                lblMensaje.setText(""); // Limpiamos mensaje de error previo

                // Creamos y mostramos la alerta con el rol del usuario
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("¡Bienvenido!");
                alert.setHeaderText(null);
                // NOTA: Si en tu LoginResponse el método se llama distinto (ej. getRole() o getNombreRol()), ajústalo aquí:
                alert.setContentText("Rol del usuario: " + response.getIdRol());
                alert.showAndWait();

                // Opcional: Redirigir a la vista principal después de cerrar la alerta
                // if (stage != null) { stage.showMainView(); }
            } else {
                lblMensaje.setText(response.getMessage());
                lblMensaje.setStyle("-fx-text-fill: red;");
            }

        } catch (Exception e) {
            lblMensaje.setText("Ocurrió un error al iniciar sesión.");
            lblMensaje.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void handleGoToRegister() throws Exception {
        if (stage != null) {
            stage.showRegisterView();
        }
    }

    private void limpiarCampos() {
        txtEmail.clear();
        txtPassword.clear();
    }
}
