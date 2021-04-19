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

import java.time.*;
import java.time.temporal.TemporalAdjusters;

class ReleaseTrainMilestoneDueOn {
	private final DayOfWeek expectedDayOfWeek;

	private final int expectedMondayCount;

    /**
     * Creates a new instance
     * @param expectedDayOfWeek the day of the week that this project releases on
     * @param expectedMondayCount the number of Mondays (inclusive) that must occur before the release.
     */
	public ReleaseTrainMilestoneDueOn(DayOfWeek expectedDayOfWeek, int expectedMondayCount) {
		this.expectedDayOfWeek = expectedDayOfWeek;
		this.expectedMondayCount = expectedMondayCount;
	}

	public void checkValidDueOn(Instant dueOn) {
		LocalDate dueOnDate = dueOn.atZone(ZoneId.systemDefault()).toLocalDate();
		YearMonth actualYearMonth = YearMonth.from(dueOnDate);
		LocalDate expectedDate = getExpectedDayTimeFor(actualYearMonth);
		if (!expectedDate.equals(dueOnDate)) {
			throw new DueOnException("Expecting " + dueOn + " to be on " + expectedDate + " for expectedDayOfWeek " + this.expectedDayOfWeek + " and expectedDayOfWeek " + this.expectedMondayCount);
		}
	}

	private LocalDate getExpectedDayTimeFor(YearMonth yearMonth) {
		LocalDate firstDayOfMonth = yearMonth.atDay(1);
		LocalDate firstMonday = firstDayOfMonth.with(TemporalAdjusters.firstDayOfMonth())
				.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
		LocalDate expectedDate = firstMonday;
		int startCount = (this.expectedDayOfWeek == DayOfWeek.MONDAY) ? 1 : 0;
		for (int i = startCount; i < this.expectedMondayCount; i++) {
			expectedDate = expectedDate.with(TemporalAdjusters.next(this.expectedDayOfWeek));
		}
		return expectedDate;
	}
}
