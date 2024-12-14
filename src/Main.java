import java.util.Scanner;

import Cartao.Cartao;
import Cartao.CartaoEmpresarial;
import Cartao.CartaoPremium;
import Cliente.Cliente;
import Cliente.TipoCliente;

public class Main {
    public static void main(String[] args) {
        // Criar cliente1
        Cliente cliente1 = cadastrarCliente();

        // Cada objeto Cliente pode possuir varios cartoes
        // Criar cartaoA e vincular a cliente1
        Cartao cartaoA = new CartaoEmpresarial();
        cliente1.adicionarCartao(cartaoA);
        // Criar cartaoB e vincular a cliente1
        Cartao cartaoB = new CartaoPremium(2000, .2f);
        cliente1.adicionarCartao(cartaoB);
        
        // Selecionar cartao atraves do numero para realizar transacao
        cliente1.selecionarCartao(cartaoB.getFinal());
        cliente1.realizarCompra("Garrafa termica", 56);
        cliente1.gerarRelatorioDeTransacoes();
    }
    
    public static Cliente cadastrarCliente() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o nome do cliente: ");
        String nome = scanner.nextLine();

        String tipoClienteSelecionado = select("Escolha o tipo de clinte: ", TipoCliente.values(), scanner);
        TipoCliente tipoCliente = TipoCliente.valueOf(tipoClienteSelecionado);
        System.out.println("Tipo de cliente selecionado: " + tipoCliente);

        scanner.close();

        return new Cliente(nome, tipoCliente);
    }

    public static String select(String label, Enum<?>[] opcoes, Scanner scanner) {
        String resposta = "";

        do {
            System.out.flush();
            
            for (int i = 0; i < opcoes.length; i++) {
                System.out.println(String.format("[%d] %s", i+1, opcoes[i].name()));
            }
            
            System.out.print(label);
            
            int i = scanner.nextInt();
            scanner.nextLine();
            if (i > 0 && i <= opcoes.length)
                resposta = opcoes[i-1].name();
        } while (resposta.isEmpty());
        return resposta;
    }
}
