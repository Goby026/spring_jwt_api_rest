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
@Table(name = "tbpagosservicios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
public class PagosServicio implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpagos", nullable = false)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "idcosto")
    private Costo costo;

    @ManyToOne()
    @JoinColumn(name = "idcliente")
    private Cliente cliente;

    @Column(name = "montoapagar", nullable = false)
    private Double montoapagar;

    @Column(name = "montotasas", nullable = false)
    private Double montotasas;

    @Column(name = "montodescuento", nullable = false)
    private Double montodescuento;

    @Column(name = "montopagado", nullable = false)
    private Double montopagado;

    @Column(name = "fecha", nullable = false)
    private Date fecha;

    @ManyToOne()
    @JoinColumn(name = "idusuario", nullable = false)
    private Usuario usuario;

    @Column(name = "esta", nullable = false)
    private Integer esta;

    @Column(name = "correlativo")
    private Integer correlativo;

    @JsonIgnore
    @OneToMany(mappedBy = "pagosServicio", fetch = FetchType.LAZY)
    private List<VoucherDetalle> voucherDetalles;

    @JsonIgnore
    @OneToMany(mappedBy = "pagosServicio", fetch = FetchType.LAZY)
    private List<PagosServiciosDeta> pagosServiciosDeta;

    @ManyToOne()
    @JoinColumn(name = "idcaja")
    private Caja caja;

    @ManyToOne()
    @JoinColumn(name = "idpagoestado", nullable = false)
    private PagoServicioEstado pagoServicioEstado;

    @ManyToOne()
    @JoinColumn(name = "idtipopagosservicio")
    private TipoPagoServicios tipoPagoServicios;

    private static final long serialVersionUID = 1L;

    @CreationTimestamp()
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @UpdateTimestamp()
    @Column(name = "updated_at")
    private Date updatedAt;

}