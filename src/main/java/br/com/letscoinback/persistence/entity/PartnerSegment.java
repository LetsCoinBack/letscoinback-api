package br.com.letscoinback.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table(name = "partner_segment", schema="public")
public class PartnerSegment {
	
	@Id
	@SequenceGenerator(name="partner_segment_id_seq",sequenceName="partner_segment_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="partner_segment_id_seq")
	private Integer id;
	private String description;
}
