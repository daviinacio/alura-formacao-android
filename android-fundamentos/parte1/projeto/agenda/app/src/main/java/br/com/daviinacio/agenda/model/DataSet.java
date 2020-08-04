package br.com.daviinacio.agenda.model;

import java.util.List;

// Created by daviinacio on 03/08/2020.
public interface DataSet<E> {
    void insert(E e);
    void update(E e);
    void delete(E e);

    List<E> select(String where, String order);
}
