package org.echocat.kata.java.part1.domain;

import java.util.List;
import java.util.Objects;

public class Publication
{
    public final String title;
    public final String isbn;
    public final List<String> authors;


    public Publication(String title, String isbn, List<String> authors)
    {
        this.title = title;
        this.isbn = isbn;
        this.authors = authors;
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        Publication that = (Publication) o;
        return Objects.equals(title, that.title) &&
            Objects.equals(isbn, that.isbn) &&
            Objects.equals(authors, that.authors);
    }


    @Override
    public int hashCode()
    {
        return Objects.hash(title, isbn, authors);
    }
}