package main.java.com.magdezis.inversionesmagdezis.repository.auth;

import java.sql.*;
import main.java.com.magdezis.inversionesmagdezis.config.ConnectionDB;
import main.java.com.magdezis.inversionesmagdezis.security.jbcrypt.BCrypt;
import main.java.com.magdezis.inversionesmagdezis.dto.request.auth.LoginRequest;
import main.java.com.magdezis.inversionesmagdezis.dto.request.auth.RegisterRequest;
import main.java.com.magdezis.inversionesmagdezis.dto.response.auth.LoginResponse;
import main.java.com.magdezis.inversionesmagdezis.dto.response.auth.RegisterResponse;

public class UsuarioRepository {

    public RegisterResponse crearUsuario(RegisterRequest req) {
        String hashedPassword = BCrypt.hashpw(req.getPsswrd(), BCrypt.gensalt());
        String sql = "INSERT INTO Usuarios (nombre, apellido, email, psswrd, idRol) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstm = ConnectionDB.getConnection().prepareStatement(sql)) {

            pstm.setString(1, req.getNombre());
            pstm.setString(2, req.getApellido());
            pstm.setString(3, req.getEmail());
            pstm.setString(4, hashedPassword);
            pstm.setInt(5, req.getIdRol());

            int affectedRows = pstm.executeUpdate();

            if (affectedRows > 0) {
                return new RegisterResponse(true, "Usuario registrado exitosamente.");
            } else {
                return new RegisterResponse(false, "No se pudo registrar el usuario.");
            }

        } catch (SQLException e) {
            return new RegisterResponse(false, "Error en la base de datos: " + e.getMessage());
        }
    }

    public LoginResponse login(LoginRequest req) {
        String sql = "SELECT id, nombre, apellido, email, psswrd, idRol FROM Usuarios WHERE email = ?";

        try (PreparedStatement pstm = ConnectionDB.getConnection().prepareStatement(sql)) {

            pstm.setString(1, req.getEmail());

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    String storedHashedPassword = rs.getString("psswrd");

                    if (BCrypt.checkpw(req.getPsswrd(), storedHashedPassword)) {
                        return new LoginResponse(
                                true,
                                "Autenticación exitosa",
                                rs.getInt("id"),
                                rs.getString("nombre"),
                                rs.getString("apellido"),
                                rs.getString("email"),
                                rs.getInt("idRol")
                        );
                    } else {
                        // Contraseña incorrecta
                        return new LoginResponse(false, "Credenciales incorrectas", 0, null, null, null, 0);
                    }
                } else {
                    // Usuario no encontrado
                    return new LoginResponse(false, "Credenciales incorrectas", 0, null, null, null, 0);
                }
            }

        } catch (SQLException e) {
            return new LoginResponse(false, "Error en la base de datos: " + e.getMessage(), 0, null, null, null, 0);
        }
    }
}
