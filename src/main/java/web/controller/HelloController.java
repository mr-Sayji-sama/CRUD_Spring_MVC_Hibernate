package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import web.dao.UserHb;
import web.model.User;
import web.service.UserService;
import web.service.UserServiceImp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HelloController {
	@Autowired
	private UserService userService;

	@GetMapping(value = "/")
	public String printWelcome(ModelMap model) {
		try {
			List<User> users = userService.allUser();
			model.addAttribute("users", users);
		} catch (IllegalArgumentException e) {
		}
		return "index";
	}

	@RequestMapping(value="delete", method= RequestMethod.GET)
	public String deleteItem(@RequestParam Long id) {
		User user = userService.getById(id);
		userService.delete(user);
		return "redirect:/";
	}

	@RequestMapping (value = "/edit", method= RequestMethod.GET)
	public String edit(ModelMap model,@RequestParam Long id) {
		//Long id = Long.parseLong(servletRequest.getParameter("id"));
		User user = userService.getById(id);
		model.addAttribute("user", user);
		return "edit";
	}

	@RequestMapping(value="/edit", method=RequestMethod.POST)
	public ModelAndView edit(@ModelAttribute User model,
							 @RequestParam(value="action", required=true) String action) {
		switch(action) {
			case "save":
				userService.add(model);
				break;
			case "cancel":
				// do stuff
				break;
			case "newthing":
				// do stuff
				break;
			default:
				// do stuff
				break;
		}
		return new ModelAndView( "redirect:/");
	}

	@RequestMapping(value = "/add", method=RequestMethod.GET)
	public String add(ModelMap model) {
		User user = new User();
		model.addAttribute("user", user);
		return "add";
	}

	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String addNewOrder(@ModelAttribute User model) {
		userService.add(model);
		return "redirect:/";
	}
}