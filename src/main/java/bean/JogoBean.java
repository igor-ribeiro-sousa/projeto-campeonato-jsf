package bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import dao.JogoDAO;
import dao.TimeDAO;
import entidade.Jogo;
import entidade.Time;
import exception.JSFException;

@ManagedBean
public class JogoBean {
	private Jogo jogo = new Jogo();
	private Integer selectTime1;
	private Integer selectTime2;
	private String nomeTimePesquisa;

	private List<Jogo> jogos = new ArrayList<Jogo>();
	private List<Jogo> listaDeResultados = new ArrayList<>();

	public String salvar() {
		try {
			if (validarInserir()) {
				completarJogada();
				JogoDAO.salvar(jogo);
				mensagemTela();
				jogo = new Jogo();
			}

		} catch (Exception e) {
			throw new JSFException(e.getMessage());
		}

		return null;
	}

	private void mensagemTela() {
		String mensagem = this.jogo.getResultado();
		;
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "RESULTADO FINAL : ", mensagem));
	}

	public void pesquisarPorNome() {
		try {
			if (this.nomeTimePesquisa != null && !this.nomeTimePesquisa.equals("")) {
			this.nomeTimePesquisa = this.nomeTimePesquisa.toUpperCase().trim();
			    this.listaDeResultados = JogoDAO.pesquisarPorNomeDoTime(nomeTimePesquisa);
			}

		} catch (JSFException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		}
	}

	private void completarJogada() {
		setCodTimes();
		setGolsMarcados();
		determinarPontuacaoRodada();
	}

	private void setCodTimes() {
		this.jogo.setCodigoTime1(this.selectTime1);
		this.jogo.setCodigoTime2(this.selectTime2);
		this.jogo.setData(new Date());
	}

	private void setGolsMarcados() {
		Integer[] opcoes = { 0, 1, 2, 3, 4, 5, 6 };
		Random random = new Random();

		this.jogo.setGolsMarcadosTime1(opcoes[random.nextInt(opcoes.length)]);
		this.jogo.setGolsMarcadosTime2(opcoes[random.nextInt(opcoes.length)]);
	}

	public void determinarPontuacaoRodada() {
		Time time1 = new Time();
		time1 = TimeDAO.getTime(selectTime1);
		Time time2 = new Time();
		time2 = TimeDAO.getTime(selectTime2);

		if (this.jogo.getGolsMarcadosTime1() == this.jogo.getGolsMarcadosTime2()) {
			completarRodada(time1, time2);
			configuraEmpate(time1, time2);

		} else if (this.jogo.getGolsMarcadosTime1() > this.jogo.getGolsMarcadosTime2()) {
			completarRodada(time1, time2);
			configuraVitoria(time1);
			configuraDerrota(time2);
			this.jogo.setResultado("VENCEDOR : " 
					+ " " + time1.getNome() 
					+ ", PLACAR FINAL : "
					+ this.jogo.getGolsMarcadosTime1() 
					+ " para o time do " + time1.getNome() 
					+ ", "
					+ this.jogo.getGolsMarcadosTime2() 
					+ " para o time do " + time2.getNome());

		} else {
			completarRodada(time1, time2);
			configuraVitoria(time2);
			configuraDerrota(time1);
			this.jogo.setResultado("VENCEDOR :" 
					+ time2.getNome() + ", PLACAR FINAL : "
					+ this.jogo.getGolsMarcadosTime2() 
					+ " para o time do " 
					+ time2.getNome() 
					+ ", "
					+ this.jogo.getGolsMarcadosTime1() 
					+ " para o time do " 
					+ time1.getNome());
		}
		TimeDAO.alterar(time1);
		TimeDAO.alterar(time2);
	}

	private void completarRodada(Time time1, Time time2) {

		int quantidadeGolsMarcadosTime1 = time1.getGolsMarcados();
		int quantidadeGolsMarcadosTime2 = time2.getGolsMarcados();
		time1.setGolsMarcados(quantidadeGolsMarcadosTime1 + this.jogo.getGolsMarcadosTime1());
		time2.setGolsMarcados(quantidadeGolsMarcadosTime2 + this.jogo.getGolsMarcadosTime2());

		int quantidadeGolsSofridosTime1 = time1.getGolsSofridos();
		int quantidadeGolsSofridosTime2 = time2.getGolsSofridos();
		time1.setGolsSofridos(quantidadeGolsSofridosTime1 + this.jogo.getGolsMarcadosTime2());
		time2.setGolsSofridos(quantidadeGolsSofridosTime2 + this.jogo.getGolsMarcadosTime1());

		int saldoGolsTime1 = time1.getGolsMarcados() - time1.getGolsSofridos();
		int saldoGolsTime2 = time2.getGolsMarcados() - time2.getGolsSofridos();
		time1.setSaldoGols(saldoGolsTime1);
		time2.setSaldoGols(saldoGolsTime2);

	}

	private void configuraEmpate(Time time1, Time time2) {
		int quantidadePontosTime1 = time1.getPontuacao();
		int quantidadePontosTime2 = time2.getPontuacao();
		time1.setPontuacao(quantidadePontosTime1 + 1);
		time2.setPontuacao(quantidadePontosTime2 + 1);

		int quantidadeEmpateTime1 = time1.getEmpate();
		int quantidadeEmpateTime2 = time1.getEmpate();
		time1.setEmpate(quantidadeEmpateTime1 + 1);
		time2.setEmpate(quantidadeEmpateTime2 + 1);
		this.jogo.setResultado("EMPATE");
	}

	private void configuraVitoria(Time time) {
		int vitoriasTime = time.getVitorias();
		time.setVitorias(vitoriasTime + 1);

		int quantidadePontosTime1 = time.getPontuacao();
		time.setPontuacao(quantidadePontosTime1 + 3);
	}

	private void configuraDerrota(Time time) {
		int derrotaTime = time.getDerrotas();
		time.setDerrotas(derrotaTime + 1);
	}

