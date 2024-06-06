package org.sandcastle.apps.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sandcastle.apps.utils.TaskResult;

import java.util.StringJoiner;

@Getter
@Setter
public class Record implements TaskResult {
    @JsonProperty("id") private String id;
    @JsonProperty("timestamp") private long timestamp;
    @JsonProperty("rank") private int rank;

    @Getter(AccessLevel.NONE)
    private boolean lastRecord;

    public Record() {
    }

    @JsonCreator
    public Record(@JsonProperty("id") String id, @JsonProperty("timestamp") long timestamp, @JsonProperty("rank") int rank) {
        this.id = id;
        this.timestamp = timestamp;
        this.rank = rank;
    }

    @Override
    public String toString() {
        try {
            ObjectWriter ow = new ObjectMapper().writer();
            return ow.writeValueAsString(this);
        } catch (JsonProcessingException ex) {
            return new StringJoiner(", ", Record.class.getSimpleName() + "[", "]")
                    .add("id=" + id)
                    .add("timestamp=" + timestamp)
                    .add("rank=" + rank)
                    .toString();
        }
    }

    @Override
    public boolean isLastResult() {
        return lastRecord;
    }


}
