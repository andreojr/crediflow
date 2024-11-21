import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class Cartao {
    private String numero;
    private Cliente cliente;
    private double limite;
    private double saldo;
    private ArrayList<Transacao> transacoes;
    NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.of("pt", "BR"));

    public Cartao(Cliente cliente) {
        this.numero = gerarNumero();
        this.cliente = cliente;
        this.transacoes = new ArrayList<Transacao>();
        switch (this.cliente.getTipo()) {
            case TipoCliente.FISICO:
                if (this.cliente.getPerfil() == PerfilDeConsumo.VIP) {
                    this.limite = 5000;
                } else {
                    this.limite = 1000;
                }
                break;
            case TipoCliente.JURIDICO:
                if (this.cliente.getPerfil() == PerfilDeConsumo.VIP) {
                    this.limite = 10000;
                } else {
                    this.limite = 5000;
                }
                break;
            default:
                break;
        }
        this.saldo = 0;
        cliente.adicionarCartao(this);

        Toast.sucesso(String.format("Cartao com final %s criado com sucesso para %s", numero.substring(numero.length() - 4), cliente.getNome()));
    }

    public String getNumero() {
        return this.numero;
    }

    public double getLimite() {
        return this.limite;
    }

    public double getSaldo() {
        return saldo;
    }

    public String gerarNumero() {
        Random random = new Random();
        StringBuilder numero = new StringBuilder();

        for (int i = 0; i < 16; i++) {
            numero.append(random.nextInt(10));
        }

        return numero.toString();
    }

    public void realizarCompra(String descricao, double valor) {
        if (this.saldo + valor > this.limite) {
            Toast.erro("Compra não realizada: saldo insuficiente");
        } else {
            this.saldo += valor;
            this.limite -= valor;
            this.transacoes.add(new Transacao(valor, descricao, this, TipoTransacao.COMPRA));
            Toast.sucesso("Compra realizada com sucesso");
        }
    }

    public void verificarLimite() {
        System.out.println("Limite disponivel: " + nf.format(limite));
    }

    public void gerarFatura() {
        System.out.println("Fatura: " + nf.format(saldo));
    }

    public void gerarExtrato() {
        for (Transacao transacao : this.transacoes) {
            char simbolo = transacao.getTipo() == TipoTransacao.COMPRA ? '-' : '+';
            System.out.println(String.format("%s %s | %s | %s", simbolo, transacao.getValor(), transacao.getDescricao(), transacao.getCriadoEm()));
        }
    }
}
