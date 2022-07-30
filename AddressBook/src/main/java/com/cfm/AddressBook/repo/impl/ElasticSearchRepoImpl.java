package com.cfm.AddressBook.repo.impl;

import com.cfm.AddressBook.model.Addresses;
import com.cfm.AddressBook.repo.AddressRepository;
import com.cfm.AddressBook.repo.ElasticSearchRepository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class ElasticSearchRepoImpl implements ElasticSearchRepository {

    private final String ADDRESS_INDEX = "address_v1";

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    ElasticsearchOperations elasticsearchOperations;

    @Override
    public Addresses createAddress(Addresses address) {
        return elasticsearchOperations.save(address, IndexCoordinates.of(ADDRESS_INDEX));
    }

    @Override
    public Page<Addresses> findAddressPaginatedAndSorted(String page, String size, String sortBy, String sortOrder, String fields) {
        PageRequest pageRequest = PageRequest.of(Integer.parseInt(page), Integer.parseInt(size));
        SortBuilder sortRequest = SortBuilders.fieldSort(sortBy);
        if(sortOrder.equalsIgnoreCase("asc")) {
            sortRequest.order(SortOrder.ASC);
        }else {
            sortRequest.order(SortOrder.DESC);
        }

        NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageRequest)
                .withSorts(sortRequest);

        if(fields != null && fields.length() > 0){
            searchQuery.withFields(fields.split(","));
        }

        SearchHits<Addresses> searchHits = elasticsearchOperations.search(searchQuery.build(), Addresses.class, IndexCoordinates.of(ADDRESS_INDEX));
        List<Addresses> addressesList = searchHits.stream().map(hit-> hit.getContent()).collect(Collectors.toList());
        log.info("Retrieved {} locations from Elasticsearch", addressesList.size());
        System.out.println("Location retrieved"+ addressesList);
        return new PageImpl<>(addressesList, pageRequest, searchHits.getTotalHits());
    }
}
