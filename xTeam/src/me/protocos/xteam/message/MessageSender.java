package me.protocos.xteam.message;

import org.bukkit.command.CommandSender;

public class MessageSender implements IMessageRecipient
{
	private final CommandSender sender;

	public MessageSender(CommandSender sender)
	{
		this.sender = sender;
	}

	@Override
	public void sendMessage(String message)
	{
		sender.sendMessage(message);
	}

	@Override
	public String getName()
	{
		return sender.getName();
	}
}
