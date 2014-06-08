package me.protocos.xteam.fakeobjects;

import java.util.ArrayList;
import java.util.List;
import me.protocos.xteam.message.IMessageRecipient;

public class FakeMessageRecipient implements IMessageRecipient
{
	private String name;
	private List<String> messages;

	public FakeMessageRecipient(String name)
	{
		this.name = name;
		messages = new ArrayList<String>();
	}

	public FakeMessageRecipient(FakeMessageRecipient recipient)
	{
		this.name = recipient.getName();
		this.messages = recipient.getMessages();
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public void sendMessage(String message)
	{
		messages.add(message);
	}

	public List<String> getMessages()
	{
		return messages;
	}

	public String getLatestMessage()
	{
		if (messages.size() > 0)
			return messages.get(messages.size() - 1);
		return "";
	}

	public boolean hasAnyMessages()
	{
		return !messages.isEmpty();
	}

	public String getAllMessages()
	{
		return messages.toString().replaceAll(", ", "\n").replaceAll("\\[", "").replaceAll("\\]", "");
	}
}
