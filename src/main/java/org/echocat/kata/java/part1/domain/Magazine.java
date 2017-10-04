package org.echocat.kata.java.part1.domain;

import java.time.LocalDate;
import java.util.List;

public class Magazine extends Publication
{
    public final LocalDate publishedAt;


    public Magazine(String title, String isbn, List<String> authors, LocalDate publishedAt)
    {
        super(title, isbn, authors);
        this.publishedAt = publishedAt;
    }


    @Override
    public String toString()
    {
        return "Magazine{" +
            "title='" + title + '\'' +
            ", isbn='" + isbn + '\'' +
            ", publishedAt=" + publishedAt +
            ", authors=" + authors +
            "} " + super.toString();
    }
}
