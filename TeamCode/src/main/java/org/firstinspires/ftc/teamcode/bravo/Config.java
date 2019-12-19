package org.firstinspires.ftc.teamcode.bravo;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface Config {

    // region used by all
    String label() default "";
    String units() default "";
    // endregion

    // region used by numerics
    double value() default 0.0;
    double min() default 0.0;
    double max() default 0.0;
    double step() default 0.0;
    double displayScale() default 1.0;
    // endregion

    // region used by boolean
    boolean isTrue() default false;
    String trueString() default "true";
    String falseString() default "false";
    // endregion

}

