import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Criar cliente1
        Cliente cliente1 = cadastrarCliente();

        // Cada objeto Cliente pode possuir varios cartoes
        // Criar cartaoA e vincular a cliente1
        Cartao cartaoA = new Cartao(1000, .1);
        cliente1.adicionarCartao(cartaoA);
        // Criar cartaoB e vincular a cliente1
        Cartao cartaoB = new Cartao(2000, .2);
        cliente1.adicionarCartao(cartaoB);
        
        // Selecionar cartao atraves do numero para realizar transacao
        cliente1.selecionarCartao(cartaoA.getFinal());
        cliente1.realizarTransacao("Garrafa termica", 56);
        cliente1.concluirTransacao();

        // Remover cartaoB de cliente1
        cliente1.selecionarCartao(cartaoB.getFinal());
        cliente1.excluirCartao();
    }
    
    public static Cliente cadastrarCliente() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o nome do cliente: ");
        String nome = scanner.nextLine();

        String tipoClienteSelecionado = select("Escolha o tipo de clinte: ", TipoCliente.values(), scanner);
        TipoCliente tipoCliente = TipoCliente.valueOf(tipoClienteSelecionado);
        System.out.println("Tipo de cliente selecionado: " + tipoCliente);

        String perfilClienteSelecionado = select("Escolha o perfil de consumo: ", PerfilDeConsumo.values(), scanner);
        PerfilDeConsumo perfilCliente = PerfilDeConsumo.valueOf(perfilClienteSelecionado);
        System.out.println("Perfil selecionado: " + tipoCliente);

        scanner.close();

        return new Cliente(nome, tipoCliente, perfilCliente);
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
