package com.ce9001.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ce9001.delegate.ChangePasswordDelegate;
import com.ce9001.delegate.LoginDelegate;
import com.ce9001.viewBean.*;

@Controller
public class ChangePasswordController {
	@Autowired
	private ChangePasswordDelegate changePasswordDelegate;
	@Autowired
	private LoginDelegate loginDelegate;

	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public ModelAndView executeLogin(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("changePassword") ChangePasswordBean changePasswordBean) {
		ModelAndView model = null;
		try {
			if (changePasswordBean.getNewPassword().equals(changePasswordBean.getConfirmNewPassword())) {
				boolean passwordChange = changePasswordDelegate.changePassword(changePasswordBean.getUserName(),
						changePasswordBean.getNewPassword());
				if (passwordChange) {
					HttpSession session = request.getSession();
					UserBean user = loginDelegate.getUserInfo(changePasswordBean.getUserName());
					session.setAttribute("user", user);
					PrintWriter out = response.getWriter();

					out.println("<script type=\"text/javascript\">");
					out.println("alert('Password Change Successfully');");
					out.println("location='/OnlineQuizSpring/';");
					out.println("</script>");

				}
			} else {
				model = new ModelAndView("changePassword", "changePasswordBean", changePasswordBean);
				request.setAttribute("errorMessage", "Password does not match the confirm password.");
				request.setAttribute("userName", changePasswordBean.getUserName());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
}
