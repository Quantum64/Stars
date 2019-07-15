package co.q64.stars.type.forming;

import co.q64.stars.block.YellowFormedBlock;
import co.q64.stars.type.FormingBlockType;
import lombok.Getter;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class YellowFormingBlockType implements FormingBlockType {
    private final @Getter int id = 0;
    private final @Getter int buildTime = 500;
    private final @Getter float r = 200, g = 200, b = 20;

    protected @Getter @Inject YellowFormedBlock formedBlock;

    protected @Inject YellowFormingBlockType() {}
}
