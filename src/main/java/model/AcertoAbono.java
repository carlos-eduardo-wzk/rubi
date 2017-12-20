package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

public class AcertoAbono implements Serializable {

	private static final long serialVersionUID = 1L;

	public AcertoAbono() {
	}




	public AcertoAbono(Long id, Date data, String matricula, String nome,
			Integer depto_id, Integer ht, Integer atraso, Integer saida,
			Integer he, Integer falta, Integer h1, Integer h2, Integer h3,
			Integer h4, Integer h5, Integer h6, Integer h7, Integer h8,
			Long colaborador_id, Integer empresa_id, Integer filial_id,
			String pis, Integer bancohoras, String agrupamento,
			Long ocorrenciaFalta_id, Long ocorrenciaAtraso_id,
			Long ocorrenciaSaida_id) {
		super();
		Id = id;
		this.data = data;
		this.matricula = matricula;
		this.nome = nome;
		this.depto_id = depto_id;
		this.ht = ht;
		this.atraso = atraso;
		this.saida = saida;
		this.he = he;
		this.falta = falta;
		this.h1 = h1;
		this.h2 = h2;
		this.h3 = h3;
		this.h4 = h4;
		this.h5 = h5;
		this.h6 = h6;
		this.h7 = h7;
		this.h8 = h8;
		this.colaborador_id = colaborador_id;
		this.empresa_id = empresa_id;
		this.filial_id = filial_id;
		this.pis = pis;
		this.bancohoras = bancohoras;
		this.agrupamento = agrupamento;
		this.ocorrenciaFalta_id = ocorrenciaFalta_id;
		this.ocorrenciaAtraso_id = ocorrenciaAtraso_id;
		this.ocorrenciaSaida_id = ocorrenciaSaida_id;
	}




	@Id
	@GeneratedValue
	private Long Id;
	private Date data;
	private String matricula;
	private String nome;
	private Integer depto_id;
	private Integer ht;
	private Integer atraso;
	private Integer saida;
	private Integer he;
	private Integer falta;
	private Integer h1;
	private Integer h2;
	private Integer h3;
	private Integer h4;
	private Integer h5;
	private Integer h6;
	private Integer h7;
	private Integer h8;
	private Long colaborador_id;
	private Integer empresa_id;
	private Integer filial_id;
	private String pis;
	private Integer bancohoras;

	@Transient
	private String agrupamento;

	private Long ocorrenciaFalta_id;
	private Long ocorrenciaAtraso_id;
	private Long ocorrenciaSaida_id;

	public Integer getBancohoras() {
		return bancohoras;
	}

	public void setBancohoras(Integer bancohoras) {
		this.bancohoras = bancohoras;
	}

	public Long getOcorrenciaFalta_id() {
		return ocorrenciaFalta_id;
	}

	public void setOcorrenciaFalta_id(Long ocorrenciaFalta_id) {
		this.ocorrenciaFalta_id = ocorrenciaFalta_id;
	}

	public Long getOcorrenciaAtraso_id() {
		return ocorrenciaAtraso_id;
	}

	public void setOcorrenciaAtraso_id(Long ocorrenciaAtraso_id) {
		this.ocorrenciaAtraso_id = ocorrenciaAtraso_id;
	}

	public Long getOcorrenciaSaida_id() {
		return ocorrenciaSaida_id;
	}

	public void setOcorrenciaSaida_id(Long ocorrenciaSaida_id) {
		this.ocorrenciaSaida_id = ocorrenciaSaida_id;
	}

	public String getAgrupamento() {
		return agrupamento;
	}

	public void setAgrupamento(String agrupamento) {
		this.agrupamento = agrupamento;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getDepto_id() {
		return depto_id;
	}

	public void setDepto_id(Integer depto_id) {
		this.depto_id = depto_id;
	}

	public Integer getHt() {
		return ht;
	}

	public void setHt(Integer ht) {
		this.ht = ht;
	}

	public Integer getAtraso() {
		return atraso;
	}

	public void setAtraso(Integer atraso) {
		this.atraso = atraso;
	}

	public Integer getSaida() {
		return saida;
	}

	public void setSaida(Integer saida) {
		this.saida = saida;
	}

	public Integer getHe() {
		return he;
	}

	public void setHe(Integer he) {
		this.he = he;
	}

	public Integer getFalta() {
		return falta;
	}

	public void setFalta(Integer falta) {
		this.falta = falta;
	}

	public Integer getH1() {
		return h1;
	}

	public void setH1(Integer h1) {
		this.h1 = h1;
	}

	public Integer getH2() {
		return h2;
	}

	public void setH2(Integer h2) {
		this.h2 = h2;
	}

	public Integer getH3() {
		return h3;
	}

	public void setH3(Integer h3) {
		this.h3 = h3;
	}

	public Integer getH4() {
		return h4;
	}

	public void setH4(Integer h4) {
		this.h4 = h4;
	}

	public Integer getH5() {
		return h5;
	}

	public void setH5(Integer h5) {
		this.h5 = h5;
	}

	public Integer getH6() {
		return h6;
	}

	public void setH6(Integer h6) {
		this.h6 = h6;
	}

	public Integer getH7() {
		return h7;
	}

	public void setH7(Integer h7) {
		this.h7 = h7;
	}

	public Integer getH8() {
		return h8;
	}

	public void setH8(Integer h8) {
		this.h8 = h8;
	}

	public Long getColaborador_id() {
		return colaborador_id;
	}

	public void setColaborador_id(Long colaborador_id) {
		this.colaborador_id = colaborador_id;
	}

	public Integer getEmpresa_id() {
		return empresa_id;
	}

	public void setEmpresa_id(Integer empresa_id) {
		this.empresa_id = empresa_id;
	}

	public Integer getFilial_id() {
		return filial_id;
	}

	public void setFilial_id(Integer filial_id) {
		this.filial_id = filial_id;
	}

	public String getPis() {
		return pis;
	}

	public void setPis(String pis) {
		this.pis = pis;
	}



	@Override
	public String toString() {
		return "AcertoAbono [Id=" + Id + ", data=" + data + ", matricula="
				+ matricula + ", nome=" + nome + ", depto_id=" + depto_id
				+ ", ht=" + ht + ", atraso=" + atraso + ", saida=" + saida
				+ ", he=" + he + ", falta=" + falta + ", h1=" + h1 + ", h2="
				+ h2 + ", h3=" + h3 + ", h4=" + h4 + ", h5=" + h5 + ", h6="
				+ h6 + ", h7=" + h7 + ", h8=" + h8 + ", colaborador_id="
				+ colaborador_id + ", empresa_id=" + empresa_id
				+ ", filial_id=" + filial_id + ", pis=" + pis + ", bancohoras="
				+ bancohoras + ", agrupamento=" + agrupamento
				+ ", ocorrenciaFalta_id=" + ocorrenciaFalta_id
				+ ", ocorrenciaAtraso_id=" + ocorrenciaAtraso_id
				+ ", ocorrenciaSaida_id=" + ocorrenciaSaida_id + "]";
	}




	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Id == null) ? 0 : Id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AcertoAbono other = (AcertoAbono) obj;
		if (Id == null) {
			if (other.Id != null)
				return false;
		} else if (!Id.equals(other.Id))
			return false;
		return true;
	}

}
