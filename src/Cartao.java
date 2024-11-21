import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class Cartao {
    private String numero;
    private Cliente cliente;
    private double limite;
    private double taxaCashback;
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
        cliente.adicionarCartao(this);

        Toast.sucesso(String.format("Cartao com final %s criado com sucesso para %s", numero.substring(numero.length() - 4), cliente.getNome()));
    }

    public Cartao(Cliente cliente, double limitePersonalizado, double taxaCashback) {
        this(cliente);
        if (cliente.getPerfil() == PerfilDeConsumo.VIP) {
            this.limite = limitePersonalizado;
            this.taxaCashback = taxaCashback;
        }
    }

    public String getNumero() {
        return this.numero;
    }

    public double getLimite() {
        return this.limite;
    }

    public String gerarNumero() {
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
        Toast.sucesso("Compra realizada com sucesso");
        return true;
    }

    public boolean validarAcesso() {
        return cliente.getPerfil() == PerfilDeConsumo.VIP;
    }

    public void realizarCompra(String descricao, double valor) {
        if (validarCompra(valor))
            this.transacoes.add(new Transacao(valor*-1, descricao, this, TipoTransacao.COMPRA));
    }

    public void realizarCompra(String descricao, double valor, boolean cashback) {
        realizarCompra(descricao, valor);
        if (cashback)
            aplicarCachback(valor);
    }


    private boolean validarCashback() {
        if (!validarAcesso()) {
            Toast.erro("Acesso negado: apenas clientes VIP tem direito a cashback");
            return false;
        }

        if (taxaCashback <= 0 || taxaCashback >= 1) {
            Toast.erro("Taxa de cashback inválida");
            return false;
        }

        return true;
    }

    private void aplicarCachback(double valor) {
        if (validarCompra(valor) && validarCashback())
            this.transacoes.add(new Transacao(valor*taxaCashback, "Cashback", this, TipoTransacao.CASHBACK));
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
