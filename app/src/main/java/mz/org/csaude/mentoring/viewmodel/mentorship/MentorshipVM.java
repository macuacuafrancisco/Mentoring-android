package mz.org.csaude.mentoring.viewmodel.mentorship;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import mz.org.csaude.mentoring.BR;
import mz.org.csaude.mentoring.adapter.recyclerview.listable.Listble;
import mz.org.csaude.mentoring.base.viewModel.BaseViewModel;
import mz.org.csaude.mentoring.model.answer.Answer;
import mz.org.csaude.mentoring.model.evaluationType.EvaluationType;
import mz.org.csaude.mentoring.model.form.Form;
import mz.org.csaude.mentoring.model.formQuestion.FormQuestion;
import mz.org.csaude.mentoring.model.location.Cabinet;
import mz.org.csaude.mentoring.model.location.HealthFacility;
import mz.org.csaude.mentoring.model.mentorship.Door;
import mz.org.csaude.mentoring.model.mentorship.IterationType;
import mz.org.csaude.mentoring.model.mentorship.Mentorship;
import mz.org.csaude.mentoring.model.mentorship.TimeOfDay;
import mz.org.csaude.mentoring.model.ronda.Ronda;
import mz.org.csaude.mentoring.model.session.Session;
import mz.org.csaude.mentoring.model.tutor.Tutor;
import mz.org.csaude.mentoring.model.tutored.Tutored;
import mz.org.csaude.mentoring.util.Utilities;
import mz.org.csaude.mentoring.view.mentorship.CreateMentorshipActivity;

public class MentorshipVM extends BaseViewModel {

    private String CURR_MENTORSHIP_STEP = "";
    public static final String CURR_MENTORSHIP_STEP_TABLE_SELECTION = "TABLE_SELECTION";
    public static final String CURR_MENTORSHIP_STEP_MENTEE_SELECTION = "MENTEE_SELECTION";
    public static final String CURR_MENTORSHIP_STEP_PERIOD_SELECTION = "PERIOD_SELECTION";
    public static final String CURR_MENTORSHIP_STEP_QUESTION_SELECTION = "QUESTION_SELECTION";


    private Mentorship mentorship;

    private Ronda ronda;

    private Session session;

    private List<Form> forms;

    private List<Tutored> tutoreds;

    private List<FormQuestion> formQuestions;

    private TreeMap<String, List<FormQuestion>> questionMap;

    private String currQuestionCategory;

    public void setCurrMentorshipStep(String step) {
        this.CURR_MENTORSHIP_STEP = step;
        notifyPropertyChanged(BR.currMentorshipStep);
    }

    @Bindable
    public String getCurrMentorshipStep() {
        return CURR_MENTORSHIP_STEP;
    }

    public MentorshipVM(@NonNull Application application) {
        super(application);
    }

    @Override
    public void preInit() {
    }

    public boolean isThereNextKey() {
        if (this.questionMap == null) return false;
        return Utilities.stringHasValue(this.questionMap.higherKey(this.currQuestionCategory));
    }

    public boolean isTherePreviousKey() {
        if (this.questionMap == null) return false;
        return Utilities.stringHasValue(this.questionMap.lowerKey(this.currQuestionCategory));
    }
    public void nextCategory() {
        if (!allCurrentQuestionsResponded()) {
            Utilities.displayAlertDialog(getRelatedActivity(), "Tem uma ou mais Competências sem a resposta indicada.").show();
            return;
        }
        if (!Utilities.stringHasValue(this.questionMap.higherKey(this.currQuestionCategory))) {
            Utilities.displayAlertDialog(getRelatedActivity(), "Não existe uma categoria posterior para visualizar.").show();
            return;
        }
        setCurrQuestionCategory(this.questionMap.higherKey(this.currQuestionCategory));
        getRelatedActivity().populateQuestionList();
    }

    private boolean allCurrentQuestionsResponded() {
        for (FormQuestion formQuestion : questionMap.get(this.currQuestionCategory)) {
            if (!Utilities.stringHasValue(formQuestion.getAnswer().getValue())) return false;
        }
        return true;
    }

    public void previousCategory() {
        if (!Utilities.stringHasValue(this.questionMap.lowerKey(this.currQuestionCategory))) {
            Utilities.displayAlertDialog(getRelatedActivity(), "Não existe uma categoria anterior para visualizar.").show();
            return;
        }
        setCurrQuestionCategory(this.questionMap.lowerKey(this.currQuestionCategory));
        getRelatedActivity().populateQuestionList();
    }

