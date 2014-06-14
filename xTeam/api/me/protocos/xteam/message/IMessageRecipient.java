package me.protocos.xteam.message;

public interface IMessageRecipient
{
	public abstract String getName();

	public abstract boolean isOnline();

	public abstract void sendMessage(String message);
}
