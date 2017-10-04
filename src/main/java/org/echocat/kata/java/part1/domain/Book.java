package org.echocat.kata.java.part1.domain;

import java.util.List;
import java.util.Objects;

public class Book extends Publication
{
    public final String description;


    public Book(String title, String isbn, List<String> authors, String description)
    {
        super(title, isbn, authors);
        this.description = description;
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
        Book book = (Book) o;
        return Objects.equals(description, book.description);
    }


    @Override
    public int hashCode()
    {
        return Objects.hash(description);
    }


    @Override
    public String toString()
    {
        return "Book{" +
            "title='" + title + '\'' +
            ", description='" + description + '\'' +
            ", isbn='" + isbn + '\'' +
            ", authors=" + authors +
            "} " + super.toString();
    }
}
