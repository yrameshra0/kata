package org.echocat.kata.java.part1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.echocat.kata.java.part1.domain.Author;
import org.echocat.kata.java.part1.domain.Book;
import org.echocat.kata.java.part1.domain.Magazine;
import org.echocat.kata.java.part1.domain.Publication;

public class AuthorsWithPublicationsRepository
{
    private static final String AUTHORS_CSV = "org/echocat/kata/java/part1/data/authors.csv";
    private static final String BOOKS_CSV = "org/echocat/kata/java/part1/data/books.csv";
    private static final String MAGAZINES_CSV = "org/echocat/kata/java/part1/data/magazines.csv";
    public static final String CSV_SPLITTER = ";";
    public static final String AUTHOR_SPLITTER = ",";
    public static final DateTimeFormatter MAGAZINE_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");


    public Publication findByISBN(String isbn)
    {
        return allPublication.stream()
            .filter(publication -> publication.isbn.equals(isbn))
            .findFirst()
            .get();
    }

    private interface Parser<T>
    {
        T apply(String[] components);
    }

    private final List<Author> authors;
    private final List<Publication> allPublication;


    public AuthorsWithPublicationsRepository()
    {
        this.authors = parseData(BOOKS_CSV, authorParser);
        this.allPublication = Stream.concat(
            parseData(BOOKS_CSV, bookParser).stream(),
            parseData(MAGAZINES_CSV, magazineParser).stream())
            .collect(Collectors.toList());

    }


    private <T> List<T> parseData(String fileName, Parser<T> parser)
    {
        InputStreamReader dataStream = new InputStreamReader(ClassLoader.getSystemResourceAsStream(fileName));
        List<String> data = new BufferedReader(dataStream).lines().skip(1).collect(Collectors.toList());
        List<T> parsedData = new ArrayList<>(data.size());
        data.forEach(csvString -> parsedData.add(parser.apply(csvString.split(CSV_SPLITTER))));

        return parsedData;
    }

    private Parser<Author> authorParser = (components) -> new Author(components[0], components[1], components[2]);

    private Parser<Book> bookParser = (components) -> {
        String title = components[0];
        String isbn = components[1];
        List<String> authors = Arrays.asList(components[2].split(AUTHOR_SPLITTER));
        String description = components[3];

        return new Book(title, isbn, authors, description);
    };

    private Parser<Magazine> magazineParser = (components) -> {
        String title = components[0];
        String isbn = components[1];
        List<String> authors = Arrays.asList(components[2].split(AUTHOR_SPLITTER));
        LocalDate publishedAt = LocalDate.parse(components[3], MAGAZINE_DATE_FORMATTER);

        return new Magazine(title, isbn, authors, publishedAt);
    };


    public Collection<Publication> findAllPublications()
    {
        return allPublication;
    }
}
