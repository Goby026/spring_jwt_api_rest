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
@Table(name = "tb_tributos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
public class Tributo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long iddetatributo;

    private String usuario;

    private String dettupa;

    private String detrequisito;

    private double subtotal;

    private int correlativo;

    @ManyToOne()
    @JoinColumn(name = "idcliente")
    private Cliente cliente;

    @ManyToOne()
    @JoinColumn(name = "idusuario")
    private Usuario user;

    @JsonIgnore
    @OneToMany(mappedBy = "tributo", fetch = FetchType.LAZY)
    private List<Tributodetalle> tributodetalles;

    private static final long serialVersionUID = 1L;

    @CreationTimestamp()
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @UpdateTimestamp()
    @Column(name = "updated_at")
    private Date updatedAt;
}
