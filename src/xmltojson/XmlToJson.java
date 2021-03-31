/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmltojson;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author Fábio
 */
public class XmlToJson {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        File arquivo = new File("Entrada.xml");
        Queue<String> linhas = new LinkedList<String>();

        if (arquivo.exists()) {
            try (Scanner scanner = new Scanner(arquivo)) {
                while (scanner.hasNext()) {
                    linhas.add(scanner.nextLine());
                }
            }

            converteParaJson(linhas);

        } else {
            System.out.print("Arquivo de entrada não encontrado. O arquivo de"
                    + "Entrada deve se chamar \'Entrada.xml\' e deve ser colocado"
                    + "na pasta raiz do projeto. Garanta que seu arquivo esteja"
                    + "bem formatado.");
        }
    }

    public static void converteParaJson(Queue<String> linhas) {
        StringBuilder builder = new StringBuilder();
        boolean elementos = false;
        
        
        builder.append("{");                                                    //Inicio de arquivo
        
        while (!linhas.isEmpty()) {
            String linha = linhas.poll();
            linha = linha.substring(1);
            if (linha.contains("<")) {
                //Tem abre e fecha tag
                if(elementos){
                    builder.append(",");
                }
                builder.append(System.lineSeparator());
                elementos = true;
                builder.append("\"");
                builder.append(linha.substring(0, linha.indexOf('>')));
                builder.append("\": \"");
                builder.append(linha.substring(linha.indexOf('>') + 1, linha.indexOf('<')));
                builder.append("\"");
            } else if (linha.contains("/")) {
                //É um fecha tag
                elementos = false;
                builder.append(System.lineSeparator());
                builder.append("}");
            } else {
                //É um abre tag
                elementos = false;
                builder.append(System.lineSeparator());
                builder.append("\"");
                builder.append(linha.substring(0, linha.length() - 1));
                builder.append("\": {");
            }
        }
        builder.append(System.lineSeparator());
        builder.append("}"); 
    }
}
