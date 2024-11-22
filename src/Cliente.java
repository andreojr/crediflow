import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;

public class Cliente {
    private String nome;
    private Cartao cartao;
    private TipoCliente tipo;
    private PerfilDeConsumo perfil;
    NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.of("pt", "BR"));

    public Cliente(String nome, TipoCliente tipo, PerfilDeConsumo perfil) {
        this.nome = nome;
        this.tipo = tipo;
        this.perfil = perfil;

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

    public void setCartao(Cartao cartao) {
        if (cartao.hasCashback() && perfil == PerfilDeConsumo.SIMPLES) {
            Toast.erro("Nao foi possivel vincular cartao: cliente simples nao possui acesso ao beneficio de cashback");
            return;
        }
        this.cartao = cartao;
        Toast.sucesso(String.format("Cartao com final %s vinculado a %s", cartao.getFinal(), nome));
    }
    
    public void transferirCartao(Cliente cliente) {
        if (cartao == null) {
            Toast.erro("Nenhum cartao selecionado");
            return;
        }
        if (tipo == TipoCliente.FISICO) {
            Toast.erro("Apenas membros de empresa podem transferir cartoes");
            return;
        }
        Toast.erro(String.format("Cartao de final %s desvinculado de %s", cartao.getFinal(), nome));
        cliente.setCartao(cartao);
        cartao = null;
    }

    public void bloquearCartao() {
        if (cartao == null) {
            Toast.erro("Nenhum cartao selecionado");
            return;
        }
        cartao.bloquear();
    }

    public void excluirCartao() {
        if (cartao == null) {
            Toast.erro("Nenhum cartao selecionado");
            return;
        }
        Toast.erro(String.format("Cartao com final %s excluido para %s", cartao.getFinal(), nome));
        cartao = null;
    }

    public boolean validarAcesso() {
        return perfil == PerfilDeConsumo.VIP;
    }

    public void realizarTransacao(double valor) {
        if (cartao == null) {
            Toast.erro("Nenhum cartao selecionado");
            return;
        }
        cartao.realizarTransacao("Pagamento de fatura", valor, TipoTransacao.PAGAMENTO);
    }

    public void realizarTransacao(String descricao, double valor, LocalDateTime when) {
        if (cartao == null) {
            Toast.erro("Nenhum cartao selecionado");
            return;
        }
        if (cartao.validarCompra(valor))
            cartao.realizarTransacao(descricao, valor, TipoTransacao.COMPRA, when);
    }

    public void realizarTransacao(String descricao, double valor) {
        if (cartao == null) {
            Toast.erro("Nenhum cartao selecionado");
            return;
        }
        if (cartao.validarCompra(valor))
            cartao.realizarTransacao(descricao, valor, TipoTransacao.COMPRA);
    }

    public void concluirTransacao() {
        if (cartao == null) {
            Toast.erro("Nenhum cartao selecionado");
            return;
        }
        cartao.concluirTransacao();
    }

    private boolean validarCashback() {
        if (cartao == null) {
            Toast.erro("Nenhum cartao selecionado");
            return false;
        }
        if (!validarAcesso()) {
            Toast.erro("Acesso negado: apenas clientes VIP tem direito a cashback");
            return false;
        }

        if (cartao.getTaxaCashback() <= 0 || cartao.getTaxaCashback() >= 1) {
            Toast.erro("Taxa de cashback inválida");
            return false;
        }

        return true;
    }

    public void aplicarCashback(double valor) {
        if (cartao == null) {
            Toast.erro("Nenhum cartao selecionado");
            return;
        }
        if (validarCashback())
            cartao.realizarTransacao("Cashback", valor*cartao.getTaxaCashback(), TipoTransacao.CASHBACK);
    }

    public void gerarRelatorioDeTransacoes() {
        if (cartao == null) {
            Toast.erro("Nenhum cartao selecionado");
            return;
        }
        Toast.aviso(String.format("RELATORIO DE TRANSACOES (%s)", this.nome));
        cartao.gerarExtrato();
    }

    public void gerarFatura() {
        Toast.erro(String.format("Total a pagar: %s", nf.format(Math.abs(cartao.verificarSaldo()))));
    }
}
