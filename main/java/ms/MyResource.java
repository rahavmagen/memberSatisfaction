package ms;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import edi.nhia.com.EDIUtils;
import hibernateDataFiles.Category;
import hibernateDataFiles.CategoryAnswer;
import hibernateDataFiles.CategoryAnswerId;
import hibernateDataFiles.CategoryIgnoreJson;
import hibernateDataFiles.EdiParameters;
import hibernateDataFiles.MemberAnswer;
import hibernateDataFiles.PossibleAnswer;
import hibernateDataFiles.Question;
import hibernateDataFiles.Survey;
import repository.CategoryAnswerRepository;
import repository.CategoryIgnoreJsonRepository;
import repository.CategoryRepository;
import repository.EdiParametersRepository;
import repository.MemberAnswerRepository;
import repository.PossibleAnswerRepository;
import repository.QuestionRepository;
import repository.SurveyRepository;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("ms")
@Component
public class MyResource {
	private final SurveyRepository surveyRepository;
	private final MemberAnswerRepository memberAnswerRepository;
	private final CategoryAnswerRepository categoryAnswerRepository;
	private final CategoryRepository categoryRepository;
	private final QuestionRepository questionRepository;
	private final PossibleAnswerRepository possibleAnswerRepository;
	private final EdiParametersRepository ediParametersRepository;
	private final CategoryIgnoreJsonRepository categoryIgnoreJsonRepository;

	public MyResource(SurveyRepository surveyRepository, MemberAnswerRepository memberAnswerRepository,
			CategoryAnswerRepository categoryAnswerRepository, 
			CategoryRepository categoryRepository,
			QuestionRepository questionRepository, PossibleAnswerRepository possibleAnswerRepository,
			EdiParametersRepository ediParametersRepository ,CategoryIgnoreJsonRepository categoryIgnoreJsonRepository) {
		this.surveyRepository = surveyRepository;
		this.memberAnswerRepository = memberAnswerRepository;
		this.categoryAnswerRepository = categoryAnswerRepository;
		this.categoryRepository = categoryRepository;
		this.questionRepository = questionRepository;
		this.possibleAnswerRepository = possibleAnswerRepository;
		this.ediParametersRepository = ediParametersRepository;
		this.categoryIgnoreJsonRepository = categoryIgnoreJsonRepository;
	}

