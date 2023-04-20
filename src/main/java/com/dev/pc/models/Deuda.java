package com.dev.pc.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import java.util.List;

@Entity
@Table(name = "tbdeudas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
public class Deuda implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idtbdeudas;

    @ManyToOne()
    @JoinColumn(name = "idclientes")
    private Cliente cliente;

    private String codigo;

    @ManyToOne()
    @JoinColumn(name = "iddeudadescripcion")
    private DeudaDescripcion deudaDescripcion;

    @ManyToOne()
    @JoinColumn(name = "iddeuda_estado")
    private DeudaEstado deudaEstado;

    @JsonIgnore
    @OneToMany(mappedBy = "deuda", fetch = FetchType.LAZY)
    private List<Condonacion> condonaciones;

    private Date periodo;
    private double total;
    private double saldo;
    private Date vencimiento;
    private int estado;

    private static final long serialVersionUID = 1L;

    @CreationTimestamp()
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @UpdateTimestamp()
    @Column(name = "updated_at")
    private Date updatedAt;
}