    public void finnalizeMentorship() {}

    @Bindable
    public String getCode() {
        return this.mentorship.getCode();
    }

    public void setName(String code) {
        this.mentorship.setCode(code);
        notifyPropertyChanged(BR.code);
    }

    @Bindable
    public Date getStartDate() {
        return this.mentorship.getStartDate();
    }

    public void setStartDate(Date startDate) {
        this.mentorship.setStartDate(startDate);
        notifyPropertyChanged(BR.startDate);
    }



    @Bindable
    public Date getEndDate() {
        return this.mentorship.getEndDate();
    }

    public void setEndDate(Date endDate) {
        this.mentorship.setEndDate(endDate);
         notifyPropertyChanged(BR.endDate);
    }

    @Bindable
    public Date getPerformedDate() {
        return this.mentorship.getPerformedDate();
    }

    public void setPerformedDate(Date performedDate) {
        this.mentorship.setPerformedDate(performedDate);
        notifyPropertyChanged(BR.performedDate);
    }


    @Bindable
    public Cabinet getCabinet() {
        return this.mentorship.getCabinet();
    }

    public void setCabinet(Cabinet cabinet) {
        this.mentorship.setCabinet(cabinet);
         notifyPropertyChanged(BR.cabinet);
    }

    @Bindable
    public Integer getIterationNumber() {
        return this.mentorship.getIterationNumber();
    }

