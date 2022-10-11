package UsuarioTests;

import com.sntech.minhasfinancas.MinhasfinancasApplication;
import com.sntech.minhasfinancas.Usuario.model.Usuario;
import com.sntech.minhasfinancas.Usuario.repository.UsuarioRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = MinhasfinancasApplication.class)
@RunWith(SpringRunner.class)
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void deveVerificarAExistenciaDeUmEmail() {

        // cenário
        Usuario usuario = Usuario.builder().nome("usuario").email("usuario@email.com").build();
        usuarioRepository.save(usuario);

        // ação/ execução
        boolean result = usuarioRepository.existsByEmail("usuario@email.com");

        //verificação
        Assertions.assertThat(result).isTrue();

    }

}
