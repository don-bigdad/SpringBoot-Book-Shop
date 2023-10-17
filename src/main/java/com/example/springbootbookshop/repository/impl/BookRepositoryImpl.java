package com.example.springbootbookshop.repository.impl;

import com.example.springbootbookshop.entity.Book;
import com.example.springbootbookshop.exception.DataProcessingException;
import com.example.springbootbookshop.exception.EntityNotFoundException;
import com.example.springbootbookshop.repository.BookRepository;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepositoryImpl implements BookRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public BookRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Book save(Book book) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(book);
            transaction.commit();
            return book;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can`t save book in DB:" + book, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Book> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Book",Book.class)
                    .getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can`t get all books from DB", e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        try {
            return Optional.ofNullable(sessionFactory
                    .fromSession(s -> s.find(Book.class, id)));
        } catch (Exception e) {
            throw new EntityNotFoundException("Can`t get Book from DB with id: "
                    + id, e);
        }
    }
}
