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
@Table(name = "tbtipoegreso")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
public class TipoEgreso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idtipoegreso;

    private String descripcion;

    private int estado;

    @CreationTimestamp()
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @UpdateTimestamp()
    @Column(name = "updated_at")
    private Date updatedAt;
}
