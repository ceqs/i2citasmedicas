package pe.edu.utp.i2.citasmedicas.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class Event {
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime end;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime start;
    private String title;

    private String textColor;

    private String backgroundColor;

    private EventExtend extendedProps;

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public EventExtend getExtendedProps() {
        if(extendedProps == null) extendedProps = new EventExtend();
        return extendedProps;
    }

    public void setExtendedProps(EventExtend extendedProps) {
        this.extendedProps = extendedProps;
    }
}
