package br.com.mv.desafioMVSistemas.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.mv.desafioMVSistemas.model.Item;
import br.com.mv.desafioMVSistemas.repository.ItemRepository;

@Controller @RequestMapping("/items")
public class ItemController {
	
	@Autowired
	ItemRepository ir;
	
	@GetMapping("")
	public ModelAndView listar(Item item) {		
		
		ModelAndView mv = new ModelAndView("CadastroItems");
		mv.addObject("itemcafedamanha", new Item());
		mv.addObject("items", ir.findAll());
		return mv;
		
	}
	
	@GetMapping("/selecionar/{id}")
	public ModelAndView selecionar(@PathVariable Integer id, Model model) {
		
		ModelAndView mv = new ModelAndView("CadastroItems");
		mv.addObject("itemcafedamanha", ir.findById(id).orElseThrow(() -> new IllegalArgumentException("Id inválido")));
		mv.addObject("items", ir.findAll());
		return mv;
		
	}
	
	@PostMapping("")
	public ModelAndView salvar(@Valid Item item, BindingResult result, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			return listar(item);
		} else if(item.equals(ir.findByNome(item.getNome()))&& item.getId() == null) {
			//valida se já existe item cadastrado com mesmo nome.
			attributes.addFlashAttribute("mensagemSalvo", "Já existe item salvo com este nome!");
		} else {
			ir.save(item);
			attributes.addFlashAttribute("mensagemSalvo", "Item salvo com sucesso.");
		}		
		ModelAndView mv = new ModelAndView("redirect:/items");		
		return mv;
	}	
	
	@GetMapping("/deletar/{id}")
	public ModelAndView deletar(@PathVariable Integer id) {
		
		ir.deleteById(id);
		ModelAndView mv = new ModelAndView("redirect:/items");
		return mv;
			
	}
	
	@GetMapping("/procurar")
	public ModelAndView procurar(HttpServletRequest request, Model model) {
		
		ModelAndView mv = new ModelAndView("CadastroItems");
		model.addAttribute("itemcafedamanha", new Item()); 
		mv.addObject("items", ir.findByNomeContainingIgnoreCase(request.getParameter("busca")));
		return mv;
		
	}

}
