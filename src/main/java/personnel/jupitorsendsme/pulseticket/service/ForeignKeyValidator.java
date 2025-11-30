package personnel.jupitorsendsme.pulseticket.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import personnel.jupitorsendsme.pulseticket.exception.InvalidForeignKeyException;

@Component
public class ForeignKeyValidator {

	public <T, ID, E> void validateExists(
		CrudRepository<T, ID> parentRepository,
		E childEntity,
		Class<?> parent,
		ID foreignKeyValue,
		String columnName
	) {
		if (foreignKeyValue == null || !parentRepository.existsById(foreignKeyValue)) {
			assert foreignKeyValue != null;
			throw new InvalidForeignKeyException(childEntity, parent, foreignKeyValue.toString(), columnName);
		}
	}
}
