package aplicativoCompleto.control;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import aplicativoCompleto.dao.IDao;
import aplicativoCompleto.dao.MongoDao;
import aplicativoCompleto.domain.Produto;

public class ControladoraProduto {
    private IDao dao;

    public ControladoraProduto() {
        this.dao = new MongoDao();
    }

    public LinkedHashMap<String, String> camposListados() {
        LinkedHashMap<String, String> campos = new LinkedHashMap<>();
        campos.put("ID", "id");
        campos.put("Nome", "nome");
        campos.put("Preço", "preco");
        campos.put("Estoque", "estoque");
        return campos;
    };

    public LinkedHashMap<String, String> camposAdicionais() {
        LinkedHashMap<String, String> campos = new LinkedHashMap<>();
        campos.put("Nome", "nome");
        campos.put("Preço", "preco");
        campos.put("Estoque", "estoque");
        return campos;
    };

    public List<String> camposOrdenaveis() {
        return List.of("nome", "preco", "estoque");
    };

    private Map<String, Object> produtoToMap(Produto produto) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", produto.getId());
        map.put("nome", produto.getNome());
        map.put("preco", produto.getPreco());
        map.put("estoque", produto.getEstoque());
        return map;
    }

    private Produto mapToProduto(Map<String, String> dados) {
        return new Produto(dados.get("nome"), Integer.valueOf(dados.get("estoque")),
                Double.valueOf(dados.get("preco")));
    }

    public Map<String, Object> adicionar(Map<String, String> dados) {
        Produto produto = mapToProduto(dados);
        dao.inserir(produto);
        return produtoToMap(produto);
    }

    public List<Map<String, Object>> listar() {
        return dao.listar().stream().map(this::produtoToMap).toList();
    }

    public void atualizar(String id, Map<String, String> dados) {
        Produto produto = mapToProduto(dados);
        dao.atualizar(id, produto);
    }

    public void remover(String id) {
        dao.remover(id);
    }
}
