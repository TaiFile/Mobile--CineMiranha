package br.ufscar.pooa.cinema_api.domain.entities;

import br.ufscar.pooa.cinema_api.domain.enums.PaymentMethod;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "tickets")
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private Instant paymentDate;

	@Column
	private Integer priceInCents;

	@Column
	@Enumerated(EnumType.STRING)
	private PaymentMethod paymentMethod;

	@ManyToOne
	@JoinColumn(name = "session_id", nullable = false)
	private Session session;

	@ManyToOne
	@JoinColumn(name = "client_id", nullable = false)
	private Client client;

	@ManyToOne
	@JoinColumn(name = "seat_id", nullable = false)
	private Seat seat;

	public Ticket() {
	}

	public Long getId() {
		return id;
	}

	public Ticket setId(Long id) {
		this.id = id;
		return this;
	}

	public Instant getPaymentDate() {
		return paymentDate;
	}

	public Ticket setPaymentDate(Instant paymentDate) {
		this.paymentDate = paymentDate;
		return this;
	}

	public Integer getPriceInCents() {
		return priceInCents;
	}

	public Ticket setPriceInCents(Integer priceInCents) {
		this.priceInCents = priceInCents;
		return this;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public Ticket setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
		return this;
	}

	public Session getSession() {
		return session;
	}

	public Ticket setSession(Session session) {
		this.session = session;
		return this;
	}

	public Client getClient() {
		return client;
	}

	public Ticket setClient(Client client) {
		this.client = client;
		return this;
	}

	public Seat getSeat() {
		return seat;
	}

	public Ticket setSeat(Seat seat) {
		this.seat = seat;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Ticket ticket = (Ticket) o;
		return Objects.equals(paymentDate, ticket.paymentDate) && Objects.equals(priceInCents, ticket.priceInCents) && Objects.equals(session, ticket.session) && Objects.equals(client, ticket.client) && Objects.equals(seat, ticket.seat) && Objects.equals(paymentMethod, ticket.paymentMethod);
	}

	@Override
	public int hashCode() {
		return Objects.hash(paymentDate, priceInCents, session, client, seat, paymentMethod);
	}

	@Override
	public String toString() {
		return "Ticket{" +
				"id=" + id +
				", paymentDate=" + paymentDate +
				", priceInCents=" + priceInCents +
				", paymentMethod=" + paymentMethod +
				'}';
	}
}
