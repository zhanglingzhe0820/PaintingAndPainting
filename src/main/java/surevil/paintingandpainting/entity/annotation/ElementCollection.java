package surevil.paintingandpainting.entity.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface ElementCollection {
    Class targetClass();
}