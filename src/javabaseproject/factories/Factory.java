package javabaseproject.factories;

import javabaseproject.javabase.core.database.faker.Fake;
import javabaseproject.models.Book;

import java.util.ArrayList;

public abstract class Factory {
    public abstract Factory make();
}
