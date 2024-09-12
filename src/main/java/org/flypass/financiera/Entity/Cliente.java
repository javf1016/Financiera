package org.flypass.financiera.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoIdentificacion;
    private String numeroIdentificacion;

    @Size(min = 2, message = "El nombre debe tener al menos 2 caracteres")
    private String nombres;

    @Size(min = 2, message = "El apellido debe tener al menos 2 caracteres")
    private String apellidos;

    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Correo electrónico inválido")
    private String email;

    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    private LocalDate fechaNacimiento;

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Producto> productos;

    @PrePersist
    private void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
    }

    @PreUpdate
    private void preUpdate() {
        this.fechaModificacion = LocalDateTime.now();
    }
}
