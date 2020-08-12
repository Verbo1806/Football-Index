package bg.verbo.footind.db.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "requests_log")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class RequestLog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "request_timestamp")
	private Date requestTimestamp;
	
	@Column(name = "url")
	private String url;
	
	@Column(name = "username")
	private String username;
}