    public void setIterationNumber(Integer iterationNumber) {
        this.mentorship.setIterationNumber(iterationNumber);
         notifyPropertyChanged(BR.iterationNumber);
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @Bindable
    public TimeOfDay getTimeOfDay() {
        return this.mentorship.getTimeOfDay();
    }

    public void setTimeOfDay(TimeOfDay timeOfDay) {
        this.mentorship.setTimeOfDay(timeOfDay);
         notifyPropertyChanged(BR.timeOfDay);
    }

    @Bindable
    public Door getDoor() {
        return this.mentorship.getDoor();
    }

    public void setDoor(Door door) {
        this.mentorship.setDoor(door);
         notifyPropertyChanged(BR.door);
    }

    public void save() {
        try {
            this.getApplication().getMentorshipService().save(this.mentorship);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Form> getForms() {
        return forms;
    }

    public List<Form> getTutorForms() {
        try {
            this.forms = getApplication().getFormService().getAllOfTutor(getCurrentTutor());
            return forms;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public void nextStep() {
        if (isTableSelectionStep()) {
            getRelatedActivity().populateMenteesList();
            setCurrMentorshipStep(CURR_MENTORSHIP_STEP_MENTEE_SELECTION);
        } else if (isMenteeSelectionStep()) {
            setCurrMentorshipStep(CURR_MENTORSHIP_STEP_PERIOD_SELECTION);
        } else if (isPeriodSelectionStep()) {
            loadQuestion();
            getRelatedActivity().populateQuestionList();
            setCurrMentorshipStep(CURR_MENTORSHIP_STEP_QUESTION_SELECTION);
        }
        notifyPropertyChanged(BR.currMentorshipStep);
    }



    @Override
    public CreateMentorshipActivity getRelatedActivity() {
        return (CreateMentorshipActivity) super.getRelatedActivity();
    }

    public void previousStep() {
        if (isTableSelectionStep()) {
            setCurrMentorshipStep(CURR_MENTORSHIP_STEP_MENTEE_SELECTION);
        } else if (isMenteeSelectionStep()) {
            setCurrMentorshipStep(CURR_MENTORSHIP_STEP_PERIOD_SELECTION);
        } else if (isPeriodSelectionStep()) {
            setCurrMentorshipStep(CURR_MENTORSHIP_STEP_QUESTION_SELECTION);
        }
        notifyPropertyChanged(BR.currMentorshipStep);
    }

    public void setRonda(Ronda ronda) {
        this.ronda = ronda;
    }

    public void determineMentorshipType() {
        try {
            if (this.mentorship == null) this.mentorship = new Mentorship();
            if (this.ronda.isRondaZero()) {
                this.mentorship.setSession(generateZeroSession());
                this.mentorship.setEvaluationType(getApplication().getEvaluationTypeService().getByCode(EvaluationType.CONSULTA));
            } else {
                this.mentorship.setSession(this.session);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Session generateZeroSession() {
        Session session = new Session();
        session.setRonda(this.ronda);
        return session;
    }

    public boolean isTableSelectionStep() {
        return this.CURR_MENTORSHIP_STEP.equals(CURR_MENTORSHIP_STEP_TABLE_SELECTION);
    }

    public boolean isMenteeSelectionStep() {
        return this.CURR_MENTORSHIP_STEP.equals(CURR_MENTORSHIP_STEP_MENTEE_SELECTION);
    }

    public boolean isPeriodSelectionStep() {
        return this.CURR_MENTORSHIP_STEP.equals(CURR_MENTORSHIP_STEP_PERIOD_SELECTION);
    }

    public boolean isQuestionSelectionStep() {
        return this.CURR_MENTORSHIP_STEP.equals(CURR_MENTORSHIP_STEP_QUESTION_SELECTION);
    }

    public void unselectAll() {
        for (Form form: forms){
            form.setItemSelected(false);
        }
    }

    public void selectForm(int position) {
        for (Form form : getForms()) {
            if (form.isSelected()) form.setItemSelected(false);
        }
        Form form = getForms().get(position);
        form.setItemSelected(true);
        mentorship.setForm(form);
    }

    public List<Tutored> getTutoreds() {
        return tutoreds;
    }

    public List<Tutored> getMentees() {
        try {
            this.tutoreds= getApplication().getTutoredService().getAll();
            for (Tutored tutored :this.tutoreds) {
                tutored.setListType(Listble.ListTypes.UNDEFINED);
            }
            return this.tutoreds;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void selectMentee(int position) {
        for (Tutored tutored : getTutoreds()) {
            if (tutored.isSelected()) tutored.setItemSelected(false);
        }
        Tutored tutored = getTutoreds().get(position);
        tutored.setItemSelected(true);
        mentorship.setTutored(tutored);
    }

    @Bindable
    public Listble getSelectedDoor() {
        return this.mentorship.getDoor();
    }

    public void setSelectedDoor(Listble selectedDoor) {
        if (selectedDoor == null) return;

        this.mentorship.setDoor((Door) selectedDoor);
        notifyPropertyChanged(BR.selectedDoor);
    }

    @Bindable
    public Listble getSelectedSector() {
        return this.mentorship.getCabinet();
    }

    public boolean isMentoringMentorship() {
        return !this.mentorship.getSession().getRonda().isRondaZero();
    }
    public void setSelectedSector(Listble selectedSector) {
        if (selectedSector == null) return;

        this.mentorship.setCabinet((Cabinet) selectedSector);
        notifyPropertyChanged(BR.selectedSector);
    }

    public List<Door> getDoors() {
        try {
            List<Door> doors = new ArrayList<>();
            doors.add(new Door());
            doors.addAll(getApplication().getDoorService().getAll());
            return doors;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Cabinet> getSectors() {
        try {
            List<Cabinet> cabinets = new ArrayList<>();
            cabinets.add(new Cabinet());
            cabinets.addAll(getApplication().getCabinetService().getAll());
            return cabinets;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadQuestion() {
        try {
            this.formQuestions = getApplication().getFormQuestionService().getAllOfForm(this.mentorship.getForm(), this.mentorship.getEvaluationType().getCode());
            if (Utilities.listHasElements(this.formQuestions)) {
                for (FormQuestion formQuestion : formQuestions) {
                    formQuestion.setAnswer(new Answer());
                    formQuestion.getAnswer().setMentorship(this.mentorship);
                    formQuestion.getAnswer().setForm(this.mentorship.getForm());
                    formQuestion.getAnswer().setValue("");
                    loadQuestionMap(formQuestion,formQuestion.getQuestion().getQuestionsCategory().getCategory());
                }
                setCurrQuestionCategory(this.questionMap.firstKey());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadQuestionMap(FormQuestion formQuestion, String category) {
        if (questionMap == null) questionMap = new TreeMap<>();
        if (!questionMap.containsKey(category)) {
            questionMap.put(category, new ArrayList<>());
        }
        questionMap.get(category).add(formQuestion);
    }

    public TreeMap<String, List<FormQuestion>> getQuestionMap() {
        return questionMap;
    }

    @Bindable
    public String getCurrQuestionCategory() {
        return currQuestionCategory;
    }

    public void setCurrQuestionCategory(String currQuestionCategory) {
        this.currQuestionCategory = currQuestionCategory;
        notifyPropertyChanged(BR.currQuestionCategory);
    }
}
