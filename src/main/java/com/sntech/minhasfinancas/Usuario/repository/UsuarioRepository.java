package com.sntech.minhasfinancas.Usuario.repository;

import com.sntech.minhasfinancas.Usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

//    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

}
