package co.q64.stars.type;

import net.minecraft.block.Block;

public interface FormingBlockType {
    public int getId();

    public float getR();

    public float getG();

    public float getB();

    public int getBuildTime();

    public Block getFormedBlock();
}
