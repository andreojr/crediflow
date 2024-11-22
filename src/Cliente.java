import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;

public class Cliente {
    private String nome;
    private ArrayList<Cartao> cartoes;
    private TipoCliente tipo;
    private Cartao cartaoAtual;
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

    public Cartao getCartaoAtual() {
        return this.cartaoAtual;
    }

    public void adicionarCartao(Cartao cartao) {
        if (cartao.hasCashback() && perfil == PerfilDeConsumo.SIMPLES) {
            Toast.erro("Nao foi possivel vincular cartao: cliente simples nao possui acesso ao beneficio de cashback");
            return;
        }
        this.cartoes.add(cartao);
        Toast.sucesso(String.format("Cartao com final %s vinculado a %s", cartao.getFinal(), nome));
    }

    public void selecionarCartao(String finalCartao) {
        for (Cartao c : cartoes) {
            if (c.getFinal().equals(finalCartao)) {
                if (c.isBloqueado()) {
                    Toast.erro(String.format("Cartao de final %s bloqueado", c.getFinal()));
                    return;
                }
                this.cartaoAtual = c;
                Toast.sucesso(String.format("Cartao de final %s selecionado por %s", c.getFinal(), nome));
                return;
            }
        }

        Toast.erro("Cartao nao vinculado a este cliente");
    }
    
    public void transferirCartao(Cliente cliente) {
        if (cartaoAtual == null) {
            Toast.erro("Nenhum cartao selecionado");
            return;
        }
        if (tipo == TipoCliente.FISICO) {
            Toast.erro("Apenas membros de empresa podem transferir cartoes");
            return;
        }
        cartoes.remove(cartaoAtual);
        Toast.erro(String.format("Cartao de final %s desvinculado de %s", cartaoAtual.getFinal(), nome));
        cliente.adicionarCartao(cartaoAtual);
        cartaoAtual = null;
    }

    public void bloquearCartao() {
        if (cartaoAtual == null) {
            Toast.erro("Nenhum cartao selecionado");
            return;
        }
        cartaoAtual.bloquear();
    }

    public void excluirCartao() {
        if (cartaoAtual == null) {
            Toast.erro("Nenhum cartao selecionado");
            return;
        }
        cartoes.remove(cartaoAtual);
        Toast.erro(String.format("Cartao com final %s excluido para %s", cartaoAtual.getFinal(), nome));
        cartaoAtual = null;
    }

    public boolean validarAcesso() {
        return perfil == PerfilDeConsumo.VIP;
    }

    public void realizarTransacao(double valor) {
        if (cartaoAtual == null) {
            Toast.erro("Nenhum cartao selecionado");
            return;
        }
        cartaoAtual.realizarTransacao("Pagamento de fatura", valor, TipoTransacao.PAGAMENTO);
    }

    public void realizarTransacao(String descricao, double valor, LocalDateTime when) {
        if (cartaoAtual == null) {
            Toast.erro("Nenhum cartao selecionado");
            return;
        }
        if (cartaoAtual.validarCompra(valor))
           cartaoAtual.realizarTransacao(descricao, valor, TipoTransacao.COMPRA, when);;
    }

    public void realizarTransacao(String descricao, double valor) {
        if (cartaoAtual == null) {
            Toast.erro("Nenhum cartao selecionado");
            return;
        }
        if (cartaoAtual.validarCompra(valor))
           cartaoAtual.realizarTransacao(descricao, valor, TipoTransacao.COMPRA);;
    }

    public void concluirTransacao() {
        if (cartaoAtual == null) {
            Toast.erro("Nenhum cartao selecionado");
            return;
        }
        cartaoAtual.concluirTransacao();
    }

    private boolean validarCashback() {
        if (cartaoAtual == null) {
            Toast.erro("Nenhum cartao selecionado");
            return false;
        }
        if (!validarAcesso()) {
            Toast.erro("Acesso negado: apenas clientes VIP tem direito a cashback");
            return false;
        }

        if (cartaoAtual.getTaxaCashback() <= 0 || cartaoAtual.getTaxaCashback() >= 1) {
            Toast.erro("Taxa de cashback inválida");
            return false;
        }

        return true;
    }

    public void aplicarCashback(double valor) {
        if (cartaoAtual == null) {
            Toast.erro("Nenhum cartao selecionado");
            return;
        }
        if (validarCashback())
            cartaoAtual.realizarTransacao("Cashback", valor*cartaoAtual.getTaxaCashback(), TipoTransacao.CASHBACK);
    }

    public void gerarRelatorioDeTransacoes() {
        Toast.aviso(String.format("RELATORIO DE TRANSACOES (%s)", this.nome));
        for (Cartao c : cartoes) {
            if (!c.isBloqueado())
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
