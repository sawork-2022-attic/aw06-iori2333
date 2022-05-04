package com.example.webpos.web;

import com.example.webpos.biz.PosService;
import com.example.webpos.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class PosController {
    private HttpSession session;

    private PosService posService;

    private int currentPage = 0;

    @Autowired
    public void setSession(HttpSession session) {
        this.session = session;
    }

    public Cart getCart() {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    @Autowired
    public void setPosService(PosService posService) {
        this.posService = posService;
    }

    @GetMapping("/")
    public String pos(Model model, @RequestParam(name = "page", defaultValue = "-1") int page) {
        var cart = getCart();
        if (posService.products() != null && cart.getItems().isEmpty()) {
            posService.add(cart, posService.randomProduct(), 1);
        }
        return setPage(page, model, cart);
    }

    @GetMapping("/add")
    public String addByGet(@RequestParam(name = "pid") String pid,
                           @RequestParam(name = "page", defaultValue = "-1") int page,
                           Model model) {
        var cart = getCart();
        posService.add(cart, pid, 1);
        return setPage(page, model, cart);
    }

    private String setPage(int page, Model model, Cart cart) {
        if (page == -1) {
            page = currentPage;
        } else {
            currentPage = page;
        }
        model.addAttribute("products", posService.products().subList(page * 20, (page + 1) * 20));
        model.addAttribute("cart", cart);
        return "index";
    }
}
