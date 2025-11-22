package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.JPA;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Usuario")
public class UsuarioJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private int IdUsuario;

    @NotNull(message = "Campo no puede ser nulo")
    @NotBlank(message = "Campo debe contener datos")
    @Size(min = 2, max = 17, message = "Entre 2 y 17")
    @Column(name = "nombre", nullable = false, length = 17)
    private String NombreUsuario;

    @NotNull(message = "El apellido paterno no puede ser nulo")
    @NotBlank(message = "El apellido paterno es obligatorio")
    @Size(min = 2, max = 20, message = "El apellido paterno debe tener entre 2 y 20 caracteres")
    @Column(name = "apellidopaterno", nullable = false, length = 20)
    private String ApellidoPatUsuario;

    @Size(max = 20, message = "El apellido materno no debe exceder 20 caracteres")
    @Column(name = "apellidomaterno", length = 20)
    private String ApellidoMatUsuario;

    @NotNull(message = "La contraseña no puede ser nula")
    @NotBlank(message = "La contraseña es obligatoria")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+=<>?{}\\[\\]-]).{8,}$",
            message = "La contraseña debe tener al menos una mayúscula, un número, un carácter especial, y mínimo 8 caracteres"
    )
    @Column(name = "password", nullable = false)
    private String PasswordUser;

    @NotNull(message = "La fecha de nacimiento no puede ser nula")
    @Past(message = "La fecha de nacimiento debe ser anterior a la fecha actual")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fechanacimiento", nullable = false)
    private Date FechaNacimiento;

    @Column(name = "status_usuario", length = 10)
    private String StatusUsuario;

    @Column(name = "fecha_modificacion")
    private Date FechaModificacion;

    @Lob
    @Column(name = "foto_usuario", length = 255)
    private byte[] FotoUsuario;

    @NotNull(message = "El nombre de usuario no puede ser nulo")
    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]+$", message = "El nombre de usuario no es valido")
    @Column(name = "username", nullable = false, unique = true)
    private String UserName;

    @NotNull(message = "El correo no puede ser nulo")
    @NotBlank(message = "El correo es obligatorio")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "El correo electrónico ingresado tiene un formato incorrecto")
    @Column(name = "email", nullable = false, unique = true)
    private String EmailUsuario;

    @NotNull(message = "El sexo no puede ser nulo")
    @NotBlank(message = "El sexo no puede estar vacio")
    @Size(max = 1, message = "El sexo no puede tener más de un carácter")
    @Column(name = "sexo", length = 1, nullable = false)
    private String SexoUsuario;

    @NotNull(message = "El teléfono no puede ser nulo")
    @NotBlank(message = "El teléfono no puede estar vacio")
    @Size(max = 20, message = "El teléfono ha sobrepasado los caracteres")
    @Column(name = "telefono", length = 20, nullable = false)
    private String TelefonoUsuario;

    @Size(max = 20, message = "El celular ha sobrepasado el número de caracteres")
    @Column(name = "celular", length = 20)
    private String CelularUsuario;

    @Size(max = 50, message = "El CURP ha sobrepasado el número de caracteres")
    @Column(name = "curp", length = 50)
    private String CurpUsuario;

    @ManyToOne()
    @JoinColumn(name = "idrol", nullable = false)
    public RolJPA RolJPA;

    @OneToMany(mappedBy = "UsuarioJPA", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonProperty("direcciones")
    public List<DireccionJPA> DireccionesJPA = new ArrayList<>();

    public UsuarioJPA() {
    }

    public UsuarioJPA(String nombreUsuario, String apellidoPatUsuario, String apellidoMatUsuario,
            String passwordUser, Date fechaNacimiento, String statusUsuario,
            Date fechaModificacion, byte[] fotoUsuario, String userName,
            String emailUsuario, String sexoUsuario, String telefonoUsuario,
            String celularUsuario, String curpUsuario) {
        this.NombreUsuario = nombreUsuario;
        this.ApellidoPatUsuario = apellidoPatUsuario;
        this.ApellidoMatUsuario = apellidoMatUsuario;
        this.PasswordUser = passwordUser;
        this.FechaNacimiento = fechaNacimiento;
        this.StatusUsuario = statusUsuario;
        this.FechaModificacion = fechaModificacion;
        this.FotoUsuario = fotoUsuario;
        this.UserName = userName;
        this.EmailUsuario = emailUsuario;
        this.SexoUsuario = sexoUsuario;
        this.TelefonoUsuario = telefonoUsuario;
        this.CelularUsuario = celularUsuario;
        this.CurpUsuario = curpUsuario;
    }

    public void setIdUsuario(int IdUsuario) {
        this.IdUsuario = IdUsuario;
    }

    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setNombreUsuario(String NombreUsuario) {
        this.NombreUsuario = NombreUsuario;
    }

    public String getNombreUsuario() {
        return NombreUsuario;
    }

    public void setApellidoPatUsuario(String ApellidoPatUsuario) {
        this.ApellidoPatUsuario = ApellidoPatUsuario;
    }

    public String getApellidoPatUsuario() {
        return ApellidoPatUsuario;
    }

    public void setApellidoMatUsuario(String ApellidoMatUsuario) {
        this.ApellidoMatUsuario = ApellidoMatUsuario;
    }

    public String getApellidoMatUsuario() {
        return ApellidoMatUsuario;
    }

    public void setPasswordUser(String PasswordUser) {
        this.PasswordUser = PasswordUser;
    }

    public String getPasswordUser() {
        return PasswordUser;
    }

    public void setFechaNacimiento(Date FechaNacimiento) {
        this.FechaNacimiento = FechaNacimiento;
    }

    public Date getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setStatusUsuario(String StatusUsuario) {
        this.StatusUsuario = StatusUsuario;
    }

    public String getStatusUsuario() {
        return StatusUsuario;
    }

    public void setFechaModificacion(Date FechaModificacion) {
        this.FechaModificacion = FechaModificacion;
    }

    public Date getFechaModificacion() {
        return FechaModificacion;
    }

    public void setFotoUsuario(byte[] FotoUsuario) {
        this.FotoUsuario = FotoUsuario;
    }

    public byte[] getFotoUsuario() {
        return FotoUsuario;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setEmailUsuario(String EmailUsuario) {
        this.EmailUsuario = EmailUsuario;
    }

    public String getEmailUsuario() {
        return EmailUsuario;
    }

    public void setSexoUsuario(String SexoUsuario) {
        this.SexoUsuario = SexoUsuario;
    }

    public String getSexoUsuario() {
        return SexoUsuario;
    }

    public void setTelefonoUsuario(String TelefonoUsuario) {
        this.TelefonoUsuario = TelefonoUsuario;
    }

    public String getTelefonoUsuario() {
        return TelefonoUsuario;
    }

    public void setCelularUsuario(String CelularUsuario) {
        this.CelularUsuario = CelularUsuario;
    }

    public String getCelularUsuario() {
        return CelularUsuario;
    }

    public void setCurpUsuario(String CurpUsuario) {
        this.CurpUsuario = CurpUsuario;
    }

    public String getCurpUsuario() {
        return CurpUsuario;
    }
}
