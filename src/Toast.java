public class Toast {
    public static String vermelho() {
        return "\u001B[31m";
    }

    public static String verde() {
        return "\u001B[32m";
    }

    public static String amarelo() {
        return "\u001B[33m";
    }

    public static String reset() {
        return "\u001B[0m";
    }

    public static void sucesso(String mensagem) {
        System.out.println(verde() + mensagem + reset());
    }

    public static void erro(String mensagem) {
        System.out.println(vermelho() + mensagem + reset());
    }

    public static void aviso(String mensagem) {
        System.out.println(amarelo() + mensagem + reset());
    }
}
