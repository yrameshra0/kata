package org.echocat.kata.java.part1;

import static java.util.Collections.unmodifiableList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.echocat.kata.java.part1.domain.Book;
import org.echocat.kata.java.part1.domain.Magazine;
import org.echocat.kata.java.part1.domain.Publication;
import org.echocat.kata.java.part1.exception.InformationNotFoundException;

public class PublicationsRepository
{
    private static final String BOOKS_CSV = "org/echocat/kata/java/part1/data/books.csv";
    private static final String MAGAZINES_CSV = "org/echocat/kata/java/part1/data/magazines.csv";
    private static final String CSV_SPLITTER = ";";
    private static final String AUTHOR_SPLITTER = ",";
    private final List<Publication> allPublication;


    public PublicationsRepository()
    {
        this.allPublication = Stream.concat(
            parseData(BOOKS_CSV, bookParser).stream(),
            parseData(MAGAZINES_CSV, magazineParser).stream())
            .collect(Collectors.toList());

    }


    private <T> List<T> parseData(String fileName, Function<String[], T> parser)
    {
        InputStreamReader dataStream = new InputStreamReader(ClassLoader.getSystemResourceAsStream(fileName));
        List<String> data = new BufferedReader(dataStream).lines().skip(1).collect(Collectors.toList());
        List<T> parsedData = new ArrayList<>(data.size());
        data.forEach(csvString -> parsedData.add(parser.apply(csvString.split(CSV_SPLITTER))));

        return parsedData;
    }

    private Function<String[], Book> bookParser = (components) -> new Book(components[0], components[1], parseAuthors(components[2]), components[3]);
    private Function<String[], Magazine> magazineParser = (components) -> new Magazine(components[0], components[1], parseAuthors(components[2]), components[3]);


    private List<String> parseAuthors(String authorRaw)
    {
        return Arrays.asList(authorRaw.split(AUTHOR_SPLITTER));
    }


    public List<Publication> findAllPublications()
    {
        return unmodifiableList(allPublication);
    }


    public Publication findByISBN(String isbn)
    {
        return allPublication.stream()
            .filter(publication -> publication.isbn.equals(isbn))
            .findFirst()
            .orElseThrow(() -> new InformationNotFoundException(String.format("No data found with ISBN : %s", isbn)));
    }


    public List<Publication> findByAuthor(String authorEmailId)
    {
        return allPublication.stream()
            .filter(publication -> publication.authors.contains(authorEmailId))
            .collect(Collectors.toList());
    }


    public List<Publication> findAllPublicationsSortedByTitle()
    {
        return allPublication.stream()
            .sorted(Comparator.comparing(publication -> publication.title))
            .collect(Collectors.toList());
    }
}
