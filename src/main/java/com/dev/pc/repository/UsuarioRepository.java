package com.dev.pc.repository;

import com.dev.pc.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    public Usuario findByUsername(String username);

//    public Usuario findByUsernameAndEmail(String username, String email);

//    @Query("select u from Usuario u where u.username=?1")
//    public Usuario buscarPorUsername(String username);
}
