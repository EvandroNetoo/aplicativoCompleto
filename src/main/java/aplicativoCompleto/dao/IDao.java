package aplicativoCompleto.dao;

import java.util.List;

import aplicativoCompleto.domain.Produto;

public interface IDao {
    public void inserir(Produto produto);

    public List<Produto> listar();

    void atualizar(String id, Produto produto);

    void remover(String id);
}
