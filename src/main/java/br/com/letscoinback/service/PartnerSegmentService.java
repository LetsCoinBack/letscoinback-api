package br.com.letscoinback.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.letscoinback.dto.PartnerSegmentDTO;
import br.com.letscoinback.persistence.repository.PartnerSegmentRepository;

@Service
public class PartnerSegmentService {

	@Autowired
	PartnerSegmentRepository partnerSegmentRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	//private static final Logger LOGGER = LoggerFactory.getLogger(PartnerSegmentService.class);
	
	public List<PartnerSegmentDTO> getAll () {
		return partnerSegmentRepository.findAll().stream()
				.map(n -> modelMapper.map(n, PartnerSegmentDTO.class))
				.collect(Collectors.toList());
	}
}
