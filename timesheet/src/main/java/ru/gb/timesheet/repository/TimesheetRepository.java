package ru.gb.timesheet.repository;

import org.springframework.stereotype.Repository;
import ru.gb.timesheet.model.Timesheet;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;

@Repository
public class TimesheetRepository {

    private static Long sequence = 1L;
    private final List<Timesheet> timesheets = new ArrayList<>();

    public Optional<Timesheet> findById(Long id) {
        return timesheets.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst();
    }


    public List<Timesheet> findAll(LocalDate createdAtBefore, LocalDate createdAtAfter) {
        Predicate<Timesheet> filter = it -> true;

        if (Objects.nonNull(createdAtBefore)) {
            filter = filter.and(it -> it.getCreatedAt().isBefore(createdAtBefore));
        }

        if (Objects.nonNull(createdAtAfter)) {
            filter = filter.and(it -> it.getCreatedAt().isAfter(createdAtAfter));
        }
        return timesheets.stream()
                .filter(filter)
                .toList();
    }

    public Timesheet create(Timesheet timesheet) {
        timesheet.setId(sequence++);
        timesheets.add(timesheet);
        return timesheet;
    }

    public void delete(Long id) {
        timesheets.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .ifPresent(timesheets::remove);
    }

    public List<Timesheet> findByProjectId(Long projectId) {
        return timesheets.stream()
                .filter(it -> Objects.equals(it.getProjectId(), projectId))
                .toList();
    }

    public void fulTimesheetList(){
        Random random = new Random();
        for (int i = 0; i < 15; i++) {
            Timesheet timesheet = new Timesheet();
            timesheet.setId((1L + i));
            timesheet.setProjectId(random.nextLong(1, 6));
            timesheet.setMinutes((random.nextInt(60, 481)/10)*10);
            String strDate = "2024-07-0" + random.nextInt(1, 9);
            timesheet.setCreatedAt(convertStringToDate(strDate));
            timesheets.add(timesheet);
        }
    }
    private LocalDate convertStringToDate(String strDate){
        LocalDate localDate = LocalDate.now();
        try {
            DateTimeFormatter formatter
                    = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            localDate = LocalDate.parse(strDate, formatter);
            return localDate;
        }
        catch (Exception e) {
            System.out.println("Ошибка при преобразовании строки в дату "
                    + e.getMessage());
        }
        return localDate;
    }
}
