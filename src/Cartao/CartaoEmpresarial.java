package Cartao;

public class CartaoEmpresarial extends Cartao {
    private static final double limiteEmpresarial = 10000;

    public CartaoEmpresarial() {
        super(limiteEmpresarial);
    }
}
