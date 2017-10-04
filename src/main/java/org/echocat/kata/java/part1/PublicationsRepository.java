package org.echocat.kata.java.part1;

import java.util.List;

import org.echocat.kata.java.part1.domain.Publication;

public interface PublicationsRepository
{
    List<Publication> findAllPublications();


    Publication findByISBN(String isbn);


    List<Publication> findByAuthor(String authorEmailId);


    List<Publication> findAllPublicationsSortedByTitle();
}
