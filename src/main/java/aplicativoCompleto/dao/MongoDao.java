package aplicativoCompleto.dao;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import aplicativoCompleto.domain.Id;

public class MongoDao<Item> {
    private static final String URI = "mongodb+srv://evandrorsneto_db_user:0YXChaLo24NUinpy@aplicativocompleto.tc475iw.mongodb.net/?appName=aplicativoCompleto";
    private MongoDatabase database;
    private MongoCollection<Document> collection;
    private Class<Item> classe;

    public MongoDao(Class<Item> classe) {
        MongoClient mongoClient = MongoClients.create(URI);
        this.database = mongoClient.getDatabase("aplicativo_completo");
        this.collection = database.getCollection("items");
        this.classe = classe;
    }

    private Document itemToDocument(Item produto) {
        Document document = new Document("_type", classe.getSimpleName());
        for (Field field : classe.getDeclaredFields()) {
            if (field.isAnnotationPresent(aplicativoCompleto.domain.Adicionar.class)) {
                try {
                    field.setAccessible(true);
                    document.append(field.getName(), field.get(produto));
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return document;
    }

    private Item documentToItem(Document doc) {
        try {
            Item item = classe.getDeclaredConstructor().newInstance();
            for (Field field : classe.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(Id.class)) {
                    field.set(item, doc.getObjectId("_id").toHexString());
                } else if (field.isAnnotationPresent(aplicativoCompleto.domain.Adicionar.class)) {
                    field.set(item, doc.get(field.getName()));
                }
            }
            return item;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void inserir(Item item) {
        Document doc = itemToDocument(item);
        collection.insertOne(doc);
    }

    public List<Item> listar() {
        List<Item> items = new LinkedList<>();
        for (Document doc : collection.find(Filters.eq("_type", classe.getSimpleName()))) {
            System.err.println(doc.toJson());
            items.add(documentToItem(doc));
        }
        return items;
    }

    public void atualizar(String id, Item item) {
        Document documentoAtualizado = itemToDocument(item);
        collection.replaceOne(
                new Document("_id", new ObjectId(id)),
                documentoAtualizado);
    }

    public void remover(String id) {
        collection.deleteOne(new Document("_id", new ObjectId(id)));
    }
}
