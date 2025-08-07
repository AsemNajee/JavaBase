package reviewjdb.model;

public class Book extends Model<Book>{
    protected String name;
    protected String author;
    protected int pageCount;
    
    public Book(){
        super(Book.class);
    }
}
