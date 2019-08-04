package co.q64.stars.item;

import co.q64.stars.group.StarsGroup;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ChallengeStarItem extends BaseItem {
    @Inject
    protected ChallengeStarItem(StarsGroup group) {
        super("challenge_star", group);
    }
}
