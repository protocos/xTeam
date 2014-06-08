package me.protocos.xteam.util;

import java.util.regex.Pattern;

public class PatternBuilder
{
	private static final String WHITE_SPACE = "\\s+";
	private static final String OPTIONAL_WHITE_SPACE = "\\s*";
	private static final String NUMBERS = "\\d+";
	private static final String OPTIONAL_NUMBERS = "\\d*";
	private static final String ANY_CHARS = "\\S+";
	private static final Object ANY_NUMBERS = "[0-9-]+";
	private static final String OPTIONAL_ANY_CHARS = "\\S*";
	private static final String ALPHA_NUMERIC = "\\w+";
	private static final String OPTIONAL_ALPHA_NUMERIC = "\\w*";
	private static final String IGNORE_CASE = "(?i)";
	private static final String CASE_SENSITIVE = "(?-i)";

	private StringBuilder pattern;

	public PatternBuilder()
	{
		this("");
	}

	public PatternBuilder(PatternBuilder pattern)
	{
		this(pattern.toString());
	}

	public PatternBuilder(Pattern pattern)
	{
		this(pattern.pattern());
	}

	public PatternBuilder(String pattern)
	{
		this.pattern = new StringBuilder(pattern);
	}

	public PatternBuilder append(PatternBuilder append)
	{
		this.pattern.append(append.toString());
		return this;
	}

	public PatternBuilder append(Pattern append)
	{
		this.pattern.append(append.pattern());
		return this;
	}

	public PatternBuilder append(String append)
	{
		this.pattern.append(append);
		return this;
	}

	public PatternBuilder noneOrMore(String append)
	{
		this.pattern.append(this.patternNoneOrMore(append));
		return this;
	}

	public PatternBuilder oneOrMore(String append)
	{
		this.pattern.append(this.patternOneOrMore(append));
		return this;
	}

	public PatternBuilder oneOrMoreIgnoreCase(String append)
	{
		this.pattern.append(IGNORE_CASE + this.patternOneOrMore(append) + CASE_SENSITIVE);
		return this;
	}

	public PatternBuilder whiteSpace()
	{
		this.pattern.append(WHITE_SPACE);
		return this;
	}

	public PatternBuilder whiteSpaceOptional()
	{
		this.pattern.append(OPTIONAL_WHITE_SPACE);
		return this;
	}

	public PatternBuilder numbers()
	{
		this.pattern.append(NUMBERS);
		return this;
	}

	public PatternBuilder numbersOptional()
	{
		this.pattern.append(OPTIONAL_NUMBERS);
		return this;
	}

	public PatternBuilder alphaNumeric()
	{
		this.pattern.append(ALPHA_NUMERIC);
		return this;
	}

	public PatternBuilder alphaNumericOptional()
	{
		this.pattern.append(OPTIONAL_ALPHA_NUMERIC);
		return this;
	}

	public PatternBuilder anyString()
	{
		this.pattern.append(ANY_CHARS);
		return this;
	}

	public PatternBuilder anyNumber()
	{
		this.pattern.append(ANY_NUMBERS);
		return this;
	}

	public PatternBuilder anyStringOptional()
	{
		this.pattern.append(OPTIONAL_ANY_CHARS);
		return this;
	}

	public PatternBuilder anyOne(PatternBuilder append)
	{
		this.pattern.append("[" + append.toString() + "]");
		return this;
	}

	public PatternBuilder anyUnlimited(PatternBuilder append)
	{
		this.pattern.append("[" + append.toString() + "]+");
		return this;
	}

	public PatternBuilder excludeOne(PatternBuilder append)
	{
		this.pattern.append("[^" + append.toString() + "]");
		return this;
	}

	public PatternBuilder excludeUnlimited(PatternBuilder append)
	{
		this.pattern.append("[^" + append.toString() + "]+");
		return this;
	}

	public PatternBuilder repeat(String append, int times)
	{
		this.pattern.append("(" + append + "){" + times + "}");
		return this;
	}

	public PatternBuilder repeatUnlimited(PatternBuilder append)
	{
		this.pattern.append("(" + append.toString() + ")+");
		return this;
	}

	public PatternBuilder ignoreCase()
	{
		this.pattern.insert(0, IGNORE_CASE);
		this.pattern.append(CASE_SENSITIVE);
		return this;
	}

	public PatternBuilder ignoreCase(String append)
	{
		this.pattern.append(IGNORE_CASE + append + CASE_SENSITIVE);
		return this;
	}

	public PatternBuilder lowerCase(String append)
	{
		this.pattern.append(append.toLowerCase());
		return this;
	}

	public PatternBuilder upperCase(String append)
	{
		this.pattern.append(append.toUpperCase());
		return this;
	}

	public PatternBuilder capture(PatternBuilder anyCharacters)
	{
		this.pattern.append("(" + anyCharacters + ")");
		return this;
	}

	public PatternBuilder optional(PatternBuilder anyCharacters)
	{
		this.pattern.append("(" + anyCharacters + ")?");
		return this;
	}

	public PatternBuilder whatEvs()
	{
		this.pattern.append(new PatternBuilder()
				.whiteSpace()
				.anyString());
		return this;
	}

	public PatternBuilder or(String... strings)
	{
		PatternBuilder[] patterns = new PatternBuilder[strings.length];
		for (int x = 0; x < strings.length; x++)
		{
			patterns[x] = new PatternBuilder(strings[x]);
		}
		return this.or(patterns);
	}

	public PatternBuilder or(PatternBuilder... patterns)
	{
		if (patterns.length <= 1)
		{
			throw new AssertionError("or() must contain at least 2 patterns");
		}
		PatternBuilder orBuilder = new PatternBuilder().append("(");
		boolean first = true;
		for (PatternBuilder patternBuilder : patterns)
		{
			if (!first)
				orBuilder.append("|");
			orBuilder.append(patternBuilder);
			first = false;
		}
		orBuilder.append(")");
		this.pattern.append(orBuilder);
		return this;
	}

	public boolean matches(String compare)
	{
		return compare.matches(this.toString());
	}

	public Pattern build()
	{
		return Pattern.compile(pattern.toString());
	}

	public String toString()
	{
		return pattern.toString();
	}

	private Object patternNoneOrMore(String append)
	{
		String noneOrMore = "";
		for (char character : append.toCharArray())
		{
			noneOrMore += "(" + character + ")?";
		}
		return noneOrMore;
	}

	private String patternOneOrMore(String append)
	{
		String noneOrMore = "";
		boolean first = true;
		for (char character : append.toCharArray())
		{
			noneOrMore += "(" + character + ")" + (first ? "" : "?");
			first = false;
		}
		return noneOrMore;
	}
}
