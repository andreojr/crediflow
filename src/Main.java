public class Main {
    public static void main(String[] args) {
        // Cliente 1
        Cliente cliente1 = new Cliente("André Luiz de Oliveira Júnior", TipoCliente.FISICO, PerfilDeConsumo.SIMPLES);
        Cartao cartaoA = new Cartao(cliente1);
        cartaoA.realizarCompra("Cantina universitária", 10);
        cartaoA.realizarCompra("RU", 2.5);
        cliente1.gerarRelatorioDeTransacoes();
        cliente1.gerarFatura();
    }
    
}
