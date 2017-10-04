package org.echocat.kata.java.part1;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.echocat.kata.java.part1.domain.Book;
import org.echocat.kata.java.part1.domain.Magazine;
import org.echocat.kata.java.part1.domain.Publication;
import org.junit.Test;

public class AuthorWithPublicationsRepositoryTest
{

    private AuthorsWithPublicationsRepository repository = new AuthorsWithPublicationsRepository();
    private final Book book = new Book(
        "Das Perfekte Dinner. Die besten Rezepte",
        "2221-5548-8585",
        Arrays.asList("null-lieblich@echocat.org"),
        "Sie wollen auch ein perfektes Dinner kreieren? Mit diesem Buch gelingt es Ihnen!");

    private final Magazine magazine = new Magazine(
        "Gourmet",
        "2365-8745-7854",
        Arrays.asList("null-ferdinand@echocat.org"),
        LocalDate.of(2010, 6, 14)

    );


    @Test
    public void find_all_publications() throws Exception
    {
        List<Publication> allPublications = repository.findAllPublications();
        assertThat(allPublications.size(), is(14));
        assertThat(allPublications, hasItem(book));
        assertThat(allPublications, hasItem(magazine));
    }


    @Test
    public void find_book_by_isbn() throws Exception
    {
        assertThat(repository.findByISBN("2221-5548-8585"), is(book));
        assertThat(repository.findByISBN("2365-8745-7854"), is(magazine));
    }


    @Test
    public void find_all_publications_for_authors() throws Exception
    {
        List<Publication> publications = repository.findByAuthor("null-lieblich@echocat.org");
        assertThat(publications.size(), is(4));
        assertThat(publications, hasItem(book));
    }


    @Test
    public void find_all_publications_sorted_by_title() throws Exception
    {
        List<Publication> allPublications = repository.findAllPublicationsSortedByTitle();
        Publication firstPublication = allPublications.get(0);
        Publication lastPublication = allPublications.get(allPublications.size() - 1);

        Publication expectedFirstPublication = new Magazine(
            "Beautiful cooking",
            "5454-5587-3210",
            Arrays.asList("null-walter@echocat.org"),
            LocalDate.of(2011, 5, 21));
        Publication expectedLastPublication = new Magazine(
            "Vinum",
            "1313-4545-8875",
            Arrays.asList("null-gustafsson@echocat.org"),
            LocalDate.of(2012, 2, 23));

        assertThat(firstPublication, is(expectedFirstPublication));
        assertThat(lastPublication, is(expectedLastPublication));
    }
}
