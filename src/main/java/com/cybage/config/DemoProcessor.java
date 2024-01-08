package com.cybage.config;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.cybage.entities.Demo;
@Component
public class DemoProcessor implements ItemProcessor<Demo,Demo>{

	@Override
	public Demo process(Demo demo) throws Exception {
		
		return demo;
	}

}
