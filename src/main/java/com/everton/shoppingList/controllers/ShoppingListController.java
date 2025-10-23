package com.everton.shoppingList.controllers;

import com.everton.shoppingList.dtos.ItemCompraDTO;
import com.everton.shoppingList.dtos.ItemRequestDto;
import com.everton.shoppingList.entities.Item;
import com.everton.shoppingList.enums.Categoria;
import com.everton.shoppingList.services.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/shoppingList")
@RequiredArgsConstructor
public class ItemViewController {

    private final ItemService itemService;

    // Página inicial — lista os itens e mostra o total
    @GetMapping
    public String index(Model model) {
        model.addAttribute("itens", itemService.listarPorCategoria(null)); // todos os itens
        model.addAttribute("total", itemService.calcularTotal().getTotal());
        model.addAttribute("categorias", Categoria.values());
        return "index";
    }

    // Abre formulário de novo item
    @GetMapping("/item/novo")
    public String novoItem(Model model) {
        model.addAttribute("item", new ItemRequestDto());
        model.addAttribute("categorias", Categoria.values());
        return "form-item";
    }

    // Salva novo item
    @PostMapping("/item/salvar")
    public String salvarItem(@ModelAttribute ItemRequestDto dto) {
        itemService.cadastrarItem(dto);
        return "redirect:/shoppingList";
    }

    // Excluir item da lista
    @GetMapping("/item/excluir/{id}")
    public String excluirItem(@PathVariable Long id) {
        itemService.excluirItem(id);
        return "redirect:/shoppingList";
    }

    // Comprar item — abre formulário
    @GetMapping("/item/comprar/{id}")
    public String comprarItemForm(@PathVariable Long id, Model model) {
        Item item = itemService.buscarPorId(id);
        model.addAttribute("item", item);
        return "form-compra";
    }

    // Salva compra
    @PostMapping("/item/comprar")
    public String comprarItem(@ModelAttribute ItemCompraDTO compraDTO) {
        itemService.comprarItem(compraDTO);
        return "redirect:/shoppingList";
    }

    // Histórico
    @GetMapping("/historico")
    public String historico(Model model) {
        model.addAttribute("historico", itemService.historicoItens());
        model.addAttribute("total", itemService.calcularTotal().getTotal());
        return "historico";
    }

    // Excluir do histórico
    @GetMapping("/item/historico/delete/{id}")
    public String excluirHistorico(@PathVariable Long id) {
        itemService.excluirItemHistorico(id);
        return "redirect:/shoppingList/historico";
    }
}