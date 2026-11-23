package br.com.estanteweb;

import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Controller
public class LivroController {

    // Missão 1.2: o repositório é injetado pelo Spring, sem "new"
    @Autowired
    private LivroRepository repositorioLivro;

    @ResponseBody
    @GetMapping("/livros")
    public String livro(){

        List<Livro> livros = repositorioLivro.listarTodos();

        String acervo = "Acervo Estante Web: ";
        for(int i = 0; i < livros.size(); i++) {
            acervo += livros.get(i).getTitulo()
                    + ", de " +  livros.get(i).getAutor()
                    + " (R$ " + livros.get(i).getPreco() + ")"
                    + " | ";
        }
        return acervo;
    }

    @ResponseBody
    @GetMapping("/livros/total")
    public String quantidadeLivros(){
        int total = repositorioLivro.contarLivros();
        return "O acervo tem " + total + " livros.";
    }

    @GetMapping("/livros/html")
    public String livroHtml(Model model){
        model.addAttribute("livros", repositorioLivro.listarTodos());

        return "livros";
    }

    // Missão 1 e 2: busca por preço máximo, com status e cabeçalho próprios
    @GetMapping("/livros/busca")
    public ResponseEntity<String> buscarPorPreco(
            @RequestParam(defaultValue = "999") double precoMaximo){

        List<Livro> encontrados = repositorioLivro.listarAtePreco(precoMaximo);

        if(encontrados.isEmpty()){
            return ResponseEntity
                    .status(404)
                    .body("Nenhum livro encontrado até R$ " + precoMaximo + ".");
        }

        String acervo = "Acervo Estante Web (até R$ " + precoMaximo + "): ";
        for(int i = 0; i < encontrados.size(); i++) {
            acervo += encontrados.get(i).getTitulo()
                    + ", de " + encontrados.get(i).getAutor()
                    + " (R$ " + encontrados.get(i).getPreco() + ")"
                    + " | ";
        }

        return ResponseEntity
                .status(200)
                .header("X-Total-Encontrados", String.valueOf(encontrados.size()))
                .body(acervo);
    }

    // Semana 7, Missão 4.4 (desafio): valor total do acervo, calculado pelo banco
    @ResponseBody
    @GetMapping("/livros/valor-total")
    public String valorTotal(){
        double total = repositorioLivro.somarValorAcervo();
        return "O valor total do acervo é R$ " + String.format("%.2f", total) + ".";
    }

    // Missão 2.3: lê o cabeçalho User-Agent da requisição
    @ResponseBody
    @GetMapping("/livros/quem-acessa")
    public String quemAcessa(@RequestHeader("User-Agent") String userAgent){
        return "Esse acesso veio de: " + userAgent;
    }

    // ------------------------------------------------------------------
    // Área administrativa: cadastrar, alterar e remover livros.
    // Toda rota abaixo que muda ou prepara uma mudança confere a sessão
    // PRIMEIRO: sem usuarioLogado, o visitante é mandado para o login.
    // ------------------------------------------------------------------

    private boolean naoEstaLogado(HttpSession session) {
        return session.getAttribute("usuarioLogado") == null;
    }

    // Devolve o livro ou responde 404 quando o id não existe
    private Livro buscarOuNaoEncontrado(Long id) {
        Livro livro = repositorioLivro.buscarPorId(id);
        if (livro == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado.");
        }
        return livro;
    }

    private boolean dadosInvalidos(String titulo, String autor, double preco) {
        return titulo.isBlank() || autor.isBlank() || preco <= 0;
    }

    private static final String MSG_DADOS_INVALIDOS =
            "Informe título e autor e um preço maior que zero.";

    // Exibe um livro (público: visitantes também veem a ficha)
    @GetMapping("/livros/{id}")
    public String exibir(@PathVariable Long id, Model model) {
        model.addAttribute("livro", buscarOuNaoEncontrado(id));
        return "livro";
    }

    // --- cadastrar ---

    @GetMapping("/livros/novo")
    public String novo(HttpSession session) {
        if (naoEstaLogado(session)) {
            return "redirect:/login";
        }
        return "livro-novo";
    }

    @PostMapping("/livros")
    public String salvar(@RequestParam String titulo,
                         @RequestParam String autor,
                         @RequestParam int ano,
                         @RequestParam double preco,
                         HttpSession session,
                         Model model) {
        if (naoEstaLogado(session)) {
            return "redirect:/login";
        }

        if (dadosInvalidos(titulo, autor, preco)) {
            model.addAttribute("erro", MSG_DADOS_INVALIDOS);
            model.addAttribute("titulo", titulo);
            model.addAttribute("autor", autor);
            model.addAttribute("ano", ano);
            model.addAttribute("preco", preco);
            return "livro-novo";
        }

        Livro livro = new Livro();
        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setAno(ano);
        livro.setPreco(preco);
        repositorioLivro.salvar(livro);

        return "redirect:/livros/html";
    }

    // --- alterar ---

    @GetMapping("/livros/{id}/editar")
    public String editar(@PathVariable Long id, HttpSession session, Model model) {
        if (naoEstaLogado(session)) {
            return "redirect:/login";
        }

        Livro livro = buscarOuNaoEncontrado(id);
        model.addAttribute("id", livro.getId());
        model.addAttribute("titulo", livro.getTitulo());
        model.addAttribute("autor", livro.getAutor());
        model.addAttribute("ano", livro.getAno());
        model.addAttribute("preco", livro.getPreco());
        return "livro-editar";
    }

    @PostMapping("/livros/{id}")
    public String alterar(@PathVariable Long id,
                          @RequestParam String titulo,
                          @RequestParam String autor,
                          @RequestParam int ano,
                          @RequestParam double preco,
                          HttpSession session,
                          Model model) {
        if (naoEstaLogado(session)) {
            return "redirect:/login";
        }

        buscarOuNaoEncontrado(id);

        if (dadosInvalidos(titulo, autor, preco)) {
            model.addAttribute("erro", MSG_DADOS_INVALIDOS);
            model.addAttribute("id", id);
            model.addAttribute("titulo", titulo);
            model.addAttribute("autor", autor);
            model.addAttribute("ano", ano);
            model.addAttribute("preco", preco);
            return "livro-editar";
        }

        Livro livro = new Livro();
        livro.setId(id);
        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setAno(ano);
        livro.setPreco(preco);
        repositorioLivro.atualizar(livro);

        return "redirect:/livros/" + id;
    }

    // --- excluir ---

    // GET só pergunta; quem remove é o POST (GET não deve alterar o estado)
    @GetMapping("/livros/{id}/excluir")
    public String confirmarExclusao(@PathVariable Long id, HttpSession session, Model model) {
        if (naoEstaLogado(session)) {
            return "redirect:/login";
        }

        model.addAttribute("livro", buscarOuNaoEncontrado(id));
        return "livro-excluir";
    }

    @PostMapping("/livros/{id}/excluir")
    public String excluir(@PathVariable Long id, HttpSession session) {
        if (naoEstaLogado(session)) {
            return "redirect:/login";
        }

        repositorioLivro.excluir(id);
        return "redirect:/livros/html";
    }
}
