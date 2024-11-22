public class Main {
    public static void main(String[] args) {
        // Relaçao FRACA entre Cliente e Cartao
        // Relaçao FORTE entre Cartao e Transacao

        // Criar cliente1
        Cliente cliente1 = new Cliente("André Luiz de Oliveira Júnior", TipoCliente.FISICO, PerfilDeConsumo.VIP);
        // Criar cartao de forma independente
        Cartao cartaoA = new Cartao(1000, 0.05);
        // Vincular cartao a cliente1
        cliente1.adicionarCartao(cartaoA);

        // seleçao de cartao
        cliente1.selecionarCartao(cartaoA);
        // transacao criada
        cliente1.realizarTransacao("Ingresso Brasil x Uruguai", 350);
        // transacao concluida
        cliente1.concluirTransacao();
        // exclusao do cartao para cliente. OBS: cartaoA continua existindo
        cliente1.excluirCartao();
        // tentativa de criar transacao apos exclusao do cartao
        cliente1.realizarTransacao(50);
        // Relatorio vazio, apos exclusao do cartao
        cliente1.gerarRelatorioDeTransacoes();
        
        Cliente cliente2 = new Cliente("Thais Novaes Rios", TipoCliente.JURIDICO, PerfilDeConsumo.VIP);
        Cartao cartaoB = new Cartao(1000, 0.05);
        // tentativa de selecionar um cartao nao vinculado ao cliente
        cliente1.selecionarCartao(cartaoB);
        cliente2.adicionarCartao(cartaoB);
        cliente2.selecionarCartao(cartaoB);
        cliente2.transferirCartao(cliente1);
        // acessando cartao transferido
        cliente1.selecionarCartao(cartaoB);
        cliente2.gerarRelatorioDeTransacoes();

        // Cartao sem exclusividade
        Cartao cartaoC = new Cartao(2000, 0);
        cliente1.adicionarCartao(cartaoC);
        cliente2.adicionarCartao(cartaoC);
    }
    
}
