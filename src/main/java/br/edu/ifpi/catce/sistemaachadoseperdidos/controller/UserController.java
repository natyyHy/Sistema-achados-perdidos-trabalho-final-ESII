package br.edu.ifpi.catce.sistemaachadoseperdidos.controller;

import br.edu.ifpi.catce.sistemaachadoseperdidos.model.AlunoModel;
import br.edu.ifpi.catce.sistemaachadoseperdidos.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
public class UserController {
    @Autowired
    AlunoRepository alunoRepository;

    @GetMapping("/index")
    public String paginaPrincipal(){
        return "/telas/index";
    }


    @GetMapping("/buscar")
    public String buscar(Model model){
        model.addAttribute(new AlunoModel());
        return "telas/buscar";
    }
    @GetMapping("/resultadoBusca")
    public String buscaRealizada(@ModelAttribute AlunoModel alunoModel, Model model, RedirectAttributes redirectAttributes){
        List<AlunoModel> alunos = alunoRepository.findByPrimeiroNome(alunoModel.getNome());
        if(alunos.size() > 0){
            model.addAttribute("stylePersonalizado","display: none");
            model.addAttribute("src","static/icons/de-volta.png");
            model.addAttribute("display","display: table-row");
            model.addAttribute("nome","Nome");
            model.addAttribute("documento","Documento");
            model.addAttribute("data","Data");
            model.addAttribute(alunos);
        } else{
            redirectAttributes.addFlashAttribute("mensagem","Não existe pendências com esse nome");
            redirectAttributes.addFlashAttribute("style","mensagemErro");
            return "redirect:/buscar";
        }

        return "telas/buscar";

    }



    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, RedirectAttributes redirectAttributes) {
        if (error != null) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro! Entre com um usuário e senha corretos.");
            redirectAttributes.addFlashAttribute("style", "mensagemErro");
            return "redirect:/login";
        }
        return "telas/login";
    }

    @GetMapping("/loginErro")
    public String loginErro(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("mensagem", "A senha ou usuário está errado");
        redirectAttributes.addFlashAttribute("style", "mensagemErro");
        return "redirect:/login";
    }


    @PostMapping("/cadastroRealizado")
    public String cadastroRealizado(AlunoModel alunoModel, RedirectAttributes redirectAttributes){
        alunoRepository.save(alunoModel);
        redirectAttributes.addFlashAttribute("mensagem","Cadastro Realizado");
        redirectAttributes.addFlashAttribute("style","mensagemSucesso");
        return "redirect:/cadastraradm";
    }

}
