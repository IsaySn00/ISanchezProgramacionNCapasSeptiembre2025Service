package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.RestController;

import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.DAO.UsuarioJPADAOImplementation;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.Result;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.UsuarioJPA;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioRestController {

    @Autowired
    private UsuarioJPADAOImplementation usuarioJPADAOImplemenation;

    @GetMapping("/usuario")
    public ResponseEntity GetUsuarios() {
        Result result = new Result();

        try {
            result = usuarioJPADAOImplemenation.GetAll();
            result.correct = true;
            result.status = 200;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.status = 500;
        }
        return ResponseEntity.status(result.status).body(result);
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity GetUsuarioById(@PathVariable("idUsuario") int idUsuario) {
        Result result = new Result();

        try {
            result = usuarioJPADAOImplemenation.GetById(idUsuario);
            result.correct = true;
            result.status = 200;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.status = 500;
        }

        return ResponseEntity.status(result.status).body(result);
    }

    @PostMapping(value = "/usuario", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity AddUsuario(@RequestPart("usuario") String usuarioJSON,
            @RequestPart("imagenFile") MultipartFile imagenFile) {

        Result result = new Result();

        try {

            ObjectMapper mapper = new ObjectMapper();
            UsuarioJPA usuario = mapper.readValue(usuarioJSON, UsuarioJPA.class);

            if (imagenFile != null) {
                usuario.setFotoUsuario(imagenFile.getBytes());
            }

            usuarioJPADAOImplemenation.AddUsuario(usuario);

            result.correct = true;
            result.object = "Se ha creado el usuario exitosamente";
            result.status = 201;

        } catch (Exception ex) {
            result.status = 500;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.correct = false;
        }

        return ResponseEntity.status(result.status).body(result);
    }

    @PutMapping("/updateUsuario")
    public ResponseEntity UpdateUsuario(@RequestBody UsuarioJPA usuario) {
        Result result = new Result();

        try {
            usuarioJPADAOImplemenation.UpdateUsuario(usuario);

            result.correct = true;
            result.status = 202;
            result.object = "Se ha actualizado el usuario correctamente";

        } catch (Exception ex) {
            result.correct = false;
            result.status = 500;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return ResponseEntity.status(result.status).body(result);
    }

    @DeleteMapping("/deleteUsuario/{idUsuario}")
    public ResponseEntity DeleteUsuario(@PathVariable("idUsuario") int id) {
        Result result = new Result();

        try {
            usuarioJPADAOImplemenation.DeleteUsuario(id);

            result.correct = true;
            result.status = 200;
            result.object = "Usuario eliminado exitosamente";

        } catch (Exception ex) {
            result.correct = false;
            result.status = 500;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return ResponseEntity.status(result.status).body(result);
    }

    @PatchMapping("/updateImgUsuario")
    public ResponseEntity UpdateImgUsuario(@RequestPart("usuario") String usuarioJSON,
            @RequestPart("imgFile") MultipartFile imgFile) {
        Result result = new Result();

        try {
            ObjectMapper mapper = new ObjectMapper();
            UsuarioJPA usuario = mapper.readValue(usuarioJSON, UsuarioJPA.class);

            if (imgFile != null) {
                usuario.setFotoUsuario(imgFile.getBytes());
            }

            usuarioJPADAOImplemenation.UpdateImgUsuario(usuario);

            result.correct = true;
            result.status = 202;
            result.object = "Se ha actualizado la foto correctamente";

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.status = 500;
        }

        return ResponseEntity.status(result.status).body(result);
    }

    @PostMapping("/GetUsuariosDinamico")
    public ResponseEntity GetDinamico(@RequestBody UsuarioJPA usuario) {
        Result result = new Result();

        try {
            result = usuarioJPADAOImplemenation.GetAllDinamico(usuario);
            result.correct = true;
            result.status = 200;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.status = 500;
        }
        return ResponseEntity.status(result.status).body(result);
    }
}
