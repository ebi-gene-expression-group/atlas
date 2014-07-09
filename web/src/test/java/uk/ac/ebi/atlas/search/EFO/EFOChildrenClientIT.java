package uk.ac.ebi.atlas.search.EFO;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class EFOChildrenClientIT {


    @Inject
    EFOChildrenClient subject;

    @Test
    public void cancer() throws Exception {
        ImmutableList<String> children = subject.fetchEFOChildren("cancer");

        //System.out.println("\"" + Joiner.on("\", \"").join(children) + "\"");

        assertThat(children, hasSize(1026));
        assertThat(children, contains("EFO:0000311", "EFO:0000313", "EFO:0000216", "EFO:0000228", "EFO:0000304", "EFO:0001416", "EFO:0000348", "EFO:0000365", "EFO:0000364", "EFO:0000402", "EFO:0000430", "EFO:0005232", "EFO:0000478", "EFO:0000503", "EFO:0000504", "EFO:0005288", "EFO:0002517", "EFO:0000639", "EFO:0000673", "EFO:0003825", "EFO:0002917", "EFO:0000231", "EFO:0003093", "EFO:0004193", "EFO:0000292", "EFO:0000305", "EFO:0000304", "EFO:0000306", "EFO:0000432", "EFO:0000186", "EFO:0000281", "EFO:0000552", "EFO:0000553", "EFO:0000580", "EFO:0000197", "EFO:0000653", "EFO:0001061", "EFO:0005221", "EFO:0004986", "EFO:0000466", "EFO:0002916", "EFO:0000178", "EFO:0000182", "EFO:0002938", "EFO:0000563", "EFO:0001071", "EFO:0000233", "EFO:0000308", "EFO:0003050", "EFO:0000571", "EFO:0003060", "EFO:0005288", "EFO:0005220", "EFO:0000702", "EFO:0000708", "EFO:0000756", "EFO:0002894", "EFO:0000389", "EFO:0002617", "EFO:0004906", "EFO:0000196", "EFO:0001075", "EFO:0002618", "EFO:0001663", "EFO:0002621", "EFO:0002890", "EFO:0000681", "EFO:0000335", "EFO:0000349", "EFO:0003016", "EFO:0000640", "EFO:0003017", "EFO:0000698", "EFO:0000707", "EFO:0000181", "EFO:0000559", "EFO:0000199", "EFO:0000205", "EFO:0000206", "EFO:0005088", "EFO:0002892", "EFO:0000501", "EFO:0000641", "EFO:0002919", "EFO:0002921", "EFO:0000326", "EFO:0000272", "EFO:0002499", "EFO:0000519", "EFO:0000630", "EFO:0002500", "EFO:0003094", "EFO:0002939", "EFO:0000632", "EFO:0002501", "EFO:0001642", "EFO:0000565", "EFO:0000222", "EFO:0003029", "EFO:0000218", "EFO:0003025", "EFO:0000221", "EFO:0003028", "EFO:0003027", "EFO:0000223", "EFO:0000224", "EFO:0000330", "EFO:0003026", "EFO:0000095", "EFO:0000339", "EFO:0004289", "EFO:0000574", "EFO:0000309", "EFO:0002913", "EFO:0000183", "EFO:0004708", "EFO:0000191", "EFO:0000255", "EFO:0000403", "EFO:0000211", "EFO:0002427", "EFO:0000222", "EFO:0003029", "EFO:0000218", "EFO:0003025", "EFO:0000221", "EFO:0003028", "EFO:0003027", "EFO:0000223", "EFO:0000224", "EFO:0000330", "EFO:0003026", "EFO:0002428", "EFO:0000339", "EFO:0000479", "EFO:0002429", "EFO:0002430", "EFO:0000198", "EFO:0003802", "EFO:0003811", "EFO:0003812", "EFO:0002425", "EFO:0000220", "EFO:0000094", "EFO:0000209", "EFO:0000096", "EFO:0000309", "EFO:0000191", "EFO:0000095", "EFO:0000403", "EFO:0000200", "EFO:0002616", "EFO:0000203", "EFO:0001378", "EFO:0003073", "EFO:0002426", "EFO:0003032", "EFO:0000255", "EFO:0000211", "EFO:0000588", "EFO:0000770", "EFO:0000691", "EFO:0000174", "EFO:0000558", "EFO:0002613", "EFO:0000248", "EFO:0000333", "EFO:0000350", "EFO:0000394", "EFO:0000437", "EFO:0002087", "EFO:0000564", "EFO:0000569", "EFO:0003085", "EFO:0000613", "EFO:0003083", "EFO:0003084", "EFO:0000736", "EFO:0000637", "EFO:0005287", "EFO:0002918", "EFO:0000705", "EFO:0001376", "EFO:0000595", "EFO:0000730", "EFO:0002914", "EFO:0003967", "EFO:0003968", "EFO:0002920", "EFO:0000737", "EFO:0002431", "EFO:0000760", "EFO:0000622", "EFO:0000397", "EFO:0000658", "EFO:0000693", "Orphanet_213517", "Orphanet_145", "Orphanet_213524", "Orphanet_26106", "BTO_0001615", "EFO:0005127", "EFO:0004627", "EFO:0004626", "Orphanet_227535", "EFO:0000196", "EFO:0000312", "Orphanet_1331", "BTO_0000551", "EFO:0001639", "EFO:0002957", "EFO:0002095", "EFO:0002096", "EFO:0002097", "EFO:0002098", "EFO:0002099", "EFO:0002101", "EFO:0005285", "EFO:0002102", "EFO:0002105", "EFO:0002106", "EFO:0002108", "EFO:0002109", "EFO:0002110", "EFO:0002111", "EFO:0002814", "EFO:0002112", "EFO:0002044", "EFO:0002046", "EFO:0002115", "EFO:0002116", "EFO:0002117", "EFO:0002709", "EFO:0002818", "EFO:0002125", "EFO:0002126", "EFO:0001101", "EFO:0002127", "EFO:0002128", "EFO:0001667", "EFO:0002131", "EFO:0002135", "EFO:0003082", "EFO:0002136", "EFO:0002137", "EFO:0002710", "EFO:0002147", "EFO:0001099", "EFO:0002149", "EFO:0002150", "EFO:0002152", "EFO:0002153", "EFO:0002154", "EFO:0002155", "EFO:0002156", "EFO:0002157", "EFO:0002051", "EFO:0002158", "EFO:0002159", "EFO:0002161", "EFO:0005231", "EFO:0002052", "EFO:0002053", "EFO:0002178", "EFO:0002054", "EFO:0001104", "EFO:0001105", "EFO:0001106", "EFO:0002057", "EFO:0005371", "EFO:0005372", "EFO:0005373", "EFO:0005374", "EFO:0005375", "EFO:0005370", "EFO:0002824", "EFO:0002189", "EFO:0002191", "EFO:0002192", "EFO:0002196", "EFO:0002197", "EFO:0002198", "EFO:0001193", "EFO:0002059", "EFO:0002202", "EFO:0002203", "EFO:0002207", "EFO:0002208", "EFO:0002210", "EFO:0002212", "EFO:0002213", "EFO:0002214", "EFO:0002796", "EFO:0002217", "EFO:0002219", "EFO:0002826", "EFO:0002223", "EFO:0002071", "EFO:0002226", "EFO:0002227", "EFO:0002229", "EFO:0002233", "EFO:0002234", "EFO:0002235", "EFO:0002236", "EFO:0002237", "EFO:0002830", "EFO:0002831", "EFO:0002832", "EFO:0002895", "EFO:0002834", "EFO:0001219", "EFO:0005265", "EFO:0001220", "EFO:0002239", "EFO:0002837", "EFO:0002838", "EFO:0002839", "EFO:0002245", "EFO:0002275", "EFO:0002289", "EFO:0002294", "EFO:0002297", "EFO:0002301", "EFO:0002303", "EFO:0001221", "EFO:0002841", "EFO:0005236", "EFO:0002959", "EFO:0002961", "EFO:0002308", "EFO:0002309", "EFO:0002310", "EFO:0002713", "EFO:0002714", "EFO:0002715", "EFO:0002716", "EFO:0002849", "EFO:0001226", "EFO:0001227", "EFO:0001228", "EFO:0001229", "EFO:0001230", "EFO:0002314", "EFO:0002315", "EFO:0002316", "EFO:0002317", "EFO:0001232", "EFO:0002318", "EFO:0002320", "EFO:0002853", "EFO:0002322", "EFO:0002323", "EFO:0002856", "EFO:0002325", "EFO:0002326", "EFO:0002329", "EFO:0002330", "EFO:0002858", "EFO:0002860", "EFO:0001237", "EFO:0002331", "EFO:0002334", "EFO:0002339", "EFO:0002341", "EFO:0002342", "EFO:0002343", "EFO:0002344", "EFO:0002351", "EFO:0002862", "EFO:0002863", "EFO:0002355", "EFO:0002356", "EFO:0002358", "EFO:0002359", "EFO:0002360", "EFO:0002361", "EFO:0002362", "EFO:0002364", "EFO:0002365", "EFO:0002366", "EFO:0002367", "EFO:0002083", "EFO:0002368", "EFO:0002369", "EFO:0002371", "EFO:0002372", "EFO:0002373", "EFO:0002375", "EFO:0002376", "EFO:0002377", "EFO:0002378", "EFO:0002864", "EFO:0002084", "EFO:0002085", "EFO:0002380", "EFO:0002865", "EFO:0002866", "EFO:0002867", "EFO:0005263", "EFO:0002382", "EFO:0002383", "EFO:0005237", "EFO:0001254", "EFO:0002384", "EFO:0002869", "EFO:0001255", "EFO:0001256", "EFO:0002387", "EFO:0002870", "EFO:0002872", "EFO:0002389", "EFO:0002876", "EFO:0002877", "EFO:0002878", "EFO:0002879", "EFO:0002880", "EFO:0002881", "EFO:0002882", "EFO:0002393", "EFO:0002885", "EFO:0002037", "EFO:0002038", "EFO:0002039", "EFO:0001092", "EFO:0001093", "EFO:0001095", "EFO:0001096", "EFO:0005356", "EFO:0005357", "EFO:0005358", "EFO:0005359", "EFO:0001100", "EFO:0002168", "EFO:0002175", "EFO:0001167", "EFO:0001168", "EFO:0001169", "EFO:0001170", "EFO:0002185", "EFO:0001171", "EFO:0001172", "EFO:0001173", "EFO:0002186", "EFO:0001174", "EFO:0001175", "EFO:0001176", "EFO:0001177", "EFO:0001178", "EFO:0002187", "EFO:0001179", "EFO:0001180", "EFO:0001181", "EFO:0001189", "EFO:0001190", "EFO:0001191", "EFO:0001192", "EFO:0002221", "EFO:0001199", "EFO:0001205", "EFO:0001206", "EFO:0001208", "EFO:0001209", "EFO:0001211", "EFO:0001212", "EFO:0001214", "EFO:0001215", "EFO:0001216", "EFO:0002241", "EFO:0001236", "EFO:0001239", "EFO:0001240", "EFO:0001241", "EFO:0001242", "EFO:0001243", "EFO:0001244", "EFO:0001245", "EFO:0001246", "EFO:0001247", "EFO:0001258", "EFO:0002385", "EFO:0001262", "EFO:0001263", "EFO:0001264", "EFO:0005215", "EFO:0001203", "EFO:0005217", "EFO:0002791", "EFO:0005218", "EFO:0002120", "EFO:0002122", "EFO:0002123", "EFO:0002171", "EFO:0001185", "EFO:0002370", "EFO:0002379", "EFO:0002782", "EFO:0005216", "EFO:0002121", "EFO:0002205", "EFO:0001187", "EFO:0001186", "EFO:0002345", "EFO:0002346", "EFO:0002347", "EFO:0002348", "EFO:0002349", "EFO:0002350", "EFO:0002934", "EFO:0002104", "EFO:0001086", "EFO:0002813", "EFO:0002138", "EFO:0002141", "EFO:0002143", "EFO:0002144", "EFO:0002151", "EFO:0002819", "EFO:0002162", "EFO:0002163", "EFO:0002164", "EFO:0002165", "EFO:0002166", "EFO:0003039", "EFO:0003115", "EFO:0001165", "EFO:0002823", "EFO:0001166", "EFO:0003127", "EFO:0003128", "EFO:0003129", "EFO:0003130", "EFO:0003131", "EFO:0003132", "EFO:0003133", "EFO:0003134", "EFO:0003135", "EFO:0003136", "EFO:0003137", "EFO:0003138", "EFO:0002827", "EFO:0003140", "EFO:0003142", "EFO:0002828", "EFO:0002836", "EFO:0002840", "EFO:0002249", "EFO:0002250", "EFO:0003116", "EFO:0003043", "EFO:0003117", "EFO:0002252", "EFO:0002253", "EFO:0002254", "EFO:0002255", "EFO:0003118", "EFO:0002256", "EFO:0002257", "EFO:0002258", "EFO:0002259", "EFO:0003119", "EFO:0002260", "EFO:0002261", "EFO:0002262", "EFO:0002263", "EFO:0002264", "EFO:0002265", "EFO:0002266", "EFO:0002267", "EFO:0002268", "EFO:0002269", "EFO:0002270", "EFO:0002271", "EFO:0002272", "EFO:0002273", "EFO:0002274", "EFO:0002276", "EFO:0002277", "EFO:0002278", "EFO:0002279", "EFO:0002280", "EFO:0002281", "EFO:0002282", "EFO:0002283", "EFO:0002284", "EFO:0002285", "EFO:0003120", "EFO:0003122", "EFO:0002286", "EFO:0002287", "EFO:0002288", "EFO:0003121", "EFO:0002290", "EFO:0003123", "EFO:0002291", "EFO:0002292", "EFO:0002293", "EFO:0003044", "EFO:0003124", "EFO:0002295", "EFO:0002296", "EFO:0002298", "EFO:0002300", "EFO:0002304", "EFO:0002305", "EFO:0002306", "EFO:0003125", "EFO:0002307", "EFO:0002843", "EFO:0002844", "EFO:0002845", "EFO:0002846", "EFO:0002847", "EFO:0002848", "EFO:0002850", "EFO:0002851", "EFO:0002852", "EFO:0002854", "EFO:0002855", "EFO:0002328", "EFO:0002861", "EFO:0002363", "EFO:0002374", "EFO:0002386", "EFO:0002937", "EFO:0002040", "EFO:0002041", "EFO:0002042", "EFO:0002043", "EFO:0002045", "EFO:0002048", "EFO:0002049", "EFO:0002113", "BTO_0000164", "EFO:0002094", "EFO:0002815", "EFO:0001091", "EFO:0002124", "EFO:0002160", "EFO:0002169", "EFO:0002172", "EFO:0002173", "EFO:0002174", "EFO:0002181", "EFO:0002199", "EFO:0002215", "EFO:0001198", "EFO:0002228", "EFO:0002246", "EFO:0002247", "EFO:0002312", "EFO:0002324", "EFO:0002077", "EFO:0002353", "EFO:0002130", "EFO:0005233", "EFO:0003037", "EFO:0002145", "EFO:0002146", "EFO:0002167", "EFO:0002170", "EFO:0002183", "EFO:0005369", "EFO:0002190", "EFO:0002193", "EFO:0002194", "EFO:0002793", "EFO:0002200", "EFO:0002209", "EFO:0002060", "EFO:0002218", "EFO:0001197", "EFO:0005284", "EFO:0002225", "EFO:0002231", "EFO:0002238", "EFO:0002242", "EFO:0002798", "EFO:0002248", "EFO:0002075", "EFO:0002313", "EFO:0002319", "EFO:0002321", "EFO:0001234", "EFO:0002352", "EFO:0002354", "EFO:0002357", "EFO:0001253", "EFO:0001257", "EFO:0005294", "EFO:0002118", "EFO:0002134", "EFO:0002176", "EFO:0002067", "EFO:0002222", "EFO:0002232", "EFO:0002244", "BTO_0000849", "EFO:0002810", "EFO:0002100", "EFO:0002103", "EFO:0002107", "EFO:0002119", "EFO:0002132", "EFO:0002140", "EFO:0002195", "EFO:0003056", "EFO:0001213", "EFO:0002243", "EFO:0002327", "EFO:0002079", "EFO:0003081", "EFO:0002332", "EFO:0002333", "EFO:0002080", "EFO:0005376", "EFO:0005377", "EFO:0002390", "EFO:0002873", "EFO:0002874", "EFO:0002875", "EFO:0002394", "EFO:0002139", "EFO:0002148", "EFO:0002177", "EFO:0002311", "EFO:0003061", "EFO:0002340", "BTO_0000383", "BTO_0001130", "Orphanet_144", "Orphanet_587", "Orphanet_99817", "BTO_0001616", "BTO_0001967", "EFO:0000326", "EFO:0000272", "EFO:0002499", "EFO:0000519", "EFO:0000630", "EFO:0002500", "EFO:0003094", "EFO:0002939", "EFO:0000632", "EFO:0002501", "BTO_0000690", "BTO_0000797", "EFO:0002394", "EFO:0002139", "EFO:0002148", "EFO:0002177", "EFO:0002311", "EFO:0003061", "EFO:0002340", "BTO_0000583", "BTO_0001033", "EFO:0002934", "EFO:0002104", "EFO:0001086", "EFO:0002813", "EFO:0002138", "EFO:0002141", "EFO:0002143", "EFO:0002144", "EFO:0002151", "EFO:0002819", "EFO:0002162", "EFO:0002163", "EFO:0002164", "EFO:0002165", "EFO:0002166", "EFO:0003039", "EFO:0003115", "EFO:0001165", "EFO:0002823", "EFO:0001166", "EFO:0003127", "EFO:0003128", "EFO:0003129", "EFO:0003130", "EFO:0003131", "EFO:0003132", "EFO:0003133", "EFO:0003134", "EFO:0003135", "EFO:0003136", "EFO:0003137", "EFO:0003138", "EFO:0002827", "EFO:0003140", "EFO:0003142", "EFO:0002828", "EFO:0002836", "EFO:0002840", "EFO:0002249", "EFO:0002250", "EFO:0003116", "EFO:0003043", "EFO:0003117", "EFO:0002252", "EFO:0002253", "EFO:0002254", "EFO:0002255", "EFO:0003118", "EFO:0002256", "EFO:0002257", "EFO:0002258", "EFO:0002259", "EFO:0003119", "EFO:0002260", "EFO:0002261", "EFO:0002262", "EFO:0002263", "EFO:0002264", "EFO:0002265", "EFO:0002266", "EFO:0002267", "EFO:0002268", "EFO:0002269", "EFO:0002270", "EFO:0002271", "EFO:0002272", "EFO:0002273", "EFO:0002274", "EFO:0002276", "EFO:0002277", "EFO:0002278", "EFO:0002279", "EFO:0002280", "EFO:0002281", "EFO:0002282", "EFO:0002283", "EFO:0002284", "EFO:0002285", "EFO:0003120", "EFO:0003122", "EFO:0002286", "EFO:0002287", "EFO:0002288", "EFO:0003121", "EFO:0002290", "EFO:0003123", "EFO:0002291", "EFO:0002292", "EFO:0002293", "EFO:0003044", "EFO:0003124", "EFO:0002295", "EFO:0002296", "EFO:0002298", "EFO:0002300", "EFO:0002304", "EFO:0002305", "EFO:0002306", "EFO:0003125", "EFO:0002307", "EFO:0002843", "EFO:0002844", "EFO:0002845", "EFO:0002846", "EFO:0002847", "EFO:0002848", "EFO:0002850", "EFO:0002851", "EFO:0002852", "EFO:0002854", "EFO:0002855", "EFO:0002328", "EFO:0002861", "EFO:0002363", "EFO:0002374", "EFO:0002386", "EFO:0002885", "EFO:0002037", "EFO:0002038", "EFO:0002039", "EFO:0001092", "EFO:0001093", "EFO:0001095", "EFO:0001096", "EFO:0005356", "EFO:0005357", "EFO:0005358", "EFO:0005359", "EFO:0001100", "EFO:0002168", "EFO:0002175", "EFO:0001167", "EFO:0001168", "EFO:0001169", "EFO:0001170", "EFO:0002185", "EFO:0001171", "EFO:0001172", "EFO:0001173", "EFO:0002186", "EFO:0001174", "EFO:0001175", "EFO:0001176", "EFO:0001177", "EFO:0001178", "EFO:0002187", "EFO:0001179", "EFO:0001180", "EFO:0001181", "EFO:0001189", "EFO:0001190", "EFO:0001191", "EFO:0001192", "EFO:0002221", "EFO:0001199", "EFO:0001205", "EFO:0001206", "EFO:0001208", "EFO:0001209", "EFO:0001211", "EFO:0001212", "EFO:0001214", "EFO:0001215", "EFO:0001216", "EFO:0002241", "EFO:0001236", "EFO:0001239", "EFO:0001240", "EFO:0001241", "EFO:0001242", "EFO:0001243", "EFO:0001244", "EFO:0001245", "EFO:0001246", "EFO:0001247", "EFO:0001258", "EFO:0002385", "EFO:0001262", "EFO:0001263", "EFO:0001264", "EFO:0005215", "EFO:0001203", "Orphanet_213524", "Orphanet_145", "Orphanet_300576", "Orphanet_183422", "Orphanet_50", "Orphanet_52", "Orphanet_261600", "Orphanet_261619", "Orphanet_261629", "Orphanet_100", "Orphanet_251347", "Orphanet_166113", "Orphanet_116", "Orphanet_231127", "Orphanet_96076", "Orphanet_231130", "Orphanet_231120", "Orphanet_238613", "Orphanet_231117", "Orphanet_96193", "Orphanet_124", "Orphanet_125", "Orphanet_169079", "Orphanet_191", "Orphanet_1466", "Orphanet_90321", "Orphanet_90322", "Orphanet_90324", "Orphanet_3071", "Orphanet_220", "Orphanet_84", "Orphanet_347", "Orphanet_2128", "Orphanet_99812", "Orphanet_562", "Orphanet_60040", "Orphanet_1052", "Orphanet_647", "Orphanet_240760", "Orphanet_648", "Orphanet_363972", "Orphanet_300576", "Orphanet_306498", "Orphanet_109", "Orphanet_201", "Orphanet_744", "Orphanet_2969", "Orphanet_137608", "Orphanet_2849", "Orphanet_2869", "Orphanet_221016", "Orphanet_783", "Orphanet_353281", "Orphanet_353277", "Orphanet_353284", "Orphanet_794", "Orphanet_798", "Orphanet_50944", "Orphanet_811", "Orphanet_813", "Orphanet_231144", "Orphanet_231137", "Orphanet_231140", "Orphanet_231147", "Orphanet_96182", "Orphanet_373", "Orphanet_821", "Orphanet_805", "Orphanet_882", "Orphanet_893", "Orphanet_280558", "Orphanet_902", "Orphanet_906", "Orphanet_910", "Orphanet_276249", "Orphanet_276252", "Orphanet_276255", "Orphanet_276258", "Orphanet_276261", "Orphanet_276264", "Orphanet_276267", "Orphanet_90342", "Orphanet_220295", "Orphanet_276252", "Orphanet_276258", "Orphanet_276264", "Orphanet_276267", "Orphanet_313846"));
    }

    @Test
    public void mus_musculus() throws Exception {
        ImmutableList<String> children = subject.fetchEFOChildren("Mus musculus");

        //System.out.println("\"" + Joiner.on("\", \"").join(children) + "\"");

        assertThat(children, hasSize(243));
    }

    @Test
    public void mus() throws Exception {
        ImmutableList<String> children = subject.fetchEFOChildren("Mus");

        //System.out.println("\"" + Joiner.on("\", \"").join(children) + "\"");

        assertThat(children, hasSize(243));
    }

    @Test
    public void musculus() throws Exception {
        ImmutableList<String> children = subject.fetchEFOChildren("musculus");

        //System.out.println("\"" + Joiner.on("\", \"").join(children) + "\"");

        assertThat(children, hasSize(136));
    }

    @Test
    public void mus_musculus_quoted() throws Exception {
        ImmutableList<String> children = subject.fetchEFOChildren("\"Mus musculus\"");

        //System.out.println("\"" + Joiner.on("\", \"").join(children) + "\"");

        assertThat(children, hasSize(136));
    }

    @Test
    public void wild_type() throws Exception {
        ImmutableList<String> children = subject.fetchEFOChildren("wild type");

        //System.out.println("\"" + Joiner.on("\", \"").join(children) + "\"");

        assertThat(children, hasSize(1790));
    }

    @Test
    public void Brachyolmia() throws Exception {
        ImmutableList<String> children = subject.fetchEFOChildren("Brachyolmia");

        //System.out.println("\"" + Joiner.on("\", \"").join(children) + "\"");

        assertThat(children, hasSize(9));
    }

    @Test
    public void Brachyolmia_wild() throws Exception {
        ImmutableList<String> children = subject.fetchEFOChildren("Brachyolmia wild");

        //System.out.println("\"" + Joiner.on("\", \"").join(children) + "\"");

        assertThat(children, hasSize(10));
    }

    @Test
    public void Brachyolmia_wild_quoted() throws Exception {
        ImmutableList<String> children = subject.fetchEFOChildren("\"Brachyolmia wild\"");

        //System.out.println("\"" + Joiner.on("\", \"").join(children) + "\"");

        assertThat(children, hasSize(0));
    }

    @Test
    public void wild() throws Exception {
        ImmutableList<String> children = subject.fetchEFOChildren("wild");

        //System.out.println("\"" + Joiner.on("\", \"").join(children) + "\"");

        assertThat(children, hasSize(1));
    }


    @Test
         public void wild_type_quoted() throws Exception {
        ImmutableList<String> children = subject.fetchEFOChildren("\"wild type\"");

        //System.out.println("\"" + Joiner.on("\", \"").join(children) + "\"");

        assertThat(children, hasSize(1));
    }


    @Test
    public void OR() throws Exception {
        ImmutableList<String> children = subject.fetchEFOChildren("OR");

        //System.out.println("\"" + Joiner.on("\", \"").join(children) + "\"");

        assertThat(children, hasSize(0));
    }


    @Test
    public void OR_wild_foobar() throws Exception {
        ImmutableList<String> children = subject.fetchEFOChildren("OR wild foobar_this_is_not_in_index");

        //System.out.println("\"" + Joiner.on("\", \"").join(children) + "\"");

        assertThat(children, hasSize(0));
    }

    @Test
    public void wild_OR_foobar() throws Exception {
        ImmutableList<String> children = subject.fetchEFOChildren("wild OR foobar_this_is_not_in_index");

        //System.out.println("\"" + Joiner.on("\", \"").join(children) + "\"");

        assertThat(children, hasSize(1));
    }
}