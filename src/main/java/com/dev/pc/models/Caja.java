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
@Table(name = "tbcaja")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
public class Caja implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcaja")
    private Long idcaja;

    private String ncaja;
    private Double efectivoape;
    private Date fapertura;
    private Date fcierre;
    private Double total; //monto de sistema
    private Double totalefectivo; //monto contabilizado
    private Double balance; //saldo positivo o negativo
    private String obs;
    private int esta;

    @ManyToOne()
    @JoinColumn(name = "idusuario", nullable = false)
    private Usuario usuario;

    @JsonIgnore
    @OneToMany(mappedBy = "caja", fetch = FetchType.LAZY)
    private List<PagosServicio> pagosServicios;

    private static final long serialVersionUID = 1L;

    @CreationTimestamp()
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @UpdateTimestamp()
    @Column(name = "updated_at")
    private Date updatedAt;
}
