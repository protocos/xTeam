package me.protocos.xteam.message;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import me.protocos.xteam.model.ILog;
import me.protocos.xteam.util.CommonUtil;
import org.bukkit.command.CommandSender;

public class Message
{
	private String message;
	private final Set<IMessageRecipient> recipients;
	private boolean formatting;

	public static class Builder
	{
		private final String message;
		private final Set<IMessageRecipient> recipients;
		private final Set<IMessageRecipient> excludes;
		private boolean formatting;

		public Builder(String message)
		{
			this.message = message;
			this.recipients = new TreeSet<IMessageRecipient>(new IMessageRecipientComparator());
			this.excludes = new TreeSet<IMessageRecipient>(new IMessageRecipientComparator());
			this.formatting = true;
		}

		public Builder addRecipients(CommandSender sender)
		{
			this.recipients.add(new MessageSender(sender));
			return this;
		}

		public Builder addRecipients(IMessageRecipient... messageRecipients)
		{
			this.recipients.addAll(CommonUtil.toList(messageRecipients));
			return this;
		}

		public Builder addRecipients(IMessageRecipientContainer messageRecipientContainer)
		{
			this.recipients.addAll(messageRecipientContainer.getMessageRecipients());
			return this;
		}

		public Builder excludeRecipients(CommandSender sender)
		{
			this.excludes.add(new MessageSender(sender));
			return this;
		}

		public Builder excludeRecipients(IMessageRecipient... excludeRecipients)
		{
			this.excludes.addAll(CommonUtil.toList(excludeRecipients));
			return this;
		}

		public Builder excludeRecipients(IMessageRecipientContainer messageExcludeContainer)
		{
			this.excludes.addAll(messageExcludeContainer.getMessageRecipients());
			return this;
		}

		public Builder disableFormatting()
		{
			this.formatting = false;
			return this;
		}

		public Message build()
		{
			return new Message(this);
		}
	}

	private Message(Builder builder)
	{
		this.message = (builder.formatting ? MessageUtil.formatMessage(builder.message) : builder.message);
		this.recipients = CommonUtil.emptySet(new IMessageRecipientComparator());
		this.recipients.addAll(builder.recipients);
		this.recipients.removeAll(builder.excludes);
	}

	public void send(ILog log)
	{
		for (IMessageRecipient recipient : recipients)
		{
			if (log != null)
				log.debug("server response: @" + recipient.getName() + " \"" + message + "\"");
			recipient.sendMessage(message);
		}
	}

	public void setMessage(String message)
	{
		this.message = this.formatting ? MessageUtil.formatMessage(message) : message;
	}

	public String getMessage()
	{
		return MessageUtil.resetFormatting(message);
	}
}

class IMessageRecipientComparator implements Comparator<IMessageRecipient>
{
	@Override
	public int compare(IMessageRecipient recipient1, IMessageRecipient recipient2)
	{
		return recipient1.getName().compareTo(recipient2.getName());
	}
}

class MessageSender implements IMessageRecipient
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
