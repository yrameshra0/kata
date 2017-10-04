package org.echocat.kata.java.part1;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.echocat.kata.java.part1.domain.Book;
import org.echocat.kata.java.part1.domain.Magazine;
import org.echocat.kata.java.part1.domain.Publication;
import org.echocat.kata.java.part1.exception.InformationNotFoundException;
import org.junit.Test;

public class AuthorWithPublicationsRepositoryTest
{

    private PublicationsRepository repository = new PublicationsRepository();
    private static final Book ANY_BOOK = new Book(
        "Das Perfekte Dinner. Die besten Rezepte",
        "2221-5548-8585",
        Arrays.asList("null-lieblich@echocat.org"),
        "Sie wollen auch ein perfektes Dinner kreieren? Mit diesem Buch gelingt es Ihnen!");

    private static final Magazine ANY_MAGAZINE = new Magazine(
        "Gourmet",
        "2365-8745-7854",
        Arrays.asList("null-ferdinand@echocat.org"),
        "14.06.2010"

    );


    @Test
    public void find_all_publications() throws Exception
    {
        List<Publication> allPublications = repository.findAllPublications();
        assertThat(allPublications.size(), is(14));
        assertThat(allPublications, hasItem(ANY_BOOK));
        assertThat(allPublications, hasItem(ANY_MAGAZINE));
    }


    @Test
    public void find_book_by_isbn() throws Exception
    {
        assertThat(repository.findByISBN("2221-5548-8585"), is(ANY_BOOK));
        assertThat(repository.findByISBN("2365-8745-7854"), is(ANY_MAGAZINE));
    }


    @Test
    public void throws_exception_for_missing_isbn() throws Exception
    {
        try
        {
            repository.findByISBN("UNKNOWN");
            fail("Expecting InformationNotFoundException exception");
        }
        catch (InformationNotFoundException e)
        {
            assertThat(e.getMessage(), is("No data found with ISBN : UNKNOWN"));
        }
    }


    @Test
    public void find_all_publications_for_authors() throws Exception
    {
        List<Publication> publications = repository.findByAuthor("null-lieblich@echocat.org");
        assertThat(publications.size(), is(4));
        assertThat(publications, hasItem(ANY_BOOK));
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
            "21.05.2011");
        Publication expectedLastPublication = new Magazine(
            "Vinum",
            "1313-4545-8875",
            Arrays.asList("null-gustafsson@echocat.org"),
            "23.02.2012");

        assertThat(firstPublication, is(expectedFirstPublication));
        assertThat(lastPublication, is(expectedLastPublication));
    }
}
