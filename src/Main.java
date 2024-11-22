import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        // Criar cliente1
        Cliente cliente1 = new Cliente("André Luiz de Oliveira Júnior", TipoCliente.FISICO, PerfilDeConsumo.VIP);
        // Criar cartao de forma independente
        Cartao cartaoA = new Cartao(1000, 0.05);
        // Vincular cartao a cliente1
        cliente1.adicionarCartao(cartaoA);

        // seleçao de cartao
        cliente1.selecionarCartao(cartaoA);
        // transacao 1 (realizada em 14 nov)
        cliente1.realizarTransacao("Ingresso Brasil x Uruguai", 350, LocalDateTime.of(2024, 11, 14, 13, 14));
        cliente1.concluirTransacao();
        // transacao 2 CASHBACK
        cliente1.aplicarCashback(350);
        cliente1.concluirTransacao();;
        // transacao 2
        cliente1.realizarTransacao("Combo Burger King", 50);
        cliente1.concluirTransacao();
        // visualizar historico de transacoes de um cartao por ordem cronologica (mais recentes primeiro)
        cartaoA.gerarExtrato();
        // visualizar historico de transacoes de um cartao por periodo
        cartaoA.gerarExtrato(LocalDateTime.of(2024, 11, 07, 5, 30), LocalDateTime.of(2024, 11, 17, 5, 30));
        // visualizar historico de transacoes de um cartao por tipo de transacao
        cartaoA.gerarExtrato(TipoTransacao.CASHBACK);
    }
    
}
