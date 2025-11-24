package aplicativoCompleto.domain;

public class Veiculo {
    @Id
    @Listar(label = "ID")
    String id;

    @Listar(label = "Modelo", ordenavel = true)
    @Adicionar(label = "Modelo")
    String modelo;

    @Listar(label = "Ano", ordenavel = true)
    @Adicionar(label = "Ano")
    int ano;

    @Listar(label = "Preço", ordenavel = true)
    @Adicionar(label = "Preço")
    double preco;
}
