package br.com.foxinline.controlebens.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.foxinline.controlebens.dao.BemDAO;
import br.com.foxinline.controlebens.dao.NomeavelDAO;
import br.com.foxinline.controlebens.dao.TipoProdutoDAO;
import br.com.foxinline.controlebens.model.Bem;
import br.com.foxinline.controlebens.model.TipoProduto;
import br.com.foxinline.controlebens.service.DepreciacaoService;

@ManagedBean
@ViewScoped
public class BemBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Bem bem = new Bem();
	private List<Bem> lista;
	private List<TipoProduto> tipos;
	private String filtroNome;


	private TipoProdutoDAO tipoProdutoDAO = new TipoProdutoDAO();
	private NomeavelDAO<Bem> bemDao = new NomeavelDAO<>(Bem.class);
	private DepreciacaoService depreciacaoService = new DepreciacaoService();

	@PostConstruct
	public void init() {
		lista = bemDao.listarTodos();
		tipos = tipoProdutoDAO.listarTodos();
	}

	public void salvar() {
		FacesContext context = FacesContext.getCurrentInstance();

		if (bem.getPrecoCompra() == null || bem.getPrecoCompra().compareTo(BigDecimal.ZERO) <= 0) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Preço de compra deve ser maior que zero.", null));
			return;
		}

		if (bem.getVidaUtil() == null || bem.getVidaUtil() <= 0) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Vida útil deve ser maior que zero.", null));
			return;
		}

		if (bem.getValorResidual() != null && bem.getValorResidual().compareTo(bem.getPrecoCompra()) > 0) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Valor residual não pode ser maior que o preço de compra.", null));
			return;
		}

		if (bem.getId() == null) {
			bemDao.salvar(bem);
		} else {
			bemDao.atualizar(bem);
		}

		limpar();
		lista = bemDao.listarTodos();
	}

	public void editar(Bem b) {
		this.bem = b;
	}

	public void excluir(Bem b) {
		bemDao.remover(b.getId());
		lista = bemDao.listarTodos(); 
	}

	public void limpar() {
		bem = new Bem();
	}

	public BigDecimal depreciacaoAnual(Bem bem) {
		return depreciacaoService.calcularDepreciacaoAnual(bem);
	}
	
	public void listar() {
		lista = bemDao.listarTodos();
	}
	
	
	
	public void filtrar() {
		if (filtroNome == null || filtroNome.trim().isEmpty()) {
			listar();
		} else {
			lista = bemDao.filtrarPorNome(filtroNome);
		}
	}


	public Bem getBem() {
		return bem;
	}

	public String getFiltroNome() {
		return filtroNome;
	}

	public void setFiltroNome(String filtroNome) {
		this.filtroNome = filtroNome;
	}

	public void setBem(Bem bem) {
		this.bem = bem;
	}

	public List<Bem> getLista() {
		return lista;
	}

	public void setLista(List<Bem> lista) {
		this.lista = lista;
	}

	public List<TipoProduto> getTipos() {
		return tipos;
	}

	public void setTipos(List<TipoProduto> tipos) {
		this.tipos = tipos;
	}
}
