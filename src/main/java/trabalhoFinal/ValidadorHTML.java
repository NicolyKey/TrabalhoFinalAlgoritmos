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
  // TODO: Fazer com que as tags sejam printadas em ordem alfabética
  private static final Pattern TAG_PATTERN = Pattern.compile("<\\s*(/?)\\s*([a-zA-Z][a-zA-Z0-9]*)\\b([^>]*)/?>");
  private static final String[] TAGS_AUTO_FECHAMENTO = {"img", "br", "hr", "input", "meta", "link","base",
          "col", "command", "embed" , "param", "source" , "!DOCTYPE"};

public ErroValidacao validarHTML(File htmlFile) throws IOException {
    PilhaLista<String> pilha = new PilhaLista<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(htmlFile))) {
        String linha;
        int numLinha = 1;
        int tagEncontradas = 0;

        while ((linha = reader.readLine()) != null) {
            
            Matcher matcher = TAG_PATTERN.matcher(linha);

            while (matcher.find()) {
                tagEncontradas ++;

                String tagOriginal = matcher.group(2);
                String tagNome = tagOriginal.toLowerCase();

                if (!tagOriginal.equals(tagNome) && !tagOriginal.equalsIgnoreCase("!DOCTYPE")) {
                    return new ErroValidacao(numLinha, tagOriginal,
                            "Tag com letras maiúsculas não é permitida.");
                }

                boolean ehFechamento = matcher.group(1).equals("/");
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

    }catch (IOException  err){
        System.out.println("Erro ao tentar ler arquivo");
    }

    return null;
    }

    private boolean ehTagAutoFechamento(String tagNome) {
        for (String tag : TAGS_AUTO_FECHAMENTO) {
            if (tag.equals(tagNome)) {
                return true;
            }
        }
        return false;
    }
    
    public ListaEncadeada<TagContador> contarTags(File htmlFile) throws IOException {
    ListaEncadeada<TagContador> listaContagem = new ListaEncadeada<>(); // TODO: Fazer com que não precise ler o arq 2x p contar

    try (BufferedReader reader = new BufferedReader(new FileReader(htmlFile))) {
        String linha;

        while ((linha = reader.readLine()) != null) {
            Matcher matcher = TAG_PATTERN.matcher(linha);

            while (matcher.find()) {
                String tagNome = matcher.group(2).toLowerCase();

                if (!matcher.group(1).equals("/")) {

                    boolean encontrada = false;
                    NoLista<TagContador> p = listaContagem.getPrimeiro();

                    while (p != null) {
                        if (p.getInfo().getTag().equals(tagNome)) {
                            p.getInfo().incrementar();
                            encontrada = true;
                        }
                        p = p.getProximo();
                    }

                    if (!encontrada) {
                        listaContagem.inserir(new TagContador(tagNome));
                    }
                }
            }
        }
    }catch(IOException erro){
        System.out.println("Erro ao ler arquivo para contagem.");
    }

    return listaContagem;
    }

}