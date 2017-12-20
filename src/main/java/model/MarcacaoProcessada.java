package model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class MarcacaoProcessada {
	
	@Id
	@GeneratedValue
	@Column(nullable=false) 
	private Long Id;
	private Date data;
	
	private Long colaborador_id;
	private Date dataProcessamento;
	
	@Enumerated(EnumType.STRING)	
	private TipoDia tipoDia ;	
	
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

	
	public Long getColaborador_id() {
		return colaborador_id;
	}
	public void setColaborador_id(Long colaborador_id) {
		this.colaborador_id = colaborador_id;
	}
	public Date getDataProcessamento() {
		return dataProcessamento;
	}
	public void setDataProcessamento(Date dataProcessamento) {
		this.dataProcessamento = dataProcessamento;

	
	
	}
	public TipoDia getTipoDia() {
		return tipoDia;
	}
	public void setTipoDia(TipoDia tipoDia) {
		this.tipoDia = tipoDia;
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
		MarcacaoProcessada other = (MarcacaoProcessada) obj;
		if (Id == null) {
			if (other.Id != null)
				return false;
		} else if (!Id.equals(other.Id))
			return false;
		return true;
	}
	
	
}
