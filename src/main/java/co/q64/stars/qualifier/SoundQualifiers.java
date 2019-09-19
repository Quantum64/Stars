package co.q64.stars.qualifier;

import javax.inject.Qualifier;

public interface SoundQualifiers {
    // @formatter:off

    // Server
    public static @Qualifier @interface Red {}
    public static @Qualifier @interface Yellow {}
    public static @Qualifier @interface Green {}
    public static @Qualifier @interface Blue {}
    public static @Qualifier @interface Cyan {}
    public static @Qualifier @interface Purple {}
    public static @Qualifier @interface Brown {}
    public static @Qualifier @interface Pink {}
    public static @Qualifier @interface Teal {}
    public static @Qualifier @interface Orange {}
    public static @Qualifier @interface White {}
    public static @Qualifier @interface Dark {}
    public static @Qualifier @interface Explode {}
    public static @Qualifier @interface Seed {}
    public static @Qualifier @interface Fall {}
    public static @Qualifier @interface ExplodeDark {}
    public static @Qualifier @interface Door {}
    public static @Qualifier @interface Ticking {}
    public static @Qualifier @interface Empty {}
    public static @Qualifier @interface DarkAir {}
    public static @Qualifier @interface Key {}
    public static @Qualifier @interface Bubble {}
    public static @Qualifier @interface Thunder {}
    public static @Qualifier @interface Pop {}
    public static @Qualifier @interface Complete {}
    public static @Qualifier @interface Exit {}
    public static @Qualifier @interface Wind {}

    // Client
    public static @Qualifier @interface AmbientDark {}
    public static @Qualifier @interface AmbientLight {}
    public static @Qualifier @interface AmbientHub {}
    public static @Qualifier @interface Lost {}
    // @formatter:on
}
