package finki.ask.service.impl;

import java.util.UUID;

import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.UUIDGenerationStrategy;

public class UUIDGeneratorImpl implements UUIDGenerationStrategy{

	
	@Override
	public UUID generateUUID(SessionImplementor arg0) {
		return UUID.randomUUID();
	}

	@Override
	public int getGeneratedVersion() {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
