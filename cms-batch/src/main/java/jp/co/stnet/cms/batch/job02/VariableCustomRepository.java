package jp.co.stnet.cms.batch.job02;

import org.apache.ibatis.cursor.Cursor;

public interface VariableCustomRepository {

    Cursor<Variable> cursor();

}