package mx.lania.siralogin.srvparticipaciones.controllers;

import mx.lania.siralogin.srvcatalogos.models.Convocatoria;
import mx.lania.siralogin.srvcatalogos.models.RequisitoConvocatoria;
import mx.lania.siralogin.srvcatalogos.models.service.ConvocatoriaService;
import mx.lania.siralogin.srvparticipaciones.models.Participacion;
import mx.lania.siralogin.srvparticipaciones.models.ParticipacionRequisitoConvocatoria;
import mx.lania.siralogin.srvusuarios.appuser.Usuario;
import mx.lania.siralogin.srvusuarios.appuser.UsuarioService;
import mx.lania.siralogin.srvusuarios.aspirante.models.Aspirante;
import mx.lania.siralogin.srvusuarios.aspirante.models.service.AspiranteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
class ParticipacionRestControllerTest {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AspiranteService aspiranteService;

    @Autowired
    private ConvocatoriaService convocatoriaService;

    @Test
    void crearParticipacion() {
        Map<String,Object> response = new HashMap<>();

        String userName = "laalonso";
        Usuario usuario = usuarioService.getUsuarioByEmail(userName);
        Aspirante aspirante = usuario.getAspirante();

        try{
            Convocatoria convocatoria = convocatoriaService.findById((long)123345);
            Participacion participacion = new Participacion();
            participacion.setConvocatoria(convocatoria);
            participacion.setAspirante(aspirante);
            participacion.setActiva(true);
            participacion.setEstatus("subir requisitos"); // subir requisitos , en validación, completada
            participacion.setFechaInscripcion(new Date());
            for (RequisitoConvocatoria rc : convocatoria.getRequisitoConvocatorias()) {
                ParticipacionRequisitoConvocatoria prc = new ParticipacionRequisitoConvocatoria();
                prc.setRequisitoConvocatoria(rc);
                prc.setParticipacion(participacion);
                prc.setEntregado(false);
                prc.setValidado(false);
                prc.setRutaArchivo(null);
                participacion.addParticipacionRequisitosConvocatoria(prc);
            }

            aspirante.addParticipacion(participacion);
            aspiranteService.save(aspirante);
            // TODO FALTA CREAR LA PARTICIPACION_REQUISITO_CONVOCATORIA POR CADA REQ_CONVOCATORIA DE LA CONV
        }catch (DataAccessException ex){
            response.put("mensaje", "Error al realizar update en la BD");
            response.put("error",ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
            Assertions.assertFalse(false);
        }
        //response.put("mensaje", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        // TODO obtuve el usuario, falta implementar para que busque su id de aspirante :)
        response.put("mensaje","Se registró su participación con éxito!");
        response.put("participaciones",aspirante.getParticipaciones());
        Assertions.assertFalse(true);
    }

    @Test
    void subirDocumento() {
    }

    @Test
    void numAspirantes() {
    }

    @Test
    void validarDocumento() {
    }
}