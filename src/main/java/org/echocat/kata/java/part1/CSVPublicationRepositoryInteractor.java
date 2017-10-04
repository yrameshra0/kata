package org.echocat.kata.java.part1;

import static java.util.Collections.unmodifiableList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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

public class CSVPublicationRepositoryInteractor implements PublicationsRepository
{
    private static final String BOOKS_CSV = "org/echocat/kata/java/part1/data/books.csv";
    private static final String MAGAZINES_CSV = "org/echocat/kata/java/part1/data/magazines.csv";
    private static final String CSV_SPLITTER = ";";
    private static final String AUTHOR_SPLITTER = ",";
    private final List<Publication> allPublication;


    public CSVPublicationRepositoryInteractor()
    {
        this.allPublication = Stream.concat(
            parseData(BOOKS_CSV, bookInstanceCreator),
            parseData(MAGAZINES_CSV, magazineInstanceCreator))
            .collect(Collectors.toList());

    }


    private <T> Stream<T> parseData(String fileName, Function<String[], T> newInstanceCreator)
    {
        InputStreamReader dataStream = new InputStreamReader(ClassLoader.getSystemResourceAsStream(fileName));
        BufferedReader dataReader = new BufferedReader(dataStream);
        return dataReader
            .lines()
            .skip(1)
            .map(csvString -> csvString.split(CSV_SPLITTER))
            .map(newInstanceCreator);
    }

    private Function<String[], Book> bookInstanceCreator = (components) -> new Book(components[0], components[1], parseAuthors(components[2]), components[3]);
    private Function<String[], Magazine> magazineInstanceCreator = (components) -> new Magazine(components[0], components[1], parseAuthors(components[2]), components[3]);


    private List<String> parseAuthors(String authorRaw)
    {
        return Arrays.asList(authorRaw.split(AUTHOR_SPLITTER));
    }


    @Override
    public List<Publication> findAllPublications()
    {
        return unmodifiableList(allPublication);
    }


    @Override
    public Publication findByISBN(String isbn)
    {
        return allPublication.stream()
            .filter(publication -> publication.isbn.equals(isbn))
            .findFirst()
            .orElseThrow(() -> new InformationNotFoundException(String.format("No data found with ISBN : %s", isbn)));
    }


    @Override
    public List<Publication> findByAuthor(String authorEmailId)
    {
        return allPublication.stream()
            .filter(publication -> publication.authors.contains(authorEmailId))
            .collect(Collectors.toList());
    }


    @Override
    public List<Publication> findAllPublicationsSortedByTitle()
    {
        return allPublication.stream()
            .sorted(Comparator.comparing(publication -> publication.title))
            .collect(Collectors.toList());
    }
}
