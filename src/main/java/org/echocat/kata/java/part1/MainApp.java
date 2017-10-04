package org.echocat.kata.java.part1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.echocat.kata.java.part1.domain.Publication;
import org.echocat.kata.java.part1.exception.InformationNotFoundException;

@SuppressWarnings("UseOfSystemOutOrSystemErr")
public class MainApp
{

    public static final String FORMATTER = "%s";

    private enum ConsoleOptions
    {
        ALL(1, "Look at all books and magazines."),
        FIND_BY_ISBN(2, "Find a book or magazine by its isbn."),
        FIND_BY_AUTHOR(3, "Find all books and magazines by their authors’ email."),
        ALL_SORTED_TITLE(4, "Look at all books and magazines with all their details sorted by title.");

        private final String sequenceNo;
        private final String description;


        ConsoleOptions(int sequenceNo, String description)
        {
            this.sequenceNo = String.valueOf(sequenceNo);
            this.description = description;
        }


        public static String formattedOptions()
        {
            return Arrays.stream(values())
                .map(value -> String.format("%s. \t%s", value.sequenceNo, value.description))
                .collect(Collectors.joining("\n"));
        }


        public static ConsoleOptions findBySequenceNo(String sequenceNo)
        {
            Optional<ConsoleOptions> option = Arrays.stream(values())
                .filter(value -> value.sequenceNo.equals(sequenceNo))
                .findFirst();

            return option.orElseThrow(() -> new IllegalArgumentException(String.format("%s is not a valid input", sequenceNo)));
        }
    }


    public static void main(String[] args) throws IOException
    {
        PublicationsRepository repository = new PublicationsRepository();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print(ConsoleOptions.formattedOptions());
        ConsoleOptions selectedOption = ConsoleOptions.findBySequenceNo(br.readLine());
        switch (selectedOption)
        {
            case ALL:
                System.out.print("Title\tISBN\tAUTHOR(s)\tDATE/DESCRIPTION");
                System.out.print(formattedData(repository.findAllPublications()));
                break;

            case FIND_BY_ISBN:
                System.out.print("Please enter ISBN:");
                String isbn = br.readLine();

                try
                {
                    System.out.print(repository.findByISBN(isbn));
                }
                catch (InformationNotFoundException e)
                {
                    System.out.print(e.getMessage());
                }

                break;

            case FIND_BY_AUTHOR:
                System.out.print("Please enter Author Email:");
                String authorEmail = br.readLine();
                System.out.print(repository.findByAuthor(authorEmail));

                break;

            case ALL_SORTED_TITLE:
                System.out.print("Title\tISBN\tAUTHOR(s)\tDATE/DESCRIPTION");
                System.out.print(formattedData(repository.findAllPublicationsSortedByTitle()));
                break;
        }

    }


    private static String formattedData(List<Publication> publications)
    {
        return publications.stream().map(Publication::toString).collect(Collectors.joining("\n"));
    }


    protected static String getHelloWorldText()
    {
        return "Hello world!";
    }

}
