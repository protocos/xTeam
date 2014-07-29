package me.protocos.xteam.model;

import java.util.List;
import me.protocos.api.util.CommonUtil;

public class HelpPages
{
	private int linesPerPage;
	private String title;
	private List<String> lines;

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
		this.title = title;
		this.linesPerPage = linesPerPage;
		lines = CommonUtil.emptyList();
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getTitle()
	{
		return title;
	}

	public int getLinesPerPage()
	{
		return linesPerPage;
	}

	public void addLine(String info)
	{
		lines.add(info);
	}

	public <T> void addDescriptions(List<T> info)
	{
		for (T line : info)
		{
			this.addLine(line.toString());
		}
	}

	public String getLine(int index)
	{
		return lines.get(index);
	}

	public void removeLine(int index)
	{
		lines.remove(index);
	}

	public int getNumLines()
	{
		return lines.size();
	}

	public int getTotalPages()
	{
		return (int) Math.ceil((double) getNumLines() / (double) (linesPerPage - 1));
	}

	public int getNumBlankLines(int pageNum)
	{
		int numLinesOnPage = 0;
		if ((pageNum + 1) * (linesPerPage - 1) > getNumLines())
		{
			if ((pageNum + 1) * (linesPerPage - 1) - getNumLines() <= (linesPerPage - 1))
			{
				numLinesOnPage = (pageNum + 1) * (linesPerPage - 1) - getNumLines();
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
			if (index < getNumLines())
				page += getLine(index);
			else
				page += " ";
			if (index != (pageNum + 1) * (linesPerPage - 1) - 1)
				page += "\n";
		}
		return page;
	}

	public String toString()
	{
		String output = getTitle() + "\n";
		for (String info : lines)
			output += info + "\n";
		return output.trim();
	}
}
