package hank.lee.cathaybk.coindesk.repository;

import hank.lee.cathaybk.coindesk.data.modal.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, String> {

}
