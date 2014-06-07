package me.protocos.xteam.message;

public interface IMessageRecipient
{
	public abstract String getName();

	public abstract void sendMessage(String message);
}
