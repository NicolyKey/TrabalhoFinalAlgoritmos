/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabalhoFinal;

/**
 *
 * @author Premiersoft
 */
public class TagContador implements Comparable<TagContador> {
    private String tag;
    private int quantidade;

    public TagContador(String tag) {
        this.tag = tag;
        this.quantidade = 1;
    }

    public String getTag() {
        return tag;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void incrementar() {
        this.quantidade++;
    }

    @Override
    public int compareTo(TagContador outro) {
        return this.tag.compareToIgnoreCase(outro.getTag());
    }
}


