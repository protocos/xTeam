package me.protocos.xteam.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import me.protocos.xteam.XTeam;

public class ErrorReporterUtil
{
	private static final String SERVER = "http://xteam-server.herokuapp.com";
	private static final String PATH_REPORT = "/services/error/report";

	public boolean report(Exception exception)
	{
		boolean reported = false;
		try
		{
			// CONNECT to server
			URL url = new URL(SERVER + PATH_REPORT);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			// WRITE JSON to connection
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
			out.write(ErrorReporterUtil.exportException(exception));
			out.close();
			// READ response from connection
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String response = in.readLine();
			reported = Boolean.parseBoolean(response);
			in.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return reported;
	}

	private static String exportException(Exception exception)
	{
		return "{" +
				"\"uniqueid\": \"" + SystemUtil.getUUID() + "\", " +
				"\"stacktrace\": " + ErrorReporterUtil.formatException(exception) +
				"}";
	}

	private static String formatException(Exception exception)
	{
		ArrayList<String> error = new ArrayList<String>();
		error.add(exception.toString() + " (xTeam v" + XTeam.getInstance().getVersion() + ")");
		for (StackTraceElement elem : exception.getStackTrace())
		{
			if (elem.toString().contains("me.protocos.xteam"))
			{
				error.add("        @ " + elem.toString());
			}
		}
		return error.toString()
				.replaceAll(", ", "\", \"")
				.replaceAll("\\<", "&lt;")
				.replaceAll("\\>", "&gt;")
				.replaceAll("\\[", "[\"")
				.replaceAll("\\]", "\"]");
	}
}