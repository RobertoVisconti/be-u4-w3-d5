package robertovisconti.exceptions;

public class IsbnNonTrovatoException extends RuntimeException {
    public IsbnNonTrovatoException(String isbn) {
        super("Impossibile trovare l'elemento con ISBN[" + isbn + "] all'interno del catalogo.");
    }
}
