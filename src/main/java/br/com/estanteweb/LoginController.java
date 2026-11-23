package br.com.estanteweb;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Exibe o formulário de login
    @GetMapping("/login")
    public String formulario() {
        return "login";
    }

    // Confere as credenciais; se corretas, guarda o usuário na sessão
    @PostMapping("/login")
    public String entrar(@RequestParam String email,
                         @RequestParam String senha,
                         HttpSession session,
                         Model model) {
        Usuario usuario = usuarioRepository.buscarPorEmail(email);

        // Mesma mensagem para e-mail inexistente e senha errada: assim a
        // resposta não revela quais e-mails têm conta na loja.
        if (usuario == null || !usuario.getSenha().equals(senha)) {
            model.addAttribute("erro", "E-mail ou senha inválidos.");
            model.addAttribute("email", email);   // devolve o e-mail, nunca a senha
            return "login";
        }

        // "Estar logado" = existir um usuário guardado na sessão deste navegador
        session.setAttribute("usuarioLogado", usuario);
        return "redirect:/livros/html";
    }

    // Logout altera o estado, por isso é POST (botão em formulário), não link
    @PostMapping("/logout")
    public String sair(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
