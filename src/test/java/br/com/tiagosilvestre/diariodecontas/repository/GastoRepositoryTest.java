package br.com.tiagosilvestre.diariodecontas.repository;

import br.com.tiagosilvestre.diariodecontas.model.Categoria;
import br.com.tiagosilvestre.diariodecontas.model.Gasto;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
class GastoRepositoryTest {

    @Autowired
    private GastoRepository gastoRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Should find gasto by category id")
    void findGastoByCategoriaIdSuccess() {

        Categoria categoria = new Categoria();
        categoria.setNome("Ferramentas");
        entityManager.persist(categoria);

        Gasto gasto = new Gasto();
        gasto.setCategoria(categoria);
        gasto.setDescricao("Martelo");
        gasto.setValor(BigDecimal.valueOf(74));

        entityManager.persist(gasto);

        entityManager.flush();
        entityManager.clear();

        List<Gasto> resultado =
                gastoRepository.findByCategoriaId(categoria.getId());

        assertThat(resultado)
                .hasSize(1);

        assertThat(resultado.getFirst().getDescricao())
                .isEqualTo("Martelo");

    }


    @Test
    void shouldReturnEmptyListWhenCategoryHasNoExpenses() {

        Categoria categoria = new Categoria();
        categoria.setNome("Ferramentas");
        entityManager.persist(categoria);

        entityManager.flush();

        List<Gasto> resultado =
                gastoRepository.findByCategoriaId(categoria.getId());

        assertThat(resultado).isEmpty();
    }

}