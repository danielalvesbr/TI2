package com.ti2cc;

import java.util.Scanner;
import java.util.List;

public class Principal {

    private static void exibirMenu() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1 - Inserir usuario");
        System.out.println("2 - Atualizar usuario");
        System.out.println("3 - Excluir usuario");
        System.out.println("4 - Listar usuarios");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opcao: ");
    }

    private static Usuario lerUsuario(Scanner sc, int codigo) {
        System.out.print("Login: ");
        String login = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();
        System.out.print("Sexo (M/F): ");
        char sexo = sc.nextLine().charAt(0);

        return new Usuario(codigo, login, senha, sexo);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DAO dao = new DAO();

        if (!dao.conectar()) {
            System.out.println("Nao foi possivel conectar ao banco.");
            sc.close();
            return;
        }

        int opcao;
        do {
            exibirMenu();
            opcao = sc.nextInt();
            sc.nextLine(); // consumir quebra de linha

            switch (opcao) {
                case 1: // INSERIR
                    System.out.print("Codigo: ");
                    int codigo = sc.nextInt();
                    sc.nextLine();
                    Usuario u1 = lerUsuario(sc, codigo);

                    System.out.println(
                        dao.inserirUsuario(u1) ? 
                        "Usuario inserido com sucesso!" : 
                        "Erro ao inserir usuario."
                    );
                    break;

                case 2: // ATUALIZAR
                    System.out.print("Informe o codigo do usuario a atualizar: ");
                    int codUpdate = sc.nextInt();
                    sc.nextLine();
                    Usuario u2 = lerUsuario(sc, codUpdate);

                    System.out.println(
                        dao.atualizarUsuario(u2) ? 
                        "Usuario atualizado com sucesso!" : 
                        "Erro ao atualizar usuario."
                    );
                    break;

                case 3: // EXCLUIR
                    System.out.print("Informe o codigo do usuario a excluir: ");
                    int codDelete = sc.nextInt();
                    sc.nextLine();

                    System.out.println(
                        dao.excluirUsuario(codDelete) ? 
                        "Usuario excluido com sucesso!" : 
                        "Erro ao excluir usuario."
                    );
                    break;

                case 4: // LISTAR
                    List<Usuario> lista = dao.getUsuarios();
                    if (lista.isEmpty()) {
                        System.out.println("Nenhum usuario cadastrado.");
                    } else {
                        System.out.println("\n--- Usuarios cadastrados ---");
                        for (Usuario u : lista) {
                            System.out.println(u.toString());
                        }
                    }
                    break;

                case 0:
                    System.out.println("Encerrando aplicacao...");
                    break;

                default:
                    System.out.println("Opcao invalida, tente novamente.");
            }

        } while (opcao != 0);

        dao.close();
        sc.close();
    }
}
