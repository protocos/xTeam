package me.protocos.xteam.message;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import me.protocos.xteam.model.ILog;
import me.protocos.xteam.util.CommonUtil;

public class Message
{
	private final String message;
	private final Set<IMessageRecipient> recipients;

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

	public void send()
	{
		send(null);
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
}

class IMessageRecipientComparator implements Comparator<IMessageRecipient>
{
	@Override
	public int compare(IMessageRecipient recipient1, IMessageRecipient recipient2)
	{
		return recipient1.getName().compareTo(recipient2.getName());
	}
}
