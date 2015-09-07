package finki.ask.service;

import java.util.UUID;

import javax.transaction.Transactional;

@Transactional
public interface UUIDGenerator {
	public UUID generateNewUUID();
}
