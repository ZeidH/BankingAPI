package nl.Inholland.model.Accounts;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "Balance")
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "balance_id")
    private Long id;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    private Account account;
}
