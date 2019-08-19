package co.q64.stars.item;

public interface SeedDescription extends HasDescription {
    @Override
    public default String getDescription() {
        return "A rare item which can only be obtained in the Stars realm. Craft a Starbound Gateway or find one in the world to start your journey.";
    }
}
