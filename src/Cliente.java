import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Cliente {
    private String nome;
    private ArrayList<Cartao> cartoes;
    private TipoCliente tipo;
    private PerfilDeConsumo perfil;
    NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.of("pt", "BR"));

    public Cliente(String nome, TipoCliente tipo, PerfilDeConsumo perfil) {
        this.nome = nome;
        this.tipo = tipo;
        this.perfil = perfil;
        this.cartoes = new ArrayList<Cartao>();

        Toast.sucesso(String.format("Cliente %s criado com sucesso", nome));
    }

    public String getNome() {
        return this.nome;
    }

    public TipoCliente getTipo() {
        return this.tipo;
    }

    public PerfilDeConsumo getPerfil() {
        return this.perfil;
    }

    public void adicionarCartao(Cartao cartao) {
        this.cartoes.add(cartao);
    }

    public void comprar(String descricao, double valor, Cartao cartao) {
        if (!this.cartoes.contains(cartao)) {
            System.out.println("Cartão não pertence ao cliente");
            return;
        }
        for (Cartao c : this.cartoes) {
            if (c.getNumero().equals(cartao.getNumero())) {
                c.realizarCompra(descricao, valor);
                System.out.println("Compra realizada com sucesso");
                break;
            }
        }
    }

    public void gerarRelatorioDeTransacoes() {
        Toast.aviso(String.format("RELATORIO DE TRANSACOES (%s)", this.nome));
        for (Cartao c : cartoes) {
            c.gerarExtrato();
        }
    }

    public void gerarFatura() {
        double saldoTotal = 0;
        for (Cartao c : cartoes) {
            saldoTotal += c.verificarSaldo();
        }

        Toast.erro(String.format("Total a pagar: %s", nf.format(Math.abs(saldoTotal))));
    }
}
