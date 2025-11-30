package aplicativoCompleto.utils;

public class ValidadorNumeroPositivo implements IValidadorCampo {
    @Override
    public String validar(Object valor) {
        if (valor instanceof Number) {
            return ((Number) valor).doubleValue() > 0 ? null : "O valor deve ser positivo";
        }
        return "O valor não é um número";
    }

}
