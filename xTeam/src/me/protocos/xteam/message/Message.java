package me.protocos.xteam.message;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import me.protocos.xteam.util.CommonUtil;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Message
{
	private final String message;
	private final List<IMessageRecipient> recipients;

	public static class Builder
	{
		private final String message;
		private final Set<MessageRecipientEqualityWrapper> recipients;
		private final Set<MessageRecipientEqualityWrapper> excludes;
		private boolean formatting;

		public Builder(String message)
		{
			this.message = message;
			this.recipients = new HashSet<MessageRecipientEqualityWrapper>();
			this.excludes = new HashSet<MessageRecipientEqualityWrapper>();
			this.formatting = true;
		}

		public Builder addRecipients(IMessageRecipient... messageRecipients)
		{
			this.recipients.addAll(MessageRecipientEqualityWrapper.toList(messageRecipients));
			return this;
		}

		public Builder addRecipients(IMessageRecipientContainer messageRecipientContainer)
		{
			this.recipients.addAll(MessageRecipientEqualityWrapper.toList(messageRecipientContainer));
			return this;
		}

		public Builder excludeRecipients(IMessageRecipient... excludeRecipients)
		{
			this.excludes.addAll(MessageRecipientEqualityWrapper.toList(excludeRecipients));
			return this;
		}

		public Builder excludeRecipients(IMessageRecipientContainer messageExcludeContainer)
		{
			this.excludes.addAll(MessageRecipientEqualityWrapper.toList(messageExcludeContainer));
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
		this.recipients = new ArrayList<IMessageRecipient>();
		this.recipients.addAll(builder.recipients);
		for (IMessageRecipient recipient : builder.recipients)
		{
			for (IMessageRecipient exclude : builder.excludes)
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

class MessageRecipientEqualityWrapper implements IMessageRecipient, Comparable<MessageRecipientEqualityWrapper>
{
	private IMessageRecipient recipient;

	public MessageRecipientEqualityWrapper(IMessageRecipient recipient)
	{
		this.recipient = recipient;
	}

	@Override
	public String getName()
	{
		return recipient.getName();
	}

	@Override
	public void sendMessage(String message)
	{
		recipient.sendMessage(message);
	}

	public static List<MessageRecipientEqualityWrapper> toList(IMessageRecipient... recipients)
	{
		List<MessageRecipientEqualityWrapper> list = CommonUtil.emptyList();
		for (IMessageRecipient recipient : recipients)
			list.add(new MessageRecipientEqualityWrapper(recipient));
		return list;
	}

	public static List<MessageRecipientEqualityWrapper> toList(IMessageRecipientContainer messageRecipientContainer)
	{
		List<MessageRecipientEqualityWrapper> list = CommonUtil.emptyList();
		for (IMessageRecipient recipient : messageRecipientContainer.getMessageRecipients())
			list.add(new MessageRecipientEqualityWrapper(recipient));
		return list;
	}

	@Override
	public int compareTo(MessageRecipientEqualityWrapper rhs)
	{
		return new CompareToBuilder().append(this.getName(), rhs.getName()).toComparison();
	}

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder(139, 163).append(this.getName()).toHashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof MessageRecipientEqualityWrapper))
			return false;

		MessageRecipientEqualityWrapper rhs = (MessageRecipientEqualityWrapper) obj;
		return new EqualsBuilder().append(this.getName(), rhs.getName()).isEquals();
	}
}
