package aplicativoCompleto.domain;

public class Produto {
    @Id
    @Listar(label = "ID")
    String id;

    @Listar(label = "Nome", ordenavel = true)
    @Adicionar(label = "Nome")
    String nome;

    @Listar(label = "Estoque", ordenavel = true)
    @Adicionar(label = "Estoque")
    int estoque;

    @Adicionar(label = "Preço")
    double preco;

    @Listar(label = "Preço", ordenavel = true)
    String getPrecoFormatado() {
        return String.format("R$ %.2f", preco).replace(".", ",");
    }
}