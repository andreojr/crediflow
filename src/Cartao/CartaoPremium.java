package Cartao;

import java.time.LocalDateTime;

import Transacao.TipoTransacao;
import Transacao.Transacao;

public class CartaoPremium extends Cartao {
    private static final float taxaLimiteExtra = .1f;
    private float taxaCashback;

    public CartaoPremium(double limitePersonalizado, float taxaCashback) {
        super(limitePersonalizado * taxaLimiteExtra);
        this.taxaCashback = taxaCashback > .5f ? .5f : taxaCashback;
    }

    public void realizarCompra(String descricao, double valor, TipoTransacao tipo, LocalDateTime when) {
        super.realizarCompra(descricao, valor, tipo, when);
        super.adicionarTransacao(new Transacao(valor * taxaCashback, "CASHBACK", this, TipoTransacao.CASHBACK));
    }
}
