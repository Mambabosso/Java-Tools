package eztools;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAmount;

public final class EzDateTime implements Serializable {

    private static final long serialVersionUID = 1L;

    private LocalDate date;
    private LocalTime time;

    public EzDateTime(LocalDate date, LocalTime time) {
        this.date = date;
        this.time = time;
    }

    public EzDateTime(LocalDateTime dateTime) {
        this.date = dateTime.toLocalDate();
        this.time = dateTime.toLocalTime();
    }

    public EzDateTime addNanos(int nanos) {
        return new EzDateTime(date, time.plusNanos(nanos));
    }

    public EzDateTime addSeconds(int seconds) {
        return new EzDateTime(date, time.plusSeconds(seconds));
    }

    public EzDateTime addMinutes(int minutes) {
        return new EzDateTime(date, time.plusMinutes(minutes));
    }

    public EzDateTime addHours(int hours) {
        return new EzDateTime(date, time.plusHours(hours));
    }

    public EzDateTime addDays(int days) {
        return new EzDateTime(date.plusDays(days), time);
    }

    public EzDateTime addWeeks(int weeks) {
        return new EzDateTime(date.plusWeeks(weeks), time);
    }

    public EzDateTime addMonths(int months) {
        return new EzDateTime(date.plusMonths(months), time);
    }

    public EzDateTime addYears(int years) {
        return new EzDateTime(date.plusYears(years), time);
    }

    public EzDateTime addTemporalAmount(TemporalAmount temporalAmount) {
        return new EzDateTime(toDateTime().plus(temporalAmount));
    }

    public LocalDateTime toDateTime() {
        return LocalDateTime.of(date, time);
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String toString() {
        return String.format("%s %s", date, time);
    }

    public String toString(String dateFormat, String timeFormat) {
        return String.format("%s %s", toDateString(dateFormat), toTimeString(timeFormat));
    }

    public String toDateString(String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return date.format(formatter);
    }

    public String toTimeString(String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return time.format(formatter);
    }

    public static EzDateTime now() {
        return new EzDateTime(LocalDate.now(), LocalTime.now());
    }

    public static EzDateTime parse(String dateTime, String format) {
        LocalDateTime parsedDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(format));
        return new EzDateTime(parsedDateTime.toLocalDate(), parsedDateTime.toLocalTime());
    }

    public static EzDateTime parseDate(String date, String format) {
        LocalDate parsedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(format));
        return new EzDateTime(parsedDate, LocalTime.MIN);
    }

    public static EzDateTime parseTime(String time, String format) {
        LocalTime parsedTime = LocalTime.parse(time, DateTimeFormatter.ofPattern(format));
        return new EzDateTime(LocalDate.MIN, parsedTime);
    }

    public static Period between(EzDateTime dateTime1, EzDateTime dateTime2) {
        return Period.between(dateTime1.getDate(), dateTime2.getDate());
    }
}
