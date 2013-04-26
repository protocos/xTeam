package me.protocos.xteam.util;

import java.util.ArrayList;

public class HelpPages extends ArrayList<String>
{
	private static final long serialVersionUID = 8824698170518054108L;
	private int linesPerPage;
	private String title;

	public HelpPages()
	{
		this(10);
	}
	public HelpPages(int linesPerPage)
	{
		this("Title", linesPerPage);
	}
	public HelpPages(String title, int linesPerPage)
	{
		setTitle(title);
		setLinesPerPage(linesPerPage);
	}
	public void addLine(String info)
	{
		add(info);
	}
	public int getLinesPerPage()
	{
		return linesPerPage;
	}
	public int getNumBlankLines(int pageNum)
	{
		int numLinesOnPage = 0;
		if ((pageNum + 1) * (linesPerPage - 1) > size())
		{
			if ((pageNum + 1) * (linesPerPage - 1) - size() <= (linesPerPage - 1))
			{
				numLinesOnPage = (pageNum + 1) * (linesPerPage - 1) - size();
			}
			else
			{
				numLinesOnPage = (linesPerPage - 1);
			}
		}
		return numLinesOnPage;
	}
	public String getPage(int pageNum)
	{
		String page = getTitle() + "\n";
		for (int index = pageNum * (linesPerPage - 1); index < (pageNum + 1) * (linesPerPage - 1); index++)
		{
			if (index < size())
				page += get(index);
			else
				page += " ";
			if (index != (pageNum + 1) * (linesPerPage - 1) - 1)
				page += "\n";

			//			page += (index < size() ? get(index) : "") + (index == (pageNum + 1) * (linesPerPage - 1) - 1 ? "" : "\n");
		}
		return page;
	}
	public String getTitle()
	{
		return title;
	}
	public int getTotalPages()
	{
		return (int) Math.ceil((double) size() / (double) (linesPerPage - 1));
	}
	public void removeLine(int index)
	{
		remove(index);
	}
	public void setLinesPerPage(int linesPerPage)
	{
		this.linesPerPage = linesPerPage;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String toString()
	{
		String output = getTitle() + "\n";
		for (String info : this)
			output += info + "\n";
		return output.trim();
	}
}
