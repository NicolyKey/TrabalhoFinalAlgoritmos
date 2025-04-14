/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabalhoFinal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Premiersoft
 */
public class ValidadorHTML {
  private static final Pattern TAG_PATTERN = Pattern.compile("<\\s*(/?)\\s*([a-zA-Z][a-zA-Z0-9]*)\\b([^>]*)/?>");
private static final String[] SELF_CLOSING_TAGS = {
    "img", "br", "hr", "input", "meta", "link"
};

public ErroValidacao validarHTML(File htmlFile) throws IOException {
    PilhaLista<String> pilha = new PilhaLista<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(htmlFile))) {
        String line;
        int numLinha = 1;

        while ((line = reader.readLine()) != null) {
            
            Matcher matcher = TAG_PATTERN.matcher(line);
            System.out.println("Lendo linha " + numLinha + ": " + line);


            while (matcher.find()) {
                boolean ehFechamento = matcher.group(1).equals("/");
                String tagNome = matcher.group(2).toLowerCase();

                System.out.println("Linha " + numLinha + ": Encontrada tag -> " + matcher.group(0));

                // Nova verificação
                boolean ehAutoFechamentoSintatico = matcher.group(0).endsWith("/>");

                if (ehTagAutoFechamento(tagNome) || ehAutoFechamentoSintatico) {
                    continue;
                }

                if (ehFechamento) {
                    if (pilha.estaVazia()) {
                        return new ErroValidacao(numLinha, tagNome, 
                            "Tag de fechamento sem abertura correspondente");
                    }

                    String tagEsperada = pilha.pop();
                    if (!tagEsperada.equals(tagNome)) {
                        return new ErroValidacao(numLinha, tagNome,
                            "Tag de fechamento incorreta. Esperado: </" + tagEsperada + ">");
                    }
                } else {
                    pilha.push(tagNome);
                }
            }
            numLinha++;
        }

        if (!pilha.estaVazia()) {
            return new ErroValidacao(numLinha, pilha.pop(),
                "Tag de abertura não foi fechada");
        }
    }

    return null;
    }

    private boolean ehTagAutoFechamento(String tagNome) {
        for (String tag : SELF_CLOSING_TAGS) {
            if (tag.equalsIgnoreCase(tagNome)) {
                return true;
            }
        }
        return false;
    }
    
    public ListaEncadeada<TagContador> contarTags(File htmlFile) throws IOException {
    ListaEncadeada<TagContador> listaContagem = new ListaEncadeada<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(htmlFile))) {
        String line;

        while ((line = reader.readLine()) != null) {
            Matcher matcher = TAG_PATTERN.matcher(line);

            while (matcher.find()) {
                String tagNome = matcher.group(2).toLowerCase();

                if (!matcher.group(1).equals("/")) { // ignorar fechamentos

                    boolean encontrada = false;
                    NoLista<TagContador> p = listaContagem.getPrimeiro();

                    while (p != null) {
                        if (p.getInfo().getTag().equals(tagNome)) {
                            p.getInfo().incrementar();
                            encontrada = true;
                            break;
                        }
                        p = p.getProximo();
                    }

                    if (!encontrada) {
                        listaContagem.inserir(new TagContador(tagNome));
                    }
                }
            }
        }
    }

    return listaContagem;
    }

}