package hank.lee.cathaybk.demo.repository;

import hank.lee.cathaybk.demo.data.modal.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, String> {

}
