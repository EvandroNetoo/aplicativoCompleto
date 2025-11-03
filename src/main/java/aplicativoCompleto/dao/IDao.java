package aplicativoCompleto.dao;

import java.util.List;

import aplicativoCompleto.domain.Produto;

public interface IDao {
    public String inserir(Produto produto);

    public List<Produto> listar();

    // public List<Produto> listar(String ordenarPor, boolean aoContrario);
}
