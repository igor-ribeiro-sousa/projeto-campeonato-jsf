package bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import dao.TimeDAO;
import entidade.Time;
import exception.JSFException;

@ManagedBean
@ViewScoped
public class TimeBean {
	private Time time = new Time();
	private List<Time> times = new ArrayList<>();

	public String salvar() {
		try {
			if (validarInserir(time)) {
				completarInserir();
				TimeDAO.salvar(time);
				mensagemTela();
				time = new Time();
			}

		} catch (Exception e) {
			throw new JSFException(e.getMessage());
		}

		return null;
	}

	private void mensagemTela() {
		String mensagem = "Time inserido com sucesso!";
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", mensagem));
	}
	
	private void completarInserir() {
		this.time.setNome(this.time.getNome().toUpperCase().trim());
		this.time.setPontuacao(0);
		this.time.setVitorias(0);
		this.time.setDerrotas(0);
		this.time.setEmpate(0);
		this.time.setGolsMarcados(0);
		this.time.setGolsSofridos(0);
		this.time.setSaldoGols(0);
		this.time.setDataInclusao(new Date());
		this.time.setFlagAtivo("S");
	}

	public void editar(Integer id) {
//		try {
//			FacesContext.getCurrentInstance().getExternalContext().redirect("jogo-alterar.xhtml");
//			Jogada jogadaAtualiza = JogadaDAO.getJogador(id);
//
//		} catch (Exception e) {
//			throw new JSFException(e.getMessage());
//		}

	}

	public void ordenarPorPontuacao() {
	}

	public void deletar(Integer id) {
		try {
			TimeDAO.excluir(id);
			String mensagem = "Time excluído!";
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "", mensagem));
//			FacesContext.getCurrentInstance().getExternalContext().redirect("time-pesquisar.xhtml");

		} catch (Exception e) {
			throw new JSFException(e.getMessage());
		}

	}

	private boolean validarInserir(Time time) {
		if (this.time.getNome() == null || this.time.getNome().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Nome do time é obrigatório!"));
			return false;
		}
		if (Objects.nonNull(this.getTimes())) {
			for (Time item : this.times) {
				if (item.getNome().equalsIgnoreCase(time.getNome())) {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Nome do time já existe!"));
					return false;
				}
			}
		}
		return true;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public List<Time> getTimes() {
		if (this.times != null) {
			this.times = TimeDAO.pesquisar();
		}
		return this.times;
	}

	public void setTimes(List<Time> times) {
		this.times = times;
	}
}
