package mz.org.csaude.mentoring.model.career;

import static mz.org.csaude.mentoring.model.tutor.Tutor.COLUMN_TABLE_NAME;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import lombok.Data;
import lombok.NoArgsConstructor;
import mz.org.csaude.mentoring.base.model.BaseModel;
import mz.org.csaude.mentoring.dao.career.CareerDAOImpl;
import mz.org.csaude.mentoring.dao.career.CareerTypeDAOImpl;
import mz.org.csaude.mentoring.dao.tutor.TutorDAOImpl;

@Data
@NoArgsConstructor
@DatabaseTable(tableName = CareerType.COLUMN_TABLE_NAME, daoClass = CareerTypeDAOImpl.class)
public class CareerType extends BaseModel {

    public static final String COLUMN_TABLE_NAME = "career_type";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_CODE = "code";

    @DatabaseField(columnName = COLUMN_DESCRIPTION)
    private String descripion;

    @DatabaseField(columnName = COLUMN_CODE)
    private String code;

}
