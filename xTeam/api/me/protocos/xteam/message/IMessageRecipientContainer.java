package me.protocos.xteam.message;

import java.util.Set;

public interface IMessageRecipientContainer
{
	public abstract Set<IMessageRecipient> getMessageRecipients();
}
