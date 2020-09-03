package com.donikrizky.kicau.itemservice.entity;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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
@Table(name = "favorite")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "createdBy", "createdDate", "lastModifiedBy", "lastmodifiedDate" }, allowGetters = true)
@ApiModel(description = "All details about Favorite. ")
@SQLDelete(sql = "UPDATE favorite SET deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "deleted = false")
public class Favorite extends Auditable<String> {
	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "favorite_id_seq_gen")
	@SequenceGenerator(name = "favorite_id_seq_gen", sequenceName = "favorite_id_seq", allocationSize = 1)
	@ApiModelProperty(notes = "Favorite DB id")
	private Integer favoriteId;

	@NotNull
	private Integer userId;

	@NotNull
	private Integer itemId;

	@NotNull
	private boolean deleted;

}
