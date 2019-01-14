package hibernateDataFiles;
// Generated Jul 5, 2018 8:37:29 AM by Hibernate Tools 5.2.10.Final

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Categories generated by hbm2java
 */
@Entity
@Table(name = "categories", schema = "edi_ms")
public class CategoryIgnoreJson implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private long categoryId;
	private String categoryName;
	private Date effectiveDate;
	private Date expirationDate;
	private String createdBy;
	private Date updateDate;
	private String updatedBy;
	

	private List<CategoryAnswer> categoryAnswer = new ArrayList<CategoryAnswer>(0);
	private List<QuestionIgnoreJson> questions = new ArrayList<QuestionIgnoreJson>(0);

	public CategoryIgnoreJson() {
	}

	public CategoryIgnoreJson(long categoryId, String categoryName, Date effectiveDate) {
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.effectiveDate = effectiveDate;
	}

	public CategoryIgnoreJson(long categoryId, String categoryName, Date effectiveDate, Date expirationDate,
			List<CategoryAnswer> categoryAnswer, List<QuestionIgnoreJson> questions 
			,	List<MemberAnswer> memberAnswer  
			) {
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.effectiveDate = effectiveDate;
		this.expirationDate = expirationDate;
		this.categoryAnswer = categoryAnswer;
		this.questions = questions;
	}

	@Id

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_id_seq")
	@SequenceGenerator(allocationSize = 1, name = "category_id_seq", sequenceName = "EDI_MS.category_id_seq")
	
	@Column(name = "category_id", unique = true, nullable = false)
	public long getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	@Column(name = "category_name", nullable = false, length = 20)
	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Column(name = "effective_date", nullable = false, length = 13)
	public Date getEffectiveDate() {
		return this.effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	@Column(name = "expiration_date", length = 13)
	public Date getExpirationDate() {
		return this.expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	
	@Column(name = "created_by", length = 20)
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	@Column(name = "updated_by", length = 20)
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	
	@Column(name = "update_date", length = 13)
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	@OneToMany(fetch = FetchType.LAZY , mappedBy = "category")
	@JsonIgnore
	public List<CategoryAnswer> getCategoryAnswer() {
		return this.categoryAnswer;
	}

	public void setCategoryAnswer(List<CategoryAnswer> categoryAnswer) {
		this.categoryAnswer = categoryAnswer;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "categoryIgnoreJson")
	@Fetch(FetchMode.SUBSELECT)
	@NotFound(action = NotFoundAction.IGNORE)
	public List<QuestionIgnoreJson> getQuestions() {
		return this.questions;
	}

	public void setQuestions(List<QuestionIgnoreJson> questions) {
		this.questions = questions;
	}


	


	

}