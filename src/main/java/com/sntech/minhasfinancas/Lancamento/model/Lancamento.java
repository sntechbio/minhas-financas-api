package com.sntech.minhasfinancas.Lancamento.model;

import com.sntech.minhasfinancas.Lancamento.enums.StatusLancamento;
import com.sntech.minhasfinancas.Lancamento.enums.TipoLancamento;
import com.sntech.minhasfinancas.Usuario.model.Usuario;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
@Table(name = "lancamento", schema = "financas")
@NoArgsConstructor
public class Lancamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "mes")
    private Integer mes;

    @Column(name = "ano")
    private Integer ano;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(name = "valor")
    private BigDecimal valor;

    @Column(name = "data_cadastro")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dataCadastro;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoLancamento tipo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusLancamento status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Lancamento that = (Lancamento) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
