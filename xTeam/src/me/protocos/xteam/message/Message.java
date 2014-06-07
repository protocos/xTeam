package me.protocos.xteam.message;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import me.protocos.xteam.util.CommonUtil;

public class Message
{
	private final String message;
	private final List<IMessageRecipient> recipients;

	public static class Builder
	{
		private final String message;
		private final Set<IMessageRecipient> recipients;
		private final Set<IMessageRecipient> excludeSet;

		public Builder(String message)
		{
			this.message = message;
			this.recipients = new HashSet<IMessageRecipient>();
			this.excludeSet = new HashSet<IMessageRecipient>();
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
			this.excludeSet.addAll(CommonUtil.toList(excludeRecipients));
			return this;
		}

		public Builder excludeRecipients(IMessageRecipientContainer messageRecipientContainer)
		{
			this.excludeSet.addAll(messageRecipientContainer.getMessageRecipients());
			return this;
		}

		public Message build()
		{
			return new Message(this);
		}
	}

	private Message(Builder builder)
	{
		this.message = MessageUtil.formatMessage(builder.message);
		this.recipients = new ArrayList<IMessageRecipient>();
		this.recipients.addAll(builder.recipients);
		for (IMessageRecipient recipient : builder.recipients)
		{
			for (IMessageRecipient exclude : builder.excludeSet)
			{
				if (recipient.getName().equals(exclude.getName()))
					recipients.remove(recipient);
			}
		}
	}

	public void send()
	{
		for (IMessageRecipient recipient : recipients)
		{
			recipient.sendMessage(message);
		}
	}
}
