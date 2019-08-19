package co.q64.stars.item;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ChallengeStarItem extends BaseItem {
    @Inject
    protected ChallengeStarItem() {
        super("challenge_star");
        setHideJEI(true);
    }
}
