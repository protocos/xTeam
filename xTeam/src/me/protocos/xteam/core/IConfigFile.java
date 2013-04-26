package me.protocos.xteam.core;

import java.io.File;

public interface IConfigFile extends IReadable, IWritable
{
	public abstract File getFile();
	public abstract void addAttribute(String attribute, String description);
	public abstract void removeAttribute(String attribute);
}
