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
@Table(name = "tbclientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
public class Cliente implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idclientes;

    private String dni;
    private int num_instalaciones;
    private String nombres;

    @Column(name = "ape_paterno")
    private String apepaterno;

    @Column(name = "ape_materno")
    private String apematerno;

    private String direccion;
    private int num_familias;
    private String exo_p;
    private String exo_a;
    private String exo_f;

    @Column(nullable = false)
    private Date fec_nac;

    @Column(nullable = false)
    private Date fec_ing;

    @Column(nullable = false)
    private String baja;

    @Column(name = "fec_baja", nullable = true)
    private Date fec_baja;

    @ManyToOne()
    @JoinColumn(name = "idtipocliente")
    private TipoCliente tipoCliente;

    @ManyToOne()
    @JoinColumn(name = "idtbzonas")
    private Zona zona;

    @JsonIgnore
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<Tributo> tributos;

    @JsonIgnore
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<Deuda> deudas;

    @JsonIgnore
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<Costo> costos;

    @JsonIgnore
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<PagosServicio> pagosServicios;

//    relacionar con detalle de pago (PagosServiciosDeta)
    @JsonIgnore
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<PagosServiciosDeta> pagosServiciosDetas;

    @JsonIgnore
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<Tributodetalle> tributodetalles;

    @JsonIgnore
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<Voucher> vouchers;

    private int estado;

    private static final long serialVersionUID = 1L;

    @CreationTimestamp
    private Date created_at;

    @UpdateTimestamp()
    private Date updated_at;
}
