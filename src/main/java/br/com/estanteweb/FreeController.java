package br.com.estanteweb;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FreeController {

    // Usa o mesmo template "ola", que pede as chaves nomeLoja e anoFundacao
    @GetMapping("/free")
    public String free(Model model) {
        model.addAttribute("nomeLoja", "EstanteWeb");
        model.addAttribute("anoFundacao", 1998);

        return "ola";
    }
}
