package main.java.com.magdezis.inversionesmagdezis.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import main.java.com.magdezis.inversionesmagdezis.controller.auth.LoginController;
import main.java.com.magdezis.inversionesmagdezis.controller.auth.RegisterController;
import main.java.com.magdezis.inversionesmagdezis.repository.auth.UsuarioRepository;
import main.java.com.magdezis.inversionesmagdezis.service.auth.UsuarioService;

public class SceneManager {

    //atributos
    private Stage primaryStage;
    private final String FXML_PATH = "/main/resources/view/auth/";

    //constructor
    public SceneManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    //metodo
    public void showLoginView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH + "login-view.fxml"));
        loader.setControllerFactory(
                clazz -> {
                    if (clazz == LoginController.class) {
                        UsuarioRepository authRepository = new UsuarioRepository();
                        UsuarioService authService = new UsuarioService(authRepository);
                        return new LoginController(this, authService);
                    }
                    try {
                        return clazz.getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException("Error al crear el constructor" + e.getMessage());
                    }
                });
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 650);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    /**/
    public void showRegisterView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH + "register-view.fxml"));
        loader.setControllerFactory(clazz -> {
            if (clazz == RegisterController.class) {
                UsuarioRepository authRepository = new UsuarioRepository();
                UsuarioService authService = new UsuarioService(authRepository);
                return new RegisterController(this, authService);
            }
            try {
                return clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("error al crear el constructor" + e.getMessage());
            }
        }
        );
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 650);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
    
    public void showInfoAlert(String head, String title, String content, AlertType type) {
        Alert alert = new Alert(type);
        alert.initOwner(this.primaryStage);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.setHeaderText(head);
        alert.showAndWait();
    }
}
