package ojective;

public enum TipoPrato {
	
	MASSA(1), OUTRO(2);
	
	private final int valor;
	
	private TipoPrato(int valor) {
		this.valor = valor;
	}

	public int getValor() {
		return valor;
	}
}
