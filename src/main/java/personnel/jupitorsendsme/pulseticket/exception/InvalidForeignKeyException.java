package personnel.jupitorsendsme.pulseticket.exception;

import lombok.Getter;

@Getter
public class InvalidForeignKeyException extends RuntimeException {
	private final String foreignKeyValue;
	private final String childTableName;
	private final String parentTableName;

	public <T> InvalidForeignKeyException(T child, Class<?> parent, String foreignKeyValue, String columnName) {
		super(String.format("유효하지 않은 외부키 - child: %s, parent: %s, foreignKeyValue: %s, columnName: %s",
			child.getClass().getSimpleName(), parent.getSimpleName(), foreignKeyValue, columnName));
		
		this.foreignKeyValue = foreignKeyValue;
		this.childTableName = child.getClass().getSimpleName();
		this.parentTableName = parent.getSimpleName();
	}
}
