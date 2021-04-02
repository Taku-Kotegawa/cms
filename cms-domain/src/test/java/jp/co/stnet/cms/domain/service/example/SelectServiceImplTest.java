package jp.co.stnet.cms.domain.service.example;

import jp.co.stnet.cms.domain.repository.example.SelectRepository;
import jp.co.stnet.cms.domain.repository.example.SelectRevisionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

class SelectServiceImplTest {

    @Mock
    SelectRepository selectRepository;

    @Mock
    SelectRevisionRepository selectRevisionRepository;

    @InjectMocks
    SelectService target;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Nested
    class save {
    }

    @Nested
    class saveDraft {
    }

    @Nested
    class cancelDraft {
    }

    @Nested
    class delete {
    }

    @Nested
    class findMaxRevById {
    }

    @Nested
    class saveMaxRev {
    }

    @Nested
    class findMaxRevPageByInput {
    }

    @Nested
    class findByIdLatestRev {
    }

    @Nested
    class findByRid {
    }

    @Nested
    class findById {
    }

    @Nested
    class beforeSave {
    }

    @Nested
    class testSave {
    }

    @Nested
    class compareEntity {
    }

    @Nested
    class testSave1 {
    }

    @Nested
    class invalid {
    }

    @Nested
    class testInvalid {
    }

    @Nested
    class valid {
    }

    @Nested
    class testDelete {
    }

    @Nested
    class testDelete1 {
    }

    @Nested
    class findPageByInput {
    }

    @Nested
    class getJPQLQuery {
    }

    @Nested
    class testGetJPQLQuery {
    }

    @Nested
    class isDate {
    }

    @Nested
    class isDateTime {
    }

    @Nested
    class isNumeric {
    }

    @Nested
    class isCollection {
    }

    @Nested
    class isBoolean {
    }

    @Nested
    class isEnum {
    }

    @Nested
    class getEnumListByName {
    }

    @Nested
    class convertColumnName {
    }

    @Nested
    class replacedColumnName {
    }

    @Nested
    class isCollectionElement {
    }

    @Nested
    class isRelation {
    }

    @Nested
    class isFilterINClause {
    }

    @Nested
    class getRelationEntity {
    }

    @Nested
    class getPageable {
    }

    @Nested
    class getRevisionRepository {
    }

    @Nested
    class getRepository {
    }

    @Nested
    class hasAuthority {
    }
}