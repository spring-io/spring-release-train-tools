package io.spring.releasetrain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class CheckMilestoneDueOnMainTests {
	@Test
	void mainWhenDueOnValid() {
		CheckMilestoneDueOnMain.main("--dueOn", "2021-04-19T22:48:47Z","--expectedDayOfWeek" , "MONDAY", "--expectedMondayCount", "3");
	}

	@Test
	void mainWhenDueOnInvalidDate() {
		assertThatExceptionOfType(DueOnException.class)
			.isThrownBy(() -> CheckMilestoneDueOnMain.main("--dueOn", "2021-04-19T22:48:47Z","--expectedDayOfWeek" , "WEDNESDAY", "--expectedMondayCount", "3"));
	}

	@Test
	void mainWhenMissingArguments() {
		assertThatIllegalArgumentException().isThrownBy(() -> CheckMilestoneDueOnMain.main("-"))
			.withMessage("Invalid usage. Expecting --dueOn <ISO-8601-DATE> --expectedDayOfWeek (SUNDAY|MONDAY|TUESDAY|WEDNESDAY|THURSDAY|FRIDAY|SATURDAY) --expectedMondayCount <int> Got [-]");
	}
}