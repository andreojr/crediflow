package Cartao;

public class CartaoBasico extends Cartao {
    private static final double limiteBasico = 1000;

    public CartaoBasico() {
        super(limiteBasico);
    }
}
