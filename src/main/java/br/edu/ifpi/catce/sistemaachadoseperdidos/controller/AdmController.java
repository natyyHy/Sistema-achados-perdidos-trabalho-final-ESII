package br.edu.ifpi.catce.sistemaachadoseperdidos.controller;

import br.edu.ifpi.catce.sistemaachadoseperdidos.model.AlunoModel;
import br.edu.ifpi.catce.sistemaachadoseperdidos.model.ItemPerdidoModel;
import br.edu.ifpi.catce.sistemaachadoseperdidos.repository.AlunoRepository;
import br.edu.ifpi.catce.sistemaachadoseperdidos.repository.ItemPerdidoRepository;
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
public class AdmController {
    @Autowired
    AlunoRepository alunoRepository;

    @Autowired
    private ItemPerdidoRepository itemPerdidoRepository;


    @GetMapping("/indexadm")
    public String paginaPrincipal(){
        return "/adm/index";
    }

    @GetMapping("/buscaradm")
    public String buscar(Model model) {
        model.addAttribute("alunoModel", new AlunoModel());
        return "adm/buscar";
    }

    @GetMapping("/resultadoBuscaAdm")
    public String buscaRealizada(@ModelAttribute("alunoModel") AlunoModel alunoModel,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        List<AlunoModel> alunos = alunoRepository.findByPrimeiroNome(alunoModel.getNome());

        if (!alunos.isEmpty()) {
            model.addAttribute("alunoModel", new AlunoModel()); // Para manter o formulário funcionando
            model.addAttribute("alunoModelList", alunos);

            model.addAttribute("stylePersonalizado", "display: none;");
            model.addAttribute("src", "static/icons/de-volta.png");
            model.addAttribute("display", "display: table-row");
            model.addAttribute("nome", "Nome");
            model.addAttribute("documento", "Documento");
            model.addAttribute("data", "Data");
            model.addAttribute("acao", "Ação");

            return "adm/buscar";
        } else {
            redirectAttributes.addFlashAttribute("mensagem", "Não existe pendências com esse nome");
            redirectAttributes.addFlashAttribute("style", "mensagemErro");
            return "redirect:/buscaradm";
        }
    }


    @GetMapping("/cadastraradm")
    public String cadastrar(Model model){
        model.addAttribute(new AlunoModel());
        return "adm/cadastrarAluno";
    }
    @GetMapping("/confirmar")
    public String confirmar(@RequestParam("id") Long id, Model model){
        model.addAttribute("src","static/icons/ideia.png");
        model.addAttribute("id",id);
        return "/adm/confirmar";
    }
    @PostMapping("/deletar")
    public String deletar(@RequestParam("id") Long id, RedirectAttributes redirectAttributes){
        alunoRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("mensagem","Apagado com sucesso.");
        redirectAttributes.addFlashAttribute("style","mensagemSucesso");
        return "redirect:/buscaradm";
    }

    @GetMapping("/cadastrarDocumento")
    public String exibirFormularioCadastroDocumento(Model model) {
        model.addAttribute("alunos", alunoRepository.findAll());
        model.addAttribute("itemPerdido", new ItemPerdidoModel());
        return "adm/cadastrarDocumento";
    }

    @PostMapping("/cadastrarDocumentoRealizado")
    public String cadastrarDocumento(@RequestParam("alunoId") Long alunoId,
                                     @ModelAttribute("itemPerdido") ItemPerdidoModel itemPerdido,
                                     RedirectAttributes redirectAttributes) {

        AlunoModel aluno = alunoRepository.findById(alunoId).orElse(null);
        if (aluno == null) {
            redirectAttributes.addFlashAttribute("mensagem", "Aluno não encontrado.");
            redirectAttributes.addFlashAttribute("style", "mensagemErro");
            return "redirect:/documento/cadastrar";
        }

        itemPerdido.setAluno(aluno);
        itemPerdidoRepository.save(itemPerdido);

        redirectAttributes.addFlashAttribute("mensagem", "Documento cadastrado com sucesso.");
        redirectAttributes.addFlashAttribute("style", "mensagemSucesso");
        return "redirect:/cadastrarDocumento";
    }

}
