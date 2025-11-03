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

    private Map<String, Object> produtoToMap(Produto produto) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", produto.getId());
        map.put("nome", produto.getNome());
        map.put("preco", produto.getPreco());
        map.put("estoque", produto.getEstoque());
        return map;
    }

    private Produto mapToProduto(Map<String, Object> dados) {
        return new Produto((String) dados.get("nome"), (int) dados.get("estoque"), (double) dados.get("preco"));
    }

    public void adicionarProduto(Map<String, Object> dados) {
        dao.inserir(mapToProduto(dados));
    }

    public List<Map<String, Object>> listarProdutos() {
        return List.of(
                new Produto("gfgrgffdsq", "Leite", 1, 5),
                new Produto("erg2r13gd", "Toddy", 12, 15),
                new Produto("12fdasf3", "Biscoito", 100, 4)).stream().map(this::produtoToMap).toList();
    }

    public List<Map<String, Object>> listarProdutos(String ordenarPor, boolean aoContrario) {
        // return dao.listarProdutos(ordenarPor, aoContrario);
        return List.of(new Produto("a", "b", 1, 2)).stream().map(this::produtoToMap).toList();
    }
}
