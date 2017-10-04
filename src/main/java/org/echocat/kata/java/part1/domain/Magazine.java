package org.echocat.kata.java.part1.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Magazine extends Publication
{
    private static final DateTimeFormatter MAGAZINE_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public final LocalDate publishedAt;


    public Magazine(String title, String isbn, List<String> authors, String publishedAt)
    {
        super(title, isbn, authors);
        this.publishedAt = LocalDate.parse(publishedAt, MAGAZINE_DATE_FORMATTER);
    }


    @Override
    public String toString()
    {
        return String.format("%s;%s", super.toString(), publishedAt.format(MAGAZINE_DATE_FORMATTER));
    }
}
