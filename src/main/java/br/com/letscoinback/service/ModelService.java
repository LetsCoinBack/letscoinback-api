package br.com.model.service;

import org.springframework.stereotype.Service;

import br.com.model.util.Translator;

@Service
public class ModelService {
	
	public String hello () {
		return Translator.toLocale("model.hello");
	}

}
