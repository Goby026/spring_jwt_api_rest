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
@Table(name = "tbingresos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
public class Ingreso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idingreso;

    private String inicio;

    private String fin;

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
