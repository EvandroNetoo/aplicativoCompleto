package aplicativoCompleto.dao;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import aplicativoCompleto.domain.Produto;

// 0YXChaLo24NUinpy
public class MongoDao implements IDao {
    private static final String URI = "mongodb+srv://evandrorsneto_db_user:0YXChaLo24NUinpy@aplicativocompleto.tc475iw.mongodb.net/?appName=aplicativoCompleto";
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public MongoDao() {
        try (MongoClient mongoClient = MongoClients.create(URI)) {
            this.database = mongoClient.getDatabase("aplicativo_completo");

            this.collection = database.getCollection("items");
        }
    }

    @Override
    public String inserir(Produto produto) {
        Document doc = new Document("_type", Produto.class.getSimpleName())
                .append("nome", produto.getNome())
                .append("estoque", produto.getEstoque())
                .append("preco", produto.getPreco());

        return collection.insertOne(doc).getInsertedId().asObjectId().getValue().toString();
    }
}
