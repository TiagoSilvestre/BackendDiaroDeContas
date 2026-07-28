package br.com.tiagosilvestre.diariodecontas.service;

import br.com.tiagosilvestre.diariodecontas.dto.GastoRequest;
import br.com.tiagosilvestre.diariodecontas.dto.GastoResponse;
import br.com.tiagosilvestre.diariodecontas.exception.CategoriaNaoEncontradaException;
import br.com.tiagosilvestre.diariodecontas.model.Categoria;
import br.com.tiagosilvestre.diariodecontas.model.Gasto;
import br.com.tiagosilvestre.diariodecontas.repository.CategoriaRepository;
import br.com.tiagosilvestre.diariodecontas.repository.GastoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GastoServiceTest {

    @Mock
    private GastoRepository gastoRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private GastoService gastoService;

    @Test
    @DisplayName("Should save gasto")
    void createGasto() {
        // Arrange
        Categoria categoria = new Categoria();
        categoria.setNome("Ferramentas");
        categoria.setId(1L);

        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));

        Gasto gasto = new Gasto();
        gasto.setCategoria(categoria);
        gasto.setDescricao("Serrote");
        gasto.setValor(BigDecimal.valueOf(74));

        when(gastoRepository.save(any())).thenReturn(gasto);

        GastoRequest gastoDto = new GastoRequest(
                BigDecimal.valueOf(74), 1L, "Serrote");

        // Act
        GastoResponse response = gastoService.cadastrar(gastoDto);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.valor()).isEqualByComparingTo("74");
        assertThat(response.descricao()).isEqualTo("Serrote");
        assertThat(response.categoriaId()).isEqualTo(1L);

        verify(categoriaRepository, times(1)).findById(any());
        verify(gastoRepository, times(1)).save(any());


        ArgumentCaptor<Gasto> captor = ArgumentCaptor.forClass(Gasto.class);

        verify(gastoRepository).save(captor.capture());

        Gasto gastoCapturado = captor.getValue();
        assertThat(gastoCapturado.getDescricao()).isEqualTo("Serrote");
        assertThat(gastoCapturado.getValor()).isEqualByComparingTo("74");
        assertThat(gastoCapturado.getCategoria()).isEqualTo(categoria);
    }


    @Test
    @DisplayName("Should not found categoria when save gasto")
    void categoriaNotFoundWhenCreateGasto() {
        // Arrange
        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());

        Exception thrown = Assertions.assertThrows(CategoriaNaoEncontradaException.class, () -> {
            GastoRequest gastoDto = new GastoRequest(
                    BigDecimal.valueOf(74), 1L, "Serrote");

            // Act
            GastoResponse response = gastoService.cadastrar(gastoDto);
        });

        Assertions.assertEquals("Categoria não encontrada com id: "+ 1L, thrown.getMessage());
    }
}
