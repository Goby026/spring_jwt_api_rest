package com.dev.pc.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "tbvouchers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
public class Voucher implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idvoucher;

    @ManyToOne()
    @JoinColumn(name = "idcliente", nullable = false)
    private Cliente cliente;

    @Column(name = "montoapagar", nullable = false)
    private Double montoapagar;

    @Column(name = "montotasas", nullable = true)
    private Double montotasas;

    @Column(name = "montodescuento", nullable = true)
    private Double montodescuento;

    @Column(name = "montopagado", nullable = false)
    private Double montopagado;

    @Column(name = "fecha", nullable = true)
    private Date fecha;

    @Column(name = "usua_crea", length = 200)
    private String usuaCrea;

    @Column(name = "imagen", nullable = true)
    private String imagen;

    @Column(name = "correlativo", nullable = true)
    private Integer correlativo;

    @ManyToOne()
    @JoinColumn(name = "idpagoestado", nullable = false)
    private PagoServicioEstado pagoServicioEstado;

    @ManyToOne()
    @JoinColumn(name = "idtipopagosservicio")
    private TipoPagoServicios tipoPagoServicios;

    @JsonIgnore
    @OneToMany(mappedBy = "voucher", fetch = FetchType.LAZY)
    private List<VoucherDetalle> voucherDetalles;

    private static final long serialVersionUID = 1L;

    @CreationTimestamp
    private Date created_at;

    @UpdateTimestamp()
    private Date updated_at;
}
