package com.NovaCraft.world;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import com.NovaCraft.config.Configs;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PopulateChunkEventHandler
{
    @SubscribeEvent
    public void onPopulateChunkEvent(PopulateChunkEvent.Post event)
    {
        boolean removeEmerald = Configs.disableEmerald;
        boolean removeMonsterEgg = Configs.disableMonsterEgg;

        if (!removeEmerald && !removeMonsterEgg)
        {
            return;
        }

        World world = event.world;
        int baseX = event.chunkX * 16;
        int baseZ = event.chunkZ * 16;

        for (ExtendedBlockStorage storage : world.getChunkFromChunkCoords(event.chunkX, event.chunkZ).getBlockStorageArray())
        {
            if (storage == null || storage.isEmpty())
            {
                continue;
            }

            int baseY = storage.getYLocation();

            for (int y = 0; y < 16; ++y)
            {
                for (int z = 0; z < 16; ++z)
                {
                    for (int x = 0; x < 16; ++x)
                    {
                        Block block = storage.getBlockByExtId(x, y, z);

                        if ((removeEmerald && block == Blocks.emerald_ore) || (removeMonsterEgg && block == Blocks.monster_egg))
                        {
                            world.setBlock(baseX + x, baseY + y, baseZ + z, Blocks.stone, 0, 2);
                        }
                    }
                }
            }
        }
    }
}
