package main;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class FridaySeed {
	public long getSeed() {
		return nextFri();
	}

	private long nextFri() {
		LocalDate d = LocalDate.now();
		if(d.getDayOfWeek() == DayOfWeek.FRIDAY) {
			return d.toEpochDay();
		}
		LocalDate r = d.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
		return r.toEpochDay();
	}
}
