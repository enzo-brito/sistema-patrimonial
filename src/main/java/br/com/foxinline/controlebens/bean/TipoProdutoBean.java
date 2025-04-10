package br.com.foxinline.controlebens.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.foxinline.controlebens.dao.NomeavelDAO;
import br.com.foxinline.controlebens.model.TipoProduto;

@ManagedBean
@ViewScoped
public class TipoProdutoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private TipoProduto tipoProduto = new TipoProduto();
	private List<TipoProduto> lista;
	private String filtroNome;

	private NomeavelDAO<TipoProduto> tipoProdutoDao = new NomeavelDAO<>(TipoProduto.class);

	@PostConstruct
	public void init() {
		listar();
	}

	public void salvar() {
		FacesContext context = FacesContext.getCurrentInstance();

		if (tipoProduto.getNome() == null || tipoProduto.getNome().trim().isEmpty()) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "O nome do tipo de produto é obrigatório.", null));
			return;
		}

		TipoProduto existente = tipoProdutoDao.buscarPorCampoUnico("nome", tipoProduto.getNome());
		if (existente != null && (tipoProduto.getId() == null || !tipoProduto.getId().equals(existente.getId()))) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Já existe um tipo de produto com este nome.", null));
			return;
		}

		if (tipoProduto.getId() == null) {
			tipoProdutoDao.salvar(tipoProduto);
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Tipo de produto cadastrado com sucesso!", null));
		} else {
			tipoProdutoDao.atualizar(tipoProduto);
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Tipo de produto atualizado com sucesso!", null));
		}

		limpar();
		listar();
	}

	public void editar(TipoProduto tp) {
		this.tipoProduto = tp;
	}

	public void excluir(TipoProduto tp) {
		tipoProdutoDao.remover(tp.getId());
		listar();
	}

	public void listar() {
		lista = tipoProdutoDao.listarTodos();
	}

	public void filtrar() {
		if (filtroNome == null || filtroNome.trim().isEmpty()) {
			listar();
		} else {
			lista = tipoProdutoDao.filtrarPorNome(filtroNome);
		}
	}

	public void limpar() {
		tipoProduto = new TipoProduto();
	}

	public TipoProduto getTipoProduto() {
		return tipoProduto;
	}

	public void setTipoProduto(TipoProduto tipoProduto) {
		this.tipoProduto = tipoProduto;
	}

	public List<TipoProduto> getLista() {
		return lista;
	}

	public void setLista(List<TipoProduto> lista) {
		this.lista = lista;
	}

	public String getFiltroNome() {
		return filtroNome;
	}

	public void setFiltroNome(String filtroNome) {
		this.filtroNome = filtroNome;
	}
}
