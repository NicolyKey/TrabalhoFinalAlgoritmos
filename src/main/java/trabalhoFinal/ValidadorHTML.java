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
    private Pattern TAG_PATTERN = Pattern.compile("<\\s*(/?)\\s*([a-zA-Z][a-zA-Z0-9]*|!doctype)\\b([^>]*)>?", Pattern.CASE_INSENSITIVE);
    private String[] TAGS_AUTO_FECHAMENTO = {"img", "br", "hr", "input", "meta", "link", "base",
            "col", "command", "embed", "param", "source", "!doctype"};
    private ListaEncadeada<TagContador> contadorTags = new ListaEncadeada<>();

    public ErroValidacao validarHTML(File htmlFile) throws IOException {
        PilhaLista<String> pilha = new PilhaLista<>();
        contadorTags = new ListaEncadeada<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(htmlFile))) {
            String linha;
            int numLinha = 1;
            int tagEncontradas = 0;

            while ((linha = reader.readLine()) != null) {
                Matcher matcher = TAG_PATTERN.matcher(linha);

                while (matcher.find()) {
                    tagEncontradas++;
                    String tagOriginal = matcher.group(2);
                    String tagNome = tagOriginal.toLowerCase();
                    String conteudoTag = matcher.group(0);

                    if (!"/".equals(matcher.group(1))) {
                        atualizarContadorTags(tagNome);
                    }

                    if (!conteudoTag.endsWith(">")) {
                        return new ErroValidacao(numLinha, tagNome,
                                "Tag malformada - falta '>' no final");
                    }

                    boolean ehFechamento = "/".equals(matcher.group(1));
                    boolean ehAutoFechamentoSintatico = matcher.group(0).endsWith("/>");

                    if (ehTagAutoFechamento(tagNome) || ehAutoFechamentoSintatico) {
                        continue;
                    }

                    if (linha.contains("<") && !TAG_PATTERN.matcher(linha).find()) {
                        return new ErroValidacao(numLinha, "", "Tag malformada detectada ou fechamento incorreto.");
                    }

                    if (ehFechamento) {
                        if (pilha.estaVazia()) {
                            return new ErroValidacao(numLinha, tagNome,
                                    "Tag de fechamento sem abertura correspondente");
                        }

                        String tagEsperada = pilha.pop();
                        if (!tagEsperada.equals(tagNome)) {
                            return new ErroValidacao(numLinha, tagNome,
                                    "Tag de fechamento incorreta. Esperado: </" + tagEsperada + "> encontrado:");
                        }
                    } else {
                        pilha.push(tagNome);
                    }
                }
                numLinha++;
            }

            if (tagEncontradas == 0) {
                return new ErroValidacao(0, "Nenhuma tag foi encontrada.");
            }

            if (!pilha.estaVazia()) {
                return new ErroValidacao(numLinha, pilha.pop(),
                        "Tag de abertura não foi fechada");
            }

        } catch (IOException err) {
            System.out.println("Erro ao tentar ler arquivo");
        }

        return null;
    }

    private void atualizarContadorTags(String tagNome) {
        boolean encontrada = false;
        NoLista<TagContador> p = contadorTags.getPrimeiro();

        while (p != null) {
            if (p.getInfo().getTag().equals(tagNome)) {
                p.getInfo().incrementar();
                encontrada = true;
                break;
            }
            p = p.getProximo();
        }

        if (!encontrada) {
            contadorTags.inserir(new TagContador(tagNome));
        }
    }

    public ListaEncadeada<TagContador> getContadorTags() {
        return contadorTags;
    }

    private boolean ehTagAutoFechamento(String tagNome) {
        for (String tag : TAGS_AUTO_FECHAMENTO) {
            if (tag.equals(tagNome)) {
                return true;
            }
        }
        return false;
    }

}