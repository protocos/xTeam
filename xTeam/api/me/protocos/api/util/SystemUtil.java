package me.protocos.api.util;

import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class SystemUtil
{
	public static String getCurrentDirectory()
	{
		try
		{
			return new java.io.File(".").getCanonicalPath();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	private static byte[] createChecksum(String filename) throws NoSuchAlgorithmException, IOException
	{
		InputStream fis = new FileInputStream(filename);
		byte[] buffer = new byte[1024];
		MessageDigest complete = MessageDigest.getInstance("MD5");
		int numRead;

		do
		{
			numRead = fis.read(buffer);
			if (numRead > 0)
			{
				complete.update(buffer, 0, numRead);
			}
		} while (numRead != -1);

		fis.close();
		return complete.digest();
	}

	// see this How-to for a faster way to convert
	// a byte array to a HEX string
	public static String getMD5Checksum(String filename) throws NoSuchAlgorithmException, IOException
	{
		byte[] b = createChecksum(filename);
		String result = "";

		for (int i = 0; i < b.length; i++)
		{
			result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}

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

	public static boolean pathIsPresent(String string)
	{
		return new File(string).exists();
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
		String[] subFolders = fileName.split(File.separator);
		if (subFolders.length > 1)
		{
			String folderBuilder = "";
			for (int x = 0; x < subFolders.length - 1; x++)
			{
				folderBuilder += subFolders[x] + File.separator;
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
