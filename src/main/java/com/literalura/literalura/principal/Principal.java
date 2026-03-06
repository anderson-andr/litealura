package com.literalura.literalura.principal;

import com.literalura.literalura.dto.DadosLivro;
import com.literalura.literalura.dto.DadosResposta;
import com.literalura.literalura.service.ConsumoApi;
import com.literalura.literalura.service.ConverteDados;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Principal {

    private Scanner leitura = new Scanner(System.in);

    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "https://gutendex.com/books/?search=";

    public void exibeMenu(){

        var opcao = -1;

        while(opcao != 0){

            System.out.println("""
                    
                    1 - Buscar livro pelo título
                    2 - Listar livros registrados
                    3 - Listar autores
                    4 - Listar autores vivos em determinado ano
                    5 - Listar livros por idioma
                    
                    0 - Sair
                    """);

            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao){

                case 1:
                    buscarLivro();
                    break;

                case 2:
                    System.out.println("Listando livros...");
                    break;

                case 3:
                    System.out.println("Listando autores...");
                    break;

                case 4:
                    System.out.println("Digite o ano:");
                    var ano = leitura.nextInt();
                    leitura.nextLine();
                    break;

                case 5:
                    System.out.println("Digite o idioma:");
                    var idioma = leitura.nextLine();
                    break;

                case 0:
                    System.out.println("Encerrando aplicação...");
                    break;

                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarLivro(){

        System.out.println("Digite o nome do livro:");
        var titulo = leitura.nextLine();

        var json = consumo.obterDados(ENDERECO + titulo.replace(" ", "+"));

        DadosResposta resposta = conversor.obterDados(json, DadosResposta.class);

        if(resposta.results().isEmpty()){
            System.out.println("Livro não encontrado.");
        } else {

            DadosLivro livro = resposta.results().get(0);

            System.out.println("\n----- LIVRO ENCONTRADO -----");
            System.out.println("Título: " + livro.title());
            System.out.println("Autor: " + livro.authors().get(0).name());
            System.out.println("Idioma: " + livro.languages().get(0));
            System.out.println("Downloads: " + livro.download_count());
            System.out.println("----------------------------\n");
        }
    }
}