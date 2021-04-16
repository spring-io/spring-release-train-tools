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

import java.time.DayOfWeek;
import java.time.Instant;
import java.util.Arrays;

public class CheckMilestoneDueOnMain {
	public static void main(String... args) {
		try {
			run(args);
		}
		catch (RuntimeException e) {
			throw new IllegalArgumentException("Invalid usage. Expecting --dueOn <ISO-8601-DATE> --expectedDayOfWeek (SUNDAY|MONDAY|TUESDAY|WEDNESDAY|THURSDAY|FRIDAY|SATURDAY) --expectedMondayCount <int> Got " + Arrays.asList(args), e);
		}
	}

	private static void run(String... args) {
		if (args.length != 6) {
			throw new IllegalArgumentException("Invalid number of arguments> Expected 6 but got " + args.length);
		}
		Instant dueOn = null;
		DayOfWeek expectedDayOfWeek = null;
		int expectedDayOfWeekCount = 0;

		for (int i=0; i < args.length; i+=2) {
			String argumentName = args[i];
			String argumentValue = args[i+1];
			if (isArgumentParamName(argumentName, "dueOn")) {
				dueOn = Instant.parse(argumentValue);
			}
			else if (isArgumentParamName(argumentName, "expectedDayOfWeek")) {
				expectedDayOfWeek = DayOfWeek.valueOf(argumentValue);
			}
			else if (isArgumentParamName(argumentName, "expectedMondayCount")) {
				expectedDayOfWeekCount = Integer.parseInt(argumentValue);
			}
			else {
				throw new IllegalArgumentException("Invalid argumentName " + argumentName);
			}
		}
		ReleaseTrainMilestoneDueOn checks = new ReleaseTrainMilestoneDueOn(expectedDayOfWeek, expectedDayOfWeekCount);
		checks.checkValidDueOn(dueOn);
	}

	private static boolean isArgumentParamName(String argument, String paramName) {
		String expectedArgumentValue = "--" + paramName;
		return argument.equals(expectedArgumentValue);
	}
}
