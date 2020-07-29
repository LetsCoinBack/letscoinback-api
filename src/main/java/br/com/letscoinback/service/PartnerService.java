package br.com.letscoinback.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.letscoinback.dto.PartnerDTO;
import br.com.letscoinback.dto.PartnerProviderDTO;
import br.com.letscoinback.exception.BusinessRunTimeException;
import br.com.letscoinback.persistence.entity.Partner;
import br.com.letscoinback.persistence.entity.PartnerSegment;
import br.com.letscoinback.persistence.entity.ProviderEntity;
import br.com.letscoinback.persistence.repository.PartnerRepository;
import br.com.letscoinback.persistence.repository.PartnerSegmentRepository;
import br.com.letscoinback.util.Translator;

@Service
public class PartnerService {

	@Autowired
	PartnerRepository partnerRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	PartnerSegmentRepository partnerSegmenteRepository;
	
	@Autowired
	ProviderService providerService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PartnerService.class);
	
	public Boolean validadeUrlIsPartner (String url) {
		List<Partner> partner = partnerRepository.findByRedirectStartsWith(url);
		if (partner == null || partner.isEmpty()) {
			return false;
		}
		return true;
	}
	
	public List<PartnerProviderDTO> getAll (Boolean isAdmin) {
		List<Partner> list = isAdmin 
				? partnerRepository.findAll(Sort.by("position").ascending().and(Sort.by("name"))) 
				: partnerRepository.findByAvailable(true, Sort.by("position").ascending().and(Sort.by("name")));
		return list
				.stream()
				.map(u -> modelMapper.map(u, PartnerProviderDTO.class))
				.collect(Collectors.toList());
	}
	
	public void savePartner (PartnerDTO partner, Integer providerId) {
		try {
			Partner ptr = modelMapper.map(partner, Partner.class);
			PartnerSegment ps = partnerSegmenteRepository.findByDescription(partner.getSegment())
									.orElse(partnerSegmenteRepository.save(newSegment(partner.getSegment())));
			ProviderEntity prov = providerService.getById(providerId);
			ptr.setPartnerSegment(ps);
			ptr.setProvider(prov);
			partnerRepository.save(ptr);	
		} catch (Exception e ) {
			String msg = "partner.register.failed";
			LOGGER.error(Translator.toLocale(msg), e);
			throw new BusinessRunTimeException(msg);
		}
	}
	
	private PartnerSegment newSegment (String segment) {
		PartnerSegment r = new PartnerSegment();
		r.setDescription(segment);
		return r;
	}
	
	public Partner getById(Integer id) {
		return partnerRepository.findById(id).orElse(null);
	}
}
