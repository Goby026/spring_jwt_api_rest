package com.dev.pc.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tbpagosserviciosdeta")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
public class PagosServiciosDeta implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddetalle", nullable = false)
    private Long id;

    @Column(name = "idcabecera", nullable = false)
    private Integer idcabecera;

    @Column(name = "idmes", nullable = false)
    private Integer idmes;

    @Column(name = "detalletasas", nullable = true, length = 150)
    private String detalletasas;

    @Column(name = "idanno", nullable = false)
    private Integer idanno;

    @Column(name = "monto", nullable = false)
    private Double monto;

    @Column(name = "idtbdeudas", nullable = true)
    private Integer iddeuda;

    @ManyToOne()
    @JoinColumn(name = "idclientes")
    private Cliente cliente;

    @ManyToOne()
    @JoinColumn(name = "idpagos")
    private PagosServicio pagosServicio;

    private static final long serialVersionUID = 1L;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @UpdateTimestamp()
    @Column(name = "updated_at")
    private Date updatedAt;

}