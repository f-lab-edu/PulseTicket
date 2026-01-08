package personnel.jupitorsendsme.pulseticket.util;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.function.BiConsumer;

import org.mockito.ArgumentCaptor;

public class MockitoTestUtils {
	private MockitoTestUtils() {
	}

	public static <T, E> void assertMethodParameterEquals(T mockClass, BiConsumer<T, E> targetMethod,
		ArgumentCaptor<E> captor, E targetObject) {
		targetMethod.accept(verify(mockClass), captor.capture());

		assertThat(captor.getValue())
			.usingRecursiveComparison()
			.isEqualTo(targetObject);
	}
}
