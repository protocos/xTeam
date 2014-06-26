package me.protocos.xteam.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class WebPage
{
	private String urlString;
	private List<String> webpage;

	public WebPage(String urlString)
	{
		this.urlString = urlString;
		this.webpage = new LinkedList<String>();
		this.download();
	}
	
	public boolean download()
	{
		URL url;
	    InputStream is = null;
	    BufferedReader br;
	    String line;

	    try
	    {
	        url = new URL(urlString);
	        is = url.openStream();  // throws an IOException
	        br = new BufferedReader(new InputStreamReader(is));

	        while ((line = br.readLine()) != null)
	        {
	        	webpage.add(line);
	        }
	        return true;
	    }
	    catch (MalformedURLException mue)
	    {
	         mue.printStackTrace();
	    }
	    catch (IOException ioe)
	    {
	         ioe.printStackTrace();
	    }
	    finally
	    {
	        try
	        {
	            if (is != null) is.close();
	        }
	        catch (IOException ioe)
	        {
	            // nothing to see here
	        }
	    }
        return false;
	}
	
	public HTMLLine searchLine(String matches)
	{
		for(String line:webpage)
		{
			if (line.matches(".+"+matches+".+"))
				return new HTMLLine(line);
		}
		return null;
	}
	
	@Override
	public String toString()
	{
		String result = "";
		for(String line:webpage)
		{
			result += line + "\n";
		}
		return result;
	}
}

class HTMLLine
{
	private String line;

	public HTMLLine(String line)
	{
		this.line = line;
	}
	
	public HTMLLine pruneTags()
	{
		line = line.replaceAll("<.+?>", "");
		return this;
	}
	
	public HTMLLine trim()
	{
		line = line.trim();
		return this;
	}
	
	@Override
	public String toString()
	{
		return line;
	}
}
