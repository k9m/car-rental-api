package org.k9m.rental.config;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.k9m.rental.persistence.model.CustomerDTO;
import org.k9m.rental.persistence.model.VehicleDTO;
import org.k9m.rental.persistence.repository.CustomerRepository;
import org.k9m.rental.persistence.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
@Profile("mock")
public class MockLoader {

    @Autowired
    private final VehicleRepository vehicleRepository;

    @Autowired
    private final CustomerRepository customerRepository;

    @PostConstruct
    void init(){
        log.info("Loading Mock Vehicle data");
        List<VehicleDTO> vehicles = loadObjectList(VehicleDTO.class, "data/testdata_vehicles.csv");
        vehicleRepository.saveAll(vehicles);

        log.info("Loading Mock Customer data");
        List<CustomerDTO> customers = loadObjectList(CustomerDTO.class, "data/testdata_customers.csv");
        customerRepository.saveAll(customers);
    }

    static <T> List<T> loadObjectList(Class<T> type, String fileName) {
        try {
            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader().withColumnSeparator(';');
            CsvMapper mapper = new CsvMapper();
            File file = new ClassPathResource(fileName).getFile();
            MappingIterator<T> readValues =
                    mapper.reader(type).with(bootstrapSchema).readValues(file);
            return readValues.readAll();
        } catch (Exception e) {
            log.error("Error occurred while loading object list from file " + fileName, e);
            return Collections.emptyList();
        }
    }

}
