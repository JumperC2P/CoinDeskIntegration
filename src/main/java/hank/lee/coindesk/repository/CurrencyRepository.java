package hank.lee.coindesk.repository;

import hank.lee.coindesk.data.modal.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, String> {

}
