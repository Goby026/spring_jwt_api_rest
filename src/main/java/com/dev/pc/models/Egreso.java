package com.dev.pc.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbegresos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
public class Egreso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idegreso;

    private String nregistro;

    private Date fecha;

    private String documento;

    private String nombrerazon;

    private String detalle;

    private double importe;

    private int estado;

    @ManyToOne()
    @JoinColumn(name = "idcaja", nullable = false)
    private Caja caja;

    @ManyToOne()
    @JoinColumn(name = "idtipoegreso", nullable = false)
    private TipoEgreso tipoEgreso;

    @ManyToOne()
    @JoinColumn(name = "idusuario", nullable = false)
    private Usuario usuario;

    @CreationTimestamp()
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @UpdateTimestamp()
    @Column(name = "updated_at")
    private Date updatedAt;
}
