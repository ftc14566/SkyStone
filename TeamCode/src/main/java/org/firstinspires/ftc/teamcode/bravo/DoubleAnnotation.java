package org.firstinspires.ftc.teamcode.bravo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Inherited;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface DoubleAnnotation {
    String label() default "double";
    double initial() default 0.0;
    double min() default 0.0;
    double max() default 1.0;
    double step() default 0.1;
    String units() default "";
}

