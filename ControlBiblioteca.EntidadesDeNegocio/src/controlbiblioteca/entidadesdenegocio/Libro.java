
package controlbiblioteca.entidadesdenegocio;


public class Libro {
    
    private int id;
    private int idAutor;
    private int idEditorial;
    private int idCategoria;
    private String titulo;
    private String anyo;
    private String edicion;
    private int top_aux;
    private Autor autor;
    private Editorial editorial;
    private Categoria categoria;

    public Libro() {
    }

    public Libro(int id, int idAutor, int idEditorial, int idCategoria, String titulo, String anyo, String edicion) {
        this.id = id;
        this.idAutor = idAutor;
        this.idEditorial = idEditorial;
        this.idCategoria = idCategoria;
        this.titulo = titulo;
        this.anyo = anyo;
        this.edicion = edicion;
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }

    public int getIdEditorial() {
        return idEditorial;
    }

    public void setIdEditorial(int idEditorial) {
        this.idEditorial = idEditorial;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAnyo() {
        return anyo;
    }

    public void setAnyo(String anyo) {
        this.anyo = anyo;
    }

    public String getEdicion() {
        return edicion;
    }

    public void setEdicion(String edicion) {
        this.edicion = edicion;
    }

    public int getTop_aux() {
        return top_aux;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Editorial getEditorial() {
        return editorial;
    }

    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    
    }
    
    
    
    

