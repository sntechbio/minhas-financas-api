package LancamentoRepository;

import com.sntech.minhasfinancas.Lancamento.model.Lancamento;
import com.sntech.minhasfinancas.Lancamento.repository.LancamentoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class LancamentoRepositoryTest {

    @MockBean
    LancamentoRepository repository;

    @MockBean
    TestEntityManager entityManager;

    @Test
    public void deveSalvarUmLancamento() {
        Lancamento lancamento = new Lancamento();
        lancamento.setId(1L);
        repository.save(lancamento);
    }

}
