/*
 * Copyright 2019-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.spring.releasetrain;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.Instant;

class ReleaseTrainMilestoneDueOnTests {

	@Test
	void checkValidDueOnWhenExpectedThirdMondayAndThurFirstDayOfMonth() {
		ReleaseTrainMilestoneDueOn validator = new ReleaseTrainMilestoneDueOn(DayOfWeek.MONDAY, 3);
		Instant instant = Instant.parse("2021-04-19T22:48:47Z");
		validator.checkValidDueOn(instant);
	}

	@Test
	void checkValidDueOnWhenExpectedThirdThursdayAndThurFirstDayOfMonth() {
		ReleaseTrainMilestoneDueOn validator = new ReleaseTrainMilestoneDueOn(DayOfWeek.THURSDAY, 3);
		Instant instant = Instant.parse("2021-04-22T22:48:47Z");
		validator.checkValidDueOn(instant);
	}

	@Test
	void checkValidDueOnWhenExpectedThirdMondayAndMonFirstDayOfMonth() {
		ReleaseTrainMilestoneDueOn validator = new ReleaseTrainMilestoneDueOn(DayOfWeek.MONDAY, 3);
		Instant instant = Instant.parse("2021-03-15T22:48:47Z");
		validator.checkValidDueOn(instant);
	}

	@Test
	void checkValidDueOnWhenExpectedThirdThursdayAndMonFirstDayOfMonth() {
		ReleaseTrainMilestoneDueOn validator = new ReleaseTrainMilestoneDueOn(DayOfWeek.THURSDAY, 3);
		Instant instant = Instant.parse("2021-03-18T22:48:47Z");
		validator.checkValidDueOn(instant);
	}
}