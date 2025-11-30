package aplicativoCompleto.domain;

import aplicativoCompleto.utils.Adicionar;
import aplicativoCompleto.utils.Id;
import aplicativoCompleto.utils.Listar;

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

    @Adicionar(label = "Preço")
    double preco;    
    
    @Listar(label = "Preço", ordenavel = true)
    String precoFormatado() {
        return String.format("R$ %.2f", preco);
    }
}
