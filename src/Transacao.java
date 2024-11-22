import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Transacao {
    private double valor;
    private String descricao;
    private TipoTransacao tipo;
    private Cartao cartao;
    private boolean concluida;
    private LocalDateTime criadoEm;
    NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.of("pt", "BR"));
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm - dd 'de' MMM 'de' yyyy");

    public Transacao(double valor, String descricao, Cartao cartao, TipoTransacao tipo, LocalDateTime criadoEm) {
        this.valor = tipo == TipoTransacao.COMPRA && valor > 0 ? valor * -1 : Math.abs(valor);
        this.descricao = descricao;
        this.concluida = false;
        this.cartao = cartao;
        this.tipo = tipo;
        this.criadoEm = criadoEm;
    }

    public Transacao(double valor, String descricao, Cartao cartao, TipoTransacao tipo) {
        this(valor, descricao, cartao, tipo, LocalDateTime.now());
    }

    public double getValorNumerico() {
        return valor;
    }

    public String getValor() {
        return nf.format(Math.abs(valor));
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

    public boolean isConcluida() {
        return this.concluida;
    }

    public LocalDateTime getLocalDateTime() {
        return criadoEm;
    }

    public String getCriadoEm() {
        return criadoEm.format(dtf);
    }

    public void concluirTransacao() {
        this.concluida = true;
    }

    public void exibir() {
        char simbolo = '+';
        String color = Toast.verde();
        if (TipoTransacao.COMPRA == tipo) {
            simbolo = '-';
            color = Toast.vermelho();
        }

        System.out.println(String.format(
            "%s | %s | %s | %s",
            tipo.name(),
            String.format("%s%s %s%s", color, simbolo, getValor(), Toast.reset()),
            descricao,
            getCriadoEm()
        ));
    }
}
