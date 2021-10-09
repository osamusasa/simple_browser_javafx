package xyz.osamusasa.browser.records;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class WebHistoryEntry implements Serializable {
    private final String url;
    private final String title;
    private final LocalDateTime accessDate;

    @Override
    public String toString() {
        return title;
    }
}
