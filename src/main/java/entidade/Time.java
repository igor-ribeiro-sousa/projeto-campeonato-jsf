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
@Table(name = "TBL_TM")
public class Time {

	@Id
	@Column(name = "CD_TM")
	@GeneratedValue
	private Integer id;

	@Column(name = "NM_TM")
	private String nome;

	@Column(name = "PT_TM")
	private Integer pontuacao;

	@Column(name = "VT_TM")
	private Integer vitorias;

	@Column(name = "DT_TM")
	private Integer derrotas;

	@Column(name = "EP_TM")
	private Integer empate;

	@Column(name = "GM_TM")
	private Integer golsMarcados;

	@Column(name = "GS_TM")
	private Integer golsSofridos;

	@Column(name = "SG_TM")
	private Integer saldoGols;

	@Column(name = "FG_ATV")
	private String flagAtivo;

	@Column(name = "DT_INC")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataInclusao;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(Integer pontuacao) {
		this.pontuacao = pontuacao;
	}

	public Integer getVitorias() {
		return vitorias;
	}

	public void setVitorias(Integer vitorias) {
		this.vitorias = vitorias;
	}

	public Integer getDerrotas() {
		return derrotas;
	}

	public void setDerrotas(Integer derrotas) {
		this.derrotas = derrotas;
	}

	public Integer getEmpate() {
		return empate;
	}

	public void setEmpate(Integer empate) {
		this.empate = empate;
	}

	public Integer getGolsMarcados() {
		return golsMarcados;
	}

	public void setGolsMarcados(Integer golsMarcados) {
		this.golsMarcados = golsMarcados;
	}

	public Integer getGolsSofridos() {
		return golsSofridos;
	}

	public void setGolsSofridos(Integer golsSofridos) {
		this.golsSofridos = golsSofridos;
	}

	public Integer getSaldoGols() {
		return saldoGols;
	}

	public void setSaldoGols(Integer saldoGols) {
		this.saldoGols = saldoGols;
	}

	public String getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(String flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

}
