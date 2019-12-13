package org.firstinspires.ftc.teamcode.bravo;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Inherited;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface IntAnnotation {
    String label() default "int";
    int initial() default 0;
    int min() default 0;
    int max() default 255;
    int step() default 1;
    String units() default "";
}
