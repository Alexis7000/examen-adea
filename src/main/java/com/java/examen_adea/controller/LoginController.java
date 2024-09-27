package com.java.examen_adea.controller;

import com.java.examen_adea.entity.Usuario;
import com.java.examen_adea.service.UsuarioService;
import org.apache.catalina.Authenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class LoginController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/login")
    public String login(Model model) {
        return "login"; // Nombre del archivo HTML
    }
    @GetMapping("/success")
    public String success() {
        return "success"; // Esto buscará success.html en src/main/resources/templates
    }
    @PostMapping("/api/usuarios/verificar")
    public String verificarUsuario(@RequestParam String login, @RequestParam String password, RedirectAttributes redirectAttributes) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login, password));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:/success";
        } catch (BadCredentialsException e) {
            redirectAttributes.addFlashAttribute("error", "Credenciales inválidas");
            return "redirect:/login";
        }
    }}
