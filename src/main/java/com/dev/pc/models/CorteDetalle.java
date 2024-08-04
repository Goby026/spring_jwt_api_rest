package com.dev.pc.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tbcortedetalle")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
public class CorteDetalle implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcortedetalle")
    private Long idcortedetalle;

    @ManyToOne()
    @JoinColumn(name = "idclientes")
    private Cliente cliente;

    private String codigo;


    //    TIPO DE DEUDA
//    @ManyToOne()
//    @JoinColumn(name = "iddeudadescripcion")
//    private DeudaDescripcion deudaDescripcion;
//
//    @ManyToOne()
//    @JoinColumn(name = "iddeuda_estado")
//    private DeudaEstado deudaEstado;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date periodo;

    private double total;
    private double saldo;
    private double dcto;
    private Date vencimiento;
    private int estado;

//    @Column(columnDefinition = "TEXT")
//    private String observacion;

    @ManyToOne()
    @JoinColumn(name = "idcorte", nullable = false)
    private Corte corte;

    private static final long serialVersionUID = 1L;

    @CreationTimestamp()
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @UpdateTimestamp()
    @Column(name = "updated_at")
    private Date updatedAt;
}
