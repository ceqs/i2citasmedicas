package pe.edu.utp.i2.citasmedicas.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pe.edu.utp.i2.citasmedicas.model.Rol;

import java.util.List;

@Controller
@RequestMapping("/home")
public class MenuController {

	@RequestMapping("/")
	public String index(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null) {
			List<GrantedAuthority> authorities = (List<GrantedAuthority>) auth.getAuthorities();
			model.addAttribute("name", auth.getName());
			Rol rol = new Rol();
			rol.setId(String.valueOf(authorities.get(0)));
			model.addAttribute("role", rol);
			System.out.println(authorities.get(0));
		}

		return "home";
	}
}
