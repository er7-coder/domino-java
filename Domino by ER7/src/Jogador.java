/**
 * @author ER7
 */

import java.util.ArrayList;

public class Jogador {
    private String nome;
    private ArrayList<Peca> pecas;

    public Jogador(String nome) {
        this.nome = nome;
        pecas = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public ArrayList<Peca> getPecas() {
        return pecas;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Jogador: ").append(nome);
        str.append("\n").append("index: ");
        for(int i = 0; i < pecas.size(); i++){
            str.append(i).append("\t");
        }
        str.append("\n").append("peças: ");
        for(Peca p : pecas){
            str.append(p).append("\t");
        }
        str.append("\n");
        return str.toString();
    }
}
