package br.com.gustavom.virtualoffer.controller;

import br.com.gustavom.virtualoffer.domain.NovoPedidoDTO;
import br.com.gustavom.virtualoffer.model.Pedido;
import br.com.gustavom.virtualoffer.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/pedido")
public class PedidoController {

    private PedidoRepository repository;

    @Autowired
    public PedidoController(PedidoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/novo_pedido")
    public String formulario(@ModelAttribute("novoPedido") NovoPedidoDTO novoPedido, Model ui) {
        ui = createTituloDescricaoNavbar(ui);
        return "pedido/formulario";
    }

    @PostMapping("/novo")
    public String novoPedido(@Valid @ModelAttribute("novoPedido") NovoPedidoDTO novoPedido, BindingResult validate, Model ui) {
        ui = createTituloDescricaoNavbar(ui);
        if (validate.hasErrors()) {
            return "pedido/formulario";
        }
        //TODO then create a mapper novoPedidoDTO to Pedido
        Pedido pedido = novoPedido.pedidoTo();
        repository.save(pedido);
        return "redirect:/meus_pedidos/todos";
    }

    private Model createTituloDescricaoNavbar(Model ui) {
        ui.addAttribute("status", new String("novo pedido"));
        ui.addAttribute("descricaoPagina", new String("Aqui você pode fazer novos pedidos na plataforma."));
        return ui;
    }

}
