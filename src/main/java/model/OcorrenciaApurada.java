package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class OcorrenciaApurada  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Integer Id;
	private Date data;		
	
	@ManyToOne (fetch=FetchType.LAZY)
	@JoinColumn(name = "ocorrencia_id", nullable = true)
	private Ocorrencia ocorrencia;	
	
	
	@ManyToOne (fetch=FetchType.LAZY)
	@JoinColumn(name = "colaborador_id", nullable = true)
	private Colaborador colaborador;
	
	
	private Integer horas;
	private Date DataAbono;
	private Integer horasAbonadas;

	@ManyToOne (fetch=FetchType.LAZY)
	@JoinColumn(name = "motivoAbono_id", nullable = true)
	private MotivoAbono motivoAbono;
	
	@Enumerated(EnumType.STRING)	
	private TipoDia tipoDia ;		
	
	@Enumerated(EnumType.STRING)	
	private TipoOcorrencia tipoOcorrencia ;	
	
	
	@ManyToOne (fetch=FetchType.LAZY)
	@JoinColumn(name = "usuario_id", nullable = true)
	private Usuario usuario;
	
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Colaborador getColaborador() {
		return colaborador;
	}
	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}
	public Ocorrencia getOcorrencia() {
		return ocorrencia;
	}
	public void setOcorrencia(Ocorrencia ocorrencia) {
		this.ocorrencia = ocorrencia;
	}
	public Integer getHoras() {
		return horas;
	}
	public void setHoras(Integer horas) {
		this.horas = horas;
	}
	public Date getDataAbono() {
		return DataAbono;
	}
	public void setDataAbono(Date dataAbono) {
		DataAbono = dataAbono;
	}
	public Integer getHorasAbonadas() {
		return horasAbonadas;
	}
	public void setHorasAbonadas(Integer horasAbonadas) {
		this.horasAbonadas = horasAbonadas;
	}
	public MotivoAbono getMotivoAbono() {
		return motivoAbono;
	}
	public void setMotivoAbono(MotivoAbono motivoAbono) {
		this.motivoAbono = motivoAbono;
	}
	

	
	
	public TipoOcorrencia getTipoOcoorencia() {
		return tipoOcorrencia;
	}
	public void setTipoOcorrencia(TipoOcorrencia tipoOcorrencia) {
		this.tipoOcorrencia = tipoOcorrencia;
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
		OcorrenciaApurada other = (OcorrenciaApurada) obj;
		if (Id == null) {
			if (other.Id != null)
				return false;
		} else if (!Id.equals(other.Id))
			return false;
		return true;
	}
	
	
	@Override
	public String toString() {
		return "OcorrenciaApurada [Id=" + Id + ", data=" + data
				+ ", ocorrencia=" + ocorrencia + ", colaborador=" + colaborador
				+ ", horas=" + horas + ", DataAbono=" + DataAbono
				+ ", horasAbonadas=" + horasAbonadas + ", motivoAbono="
				+ motivoAbono + ", tipoDia=" + tipoDia + "]";
	}

	
	
}
