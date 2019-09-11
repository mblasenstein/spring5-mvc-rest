package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by jt on 9/24/17.
 */
@Component
public class Bootstrap implements CommandLineRunner{

    private final CategoryRepository categoryRespository;
    private final CustomerRepository customerRepository;

    public Bootstrap(CategoryRepository categoryRespository, CustomerRepository customerRepository) {
        this.categoryRespository = categoryRespository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRespository.save(fruits);
        categoryRespository.save(dried);
        categoryRespository.save(fresh);
        categoryRespository.save(exotic);
        categoryRespository.save(nuts);


        Customer cust1 = new Customer();
        cust1.setFirstname("Joe");
        cust1.setLastname("Shmo");

        Customer cust2 = new Customer();
        cust2.setFirstname("Baba");
        cust2.setLastname("Booey");

        Customer cust3 = new Customer();
        cust3.setFirstname("Hannah");
        cust3.setLastname("Banana");

        customerRepository.save(cust1);
        customerRepository.save(cust2);
        customerRepository.save(cust3);


        System.out.println("Data Loaded = " + (categoryRespository.count() + customerRepository.count()));

    }
}
