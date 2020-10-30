package conductor.connect.probate.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "tbl_request")
@Getter
@Setter
@AllArgsConstructor
public class Request extends BaseModel{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Integer id;

    @Column(name = "time_request", nullable = false, updatable = false)
    public long timeRequest;

    @OneToMany(mappedBy = "request", cascade = CascadeType.REMOVE, orphanRemoval = true)
    public Set<Video> videos;

    @OneToMany(mappedBy = "request", cascade = CascadeType.REMOVE, orphanRemoval = true)
    public Set<Audio> audios;

    public Request() {
        Date date = new Date();
        long time = date.getTime();
        this.timeRequest = time;
    }

}
