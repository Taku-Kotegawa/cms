package jp.co.stnet.cms.domain.common.datatables;


public class JpaHelper {

//    private DataTablesInput dataTablesInput;
//
//    private Object entity;
//
//    public JpaHelper(DataTablesInput dataTablesInput, Object entity){
//        this.dataTablesInput = dataTablesInput;
//        this.entity = entity;
//    }

    private final String TEMPLATE_SQL = "SELECT t FROM [table] t";
    private final String TEMPLATE_WHERE = " WHERE ";
    private final String TEMPLATE_WHERE_EQ = " [field] = :[value] ";
    private final String TEMPLATE_WHERE_LIKE = " [field] like :[value] ESCAPE '~'";
    private final String TEMPLATE_JOIN = " LEFT JOIN ";


}
