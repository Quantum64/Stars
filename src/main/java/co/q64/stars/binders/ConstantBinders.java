package co.q64.stars.binders;

import javax.inject.Qualifier;

public interface ConstantBinders {
    // @formatter:off
    public static @Qualifier @interface ModId {}
    public static @Qualifier @interface Name {}
    public static @Qualifier @interface Author {}
    public static @Qualifier @interface Version {}
    // @formatter:on
}
