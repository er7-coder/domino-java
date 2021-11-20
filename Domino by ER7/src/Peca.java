/**
 * @author ER7
 */

public class Peca {
    private int ladoA;
    private int ladoB;

    public Peca(int ladoA, int ladoB) {
        this.ladoA = ladoA;
        this.ladoB = ladoB;
    }

    public int getLadoA() {
        return ladoA;
    }

    public int getLadoB() {
        return ladoB;
    }

    public void setLadoA(int ladoA) {
        this.ladoA = ladoA;
    }

    public void setLadoB(int ladoB) {
        this.ladoB = ladoB;
    }

    @Override
    public String toString() {
        return "[" + ladoA + "|" + ladoB + "]";
    }
}