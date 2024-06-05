package Entidades;

import Models.UsuarioDAO;
import Main.App;

import java.awt.*;
import java.io.IOException;
import java.util.Scanner;

public class Usuario {
    private String email;
    private String senha;

    public Usuario(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public static void fazerLogin() throws IOException {
        Scanner leitor = new Scanner(System.in);

        System.out.println("+-------------------------------------------+");
        System.out.println("|            Entre na sua conta              ");
        System.out.print("| Email: ");
        String email = leitor.next();

        System.out.print("| Senha: ");
        String senha = leitor.next();
        System.out.println("+-------------------------------------------+");

        Usuario usuario = new Usuario(email, senha);

        boolean usuarioExiste = UsuarioDAO.verificarUsuario(usuario);

        if (usuarioExiste) {
            String fkEmpresa = UsuarioDAO.buscarFkEmpresa(usuario);
            App.menu(fkEmpresa);
        } else {
            System.out.println("Dados incorretos, tente novamente.");
            fazerLogin(); // Chama o método fazerLogin novamente após uma tentativa malsucedida
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
