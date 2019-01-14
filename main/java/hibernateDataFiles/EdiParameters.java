package hibernateDataFiles;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "edi_parameters", schema = "edi_util")
public class EdiParameters implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6309950711923330939L;
	private long parameterId;
	private String parameterSystem;
	private String parameterName;
	private String parameterValue;
	
	@Id
	@Column(name = "parameter_id", unique = true, nullable = false)
	public long getParameterId() {
		return parameterId;
	}
	
	public void setParameterId(long parameterId) {
		this.parameterId = parameterId;
	}
	
	@Column(name = "parameter_system", unique = true, nullable = false , length = 10)
	public String getParameterSystem() {
		return parameterSystem;
	}
	public void setParameterSystem(String parameterSystem) {
		this.parameterSystem = parameterSystem;
	}
	
	@Column(name = "parameter_name", unique = true, nullable = false , length = 30)
	public String getParameterName() {
		return parameterName;
	}
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	
	@Column(name = "parameter_value", unique = true, nullable = false , length = 255)
	public String getParameterValue() {
		return parameterValue;
	}
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
	

}
