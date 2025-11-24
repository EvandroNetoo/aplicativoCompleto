package aplicativoCompleto.control;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import aplicativoCompleto.dao.MongoDao;
import aplicativoCompleto.domain.Adicionar;
import aplicativoCompleto.domain.Listar;

public class Controladora<Item extends Object> {
    private MongoDao<Item> dao;
    private Class<Item> classe;

    public Controladora(Class<Item> classe) {
        this.classe = classe;
        this.dao = new MongoDao<Item>(classe);
    }

    public LinkedHashMap<String, String> camposListados() {
        LinkedHashMap<String, String> campos = new LinkedHashMap<>();
        for (Field field : classe.getDeclaredFields()) {
            if (field.isAnnotationPresent(Listar.class)) {
                Listar listar = field.getAnnotation(Listar.class);
                campos.put(listar.label(), field.getName());
            }
        }

        for (Method method : classe.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Listar.class)) {
                Listar listar = method.getAnnotation(Listar.class);
                campos.put(listar.label(), method.getName());
            }
        }

        return campos;
    };

    public LinkedHashMap<String, String> camposAdicionais() {
        LinkedHashMap<String, String> campos = new LinkedHashMap<>();
        for (Field field : classe.getDeclaredFields()) {
            if (field.isAnnotationPresent(Adicionar.class)) {
                Adicionar adicionar = field.getAnnotation(Adicionar.class);
                campos.put(adicionar.label(), field.getName());
            }
        }
        return campos;
    };

    public List<String> camposOrdenaveis() {
        List<String> campos = new java.util.ArrayList<>();
        for (Field field : classe.getDeclaredFields()) {
            if (field.isAnnotationPresent(Listar.class)) {
                Listar listar = field.getAnnotation(Listar.class);
                if (listar.ordenavel()) {
                    campos.add(field.getName());
                }
            }
        }
        for (Method method : classe.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Listar.class)) {
                Listar listar = method.getAnnotation(Listar.class);
                if (listar.ordenavel()) {
                    campos.add(method.getName());
                }
            }
        }
        return campos;
    };

    private Map<String, Object> itemToMap(Item item) {
        Map<String, Object> map = new LinkedHashMap<>();

        for (Field field : classe.getDeclaredFields()) {
            if (field.isAnnotationPresent(Listar.class) || field.isAnnotationPresent(Adicionar.class)) {
                try {
                    field.setAccessible(true);
                    Object valor = field.get(item);
                    map.put(field.getName(), valor);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        for (Method method : classe.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Listar.class)) {
                try {
                    method.setAccessible(true);
                    Object valor = method.invoke(item);
                    map.put(method.getName(), valor);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }

    private Object converterValor(Class<?> tipo, String valor) {
        if (tipo == String.class) {
            return valor;
        } else if (tipo == int.class || tipo == Integer.class) {
            return Integer.valueOf(valor);
        } else if (tipo == double.class || tipo == Double.class) {
            return Double.valueOf(valor);
        }
        return null;
    }

    private Item mapToItem(Map<String, String> dados) {
        try {
            Item item = classe.getDeclaredConstructor().newInstance();
            for (Field field : classe.getDeclaredFields()) {
                if (!field.isAnnotationPresent(Adicionar.class))
                    continue;

                String valor = dados.get(field.getName());

                field.setAccessible(true);

                Object convertido = converterValor(field.getType(), valor);
                field.set(item, convertido);
            }
            return item;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, Object> adicionar(Map<String, String> dados) {
        Item item = mapToItem(dados);
        dao.inserir(item);
        return itemToMap(item);
    }

    public List<Map<String, Object>> listar() {
        return dao.listar().stream().map(this::itemToMap).toList();
    }

    public void atualizar(String id, Map<String, String> dados) {
        Item item = mapToItem(dados);
        dao.atualizar(id, item);
    }

    public void remover(String id) {
        dao.remover(id);
    }
}
