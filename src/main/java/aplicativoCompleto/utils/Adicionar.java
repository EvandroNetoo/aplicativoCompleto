package aplicativoCompleto.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Adicionar {
    String label() default "";
    Class<? extends IValidadorCampo>[] validadores() default {};
}
