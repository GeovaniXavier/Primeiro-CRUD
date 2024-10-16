package br.projeto.ProjPessoal.model;

public class UsuarioFactory {

    private static Usuario instance;

    // Singleton
    public static Usuario getUsuarioInstance() {
        if (instance == null) {
            synchronized (UsuarioFactory.class) {
                if (instance == null) {
                    instance = Usuario.builder().build();
                }
            }
        }
        return instance;
    }
}
