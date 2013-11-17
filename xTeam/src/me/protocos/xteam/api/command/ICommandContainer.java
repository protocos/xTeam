package me.protocos.xteam.api.command;


public interface ICommandContainer
{
	public void registerConsoleCommands(ICommandManager manager);

	public void registerServerAdminCommands(ICommandManager manager);

	public void registerLeaderCommands(ICommandManager manager);

	public void registerAdminCommands(ICommandManager manager);

	public void registerUserCommands(ICommandManager manager);
}
