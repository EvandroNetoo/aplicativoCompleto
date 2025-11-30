package aplicativoCompleto.control;

import java.util.List;
import java.util.Map;

public class ValidacaoException extends Exception {
    private Map<String, List<String>> erros;

    public ValidacaoException(Map<String, List<String>> erros) {
        super(erros.toString());
        this.erros = erros;
    }

    public Map<String, List<String>> getErros() {
        return erros;
    }
}
