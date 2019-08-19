package co.q64.stars.item;

public interface HasDescription {
    public default String[] getDescriptionLines() {
        return new String[]{getDescription()};
    }

    public default String getDescription() {
        return "";
    }
}
