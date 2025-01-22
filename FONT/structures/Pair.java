package structures;

/**
 * La clase Pair es un contenedor genérico para almacenar un par de objetos relacionados.
 * Proporciona métodos para recuperar el primer y el segundo valor del par.
 *
 * @param <F> el tipo del primer valor
 * @param <S> el tipo del segundo valor
 */
public class Pair<F, S> {
    private final F first;  // Primer valor del par
    private final S second; // Segundo valor del par

    /**
     * Constructor de la clase Pair.
     * @param first El primer valor del par.
     * @param second El segundo valor del par.
     */
    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Devuelve el primer valor del par.
     * @return first
     */
    public F getFirst() {
        return first;
    }

    /**
     * Devuelve el segundo valor del par.
     * @return second
     */
    public S getSecond() {
        return second;
    }

    /**
     * Devuelve el par en formato de cadena.
     * @return first y second
     */
    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}
