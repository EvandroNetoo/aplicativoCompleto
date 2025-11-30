package aplicativoCompleto.utils;

public class ValidadorMinLen2 implements IValidadorCampo {
    @Override
    public String validar(Object valor) {
        if (valor instanceof String) {
            String str = (String) valor;
            if (str.length() < 2) {
                return "O valor deve ter no mínimo 2 caracteres.";
            }
        }
        return null;
    }

}
