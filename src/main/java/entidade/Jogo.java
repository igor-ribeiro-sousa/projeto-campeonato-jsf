package entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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

	@Column(name = "NM_TM1")
	private String nomeTime1;

	@Column(name = "NM_TM2")
	private String nomeTime2;

//    @Column(name = "CD_TM1")
//	private Integer codigoTime1;
//
//	@ManyToOne
//	@JoinColumn(name = "CD_TM1", referencedColumnName = "CD_TM", insertable = false, updatable = false)
//	private Time time1;

//	@Column(name = "CD_TM2")
//	private Integer codigoTime2;
//
//	@ManyToOne
//	@JoinColumn(name = "CD_TM2", referencedColumnName = "CD_TM", insertable = false, updatable = false)
//	private Time time2;

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

	public String getNomeTime1() {
		return nomeTime1;
	}

	public void setNomeTime1(String nomeTime1) {
		this.nomeTime1 = nomeTime1;
	}

	public String getNomeTime2() {
		return nomeTime2;
	}

	public void setNomeTime2(String nomeTime2) {
		this.nomeTime2 = nomeTime2;
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

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

}
