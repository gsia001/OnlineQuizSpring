package com.ce9001.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ce9001.delegate.LoginDelegate;
import com.ce9001.viewBean.ChangePasswordBean;
import com.ce9001.viewBean.LoginBean;
import com.ce9001.viewBean.UserBean;

@Controller
public class LoginController {
	@Autowired
	private LoginDelegate loginDelegate;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView displayLogin(HttpServletRequest request, HttpServletResponse response, LoginBean loginBean) {
		ModelAndView model = new ModelAndView("login");
		// LoginBean loginBean = new LoginBean();
		model.addObject("loginBean", loginBean);
		return model;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView executeLogin(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("loginBean") LoginBean loginBean) {
		ModelAndView model = null;
		try {
			boolean isValidUser = loginDelegate.isValidUser(loginBean.getUsername(), loginBean.getPassword());
			if (isValidUser) {
				String page = "";
				UserBean user = loginDelegate.getUserInfo(loginBean.getUsername());
				request.setAttribute("userName", loginBean.getUsername());

				if (user.getFirstTimeAccess() == 0) {
					page = "changePassword";
					ChangePasswordBean changePasswordBean = new ChangePasswordBean();
					model = new ModelAndView(page, "changePasswordBean", changePasswordBean);
				} else {

					HttpSession session = request.getSession();

					session.setAttribute("user", user);
					request.setAttribute("accountType", user.getAccountType());
					page = "home";
					model = new ModelAndView(page);
				}


			} else {
				model = new ModelAndView("login");
				request.setAttribute("errorMessage", "Invalid credentials!!");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/logout")
	public ModelAndView displayLogout(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = null;
		request.getSession().invalidate();
		model = new ModelAndView("home");
		return model;
	}
}
