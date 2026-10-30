package com.place2book.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest 
class ExampleUnitTest {

    @Test
    void twoPlusTwoIsFour() {
        assertThat(2 + 2).isEqualTo(4);
    }

    @Test
    @DisplayName("Dividing by zero throws ArithmeticException")
    void divideByZeroThrows() {
        assertThrows(ArithmeticException.class, () -> {
            int result = 10 / 0;
        });
    }

    @ParameterizedTest(name = "{0} + {1} = {2}")
    @CsvSource({
        "1, 1, 2",
        "2, 3, 5",
        "-4, 4, 0"
    })
    void addsNumbers(int a, int b, int expected) {
        assertThat(a + b).isEqualTo(expected);
    }
}
