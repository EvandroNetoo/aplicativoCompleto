package aplicativoCompleto.dao;

import java.util.List;

import aplicativoCompleto.domain.Produto;

public interface IDao {
    public void inserir(Produto produto);

    public List<Produto> listar();

    void remover(String id);

    // public List<Produto> listar(String ordenarPor, boolean aoContrario);
}
