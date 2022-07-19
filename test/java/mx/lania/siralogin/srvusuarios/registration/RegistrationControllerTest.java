package mx.lania.siralogin.srvusuarios.registration;

import mx.lania.siralogin.srvusuarios.appuser.Usuario;
import mx.lania.siralogin.srvusuarios.appuser.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class RegistrationControllerTest {
    private final RegistrationService registrationService;
    private UsuarioRepository usuarioRepository;

    @Autowired
    public RegistrationControllerTest(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @Test
    void register() {
        RegistrationRequest request = new RegistrationRequest(
                "Pedro",
                "Páramo",
                "laalonso@icc.clavijero.edu.mx",
                "12345",
                "ICC",
                "2288904325"
        );
        String token = registrationService.register(request);
        Assertions.assertNotNull(token);
    }

    @Test
    void confirm() {
        String token = "d591ad6c-a5da-490e-b052-04d34c19281e";
        String res = registrationService.confirmToken(token);
        Assertions.assertEquals("confirmed", res);
    }

    @Test
    void registerEmpleado() {
        RegistrationRequestEmpleado requestEmpleado = new RegistrationRequestEmpleado(
                "José",
                "Juan",
                "josejuan@lania.edu.mx",
                "12345",
                "12345",
                "SEGUIMIENTO"

        );
        Usuario user = registrationService.registerEmpleado(requestEmpleado);
        Assertions.assertNotNull(user);
    }

    @Test
    void obtenerUsuariosLania() {
        List<Usuario> listUsers = registrationService.obtenerUsuariosLania();
        Assertions.assertNotNull(listUsers);
    }

    @Test
    void editaUsuarioLania() {
        RegistrationRequestEmpleado requestEmpleado = new RegistrationRequestEmpleado(
                "José",
                "Juan",
                "josejuan@lania.edu.mx",
                "12345",
                "12345",
                "SEGUIMIENTO"
        );
        Usuario user = registrationService.editarEmpleado(requestEmpleado);
        Assertions.assertNotNull(user);
    }

    @Test
    void deshabilitaUsuario() {
        RegistrationRequestEmpleado requestEmpleado = new RegistrationRequestEmpleado(
                "","",
                "josejuan@lania.edu.mx",
                "", "", ""
        );

        Usuario user = usuarioRepository.findByEmail(requestEmpleado.getEmail()).orElse(null);

        Map<String,Object> response = new HashMap<>();

        if(user.equals(null)){
            Assertions.assertFalse(true);
        }
        registrationService.unableUsuario(requestEmpleado.getEmail());
        Assertions.assertFalse(false);
    }


    @Test
    void habilitaUsuarioEmpleado() {
        RegistrationRequestEmpleado requestEmpleado = new RegistrationRequestEmpleado(
                "","",
                "josejuan@lania.edu.mx",
                "", "", ""
        );
        Usuario user = usuarioRepository.findByEmail(requestEmpleado.getEmail()).orElse(null);
        Map<String,Object> response = new HashMap<>();
        if(user.equals(null)){
            Assertions.assertFalse(true);
        }
        registrationService.enableUsuario(requestEmpleado.getEmail());
        Assertions.assertFalse(false);
    }
}