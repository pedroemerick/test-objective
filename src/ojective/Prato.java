package ojective;

public class Prato {
	
	private Long id;
	private TipoPrato tipo;
	private String descricao;
	private String qualidade;
	private Long pratoPaiId;
	
	public Prato() {
		super();
	}
	
	public Prato(Long id, TipoPrato tipo, String descricao, String qualidade, Long pratoPaiId) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.descricao = descricao;
		this.qualidade = qualidade;
		this.pratoPaiId = pratoPaiId;
	}
	
	public Long getId() {
		return id;
	}

	public TipoPrato getTipo() {
		return tipo;
	}

	public void setTipo(TipoPrato tipo) {
		this.tipo = tipo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getQualidade() {
		return qualidade;
	}

	public void setQualidade(String qualidade) {
		this.qualidade = qualidade;
	}

	public Long getPratoPaiId() {
		return pratoPaiId;
	}

	public void setPratoPaiId(Long pratoPaiId) {
		this.pratoPaiId = pratoPaiId;
	}
}