//	public void editar(Integer id) {
//		try {
//			FacesContext.getCurrentInstance().getExternalContext().redirect("jogo-alterar.xhtml");
//			Jogada jogadaAtualiza = JogadaDAO.getJogador(id);
//
//		} catch (Exception e) {
//			throw new JSFException(e.getMessage());
//		}
//
//	}
//
//	public Jogada carregarJogada() {
//		return jogada;
//	}
//
	public void deletar(Integer id) {
		try {
			JogoDAO.excluir(id);
			String mensagem = "Jogo excluída!";
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Resultado", mensagem));
//			FacesContext.getCurrentInstance().getExternalContext().redirect("time-pesquisar.xhtml");

		} catch (Exception e) {
			throw new JSFException(e.getMessage());
		}

	}

	private boolean validarInserir() {
		if (Objects.isNull(this.selectTime1) || Objects.isNull(this.selectTime2)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Os 2(dois) times devem ser selecionados"));
			return false;
		}
		if (this.selectTime1 == this.selectTime2) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ops!", "Times iguais não podem jogar!"));
			return false;
		}

		return true;
	}

	public Jogo getJogo() {
		return jogo;
	}

	public void setJogo(Jogo jogada) {
		this.jogo = jogada;
	}

	public List<Jogo> getJogos() {
		if (this.jogos != null) {
			this.jogos = JogoDAO.pesquisar();
		}
		return jogos;
	}

	public void setJogos(List<Jogo> jogos) {
		this.jogos = jogos;
	}

	public Integer getSelectTime1() {
		return selectTime1;
	}

	public void setSelectTime1(Integer selectTime1) {
		this.selectTime1 = selectTime1;
	}

	public Integer getSelectTime2() {
		return selectTime2;
	}

	public void setSelectTime2(Integer selectTime2) {
		this.selectTime2 = selectTime2;
	}

	public String getNomeTimePesquisa() {
		return nomeTimePesquisa;
	}

	public void setNomeTimePesquisa(String nomeTimePesquisa) {
		this.nomeTimePesquisa = nomeTimePesquisa;
	}

	public List<Jogo> getListaDeResultados() {
		return listaDeResultados;
	}

	public void setListaDeResultados(List<Jogo> listaDeResultados) {
		this.listaDeResultados = listaDeResultados;
	}
}