	/**
	 * Method handling HTTP GET requests. The returned object will be sent to the
	 * client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 */
	private EDIUtils ediUtils;
	private static final Logger log = LoggerFactory.getLogger(MyResource.class);
	public final static String SYSTEM_NAME = "MSS";
	public final static String USER_NAME = "Rahav";

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("test")
	public String testWs() {
		return "member satisfaction should be called using post";
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("saveQuickFeedback")
	public Survey saveQuickFeedback(Survey survey) {

		ediUtils = new EDIUtils(SYSTEM_NAME, USER_NAME);
		Init(ediUtils);
		ediUtils.writeToLog("umn=" + survey.getUmn());
		Date runDate = new Date();
		survey.setCreationDate(runDate);
		surveyRepository.save(survey);
		List<CategoryAnswer> categoryAnswersArray = survey.getCategoriesAnswers();

//		List<MemberAnswer> membersAnswersArray = survey.getMemberAnswers();

//		for (MemberAnswer membersAnswers : membersAnswersArray) {
//
//			membersAnswers.setSurvey(survey);
//
//		}
//
//		memberAnswerRepository.saveAll(membersAnswersArray);

		for (CategoryAnswer categoryAnswer : categoryAnswersArray) {
			if (categoryAnswer != null) {
				CategoryAnswerId id = new CategoryAnswerId();
				id.setSurveyId(survey.getSurveyId());
				id.setCategoryId(categoryAnswer.getId().getCategoryId());
				categoryAnswer.setId(id);
				categoryAnswer.setSurvey(survey);
				categoryAnswerRepository.save(categoryAnswer);
			}
		}

		ediUtils.writeToLog("survey id is " + survey.getSurveyId());

		return survey;
	}

//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	@Path("getCategoriesQandA")
//	public List<Category> getCategoriesQandA() {
//		ediUtils = new EDIUtils(SYSTEM_NAME, USER_NAME);
//		Init(ediUtils);
//		ediUtils.writeToLog("get Categories , questions and answers ");
//		List<Category> categoriesArray;
//
//		categoriesArray = categoryRepository.getEffective();
//
//		return categoriesArray;
//
//	}
//	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getCategoriesQandA")
	public List<CategoryIgnoreJson> getCategoriesQandA() {
		ediUtils = new EDIUtils(SYSTEM_NAME, USER_NAME);
		Init(ediUtils);
		ediUtils.writeToLog("get Categories , questions and answers ");
		List<CategoryIgnoreJson> categoriesArray;

		categoriesArray = categoryIgnoreJsonRepository.getEffective();

		return categoriesArray;

	}
	
	
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	@Path("getCategoriesQandA")
//	public List<Category> getCategoriesQandA() {
//		ediUtils = new EDIUtils(SYSTEM_NAME, USER_NAME);
//		Init(ediUtils);
//		ediUtils.writeToLog("get Categories , questions and answers ");
//		List<Category> categoriesArray = categoryRepository.getEffective();
//		for(Category category : categoriesArray)
//		{
//			ediUtils.writeToLog("category id = "+category.getCategoryId());
//			List<Question> questionsArr = questionRepository.findByCategoryId(category.getCategoryId());
//			category.setQuestions(questionsArr);
//			for(Question questions : questionsArr)
//			{
//				List<PossibleAnswer> possibleAnswersArray = possibleAnswerRepository.getAllEffective(questions.getQuestionId());
//				questions.setPossibleAnswers(possibleAnswersArray);
//			}
//		}
//		return categoriesArray;
//
//	}


	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getCategories")
	public List<CategoryIgnoreJson> getCategories(Boolean getExpired) {
		ediUtils = new EDIUtils(SYSTEM_NAME, USER_NAME);
		Init(ediUtils);
		ediUtils.writeToLog("in Categories getExpired=" + getExpired);

		List<CategoryIgnoreJson> categoriesArray;
		if (getExpired) {
			categoriesArray = categoryIgnoreJsonRepository.findAll();

		} else {
			categoriesArray = categoryIgnoreJsonRepository.getEffective();
		}

		ediUtils.writeToLog("number of records=" + categoriesArray.size());

		return categoriesArray;

	}

//	@POST
//	@Produces(MediaType.APPLICATION_JSON)
//	@Path("saveCategory")
//	public Category saveCategoys(Category category) {
//
//		ediUtils = new EDIUtils(SYSTEM_NAME, USER_NAME);
//		Init(ediUtils);
//		ediUtils.writeToLog("new category=" + category.getCategoryName());
//		Date runDate = new Date();
//		category.setEffectiveDate(runDate);
//		categoryRepository.save(category);
//
//		return category;
//	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getQuestions")
	public List<Question> getQuestion() {

		ediUtils = new EDIUtils(SYSTEM_NAME, USER_NAME);
		Init(ediUtils);
		// ediUtils.writeToLog("new category=" + category.getCategoryName());
		List<Question> questionArr = (List<Question>) questionRepository.findAll();
		// for(Question question : questionArr)
		// {
		// question.getCategories().setCategoryId(categoryId);
		// }

		return questionArr;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("saveQuestion")
	public Question saveQuestion(Question question) {

		ediUtils = new EDIUtils(SYSTEM_NAME, USER_NAME);
		Init(ediUtils);
		ediUtils.writeToLog("new question=" + question.getQuestionText());
		Date runDate = new Date();
		question.setEffectiveDate(runDate);
		questionRepository.save(question);

		return question;
	}
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("saveQuestionUpdate")
	public Question saveQuestionUpdate(Question question) {

		ediUtils = new EDIUtils(SYSTEM_NAME, USER_NAME);
		Init(ediUtils);
		ediUtils.writeToLog("update question=" + question.getQuestionText());
		Date runDate = new Date();
		question.setUpdateDate(runDate);
		questionRepository.save(question);

		return question;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("savePossibleAnswer")
	public PossibleAnswer savePossibleAnswer(PossibleAnswer possibleAnswer) {

		ediUtils = new EDIUtils(SYSTEM_NAME, USER_NAME);
		Init(ediUtils);
		ediUtils.writeToLog("new answer=" + possibleAnswer.getAnswerValue());
		Date runDate = new Date();
		possibleAnswer.setEffectiveDate(runDate);
		possibleAnswerRepository.save(possibleAnswer);

		return possibleAnswer;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("savePossibleAnswerUpdate")
	public PossibleAnswer savePossibleAnswerUpdate(PossibleAnswer possibleAnswer) {

		ediUtils = new EDIUtils(SYSTEM_NAME, USER_NAME);
		Init(ediUtils);
		ediUtils.writeToLog("update answer=" + possibleAnswer.getAnswerValue());
		Date runDate = new Date();
		possibleAnswer.setUpdateDate(runDate);
		possibleAnswerRepository.save(possibleAnswer);

		return possibleAnswer;
	}


	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("expireCategory")
	public void expireCategory(Category category) {

		ediUtils = new EDIUtils(SYSTEM_NAME, USER_NAME);
		Init(ediUtils);
		ediUtils.writeToLog("expire category=" + category.getCategoryName());
		categoryRepository.expireCategory(category.getCategoryId());

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("expireQuestion")
	public void expireQuestion(Question question) {

		ediUtils = new EDIUtils(SYSTEM_NAME, USER_NAME);
		Init(ediUtils);
		ediUtils.writeToLog("expire question=" + question.getQuestionText());
		questionRepository.expireQuestion(question.getQuestionId());

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("expireAnswer")
	public void expireAnswer(PossibleAnswer possibleAnswer) {

		ediUtils = new EDIUtils(SYSTEM_NAME, USER_NAME);
		Init(ediUtils);
		ediUtils.writeToLog("expire answer =" + possibleAnswer.getAnswerValue());
		possibleAnswerRepository.expireAnswer(possibleAnswer.getPossibleAnswerId());

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getSurveys")
	public List<CategoryAnswer> getSurveys() {
		ediUtils = new EDIUtils(SYSTEM_NAME, USER_NAME);
		Init(ediUtils);
		ediUtils.writeToLog("get surveys ");

		List<CategoryAnswer> survey = categoryAnswerRepository.findBySurveyId(100L);
		return survey;

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getMembersAnswer")
	public Survey getSurveys(Survey body) {

		ediUtils = new EDIUtils(SYSTEM_NAME, USER_NAME);
		Init(ediUtils);
		Survey surveys = new Survey();

		Optional<Survey> op = surveyRepository.findById(100L);
		if (op.isPresent())
			return op.get();

		return surveys;
	}

	public void Init(EDIUtils ediUtils) {
		ediUtils = new EDIUtils(SYSTEM_NAME, USER_NAME);
		try {
			ediUtils.initDB();
			ediUtils.initDebugFlag();
		} catch (SQLException | NamingException e) {
			log.error(e.getMessage());
		}

		System.out.println("init");

		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getQuickSurvey")
	public List<Survey> getQuickSurvey(DateRange dateRange) throws ParseException {

		ediUtils = new EDIUtils(SYSTEM_NAME, USER_NAME);
		Init(ediUtils);
		ediUtils.writeToLog("getting quick survey answers from " + dateRange.fromDate + " until " + dateRange.toDate);
		if (dateRange.toDate == null) {
			dateRange.toDate = new GregorianCalendar(4700, Calendar.DECEMBER, 31).getTime();
		}
		List<Survey> surveysArray = surveyRepository.getSurveysByDates(dateRange.fromDate, dateRange.toDate);

		return surveysArray;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getDefaultDate")
	public Date getDefaultDate() throws InterruptedException {

		EDIUtils ediUtils = new EDIUtils(SYSTEM_NAME, USER_NAME);
		Init(ediUtils);
		EdiParameters ediParameters = ediParametersRepository.getEdiParametersByName("MSS", "NUM_OF_DAYS");
		ediUtils.writeToLog("number of days =" + ediParameters.getParameterValue());
		Calendar calander = Calendar.getInstance();
		calander.add(Calendar.DATE, Integer.parseInt(ediParameters.getParameterValue()) * -1); // subtract
		Date calcDate = calander.getTime();
		ediUtils.writeToLog("calc date is" + calcDate);

		return calcDate;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getSurveyByCategory")
	public List<Survey> getSurveyByCategory(DateRange dateRange) throws ParseException {

		ediUtils = new EDIUtils(SYSTEM_NAME, USER_NAME);
		Init(ediUtils);
		ediUtils.writeToLog("getting quick survey answers from " + dateRange.fromDate + " until " + dateRange.toDate);
		if (dateRange.toDate == null) {
			dateRange.toDate = new GregorianCalendar(4700, Calendar.DECEMBER, 31).getTime();
		}
		List<Survey> surveysArray = surveyRepository.getSurveysByDates(dateRange.fromDate, dateRange.toDate);

		return surveysArray;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getAvargeGrade")
	public List< ReportsData> getAvargeGrade(DateRange dateRange) throws ParseException {

		ediUtils = new EDIUtils(SYSTEM_NAME, USER_NAME);
		Init(ediUtils);	
		ediUtils.writeToLog("getting quick survey answers from " + dateRange.fromDate + " until " + dateRange.toDate);
		if( dateRange.toDate == null )
		{
			dateRange.toDate = new GregorianCalendar(4700, Calendar.DECEMBER, 31).getTime();
		}
		List<Survey> surveysArray = surveyRepository.getSurveysByDates(dateRange.fromDate, dateRange.toDate);

		HashMap <Long , ReportsData> reportsDataMap = new HashMap<>();
		for(Survey survey : surveysArray)
		{
			for(CategoryAnswer categoryAnswer : survey.getCategoriesAnswers())
			{
				
				ReportsData reportData = null;
				
				//check if the category already exists in the hashMap and grade was given ( 0 means no grade given ) 
				if(reportsDataMap.get(categoryAnswer.getCategory().getCategoryId()) == null  && categoryAnswer.getGrade() !=0  )
				{
				
					reportData = new ReportsData();
					reportData.CategoryName = categoryAnswer.getCategory().getCategoryName();
					reportData.numberOfAnswers = 1;
					reportData.average = String.valueOf(categoryAnswer.getGrade());
					reportData.tmpGradeSums = categoryAnswer.getGrade();
					reportsDataMap.put(categoryAnswer.getCategory().getCategoryId(), reportData);
				}
				else if(categoryAnswer.getGrade() !=0 )
				{
					reportData = reportsDataMap.get(categoryAnswer.getCategory().getCategoryId());
					reportData.numberOfAnswers ++;
					reportData.tmpGradeSums += categoryAnswer.getGrade();
					Float tmpAvarge = (float) reportData.tmpGradeSums / reportData.numberOfAnswers;
					reportData.average = new DecimalFormat("#.##").format(tmpAvarge); 
					reportsDataMap.put(categoryAnswer.getCategory().getCategoryId(), reportData);
				}
				
			}
		
		}
				
		
		List<ReportsData>  reportDataList = new ArrayList<ReportsData>();
		for(ReportsData reportData  : reportsDataMap.values())
		{
			reportDataList.add(reportData);
			ediUtils.writeToLog("for category :"+ reportData.CategoryName+ " number of answer="+reportData.numberOfAnswers +" average=" +  reportData.average);
		}
		
		return reportDataList;
	}


	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("saveCategoryUpdates")
	public Category saveCategoryUpdates(Category category) {

		ediUtils = new EDIUtils(SYSTEM_NAME, USER_NAME);
		Init(ediUtils);
		ediUtils.writeToLog("update category=" + category.getCategoryName());
		Date runDate = new Date();
		category.setUpdateDate(runDate);
		categoryRepository.save(category);

		return category;
	}
	
	
}
