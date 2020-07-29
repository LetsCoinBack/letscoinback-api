package br.com.letscoinback.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.letscoinback.dto.PartnerSegmentDTO;
import br.com.letscoinback.service.PartnerSegmentService;

@RestController
@RequestMapping("segment")
public class PartnerSegmentController {
	
	@Autowired
	PartnerSegmentService partnerSegmentService;
	
	@GetMapping("/all")
	public List<PartnerSegmentDTO> login () {
		return partnerSegmentService.getAll();
	}	
}
