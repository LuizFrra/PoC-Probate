package conductor.connect.probate.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tbl_audios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Audio extends BaseModel {

    @Id
    public String id;

    private Status Status;

    public String path;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "request_id", nullable = false)
    public Request request;

    public Audio(String url) {
        this.id = url.split("=")[1];
    }

    @Override
    public String getPath() {
        return path;
    }

}
