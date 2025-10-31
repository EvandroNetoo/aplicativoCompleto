package aplicativoCompleto.dao;

import java.util.List;

import aplicativoCompleto.domain.Produto;

public interface IDao {
    public List<Produto> listarProdutos();

    public List<Produto> listarProdutos(String ordenarPor, boolean aoContrario);
}
