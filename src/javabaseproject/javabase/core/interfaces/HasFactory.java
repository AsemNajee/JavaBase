package javabaseproject.javabase.core.interfaces;

import java.util.ArrayList;

public interface HasFactory<T>{
    ArrayList<T> make(int count);
    T make();
}
