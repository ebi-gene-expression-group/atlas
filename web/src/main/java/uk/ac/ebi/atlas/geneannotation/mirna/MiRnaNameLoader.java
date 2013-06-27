package uk.ac.ebi.atlas.geneannotation.mirna;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.geneannotation.BioEntityAnnotationDao;
import uk.ac.ebi.atlas.geneannotation.BioEntityType;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@Scope("singleton")
public class MiRnaNameLoader {

    private MirbaseFastaParser parser;

    private BioEntityAnnotationDao bioEntityAnnotationDao;

    @Inject
    public MiRnaNameLoader(MirbaseFastaParser parser, BioEntityAnnotationDao bioEntityAnnotationDao) {
        this.parser = parser;
        this.bioEntityAnnotationDao = bioEntityAnnotationDao;
    }

    public int loadAnnotations() {
        List<MiRNAEntity> miRNAEntities = parser.parse();
        //ToDo: check if list is Empty, report that no Update happened
        bioEntityAnnotationDao.deleteAnnotations(BioEntityType.MIRNA.getName());
        return bioEntityAnnotationDao.saveMiRnaAnnotations(miRNAEntities);
    }
}
