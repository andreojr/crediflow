import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        // Relaçao FRACA entre Cliente e Cartao
        // Relaçao FORTE entre Cartao e Transacao
        
        // Cliente 1
        Cliente cliente1 = new Cliente("André Luiz de Oliveira Júnior", TipoCliente.FISICO, PerfilDeConsumo.VIP);
        Cartao cartaoA = new Cartao(2000, 0.05);
        cliente1.setCartao(cartaoA);
        // transacao criada
        cliente1.realizarTransacao("Oliva Gourmet", 59.9);
        // transacao concluida
        cliente1.concluirTransacao();
        // transacao criada
        cliente1.realizarTransacao("Ingresso Brasil x Uruguai", 350);
        // transacao concluida
        cliente1.concluirTransacao();
        // exclusao do cartao para cliente. OBS: cartaoB continua existindo
        cliente1.excluirCartao();
        // tentativa de criar transacao apos exclusao do cartao
        cliente1.realizarTransacao(50);
        // Relatorio vazio, apos exclusao do cartao
        cliente1.gerarRelatorioDeTransacoes();
        
        // Cliente 2
        Cliente cliente2 = new Cliente("Thais Novaes Rios", TipoCliente.JURIDICO, PerfilDeConsumo.VIP);
        Cartao cartaoB = new Cartao(1000, 0.05);
        // tentativa de selecionar um cartao nao vinculado ao cliente
        cliente2.setCartao(cartaoB);
        cliente2.transferirCartao(cliente1);
        // acessando cartao transferido
        cliente1.realizarTransacao("Cinema", 100);
        cliente1.concluirTransacao();
        cliente1.gerarRelatorioDeTransacoes();
        // Cartao sem exclusividade
        Cartao cartaoC = new Cartao(2000, 0);
        cliente1.setCartao(cartaoC);
        cliente2.setCartao(cartaoC);
    }

}
