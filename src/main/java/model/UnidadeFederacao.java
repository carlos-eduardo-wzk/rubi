package model;

public enum UnidadeFederacao {

	AC("Acre"), AL("Alagoas"), AM("Amazonas"), AP("Amapa"), BA("Bahia"), CE(
			"Ceará"), DF("Distrito Federal"), ES("Espirito Santo"), GO("Goias"), MA(
			"Maranhão"), MG("Minas Gerais"), MS("Mato Grosso Sul"), MT(
			"Mato Grosso"), PA("Pará"), PB("Paraiba"), PE("Pernanbuco"), PI(
			"Piaui"), PR("Parana"), RJ("Rio de Janeiro"), RN(
			"Rio Grande do Norte"), RO("Rondonia"), RR("Roraima"), RS(
			"Rio Grande do Sul"), SC("Santa Catarina"), SE("Sergipe"), SP(
			"São Paulo"), TO("Tocantins");

	private String descricao;

	UnidadeFederacao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
