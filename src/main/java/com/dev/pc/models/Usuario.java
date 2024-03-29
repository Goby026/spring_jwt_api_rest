package com.dev.pc.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private Long id;

    @Column(unique = true, length = 120)
    private String username;

    @Column(length = 60)
    private String password;

    private String email;

    private Boolean enabled;

    @ManyToMany()
    @JoinTable(name = "usuarios_roles",
            joinColumns = @JoinColumn(name = "idusuario"),
            inverseJoinColumns = @JoinColumn(name = "idrole"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"idusuario", "idrole"} )})
    private List<Role> roles;

/*    @JsonIgnore
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Caja> cajas;*/

//    @JsonIgnore
//    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
//    private List<PagosServicio> pagosServicios;

    private static final long serialVersionUID = 1L;

    @CreationTimestamp()
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @UpdateTimestamp()
    @Column(name = "updated_at")
    private Date updatedAt;
}
