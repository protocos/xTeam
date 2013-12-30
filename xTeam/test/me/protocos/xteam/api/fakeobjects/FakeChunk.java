package me.protocos.xteam.api.fakeobjects;

import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;

public class FakeChunk implements Chunk
{

	@Override
	public Block getBlock(int arg0, int arg1, int arg2)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChunkSnapshot getChunkSnapshot()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChunkSnapshot getChunkSnapshot(boolean arg0, boolean arg1, boolean arg2)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entity[] getEntities()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BlockState[] getTileEntities()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public World getWorld()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getX()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getZ()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isLoaded()
	{
		return true;
	}

	@Override
	public boolean load()
	{
		return true;
	}

	@Override
	public boolean load(boolean arg0)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean unload()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean unload(boolean arg0)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean unload(boolean arg0, boolean arg1)
	{
		// TODO Auto-generated method stub
		return false;
	}

}
