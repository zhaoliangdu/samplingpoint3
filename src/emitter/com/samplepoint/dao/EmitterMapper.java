package com.samplepoint.dao;

import java.util.List;

import com.samplepoint.beans.Emitter;
import java.util.Map;

public interface EmitterMapper {

	List<Emitter> geEmitters();
	
	Emitter findByEmitter();

	Emitter findById(int id);

	Emitter findByName(String emitterName);

	Emitter findByLocation(Map<String, Float> map);
	
	int updateEmitter(Emitter emitter);
}
