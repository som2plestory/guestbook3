package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.service.GuestbookService;
import com.javaex.vo.GuestbookVo;

@Controller
public class GuestbookController {
	
	@Autowired 
	private GuestbookService guestbookService;

	// 방명록 삭제
	@RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
	public String delete(Model model, @ModelAttribute GuestbookVo guestbookVo) {
		System.out.println("GuestbookController>delete()");

		GuestbookVo guestVo = guestbookService.checkGuest(guestbookVo);

		if (guestVo != null) {
			guestbookService.delete(guestbookVo);
			return "redirect:/addList";
		} else {
			guestbookService.getGuest(guestbookVo.getNo());
			model.addAttribute("vo", guestbookVo);
			return "deleteForm";
		}
	}

	// 방명록 삭제폼
	@RequestMapping(value = "/deleteForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String deleteForm(Model model, @RequestParam("no") int no) {
		System.out.println("GuestbookController>deleteForm()");

		GuestbookVo guestbookVo = guestbookService.getGuest(no);

		model.addAttribute("guestbookVo", guestbookVo);

		return "/deleteForm";
	}

	// 방명록 등록
	@RequestMapping(value = "/add", method = { RequestMethod.GET, RequestMethod.POST })
	public String add(@ModelAttribute GuestbookVo guestbookVo) {

		System.out.println("GuestbookController>add()");

		guestbookService.add(guestbookVo);

		return "redirect:/addList";
	}

	// 방명록 리스트
	@RequestMapping(value = "/addList", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(Model model) {
		System.out.println("GuestbookController>addList()");

		List<GuestbookVo> guestList = guestbookService.getGuestList();

		model.addAttribute("guestList", guestList);

		return "addList";
	}

}
