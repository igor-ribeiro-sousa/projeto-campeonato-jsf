package entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TBL_JG")
public class Jogo {

    @Id
    @Column(name = "CD_JG")
    @GeneratedValue
    private Integer id;

    @Column(name = "CD_TM1")
	private Integer codigoTime1;

	@ManyToOne
	@JoinColumn(name = "CD_TM1", referencedColumnName = "CD_TM", insertable = false, updatable = false)
	private Time time1;

	@Column(name = "CD_TM2")
	private Integer codigoTime2;

	@ManyToOne
	@JoinColumn(name = "CD_TM2", referencedColumnName = "CD_TM", insertable = false, updatable = false)
	private Time time2;

	@Column(name = "GM_TM1")
	private Integer golsMarcadosTime1;

	@Column(name = "GM_TM2")
	private Integer golsMarcadosTime2;

	@Column(name = "RD_JG")
	private String resultado;

	@Column(name = "DT_JGD")
	@Temporal(TemporalType.TIMESTAMP)
	private Date data;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCodigoTime1() {
		return codigoTime1;
	}

	public void setCodigoTime1(Integer codigoTime1) {
		this.codigoTime1 = codigoTime1;
	}

	public Time getTime1() {
		return time1;
	}

	public void setTime1(Time time1) {
		this.time1 = time1;
	}

	public Integer getCodigoTime2() {
		return codigoTime2;
	}

	public void setCodigoTime2(Integer codigoTime2) {
		this.codigoTime2 = codigoTime2;
	}

	public Time getTime2() {
		return time2;
	}

	public void setTime2(Time time2) {
		this.time2 = time2;
	}

	public Integer getGolsMarcadosTime1() {
		return golsMarcadosTime1;
	}

	public void setGolsMarcadosTime1(Integer golsMarcadosTime1) {
		this.golsMarcadosTime1 = golsMarcadosTime1;
	}

	public Integer getGolsMarcadosTime2() {
		return golsMarcadosTime2;
	}

	public void setGolsMarcadosTime2(Integer golsMarcadosTime2) {
		this.golsMarcadosTime2 = golsMarcadosTime2;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
}
