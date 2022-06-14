package com.javaex.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.dao.GuestbookDao;
import com.javaex.vo.GuestbookVo;

@Controller
public class GuestbookController {

	// 방명록 삭제
	@RequestMapping(value = "/delete/{no}", method = { RequestMethod.GET, RequestMethod.POST })
	public String delete(Model model, @PathVariable("no") int no, @RequestParam("password") String password) {
		System.out.println("GuestbookController>delete()");

		GuestbookDao dao = new GuestbookDao();
		GuestbookVo vo = new GuestbookVo(no, password);

		GuestbookVo guestVo = dao.checkGuest(vo);

		if (guestVo != null) {
			dao.delete(vo);
			return "redirect:/addList";
		} else {
			vo = dao.getGuest(no);
			model.addAttribute("vo", vo);
			return "deleteForm";
		}
	}

	// 방명록 삭제폼
	@RequestMapping(value = "/deleteForm/{no}", method = { RequestMethod.GET, RequestMethod.POST })
	public String deleteForm(Model model, @PathVariable("no") int no) {
		System.out.println("GuestbookController>deleteForm()");

		GuestbookDao dao = new GuestbookDao();
		GuestbookVo vo = dao.getGuest(no);

		model.addAttribute("vo", vo);

		return "/deleteForm";
	}

	// 방명록 등록
	@RequestMapping(value = "/add", method = { RequestMethod.GET, RequestMethod.POST })
	public String add(@RequestParam("name") String name, @RequestParam("password") String password,
			@RequestParam("content") String content) {

		System.out.println("GuestbookController>add()");

		GuestbookDao dao = new GuestbookDao();
		GuestbookVo vo = new GuestbookVo();
		vo.setName(name);
		vo.setPassword(password);
		vo.setContent(content);
		dao.add(vo);

		return "redirect:/addList";
	}

	// 방명록 리스트
	@RequestMapping(value = "/addList", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(Model model) {
		System.out.println("GuestbookController>addList()");

		GuestbookDao dao = new GuestbookDao();
		List<GuestbookVo> guestList = dao.getGuestList();

		model.addAttribute("guestList", guestList);

		return "addList";
	}

}
