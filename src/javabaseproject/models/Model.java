package javabaseproject.models;

import javabaseproject.javabase.core.Recorder;
import javabaseproject.javabase.core.database.DBMS;
import javabaseproject.javabase.core.database.querybuilders.Build;

import java.util.ArrayList;

/**
 * 
 * @author AsemNajee
 */
public class Model<T> extends AbstractModel<T> {
    
    private Class clazz;
    
    protected Model(Class clazz){
        this.clazz = clazz;
    }

    @Override
    public T find(int id) throws Exception {
        return super.find(id, clazz);
    }

    @Override
    public ArrayList<T> getAll() throws Exception {
        return super.getAll(clazz);
    }

    public boolean save() throws Exception{
        return super.save(this);
    }

    public boolean dropTable() throws Exception {
        return DBMS.execute(Build.dropTable(Recorder.getRecordedClass(clazz)));
    }
}
