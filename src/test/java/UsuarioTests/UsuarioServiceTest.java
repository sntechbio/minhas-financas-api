package UsuarioTests;

import com.sntech.minhasfinancas.Usuario.model.Usuario;
import com.sntech.minhasfinancas.Usuario.repository.UsuarioRepository;
import com.sntech.minhasfinancas.Usuario.service.UsuarioService;
import com.sntech.minhasfinancas.Usuario.service.impl.UsuarioServiceImpl;
import com.sntech.minhasfinancas.exception.ErroAutenticacao;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

//@SpringBootTest(classes = MinhasfinancasApplication.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

    UsuarioService usuarioService;

    @MockBean
    UsuarioRepository usuarioRepository;

    @Before
    public void setUp() {
        usuarioService = new UsuarioServiceImpl(usuarioRepository);
    }
    @Test(expected = Test.None.class)
    public void deveAutenticarUmUsuarioComSucesso() {
        //cenario
        String email = "email@email.com";
        String senha = "senha";
        Usuario usuario = Usuario.builder().email(email).senha(senha).id(1L).build();
        Mockito.when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));
        //acao
        Usuario result = usuarioService.autenticar(email, senha);
        //verificacao
        Assertions.assertThat(result).isNotNull();
    }

    @Test(expected = ErroAutenticacao.class)
    public void deveLancarErroQuandoNaoEncontrarUsuarioCadastradoComOEmailInformado() {
        //cenario
        Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        //acao
        usuarioService.autenticar("email@email.com", "senha");
    }

    @Test(expected = Test.None.class)
    public void deveValidarEmail() {
        Mockito.when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(false);
        //acao
        usuarioService.validarEmail("email@email.com");
    }

    @Test(expected = ErroAutenticacao.class)
    public void deveLancarErroQuandoNaoEncontrarUsuarioCadastradoComOEmailInformad() {
        //cenario
        Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        //acao
        usuarioService.autenticar("email@email.com", "senha");
    }

    @Test(expected = ErroAutenticacao.class)
    public void deveLancarErroQuandoSenhaNaoBater() {
        //cenario
        String senha = "senha";
        Usuario usuario = Usuario.builder().email("email@email.com").senha(senha).build();
        Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));

        //acao
        usuarioService.autenticar("email@email.com", "123");
    }

}
