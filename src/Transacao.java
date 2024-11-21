import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Transacao {
    private double valor;
    private String descricao;
    private TipoTransacao tipo;
    private Cartao cartao;
    private LocalDateTime criadoEm;
    NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.of("pt", "BR"));
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm - dd 'de' MMM 'de' yyyy");

    public Transacao(double valor, String descricao, Cartao cartao, TipoTransacao tipo) {
        this.valor = valor;
        this.descricao = descricao;
        this.cartao = cartao;
        this.tipo = tipo;
        this.criadoEm = LocalDateTime.now();
    }

    public double getValorNumerico() {
        return valor;
    }

    public String getValor() {
        return nf.format(valor);
    }

    public TipoTransacao getTipo() {
        return this.tipo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Cartao getCartao() {
        return this.cartao;
    }

    public String getCriadoEm() {
        return criadoEm.format(dtf);
    }
}
