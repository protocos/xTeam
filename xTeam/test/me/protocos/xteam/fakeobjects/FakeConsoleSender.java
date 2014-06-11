package me.protocos.xteam.fakeobjects;

import java.util.Set;
import me.protocos.xteam.collections.LimitedQueue;
import me.protocos.xteam.message.IMessageRecorder;
import me.protocos.xteam.util.CommonUtil;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

public class FakeConsoleSender implements ConsoleCommandSender, IMessageRecorder
{
	private LimitedQueue<String> messageLog;

	public FakeConsoleSender()
	{
		messageLog = new LimitedQueue<String>(50);
	}

	@Override
	public void abandonConversation(Conversation arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void abandonConversation(Conversation arg0, ConversationAbandonedEvent arg1)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void acceptConversationInput(String arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public PermissionAttachment addAttachment(Plugin arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PermissionAttachment addAttachment(Plugin arg0, int arg1)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PermissionAttachment addAttachment(Plugin arg0, String arg1, boolean arg2)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PermissionAttachment addAttachment(Plugin arg0, String arg1, boolean arg2, int arg3)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean beginConversation(Conversation arg0)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<PermissionAttachmentInfo> getEffectivePermissions()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName()
	{
		return "CONSOLE";
	}

	@Override
	public Server getServer()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasPermission(Permission anyPermission)
	{
		return true;
	}

	@Override
	public boolean hasPermission(String anyPermission)
	{
		return true;
	}

	@Override
	public boolean isConversing()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isOp()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPermissionSet(Permission arg0)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPermissionSet(String arg0)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void recalculatePermissions()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void removeAttachment(PermissionAttachment arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void sendMessage(String message)
	{
		messageLog.offer(message.replaceAll("§.", ""));
	}

	@Override
	public void sendMessage(String[] arg0)
	{
		String message = CommonUtil.concatenate(arg0);
		sendMessage(message);
	}

	@Override
	public void sendRawMessage(String arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setOp(boolean arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public String getLastMessage()
	{
		return messageLog.getLast();
	}

	@Override
	public String getAllMessages()
	{
		return messageLog.toString();
	}

	@Override
	public LimitedQueue<String> getMessages()
	{
		return messageLog;
	}
}
