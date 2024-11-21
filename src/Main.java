public class Main {
    public static void main(String[] args) {
        // Cliente 1
        Cliente cliente1 = new Cliente("André Luiz de Oliveira Júnior", TipoCliente.FISICO, PerfilDeConsumo.SIMPLES);
        Cartao cartaoA = new Cartao(cliente1);
        cartaoA.realizarCompra("Cantina universitária", 10);
        cartaoA.realizarCompra("RU", 2.5);
        cartaoA.realizarCompra("Playstation 2", 830, true);
        cliente1.gerarRelatorioDeTransacoes();
        cliente1.gerarFatura();

        // Cliente 2
        Cliente cliente2 = new Cliente("Eduardo Fontana Enterprises", TipoCliente.JURIDICO, PerfilDeConsumo.VIP);
        Cartao cartaoB = new Cartao(cliente2, 10000, 0.05);
        cartaoB.realizarCompra("Oliva Gourmet", 59.9, true);
        cliente2.gerarRelatorioDeTransacoes();
    }
    
}
