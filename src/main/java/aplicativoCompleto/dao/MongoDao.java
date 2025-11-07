package aplicativoCompleto.dao;

import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;

import aplicativoCompleto.domain.Produto;

// 0YXChaLo24NUinpy
public class MongoDao implements IDao {
    private static final String URI = "mongodb+srv://evandrorsneto_db_user:0YXChaLo24NUinpy@aplicativocompleto.tc475iw.mongodb.net/?appName=aplicativoCompleto";
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public MongoDao() {
        MongoClient mongoClient = MongoClients.create(URI);
        this.database = mongoClient.getDatabase("aplicativo_completo");

        this.collection = database.getCollection("items");
    }

    private Document toDocument(Produto produto) {
        return new Document("_type", Produto.class.getSimpleName())
                .append("nome", produto.getNome())
                .append("estoque", produto.getEstoque())
                .append("preco", produto.getPreco());
    }

    @Override
    public void inserir(Produto produto) {
        Document doc = toDocument(produto);
        InsertOneResult result = collection.insertOne(doc);
        produto.setId(result.getInsertedId().asObjectId().getValue().toHexString());
    }

    @Override
    public List<Produto> listar() {
        List<Produto> produtos = new LinkedList<>();
        for (Document doc : collection.find()) {
            Produto produto = new Produto(
                    doc.getObjectId("_id").toString(),
                    doc.getString("nome"),
                    doc.getInteger("estoque"),
                    doc.getDouble("preco"));
            produtos.add(produto);
        }
        return produtos;
    }

    @Override
    public void remover(String id) {
        collection.deleteOne(new Document("_id", new ObjectId(id)));
    }

    // public static void main(String[] args) {
    // MongoDao dao = new MongoDao();
    // Produto produto = new Produto("Produto de Teste", 10, 99.99);
    // String id = dao.inserir(produto);
    // System.out.println("Produto inserido com ID: " + id);
    // }
}
