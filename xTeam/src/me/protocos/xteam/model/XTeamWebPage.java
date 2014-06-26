package me.protocos.xteam.model;

public class XTeamWebPage extends WebPage
{
	public XTeamWebPage(String urlString, ILog log)
	{
		super(urlString, log);
	}
	
	public String getMostRecentVersion()
	{
		return this.searchLine("<a href=\"/bukkit-plugins/xteam/files/.+/\">").pruneTags().trim().toString();
	}
}
