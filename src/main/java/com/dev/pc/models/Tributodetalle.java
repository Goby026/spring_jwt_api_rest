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
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tb_tributosdetalle")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
public class Tributodetalle implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtributodetalle", nullable = false)
    private Long id;

    @Column(name = "datosclientes", nullable = false, length = 250)
    private String datosclientes;

    @Column(name = "direccion", nullable = false, length = 150)
    private String direccion;

    @Column(name = "monto", nullable = false)
    private Double monto;

    @Column(name = "usua_crea", nullable = false, length = 20)
    private String usuaCrea;

    @Column(name = "fecha", nullable = false)
    private Date fecha;

    @Column(name = "esta", nullable = false)
    private Integer esta;

    @Column(name = "correlativo", nullable = false)
    private Integer correlativo;

    @Column(name = "idanno", nullable = false)
    private Integer idanno;

    @ManyToOne()
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne()
    @JoinColumn(name = "id_requisito")
    private Requisitos requisito;

    @ManyToOne()
    @JoinColumn(name = "id_tributo")
    private Tributo tributo;

    private static final long serialVersionUID = 1L;

    @CreationTimestamp
    private Date created_at;

    @UpdateTimestamp()
    private Date updated_at;

}