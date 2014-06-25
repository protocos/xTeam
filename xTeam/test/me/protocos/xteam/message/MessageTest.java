package me.protocos.xteam.message;

import me.protocos.xteam.fakeobjects.FakeMessageRecipient;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MessageTest
{
	private Message message;
	private FakeMessageRecipient recipient1, recipient2, duplicateRecipient1;

	@Before
	public void setup()
	{
		recipient1 = new FakeMessageRecipient("player1");
		recipient2 = new FakeMessageRecipient("player2");
		duplicateRecipient1 = new FakeMessageRecipient(recipient1);
	}

	@Test
	public void ShouldBeSendMessageToFirstRecipient()
	{
		//ASSEMBLE
		message = new Message.Builder("Test message.").addRecipients(recipient1).build();
		//ACT
		message.send(null);
		//ASSERT
		Assert.assertEquals("Test message.", recipient1.getLatestMessage());
	}

	@Test
	public void ShouldBeSendMessageToAllRecipientsOnlyOnce()
	{
		//ASSEMBLE
		message = new Message.Builder("Test message.").addRecipients(recipient1, recipient2, duplicateRecipient1).build();
		//ACT
		message.send(null);
		//ASSERT
		Assert.assertEquals("Test message.", recipient1.getAllMessages());
		Assert.assertEquals("Test message.", recipient2.getAllMessages());
	}

	@Test
	public void ShouldBeSendMessageToRecipient2Only()
	{
		//ASSEMBLE
		message = new Message.Builder("Test message.").addRecipients(recipient1, recipient2).excludeRecipients(duplicateRecipient1).build();
		//ACT
		message.send(null);
		//ASSERT
		Assert.assertEquals("Test message.", recipient2.getLatestMessage());
		Assert.assertFalse(recipient1.hasAnyMessages());
	}

	@Test
	public void ShouldBeSendFormattedMessageToRecipient1()
	{
		//ASSEMBLE
		message = new Message.Builder("refreshed").addRecipients(recipient1).build();
		//ACT
		message.send(null);
		//ASSERT
		Assert.assertEquals("§arefreshed§r", recipient1.getLatestMessage());
	}

	@Test
	public void ShouldBeSendMessageToRecipient1NoFormatting()
	{
		//ASSEMBLE
		message = new Message.Builder("refreshed").addRecipients(recipient1).disableFormatting().build();
		//ACT
		message.send(null);
		//ASSERT
		Assert.assertEquals("refreshed", recipient1.getLatestMessage());
	}

	@Test
	public void ShouldBeSetMessage()
	{
		//ASSEMBLE
		message = new Message.Builder("Message refreshed!").addRecipients(recipient1).build();
		//ACT
		message.setMessage("Message refreshed!");
		//ASSERT
		Assert.assertEquals("Message refreshed!", message.getMessage());
	}

	@Test
	public void ShouldBeSomething()
	{
		//ASSEMBLE
		//ACT
		new Message.Builder("refreshed").addRecipients(recipient1).send(null);
		//ASSERT
		Assert.assertEquals("§arefreshed§r", recipient1.getLatestMessage());
	}

	@After
	public void takedown()
	{
	}
}