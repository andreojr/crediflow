import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class Cartao {
    private String numero;
    private double limite;
    private double taxaCashback;
    private boolean bloqueado;
    private Transacao transacaoAtiva;
    private ArrayList<Transacao> transacoes;
    NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.of("pt", "BR"));

    public Cartao(double limitePersonalizado, double taxaCashback) {
        this.numero = gerarNumero();
        this.transacoes = new ArrayList<Transacao>();
        this.limite = limitePersonalizado;
        this.taxaCashback = taxaCashback > .5 ? .5 : taxaCashback;
    }

    public String getFinal() {
        return this.numero.substring(this.numero.length() - 4);
    }

    public String getNumero() {
        return numero;
    }

    public double getTaxaCashback() {
        return taxaCashback;
    }

    public boolean hasCashback() {
        return taxaCashback > 0;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public double getLimite() {
        return limite;
    }

    public Transacao getTransacaoAtiva() {
        return transacaoAtiva;
    }

    private String gerarNumero() {
        Random random = new Random();
        StringBuilder numero = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            numero.append(random.nextInt(10));
        }
        return numero.toString();
    }
    
    public boolean validarCompra(double valor) {
        if (verificarSaldo() + valor > verificarLimiteDisponivel()) {
            Toast.erro("Compra não realizada: saldo insuficiente");
            return false;
        }
        return true;
    }

    public void realizarTransacao(String descricao, double valor, TipoTransacao tipo) {
        if (bloqueado) {
            Toast.erro(String.format("Nao foi possivel criar transaçao: cartao com final %s bloqueado", getFinal()));
            return;
        }
        if (valor == 0)
            return;
        if (transacaoAtiva != null) {
            Toast.erro("Transação em andamento");
            return;
        }
        transacaoAtiva = new Transacao(valor, descricao, this, tipo);
    }

    public void concluirTransacao() {
        if (this.transacaoAtiva != null) {
            Toast.sucesso(String.format("%s realizada com sucesso", transacaoAtiva.getTipo().name()));
            this.transacaoAtiva.concluirTransacao();
            this.transacoes.add(this.transacaoAtiva);
            this.transacaoAtiva = null;
        }
    }

    public void bloquear() {
        this.bloqueado = true;
        this.transacaoAtiva = null;
        Toast.aviso(String.format("O cartao com final %s foi bloqueado", getFinal()));
    }

    public double verificarLimiteDisponivel() {
        return limite - verificarSaldo();
    }

    public double verificarSaldo() {
        double saldo = 0;
        for (Transacao t : transacoes) {
            saldo += t.getValorNumerico();
        }
        return saldo;
    }

    public void gerarExtrato() {
        for (Transacao transacao : this.transacoes) {
            char simbolo = '+';
            String color = Toast.verde();
            if (TipoTransacao.COMPRA == transacao.getTipo()) {
                simbolo = '-';
                color = Toast.vermelho();
            }
            
            System.out.println(String.format("%s | %s | %s", String.format("%s%s %s%s", color, simbolo, transacao.getValor(), Toast.reset()), transacao.getDescricao(), transacao.getCriadoEm()));
        }
    }
}
