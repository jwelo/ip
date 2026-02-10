package martin.task;

public class Event extends Task {

    private String startDate;
    private String endDate;

    public void changeStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void changeEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Event(String description, String startDate, String endDate) {
        super(description);
        changeTypeOfTask("E");
        changeTaskDescription(description + " (from: " + startDate + " to: " + endDate + ")");
    }
}
