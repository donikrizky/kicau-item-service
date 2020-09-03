package com.donikrizky.kicau.itemservice.entity;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.donikrizky.kicau.itemservice.config.audit.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "item")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "createdBy", "createdDate", "lastModifiedBy", "lastmodifiedDate" }, allowGetters = true)
@ApiModel(description = "All details about Item. ")
@SQLDelete(sql = "UPDATE item SET deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "deleted = false")
public class Item extends Auditable<String> {
	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_id_seq_gen")
	@SequenceGenerator(name = "item_id_seq_gen", sequenceName = "item_id_seq", allocationSize = 1)
	@ApiModelProperty(notes = "Item DB id")
	private Integer itemId;

	@NotNull
	private Integer userId;

	@NotEmpty
	@ApiModelProperty(notes = "What being posted")
	@Size(min = 1, max = 360)
	private String comment;

	@NotNull
	@Builder.Default
	private Integer parentItemId = 0;
	
	@NotNull
	private boolean deleted;

}
