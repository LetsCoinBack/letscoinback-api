package br.com.letscoinback.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table(name = "partner_X_segment", schema="public")
public class PartnerXSegment {

	@Id
	@SequenceGenerator(name="partner_x_segment_id_seq",sequenceName="partner_x_segment_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="partner_x_segment_id_seq")
	private Integer id;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Partner partner;

	@ManyToOne
	@JoinColumn(nullable = false)
	private PartnerSegment partnerSegment;

}
