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
@Table(name = "tbcosto")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
public class Costo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codcosto;

    @ManyToOne()
    @JoinColumn(name = "codservi")
    private Servicio servicio;

    @ManyToOne()
    @JoinColumn(name = "codzona")
    private Zona zona;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codcliente")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Cliente cliente;

    private String mza;
    private String lote;
    private String nropre;
    private Date fecha_registro;
    private Date fecha_inicio_servicio;
    private Date fcobranza;
    private int esta;
    private String suministro;
    private Double kw;
    private boolean exof;
    private boolean exoa;
    private boolean exop;
    private String tpousuario;
    private String tpovivienda;
    private int nrointegrante;
    private String referencia_dom;

//    @JsonIgnore
//    @OneToMany(mappedBy = "costo", fetch = FetchType.LAZY)
//    private List<Costootroservicio> costootroservicios;

    @JsonIgnore
    @OneToMany(mappedBy = "costo", fetch = FetchType.LAZY)
    private List<PagosServicio> pagosServicios;

    private static final long serialVersionUID = 1L;

    @CreationTimestamp()
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @UpdateTimestamp()
    @Column(name = "updated_at")
    private Date updatedAt;
}


