package br.com.letscoinback.service;

import org.springframework.stereotype.Service;

import br.com.letscoinback.util.Translator;

@Service
public class ModelService {
	
	public String hello () {
		return Translator.toLocale("model.hello");
	}

}
