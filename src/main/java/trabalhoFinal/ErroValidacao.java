/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabalhoFinal;

/**
 *
 * @author Premiersoft
 */
public class ErroValidacao extends Error {
    private int linha;
    private String tag;
    private String mensagem;

    public ErroValidacao(int linha, String tag, String mensagem) {
        this.linha = linha;
        this.tag = tag;
        this.mensagem = mensagem;
    }

    public ErroValidacao(int linha, String mensagem){
        this.linha = linha;
        this.mensagem = mensagem;
    }

    public int getLinha() { return linha; }
    public String getTag() { return tag; }
    public String getMensagem() { return mensagem; }

    @Override
    public String toString() {
        return "Erro na linha " + linha + ": " + mensagem + " (Tag: <" + tag + ">)";
    }

}
