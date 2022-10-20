package UsuarioTests;

import com.sntech.minhasfinancas.MinhasfinancasApplication;
import com.sntech.minhasfinancas.Usuario.model.Usuario;
import com.sntech.minhasfinancas.Usuario.repository.UsuarioRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@SpringBootTest(classes = MinhasfinancasApplication.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
//@DataJpaTest // dar um rollback ao final de cada teste de método
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //
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

    @Test
    public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComEmail() {

        // cenário
        usuarioRepository.deleteAll();
        // ação
        boolean result = usuarioRepository.existsByEmail("usuario@email.com");
        // verificacao
        Assertions.assertThat(result).isFalse();

    }

    @Test
    public void devePersistirUmUsuarioNaBaseDeDados() {
        //cenário
        Usuario usuario = criarUsuario();
        // acao
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        //verificacao
        Assertions.assertThat(usuarioSalvo.getId()).isNotNull();

    }

//    @Test
//    public void deveBuscarUmUsuarioPorEmail() {
//        //cenario
//        Usuario usuario = criarUsuario();
//        usuarioRepository.save(usuario);
//
//        //verificacao
//        Optional<Usuario> result = usuarioRepository.findByEmail("usuario@email.com");
//        Assertions.assertThat(result.isPresent()).isTrue();
//
//    }

    public static Usuario criarUsuario() {
        return Usuario
                .builder()
                .nome("usuario")
                .email("usuario@email.com")
                .senha("senha")
                .build();
    }

}
