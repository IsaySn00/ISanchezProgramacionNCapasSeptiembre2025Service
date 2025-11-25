package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.RestController;

import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.DAO.UsuarioJPADAOImplementation;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.DTO.UsuarioUpdateDTO;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.ErrorCarga;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.Result;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.RolJPA;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA.UsuarioJPA;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

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
    public ResponseEntity AddUsuario(@Valid @RequestPart("usuario") UsuarioJPA usuario, BindingResult bindingResult,
            @RequestPart("imagenFile") MultipartFile imagenFile) {

        Result result = new Result();

        if (bindingResult.hasErrors()) {

            List<ErrorCarga> listaError = new ArrayList<>();

            for (FieldError errorCampo : bindingResult.getFieldErrors()) {

                ErrorCarga error = new ErrorCarga();

                error.setCampo(errorCampo.getField());
                error.setDescripcion(errorCampo.getDefaultMessage());

                listaError.add(error);
            }

            result.object = listaError;
            result.status = 422;
            result.errorMessage = "Datos Invalidos";

            return ResponseEntity.status(result.status).body(result);
        }

        try {

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
    public ResponseEntity UpdateUsuario(@Valid @RequestBody UsuarioUpdateDTO usuario, BindingResult bindingResult) {
        Result result = new Result();

        if (bindingResult.hasErrors()) {

            List<ErrorCarga> listaError = new ArrayList<>();

            for (FieldError errorCampo : bindingResult.getFieldErrors()) {

                ErrorCarga error = new ErrorCarga();

                error.setCampo(errorCampo.getField());
                error.setDescripcion(errorCampo.getDefaultMessage());

                listaError.add(error);
            }

            result.object = listaError;
            result.status = 422;
            result.errorMessage = "Datos Invalidos";

            return ResponseEntity.status(result.status).body(result);
        }
        
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
    public ResponseEntity UpdateImgUsuario(@RequestPart("usuario") UsuarioJPA usuario,
            @RequestPart("imgFile") MultipartFile imgFile) {
        Result result = new Result();

        try {
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

    @PatchMapping("/usuario/{idUsuario}/bajaLogica")
    public ResponseEntity BajaLogica(@PathVariable("idUsuario") int idUsuario, @RequestParam("status") int status) {
        Result result = new Result();

        try {
            usuarioJPADAOImplemenation.BorradoLogicoUsuario(idUsuario, status);
            result.correct = true;
            result.status = 202;
            result.object = "Se he hecho la baja del usuario";

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.status = 500;
        }

        return ResponseEntity.status(result.status).body(result);
    }

    @PostMapping(value = "/cargaMasiva", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity CargaMasiva(@RequestPart("archivo") MultipartFile archivo) {

        Result result = new Result();

        List<UsuarioJPA> lista = new ArrayList<>();
        List<ErrorCarga> listaError = new ArrayList<>();

        try {
            String extension = archivo.getOriginalFilename().split("\\.")[1];

            String path = System.getProperty("user.dir");
            String pathArchivo = "src/main/resources/archivosCarga";
            String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            String pathDefinitivo = path + "/" + pathArchivo + "/" + fecha + archivo.getOriginalFilename();
            String tkn = "";

            try {
                tkn = generateHash(pathDefinitivo);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(UsuarioRestController.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                archivo.transferTo(new File(pathDefinitivo));
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(UsuarioRestController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalStateException ex) {
                java.util.logging.Logger.getLogger(UsuarioRestController.class.getName()).log(Level.SEVERE, null, ex);
            }

            File file = new File(pathDefinitivo);

            if (extension.equals("txt")) {
                lista = LeerArchivoTXT(file);
            } else if (extension.equals("xlsx")) {
                lista = LecturaArchivoXLSX(file);
            } else {

            }

            listaError = validarDatosArchivo(lista);

            String pathLog = "src/main/resources/logCargaMasiva/LOG_CargaMasiva.txt";

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathLog, true))) {

                bw.write(fecha + archivo.getOriginalFilename());
                bw.write("|");
                bw.write(listaError.size() > 0 ? "null" : tkn);
                bw.write("|");
                bw.write(listaError.size() > 0 ? "Error" : "Valido");
                bw.write("|");
                bw.write(fecha);
                bw.write("|");

                StringBuilder sb = new StringBuilder();

                for (ErrorCarga error : listaError) {
                    sb.append("Linea " + error.linea + " error en: " + error.campo + " porque " + error.descripcion + ", ");
                }

                bw.write(sb.toString());

                bw.newLine();
                bw.newLine();

            } catch (IOException e) {
                System.out.println("Error al escribir en el archivo: " + e.getMessage());
            }

            result.correct = true;
            result.status = listaError.size() > 0 ? 422 : 200;
            result.object = listaError.size() > 0 ? listaError : tkn;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.status = 500;
        }

        return ResponseEntity.status(result.status).body(result);
    }

    @GetMapping("/cargaMasiva")
    public ResponseEntity CargaMasiva(@RequestParam("tkn") String tkn) {

        Result result = new Result();
        List<UsuarioJPA> lista = new ArrayList<>();

        try {
            String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

            String pathLog = "src/main/resources/logCargaMasiva/LOG_CargaMasiva.txt";

            String linea = encontrarEnLogTxt(pathLog, tkn);

            if (linea == null) {
                result.correct = false;
                result.status = 404;
                result.errorMessage = "Token no encontrado";
                return ResponseEntity.status(result.status).body(result);
            }

            String[] elem = linea.split("\\|");

            String nombreArchivo = elem[0];
            String fechaLinea = elem[3];

            if (tokenExpirado(fechaLinea)) {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathLog, true))) {

                    bw.write(nombreArchivo);
                    bw.write("|");
                    bw.write(tkn);
                    bw.write("|");
                    bw.write("Error");
                    bw.write("|");
                    bw.write(fecha);
                    bw.write("|");
                    bw.write("Tiempo expirado");

                    bw.newLine();
                    bw.newLine();

                } catch (IOException e) {
                    System.out.println("Error al escribir en el archivo: " + e.getMessage());
                }

                result.correct = false;
                result.status = 408;
                result.errorMessage = "Tiempo Expirado";
                return ResponseEntity.status(result.status).body(result);
            }

            String path = "src/main/resources/archivosCarga/" + nombreArchivo;

            File file = new File(path);
            String extension = FilenameUtils.getExtension(path);

            if (extension.equals("txt")) {
                lista = LeerArchivoTXT(file);
            } else if (extension.equals("xlsx")) {
                lista = LecturaArchivoXLSX(file);
            }

            result = usuarioJPADAOImplemenation.AddUsuariosByFile(lista);

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathLog, true))) {

                bw.write(nombreArchivo);
                bw.write("|");
                bw.write(tkn);
                bw.write("|");
                bw.write("Procesado");
                bw.write("|");
                bw.write(fecha);
                bw.write("|");

                bw.newLine();
                bw.newLine();

            } catch (IOException e) {
                System.out.println("Error al escribir en el archivo: " + e.getMessage());
            }

            result.object = "Se ha procesado el archivo exitosamente";
            result.correct = true;
            result.status = 201;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.status = 500;
        }

        return ResponseEntity.status(result.status).body(result);
    }

    public List<UsuarioJPA> LeerArchivoTXT(File archivo) {
        List<UsuarioJPA> usuarioList = new ArrayList<>();

        try (InputStream inputStream = new FileInputStream(archivo); BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));) {
            String linea = "";

            while ((linea = bufferedReader.readLine()) != null) {

                String[] campos = linea.split("\\|");
                UsuarioJPA usuario = new UsuarioJPA();
                usuario.setNombreUsuario(campos[0]);
                usuario.setApellidoPatUsuario(campos[1]);
                usuario.setApellidoMatUsuario(campos[2]);
                usuario.setPasswordUser(campos[3]);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date fechaNac = formatter.parse(campos[4]);
                usuario.setFechaNacimiento(fechaNac);
                usuario.setStatusUsuario(Integer.parseInt(campos[5]));
                usuario.setFechaModificacion(new Date());
                usuario.setUserName(campos[7]);
                usuario.setEmailUsuario(campos[8]);
                usuario.setSexoUsuario(campos[9]);
                usuario.setTelefonoUsuario(campos[10]);
                usuario.setCelularUsuario(campos[11]);
                usuario.setCurpUsuario(campos[12]);
                usuario.RolJPA = new RolJPA();
                usuario.RolJPA.setIdRol(Integer.parseInt(campos[13]));

                usuarioList.add(usuario);
            }

        } catch (Exception ex) {
            return null;
        }
        return usuarioList;
    }

    public List<UsuarioJPA> LecturaArchivoXLSX(File archivo) {
        List<UsuarioJPA> usuarioList = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(archivo); XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
            XSSFSheet workSheet = workbook.getSheetAt(0);
            for (Row row : workSheet) {
                UsuarioJPA usuario = new UsuarioJPA();
                usuario.setNombreUsuario(row.getCell(0).toString());
                usuario.setApellidoPatUsuario(row.getCell(1).toString());
                usuario.setApellidoMatUsuario(row.getCell(2).toString());
                usuario.setPasswordUser(row.getCell(3).toString());

                Date fechaNac = null;
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

                if (row.getCell(4) != null) {
                    switch (row.getCell(4).getCellType()) {
                        case NUMERIC:
                            if (DateUtil.isCellDateFormatted(row.getCell(4))) {
                                fechaNac = row.getCell(4).getDateCellValue();
                            } else {
                                fechaNac = new Date((long) row.getCell(4).getNumericCellValue());
                            }
                            break;
                        case STRING:
                            fechaNac = formatter.parse(row.getCell(4).getStringCellValue());
                            break;
                        default:
                            fechaNac = null;
                            break;
                    }
                }

                usuario.setFechaNacimiento(fechaNac);
                usuario.setStatusUsuario((int) row.getCell(5).getNumericCellValue());
                usuario.setFechaModificacion(new Date());
                usuario.setUserName(row.getCell(7).toString());
                usuario.setEmailUsuario(row.getCell(8).toString());
                usuario.setSexoUsuario(row.getCell(9).toString());
                usuario.setTelefonoUsuario(row.getCell(10).toString());
                usuario.setCelularUsuario(row.getCell(11).toString());
                usuario.setCurpUsuario(row.getCell(12).toString());
                usuario.RolJPA = new RolJPA();
                if (row.getCell(13) != null) {
                    if (row.getCell(13).getCellType() == CellType.NUMERIC) {
                        usuario.RolJPA.setIdRol((int) row.getCell(13).getNumericCellValue());
                    } else {
                        usuario.RolJPA.setIdRol(Integer.parseInt(row.getCell(13).getStringCellValue().trim()));
                    }
                }
                usuarioList.add(usuario);
            }

        } catch (Exception ex) {
            return null;
        }
        return usuarioList;

    }

    public List<ErrorCarga> validarDatosArchivo(List<UsuarioJPA> lista) {

        List<ErrorCarga> errorLista = new ArrayList<>();

        String validarLetras = "^[a-zA-ZÁÉÍÓÚáéíóúñÑ\\s]+$";
        Pattern patternLetras = Pattern.compile(validarLetras);

        String validarCorreo = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern patternCorreo = Pattern.compile(validarCorreo);

        String validarUsername = "^[a-zA-Z0-9_]+$";
        Pattern patternUsername = Pattern.compile(validarUsername);

        //Validaciones de contraseña
        String RegExpMayus = "[A-Z]";
        Pattern patternMayus = Pattern.compile(RegExpMayus);

        String RegExpNumber = "[\\d]";
        Pattern patternNumber = Pattern.compile(RegExpNumber);

        String RegExpEspChar = "[@$?#¡!\\-_]";
        Pattern patternEspChar = Pattern.compile(RegExpEspChar);

        String RegExpMinChar = ".{8,}";
        Pattern patternExpMinChar = Pattern.compile(RegExpMinChar);

        for (int i = 0; i < lista.size(); i++) {

            String nombreUsuario = lista.get(i).getNombreUsuario();
            Matcher matcherNombre = patternLetras.matcher(nombreUsuario);

            String apellidoPatUsuario = lista.get(i).getApellidoPatUsuario();
            Matcher matcherApellidoPat = patternLetras.matcher(apellidoPatUsuario);

            String apellidoMatUsuario = lista.get(i).getApellidoMatUsuario();
            Matcher matcherApellidoMat = patternLetras.matcher(apellidoMatUsuario);

            Date fechaNac = lista.get(i).getFechaNacimiento();
            Date fechaAct = new Date();
            String passwordUsuario = lista.get(i).getPasswordUser();
            Matcher matcherMayus = patternMayus.matcher(passwordUsuario);
            Matcher matcherNumber = patternNumber.matcher(passwordUsuario);
            Matcher matcherEspChar = patternEspChar.matcher(passwordUsuario);
            Matcher matcherMinChar = patternExpMinChar.matcher(passwordUsuario);

            String userName = lista.get(i).getUserName();
            Matcher matcherUsername = patternUsername.matcher(userName);

            String email = lista.get(i).getEmailUsuario();
            Matcher matcherEmail = patternCorreo.matcher(email);

            if (!matcherNombre.matches()) {
                ErrorCarga errorCarga = new ErrorCarga();
                errorCarga.linea = i + 1;
                errorCarga.campo = "Nombre";
                errorCarga.descripcion = "El campo solo permite letras";
                errorLista.add(errorCarga);
            }

            if (!matcherApellidoPat.matches()) {
                ErrorCarga errorCarga = new ErrorCarga();
                errorCarga.linea = i + 1;
                errorCarga.campo = "Apellido Paterno";
                errorCarga.descripcion = "El campo solo permite letras";
                errorLista.add(errorCarga);
            }

            if (!matcherApellidoMat.matches()) {
                ErrorCarga errorCarga = new ErrorCarga();
                errorCarga.linea = i + 1;
                errorCarga.campo = "Apellido Materno";
                errorCarga.descripcion = "El campo solo permite letras";
                errorLista.add(errorCarga);
            }

            if (!matcherMayus.find()) {
                ErrorCarga errorCarga = new ErrorCarga();
                errorCarga.linea = i + 1;
                errorCarga.campo = "Contraseña";
                errorCarga.descripcion = "La contraseña tiene que tener al menos una letra mayúscula";
                errorLista.add(errorCarga);
            }

            if (!matcherNumber.find()) {
                ErrorCarga errorCarga = new ErrorCarga();
                errorCarga.linea = i + 1;
                errorCarga.campo = "Contraseña";
                errorCarga.descripcion = "La contraseña tiene que tener al menos un numero";
                errorLista.add(errorCarga);
            }

            if (!matcherEspChar.find()) {
                ErrorCarga errorCarga = new ErrorCarga();
                errorCarga.linea = i + 1;
                errorCarga.campo = "Contraseña";
                errorCarga.descripcion = "La contraseña tiene que tener al menos un carácter especial";
                errorLista.add(errorCarga);
            }

            if (!matcherMinChar.matches()) {
                ErrorCarga errorCarga = new ErrorCarga();
                errorCarga.linea = i + 1;
                errorCarga.campo = "Contraseña";
                errorCarga.descripcion = "La contraseña tiene que tener al menos 8 carácteres";
                errorLista.add(errorCarga);
            }

            if (fechaNac.after(fechaAct)) {
                ErrorCarga errorCarga = new ErrorCarga();
                errorCarga.linea = i + 1;
                errorCarga.campo = "Fecha de Nacimiento";
                errorCarga.descripcion = "La fecha de nacimiento no es valida";
                errorLista.add(errorCarga);
            }

            if (!matcherUsername.matches()) {
                ErrorCarga errorCarga = new ErrorCarga();
                errorCarga.linea = i + 1;
                errorCarga.campo = "Nombre de usuario";
                errorCarga.descripcion = "El nombre de usuario no es valido";
                errorLista.add(errorCarga);
            }

            if (!matcherEmail.matches()) {
                ErrorCarga errorCarga = new ErrorCarga();
                errorCarga.linea = i + 1;
                errorCarga.campo = "Correo electrónico";
                errorCarga.descripcion = "El correo electrónico no es valido";
                errorLista.add(errorCarga);
            }
        }
        return errorLista;
    }

    public String generateHash(String input) throws NoSuchAlgorithmException {

        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        byte[] encodedHash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

        BigInteger number = new BigInteger(1, encodedHash);

        String hexString = number.toString();

        while (hexString.length() < 64) {
            hexString = "0" + hexString;
        }

        return hexString;
    }

    public String encontrarEnLogTxt(String path, String tkn) {

        try (Stream<String> lines = Files.lines(Paths.get(path))) {

            return lines
                    .filter(linea -> linea.contains("|" + tkn + "|"))
                    .findFirst()
                    .orElse(null);

        } catch (IOException ex) {
            Logger.getLogger(UsuarioRestController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public boolean tokenExpirado(String fecha) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        LocalDateTime fechaArchivo = LocalDateTime.parse(fecha, formatter);
        LocalDateTime fechaActual = LocalDateTime.now();

        Duration diff = Duration.between(fechaArchivo, fechaActual);

        return diff.toMinutes() >= 2;
    }
}
