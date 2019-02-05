package com.cat.web;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cat.DAO.ProduitRepository;
import com.cat.DAO.UsersRepository;
import com.cat.entities.Produit;
import com.cat.entities.Users;

/**
 * 
 * 
 * @author jebel haythem	
 *
 */

@Controller
public class ProduitControleur {
	@Autowired
	private ProduitRepository produitRepository;
	@Autowired
	private UsersRepository usersRepository;

	@RequestMapping(value = "/index")
	public String index(Model model, @RequestParam(name = "page", defaultValue = "0") int p,
			@RequestParam(name = "size", defaultValue = "5") int s) {
		Page<Produit> PageProduit = produitRepository.findAll(new PageRequest(p, s));
		model.addAttribute("listproduits", PageProduit.getContent());
		int[] pages = new int[PageProduit.getTotalPages()];
		model.addAttribute("pages", pages);
		model.addAttribute("size", s);
		model.addAttribute("pageCourant", p);
		return "produits";
	}

	@RequestMapping(value = "/find")
	public String find(Model model, @RequestParam(name = "page", defaultValue = "0") int p,
			@RequestParam(name = "size", defaultValue = "5") int s,
			@RequestParam(name = "motcle", defaultValue = "") String mc) {
		Page<Produit> PageProduit = produitRepository.chercher("%" + mc + "%", new PageRequest(p, s));
		model.addAttribute("listproduits", PageProduit.getContent());
		int[] pages = new int[PageProduit.getTotalPages()];
		model.addAttribute("pages", pages);
		model.addAttribute("size", s);
		model.addAttribute("pageCourant", p);
		model.addAttribute("motcle", mc);
		return "chercher";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String Delete(Long id, String motcle, int page, int size) {
		produitRepository.deleteById(id);
		return "redirect:/find?page=" + page + "&size=" + size + "&motcle=" + motcle;
	}

	@RequestMapping(value = "/deletep", method = RequestMethod.GET)
	public String Deletep(Long id, int page, int size) {
		produitRepository.deleteById(id);
		return "redirect:/find?page=" + page + "&size=" + size;
	}

	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public String FromProduit(Model model) {
		model.addAttribute("produit", new Produit());
		return "formProduit";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String SaveP(Model model, @Valid Produit produit, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "formProduit";
		}
		produitRepository.save(produit);
		return "confirmation";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Model model, Long id) {
		Produit p = produitRepository.findOne(id);
		model.addAttribute("produit", p);
		return "Editproduit";
	}

	@RequestMapping(value = "/supprimer")
	public String delProduit(Model model) {
		return "deleteProduit";
	}

	@RequestMapping(value = "/del", method = RequestMethod.GET)
	public String del(Model model, Long id) {
		produitRepository.deleteById(id);
		return "redirect:/supprimer";
	}

	@RequestMapping(value = "/")
	public String per() {
		return "login";

	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(Long id, String password , HttpSession session) {
		Users u = usersRepository.findusers(id, password);
		if(u==null) {
			return "login";
		}
		String name=u.getUsername();
		session.setAttribute("name",name);
		return "redirect:/index";

	}
	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "login";
	}
	@RequestMapping(value = "/ajouter", method = RequestMethod.GET)
	public String addUser(Model model) {
		model.addAttribute("users", new Users());
		return "ajouterUser";
	}
	@RequestMapping(value = "/enregistre", method = RequestMethod.POST)
	public String SaveU(Model model,@Valid Users users, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "ajouterUser";
		}
		usersRepository.save(users);
		return "confirmation2";
	}
	@RequestMapping(value = "/drop")
	public String dropp(Model model) {
		model.addAttribute("users", usersRepository.findAll());
		return "dropUser";

	}
	@RequestMapping(value = "/tous")
	public String all(Model model,@RequestParam(name = "id", defaultValue = "0") Long id) {
		usersRepository.deleteById(id);
		model.addAttribute("users", usersRepository.findAll());
		return "dropUser";
	}
/*	@RequestMapping(value = "/modifier")
	public String modif(Model model) {
		model.addAttribute("users", usersRepository.findAll());
		return "modifUser";

	}
	@RequestMapping(value = "/affupdate")
	public String afficheUpdate(Model model,@RequestParam(name = "id", defaultValue = "0") Long id) {
		model.addAttribute("users", usersRepository.findAll());
		Users Us = usersRepository.findOne(id);
		model.addAttribute("us", Us);
		return "modifUser";
	}
	@RequestMapping(value = "/sauvgarder", method = RequestMethod.POST)
	public String SaveU(Users users) {
		usersRepository.save(users);
		return "modifUser";
	}*/
}
