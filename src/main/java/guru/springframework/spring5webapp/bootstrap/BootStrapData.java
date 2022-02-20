package guru.springframework.spring5webapp.bootstrap;

import guru.springframework.spring5webapp.model.Author;
import guru.springframework.spring5webapp.model.Book;
import guru.springframework.spring5webapp.model.Publisher;
import guru.springframework.spring5webapp.repositories.AuthorRepository;
import guru.springframework.spring5webapp.repositories.BookRepository;
import guru.springframework.spring5webapp.repositories.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootStrapData implements CommandLineRunner {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    public BootStrapData(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Started in Bootsrap");

        Author eric = new Author("Eric", "Evans");
        Book ddd = new Book("Domain Driven Design", "1123546");
        eric.getBooks().add(ddd);
        ddd.getAuthors().add(eric);

        Publisher hachette = new Publisher("Hachette", "1 rue du Landrie 13265 Pouletcity");
        publisherRepository.save(hachette); // must be inserted into DB before binding it to books (otherwise exception throwed : object references an unsaved transient instance - save the transient instance before flushing )
        ddd.setPublisher(hachette);
        hachette.getBooks().add(ddd);

        // Spring Data JPA uses Hibernate to save these datas in the H2 DB
        authorRepository.save(eric);
        bookRepository.save(ddd);

        Author rod = new Author("Rod", "Johnson");
        Book noEjb = new Book("JEE dev without EJB", "114569823546");
        rod.getBooks().add(noEjb);
        noEjb.getAuthors().add(rod);
        noEjb.setPublisher(hachette);
        hachette.getBooks().add(noEjb);

        authorRepository.save(rod);
        bookRepository.save(noEjb);

        publisherRepository.save(hachette);

        System.out.println("Hachette books : "+hachette.getBooks().size());
        System.out.println("Number of books : "+bookRepository.count());
    }
}
