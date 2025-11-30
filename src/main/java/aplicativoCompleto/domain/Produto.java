package aplicativoCompleto.domain;

import aplicativoCompleto.utils.Adicionar;
import aplicativoCompleto.utils.Id;
import aplicativoCompleto.utils.Listar;
import aplicativoCompleto.utils.ValidadorMinLen2;
import aplicativoCompleto.utils.ValidadorNumeroPositivo;

public class Produto {
    @Id
    // @Listar(label = "ID")
    String id;

    @Listar(label = "Nome", ordenavel = true)
    @Adicionar(label = "Nome", validadores = { ValidadorMinLen2.class })
    String nome;

    @Listar(label = "Estoque", ordenavel = true)
    @Adicionar(label = "Estoque")
    int estoque;

    @Adicionar(label = "Preço", validadores = { ValidadorNumeroPositivo.class })
    double preco;

    @Listar(label = "Preço", ordenavel = true)
    String getPrecoFormatado() {
        return String.format("R$ %.2f", preco).replace(".", ",");
    }
}