package Cartao;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;
import java.util.Random;

import Transacao.TipoTransacao;
import Transacao.Transacao;
import UI.Toast;

public abstract class Cartao {
    private String numero;
    private double limite;
    private boolean bloqueado;
    private ArrayList<Transacao> transacoes;
    NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.of("pt", "BR"));

    public Cartao(double limitePersonalizado) {
        this.numero = gerarNumero();
        this.transacoes = new ArrayList<Transacao>();
        this.limite = limitePersonalizado;
    }

    public String getFinal() {
        return this.numero.substring(this.numero.length() - 4);
    }

    public String getNumero() {
        return numero;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public double getLimite() {
        return limite;
    }

    public void adicionarTransacao(Transacao transacao) {
        transacoes.add(transacao);
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

    public void realizarCompra(String descricao, double valor, TipoTransacao tipo, LocalDateTime when) {
        if (bloqueado) {
            Toast.erro(String.format("Nao foi possivel criar transaçao: cartao com final %s bloqueado", getFinal()));
            return;
        }
        if (valor == 0)
            return;
        adicionarTransacao(new Transacao(valor, descricao, this, tipo, when));
    }

    public void bloquear() {
        this.bloqueado = true;
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
        Toast.aviso(String.format("EXTRATO (%s)", getFinal()));
        // Ordenar a partir das transacoes mais recentes
        transacoes.sort(Comparator.comparing(Transacao::getCriadoEm).reversed());
        for (Transacao transacao : transacoes) {
            transacao.exibir();
        }
    }

    public void gerarExtrato(LocalDateTime inicio, LocalDateTime fim) {
        Toast.aviso(String.format("EXTRATO (%s) - PERÍODO: %s até %s", getFinal(), inicio, fim));
    
        // Ordenar as transações por ordem decrescente (mais recentes primeiro)
        transacoes.sort(Comparator.comparing(Transacao::getCriadoEm).reversed());
    
        // Filtrar e exibir as transações no intervalo fornecido
        for (Transacao transacao : transacoes) {
            if (transacao.getLocalDateTime().isAfter(inicio) && transacao.getLocalDateTime().isBefore(fim)) {
                transacao.exibir();
            }
        }
    }

    public void gerarExtrato(TipoTransacao tipo) {
        Toast.aviso(String.format("EXTRATO (%s) - TIPO: %s", getFinal(), tipo.name()));
    
        // Ordenar as transações por ordem decrescente (mais recentes primeiro)
        transacoes.sort(Comparator.comparing(Transacao::getCriadoEm).reversed());
    
        // Filtrar e exibir as transações por tipo fornecido
        for (Transacao transacao : transacoes) {
            if (transacao.getTipo() == tipo) {
                transacao.exibir();
            }
        }
    }
}
