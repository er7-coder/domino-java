/**
 * @author ER7
 */

// imports que serão usados na aplicação
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Domino {

    // listas que serão usadas na aplicação
    private ArrayList<Peca> pecasRestantes;
    private ArrayList<Peca> tabuleiro;
    private ArrayList<Jogador> jogadores;

    // construtor
    public Domino() {
        pecasRestantes = new ArrayList<>();
        tabuleiro = new ArrayList<>();
        jogadores = new ArrayList<>();
    }

    // conteudo lista de peças restantes
    public ArrayList<Peca> getPecasRestantes() {
        return pecasRestantes;
    }

    // conteudo lista de jogadores
    public ArrayList<Jogador> getJogadores() {
        return jogadores;
    }

    // conteudo lista de peças do tabuleiro
    public ArrayList<Peca> getTabuleiro() {
        return tabuleiro;
    }

    // geração de peças
    public void gerarPecas() {
        for (int i = 0; i <= 6; i++) {
            for (int j = 0; j <= 6; j++) {
                Peca p = new Peca(i, j);
                if (isEquals(p)) {
                    pecasRestantes.add(p);
                }
            }
        }
    }

    // embaralhamento das peças
    public void embaralharPecas() {
        Collections.shuffle(pecasRestantes);
    }
    
    // verificação para a geração de peças
    public boolean isEquals(Peca peca) {
        for (Peca p : pecasRestantes) {
            if ((peca.getLadoA() != peca.getLadoB()) && (peca.getLadoA() == p.getLadoB() && peca.getLadoB() == p.getLadoA())) {
                return false;
            }
        }
        return true;
    }

    // função para adicionar o jogador
    public boolean adicionarJogador(Jogador j) {
        if (jogadores.size() < 4) {
            jogadores.add(j);
            return true;
        }
        return false;
    }

    // função para distribuir as peças
    public void distribuirPecas(Jogador j) {
        for (int i = 0; i < 7; i++) {
            j.getPecas().add(pecasRestantes.get(0));
            pecasRestantes.remove(0);
        }
    }

    // função para verificar a peça dobrada maior
    public Peca getDobradaMaior() {
        Peca dobrada = null;
        
        for (Jogador j : jogadores) {
            for (Peca p : j.getPecas()) {
                if (p.getLadoA() == p.getLadoB()) {
                    if (dobrada == null) {
                        dobrada = p;
                    } else {
                        if (p.getLadoA() > dobrada.getLadoA()) {
                            dobrada = p;
                        }
                    }
                }
            }
        }
        // se a peça dobrada maior ainda não foi localizada, é escolhido a peça com a maior soma
        if (dobrada == null) {
            int maior = 0;
            
            for (Jogador j : jogadores) {
                for (Peca p : j.getPecas()) {
                    if ((p.getLadoA() + p.getLadoB()) > maior) {
                        dobrada = p;
                    }
                }
            }
        }
        return dobrada;
    }

    // busca o jogador pela peça
    public Jogador buscaJogador(Peca peca) {
        for (Jogador j : jogadores) {
            for (Peca p : j.getPecas()) {
                if (p.getLadoA() == peca.getLadoA() && p.getLadoB() == peca.getLadoB()) {
                    return j;
                }
            }
        }
        return null;
    }

    // função para iniciar o jogo: gera e embaralha as 28 peças, além de colocar a peça dobrada maior de um dos jogadores no tabuleiro
    // Obs: a subtração da peça dobrada maior que está com o jogador é feita no main
    public void iniciaJogo() {
        gerarPecas();
        
        embaralharPecas();
        
        for (Jogador j : jogadores) {
            distribuirPecas(j);
        }
        
        tabuleiro.add(getDobradaMaior());
    }

    // função para validar e realizar a jogada
    public boolean jogada(Jogador j, Peca p) {
        if (j == null) {
            return false;
        } else if (p == null) {
            return false;
        } else {
            Peca umaPeca = buscaPeca(j, p);
            if (umaPeca != null) {
                if (encaixaPeca(p)) {
                    j.getPecas().remove(p);
                    return true;
                }
            }
        }
        return false;
    }

    // função para encaixar a peça escolhida no tabuleiro
    public boolean encaixaPeca(Peca p) {
        if (tabuleiro.size() == 1) {
            if (p.getLadoA() == tabuleiro.get(0).getLadoA()) {
                invertePeca(p);
                tabuleiro.add(0, p);
                return true;
            } else if (p.getLadoA() == tabuleiro.get(0).getLadoB()) {
                tabuleiro.add(p);
                return true;
            } else if (p.getLadoB() == tabuleiro.get(0).getLadoA()) {
                tabuleiro.add(0, p);
                return true;
            } else if (p.getLadoB() == tabuleiro.get(0).getLadoB()) {
                invertePeca(p);
                tabuleiro.add(p);
                return true;
            } else {
                return false;
            }
        } else {
            Peca primeira = tabuleiro.get(0);
            Peca ultima = tabuleiro.get(tabuleiro.size() - 1);
            if (p.getLadoA() == primeira.getLadoA()) {
                invertePeca(p);
                tabuleiro.add(0, p);
                return true;
            } else if (p.getLadoA() == ultima.getLadoB()) {
                tabuleiro.add(p);
                return true;
            } else if (p.getLadoB() == primeira.getLadoA()) {
                tabuleiro.add(0, p);
                return true;
            } else if (p.getLadoB() == ultima.getLadoB()) {
                invertePeca(p);
                tabuleiro.add(p);
                return true;
            } else {
                return false;
            }
        }
    }

    // função para inverter a peça escolhida
    public void invertePeca(Peca p) {
        int aux = p.getLadoA();
        
        p.setLadoA(p.getLadoB());
        
        p.setLadoB(aux);
    }

    // função para busca e peça, solicitando o jogador e a peça requerida
    public Peca buscaPeca(Jogador j, Peca p) {
        for (Peca peca : j.getPecas()) {
            if (p.getLadoA() == peca.getLadoA() && p.getLadoB() == peca.getLadoB()) {
                return peca;
            }
        }
        return null;
    }

    // função para buscar a peça pelo index
    public Peca buscaPecaPeloIndex(Jogador j, int index) {
        if (index < 0 || index > j.getPecas().size()) {
            return null;
        }
        return j.getPecas().get(index);
    }

    // função para buscar o index do jogador pelo seu nome
    public int buscaIndexJogador(String nome) {
        for (int i = 0; i < jogadores.size(); i++) {
            if (jogadores.get(i).getNome().equalsIgnoreCase(nome)) {
                return i;
            }
        }
        return -1;
    }

    // função para comprar peças
    public boolean comprar(Jogador j) {
        if (!pecasRestantes.isEmpty()) {
            Random r = new Random();
            
            int indexSorteado = r.nextInt(pecasRestantes.size());
            
            Peca pecaSorteada = pecasRestantes.get(indexSorteado);
            
            j.getPecas().add(pecaSorteada);
            
            pecasRestantes.remove(pecaSorteada);
            
            return true;
        }
        return false;
    }

    // função para validar um possível empate no jogo
    // caso o dorme esteja vazio e nenhum jogador tenha as peças solicitadas pelo tabuleiro é considera o empate
    public boolean isEmpate() {
        if (pecasRestantes.isEmpty()) {
            Peca primeira = tabuleiro.get(0);
            
            Peca ultima = tabuleiro.get(tabuleiro.size() - 1);
            
            for (Jogador j : jogadores) {
                for (Peca p : j.getPecas()) {
                    if (p.getLadoA() == primeira.getLadoA() || p.getLadoB() == primeira.getLadoA() || p.getLadoA() == ultima.getLadoB() || p.getLadoB() == ultima.getLadoB()) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    // função para mostrar o estado atual do jogo
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append("\n").append("Tabuleiro:").append("\t");
        if (!tabuleiro.isEmpty()) {

            for (Peca p : tabuleiro) {
                str.append(p);
            }
        }

        str.append("\n").append("\n").append("Dorme:").append("\t");

        if (pecasRestantes.isEmpty()) {
            str.append("Vazio...");
        } else {
            for (Peca p : pecasRestantes) {
                str.append(p).append(" ");
            }
        }

        for (Jogador j : jogadores) {
            str.append("\n").append("\n").append("Jogador: ").append(j.getNome()).append("\n");

            str.append("Index\t");

            for (int i = 0; i < j.getPecas().size(); i++) {
                str.append("  ").append(i).append("\t");
            }

            str.append("\n").append("Peças\t");

            for (Peca p : j.getPecas()) {
                str.append(p).append("\t");
            }
        }

        str.append("\n");

        return str.toString();
    }

    // função para mostrar uma mensagem final ao jogador vencedor
    public String winner(Jogador j) {
        StringBuilder str = new StringBuilder();
        str.append("─▄█▀█▄──▄███▄─").append("\n");
        str.append("▐█░██████████▌").append("\n");
        str.append("─██▒ ").append(j.getNome()).append(" ███─").append("\n");
        str.append("──▀████████▀── ").append("\n");
        str.append("─────▀██▀─────-").append("\n");
        return str.toString();
    }

    // função para limpar a tela 
    public void limparSaida() {
        try {
            Robot robot = new Robot();
            robot.setAutoDelay(10);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_L);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_L);
        } catch (AWTException ex) {
        }
    }
}