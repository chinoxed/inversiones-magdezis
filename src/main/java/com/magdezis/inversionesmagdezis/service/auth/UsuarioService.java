package main.java.com.magdezis.inversionesmagdezis.service.auth;

import main.java.com.magdezis.inversionesmagdezis.dto.request.auth.LoginRequest;
import main.java.com.magdezis.inversionesmagdezis.dto.request.auth.RegisterRequest;
import main.java.com.magdezis.inversionesmagdezis.dto.response.auth.LoginResponse;
import main.java.com.magdezis.inversionesmagdezis.dto.response.auth.RegisterResponse;
import main.java.com.magdezis.inversionesmagdezis.repository.auth.UsuarioRepository;

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public RegisterResponse registrar(RegisterRequest req) {
        if (req.getEmail() == null || req.getEmail().trim().isEmpty()) {
            return new RegisterResponse(false, "El correo electrónico es obligatorio.");
        }

        if (req.getPsswrd() == null || req.getPsswrd().trim().length() < 6) {
            return new RegisterResponse(false, "La contraseña debe tener al menos 6 caracteres.");
        }

        return usuarioRepository.crearUsuario(req);
    }

    public LoginResponse login(LoginRequest req) {

        if (req.getEmail() == null || req.getEmail().trim().isEmpty()
                || req.getPsswrd() == null || req.getPsswrd().trim().isEmpty()) {
            return new LoginResponse(false, "El correo y la contraseña son obligatorios.", 0, null, null, null, 0);
        }

        return usuarioRepository.login(req);
    }
}
