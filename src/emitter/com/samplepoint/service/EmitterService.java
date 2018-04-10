package com.samplepoint.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.samplepoint.beans.Emitter;
import com.samplepoint.dao.EmitterMapper;
import java.util.Map;

@Service
public class EmitterService {
	@Autowired
	EmitterMapper emitterMapper;

	public Emitter findByEmitter() {
		return emitterMapper.findByEmitter();
	}

	public List<Emitter> geEmitters() {
		return emitterMapper.geEmitters();
	}

	public Emitter findById(int id) {
		return emitterMapper.findById(id);
	}

	public Emitter findByName(String emitterName) {
		return emitterMapper.findByName(emitterName);
	}

	public Emitter findByLocation(Map<String, Float> map) {
		return emitterMapper.findByLocation(map);
	}

	public int updateEmitter(Emitter emitter) {
		return emitterMapper.updateEmitter(emitter);
	}
}
