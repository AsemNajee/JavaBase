 /**
  *   >> Al-Reecha .~
  *   << BY : Asem Najee >>
  */

package reviewjdb.model;

import java.util.ArrayList;

/**
 * @Coder Asem Najee
 * @author Al-Reecha
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

    
}
