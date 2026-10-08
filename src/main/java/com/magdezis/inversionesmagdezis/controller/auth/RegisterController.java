package main.java.com.magdezis.inversionesmagdezis.controller.auth;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.com.magdezis.inversionesmagdezis.dto.request.auth.RegisterRequest;
import main.java.com.magdezis.inversionesmagdezis.dto.response.auth.RegisterResponse;
import main.java.com.magdezis.inversionesmagdezis.repository.auth.UsuarioRepository;
import main.java.com.magdezis.inversionesmagdezis.service.auth.UsuarioService;
import main.java.com.magdezis.inversionesmagdezis.utils.SceneManager;

public class RegisterController implements Initializable {

    private SceneManager stage;
    private UsuarioService usuarioService;

    public RegisterController() {
        // Constructor por defecto necesario para JavaFX si se carga vía FXML loader sin inyección manual
        UsuarioRepository usuarioRepository = new UsuarioRepository();
        this.usuarioService = new UsuarioService(usuarioRepository);
    }

    public RegisterController(SceneManager stage, UsuarioService userService) {
        this.stage = stage;
        this.usuarioService = userService;
    }

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtIdRol;

    @FXML
    private Label lblMensaje;

    @FXML
    private Button btnRegistrar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (this.usuarioService == null) {
            UsuarioRepository usuarioRepository = new UsuarioRepository();
            this.usuarioService = new UsuarioService(usuarioRepository);
        }
    }

    @FXML
    private void handleRegistrar() {
        try {
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            String email = txtEmail.getText().trim();
            String password = txtPassword.getText();

            if (txtIdRol.getText() == null || txtIdRol.getText().trim().isEmpty()) {
                lblMensaje.setText("El ID de rol es obligatorio.");
                lblMensaje.setStyle("-fx-text-fill: red;");
                return;
            }

            int idRol = Integer.parseInt(txtIdRol.getText().trim());

            RegisterRequest request = new RegisterRequest(nombre, apellido, email, password, idRol);

            RegisterResponse response = usuarioService.registrar(request);

            lblMensaje.setText(response.getMessage());

            if (response.isExito()) {
                lblMensaje.setStyle("-fx-text-fill: green;");
                limpiarCampos();
            } else {
                lblMensaje.setStyle("-fx-text-fill: red;");
            }

        } catch (NumberFormatException e) {
            lblMensaje.setText("El ID de rol debe ser un número válido.");
            lblMensaje.setStyle("-fx-text-fill: red;");
        }
    }
    
    @FXML
    private void handleLogin() throws Exception {
        
        stage.showLoginView();
        
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtApellido.clear();
        txtEmail.clear();
        txtPassword.clear();
        txtIdRol.clear();
    }
}
