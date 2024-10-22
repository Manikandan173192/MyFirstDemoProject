package com.maxbyte.sam.SecondaryDBFlow.WRWOType.Service;

import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.Department;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.service.CrudService;
import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import com.maxbyte.sam.SecondaryDBFlow.SA.Entity.SA;
import com.maxbyte.sam.SecondaryDBFlow.SA.Specification.SASpecificationBuilder;
import com.maxbyte.sam.SecondaryDBFlow.WRWOType.Entity.WrWoType;
import com.maxbyte.sam.SecondaryDBFlow.WRWOType.Repository.WrWoTypeRepository;
import com.maxbyte.sam.SecondaryDBFlow.WRWOType.Specification.WrWoTypeSpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WrWoTypeService extends CrudService<WrWoType,Integer> {
    @Autowired
    private WrWoTypeRepository wrWoTypeRepository;

    @Value("${pagination.default-page}")
    private int defaultPage;
    @Value("${pagination.default-size}")
    private int defaultSize;

    @Override
    public CrudRepository repository() {
        return null;
    }

    @Override
    public void validateAdd(WrWoType data) {

    }

    @Override
    public void validateEdit(WrWoType data) {

    }

    @Override
    public void validateDelete(Integer id) {

    }

    public ResponseModel<List<WrWoType>> list(String NAME, String LOOKUP_TYPE, Integer LOOKUP_CODE,
                                        String MEANING, String DESCRIPTION) {

        try {
            WrWoTypeSpecificationBuilder builder = new WrWoTypeSpecificationBuilder();
            if (NAME != null) builder.with("NAME", ":", NAME);
            if (LOOKUP_TYPE != null) builder.with("LOOKUP_TYPE", ":", LOOKUP_TYPE);
            if (LOOKUP_CODE != null) builder.with("LOOKUP_CODE", ":", LOOKUP_CODE);
            if (MEANING != null) builder.with("MEANING", ":", MEANING);
            if (DESCRIPTION != null) builder.with("DESCRIPTION", ":", DESCRIPTION);

            List<WrWoType> results = wrWoTypeRepository.findAll(builder.build());

            return new ResponseModel<>(true, "Records Found and ", results);

        } catch (Exception e) {
            return new ResponseModel<>(false, "Records not found", null);
        }
    }






}
