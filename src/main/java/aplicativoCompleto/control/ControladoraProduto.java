package aplicativoCompleto.control;

import java.util.List;

import aplicativoCompleto.dao.IDao;
import aplicativoCompleto.domain.Produto;

public class ControladoraProduto {
    private IDao dao;

    public ControladoraProduto() {
        // this.dao = new MongoDao();
    }

    public void adicionarProduto() {

    }

    public List<Produto> listarProdutos() {
        return dao.listarProdutos();
    }

    public List<Produto> listarProdutos(String ordenarPor, boolean aoContrario) {
        return dao.listarProdutos(ordenarPor, aoContrario);
    }
}
