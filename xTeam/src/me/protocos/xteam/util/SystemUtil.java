package me.protocos.xteam.util;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.UUID;

public class SystemUtil
{
	public static final String getUUID()
	{
		String uuid;
		try
		{
			uuid = UUID.nameUUIDFromBytes(getMACAddress()).toString();
		}
		catch (SocketException | UnknownHostException e)
		{
			uuid = "ANONYMOUS";
		}
		return uuid;
	}

	private static final byte[] getMACAddress() throws SocketException, UnknownHostException
	{
		NetworkInterface network = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
		byte[] mac = network.getHardwareAddress();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < mac.length; i++)
		{
			sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
		}
		return mac;
	}

	public static boolean isMac()
	{
		String os = System.getProperty("os.name").toLowerCase();
		return (os.indexOf("mac") >= 0);
	}

	public static boolean isWindows()
	{
		String os = System.getProperty("os.name").toLowerCase();
		return (os.indexOf("win") >= 0);
	}

	public static File ensureFolder(String folderName)
	{
		File file = new File(folderName);
		if (!file.exists())
		{
			file.mkdir();
		}
		return file;
	}

	public static File ensureFile(String fileName)
	{
		String[] subFolders = fileName.split("/+");
		if (subFolders.length > 1)
		{
			String folderBuilder = "";
			for (int x = 0; x < subFolders.length - 1; x++)
			{
				folderBuilder += subFolders[x] + "/";
				ensureFolder(folderBuilder);
			}
		}
		File file = new File(fileName);
		if (!file.exists())
		{
			try
			{
				file.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return file;
	}

	public static boolean deleteFile(String fileName)
	{
		File file = new File(fileName);
		if (file.exists())
		{
			return file.delete();
		}
		return false;
	}
}
