package app.utils;

import app.controllers.Authorization;
import app.controllers.HikingParametrs;
import app.controllers.InstructorsOfHiking;
import app.controllers.Schedule;
import app.models.*;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.collections.FXCollections;
import org.hibernate.Criteria;
import org.hibernate.HibernateError;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.*;


public class HibernateManager {
    private static HibernateManager instance = null;
    private EntityManager entityManager;

    private static String usrname;
    private static String pass;
    private static List<String> role;

    private static boolean error = false;

    public static void setUsrname(String usrname) {
        HibernateManager.usrname = usrname;
    }

    public static void setPass(String pass) {
        HibernateManager.pass = pass;
    }

    public static List<String> getRole() {
        return role;
    }

    public static void setRole(List<String> role) {
        HibernateManager.role = role;
    }

    public static boolean isError() {
        return error;
    }

    private HibernateManager() {
        try {
            SessionFactory sessionFactory = buildSessionFactory();
            entityManager = sessionFactory.createEntityManager();
            error = false;
        }
        catch (Throwable exp){
            error = true;
        }
    }

    public static HibernateManager getInstance() {
        if (instance == null || error) {
            instance = new HibernateManager();
        }
        return instance;
    }

    private SessionFactory buildSessionFactory() throws Throwable{
        Properties dbConnectionProperties = new Properties();
        try {
            dbConnectionProperties.load(
                    ClassLoader.getSystemClassLoader().getResourceAsStream("hibernate.properties")
            );
            dbConnectionProperties.setProperty("hibernate.connection.username", usrname);
            dbConnectionProperties.setProperty("hibernate.connection.password", pass);

        } catch (IOException e) {
            throw new Error("Cannot read hibernate properties");
        }

        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(Participants.class);
        configuration.addAnnotatedClass(Equipment.class);
        configuration.addAnnotatedClass(HikingSchedule.class);
        configuration.addAnnotatedClass(Places.class);
        configuration.addAnnotatedClass(Routes.class);
        configuration.addAnnotatedClass(TypeOfHiking.class);

        configuration.addProperties(dbConnectionProperties);

        return configuration.buildSessionFactory();
    }


    public void beginTransaction() {
        entityManager.getTransaction().begin();
    }

    public void endTransaction() {
        entityManager.getTransaction().commit();
    }

    public void rollbackTransaction() {
        entityManager.getTransaction().rollback();
    }

    public void save(Object entity) throws SQLException {
        entityManager.persist(entity);
    }

    public Object find(Class entityClass, Integer id) throws SQLException {
        return entityManager.find(entityClass, id);
    }

    public void update(Object entity)throws SQLException {
        entityManager.merge(entity);
    }

    public void delete(Object entity) throws SQLException {
        entityManager.remove(entity);
    }

    public void clear() {
        entityManager.clear();
    }

    public List<String> getUserRole() throws SQLException {
        Query nativeQuery = entityManager.createNativeQuery("{call sp_helpuser (?)}", "User")
                .setParameter(1, usrname);
        return nativeQuery.getResultList();
    }

    public List<String> getTypeOfHiking() throws SQLException{
        TypedQuery<String> typesQuery = entityManager.createQuery("Select r.name from TypeOfHiking r",
                String.class);
        return typesQuery.getResultList();
    }

    public List<TypeOfHiking> getAllTypeOfHiking() throws SQLException{
        TypedQuery<TypeOfHiking> typesQuery = entityManager.createQuery("Select r from TypeOfHiking r",
                TypeOfHiking.class);
        return typesQuery.getResultList();
    }

    public int idOfTypeOfRoute(String type)throws SQLException {
        TypedQuery<Integer> typesQuery = entityManager.createQuery
                ("Select t.id from TypeOfHiking t where t.name = :type", Integer.class);
        typesQuery.setParameter("type", type);
        List<Integer> list = typesQuery.getResultList();
        return list.get(0);
    }

    public ScheduleList fromHikingScheduleToScheduleList(HikingSchedule hikingSchedule) throws SQLException {

        ScheduleList scheduleList = new ScheduleList();

        scheduleList.setId(hikingSchedule.getId());
        scheduleList.setName(hikingSchedule.getIdRoute().getName());
        scheduleList.setType(hikingSchedule.getIdRoute().getType().getName());
        scheduleList.setStart(hikingSchedule.getStart());
        scheduleList.setFinish(hikingSchedule.getFinish());
        scheduleList.setCost(hikingSchedule.getCost());

        return scheduleList;

    }

    public List<ScheduleList> getSchedule(String route, Date start, Date finish, int cost) throws SQLException {

        int idRoute;
        if(route.equals("Любой"))
            idRoute = -1;
        else
            idRoute = idOfTypeOfRoute(route);

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<HikingSchedule> criteriaQuery = builder.createQuery(HikingSchedule.class);
        Root<HikingSchedule> root = criteriaQuery.from(HikingSchedule.class);

        List<Predicate> predicates = new ArrayList<>();

        if(start != null){
            Predicate startDate = builder.greaterThanOrEqualTo(root.get("start"), start);
            predicates.add(startDate);
        }
        if(finish != null){
            Predicate finishDate = builder.lessThanOrEqualTo(root.get("finish"), finish);
            predicates.add(finishDate);
        }
        Predicate maxCost = builder.lessThanOrEqualTo(root.get("cost"), cost);
        predicates.add(maxCost);

        criteriaQuery.select(root).where(predicates.toArray(new Predicate[] {}));
        TypedQuery<HikingSchedule> query = entityManager.createQuery(criteriaQuery);
        List<HikingSchedule> list = query.getResultList();

        List<ScheduleList> res = new ArrayList<>();

        for(int i=0; i<list.size(); ++i){
            if(idRoute == -1 || idRoute == list.get(i).getIdRoute().getType().getId()) {
                res.add(fromHikingScheduleToScheduleList(list.get(i)));
            }
        }

        return res;
    }

    public Routes getRoute(String name) throws SQLException{

        TypedQuery<Routes> routesQuery = entityManager.createQuery("Select r from Routes r where r.name = :name", Routes.class)
                .setParameter("name", name);
        return routesQuery.getResultList().get(0);
    }

    public List<String> getRouteNames()throws SQLException {

        TypedQuery<String> routesTypedQuery = entityManager.createQuery("Select r.name from Routes r", String.class);
        return routesTypedQuery.getResultList();
    }

    public List<Salaries> getSalaries(Date start, Date finish) throws SQLException {

        Query nativeQuery = entityManager.createNativeQuery("{call Salaries(?,?)}", "Salaries")
                .setParameter(1, start)
                .setParameter(2, finish);

        return nativeQuery.getResultList();
    }

    public List<HikingTable> getHikingTable() throws SQLException {

        TypedQuery<HikingSchedule> hikingScheduleTypedQuery = entityManager.createQuery(
                "select h from HikingSchedule h", HikingSchedule.class);

        List<HikingSchedule> hikingScheduleList = hikingScheduleTypedQuery.getResultList();
        List<HikingTable> hikingTableList = new ArrayList<>();

        for(int i=0; i<hikingScheduleList.size(); ++i){
            HikingTable item = new HikingTable();
            item.setIdHiking(hikingScheduleList.get(i).getId());
            item.setNameHiking(hikingScheduleList.get(i).getIdRoute().getName());
            item.setStartHiking(hikingScheduleList.get(i).getStart());

            hikingTableList.add(item);
        }

        System.out.println(hikingTableList.size());

        return hikingTableList;
    }

    public InstructorsTable toInstructorsTable(Participants itemP) throws SQLException {

        InstructorsTable item = new InstructorsTable();
        item.setIdInstructor(itemP.getId());
        String snp = itemP.getSurname().concat(" ").concat(itemP.getName().substring(0, 1)).concat(". ");
        if(itemP.getPatronymic() != ""){
            snp = snp.concat(itemP.getPatronymic().substring(0, 1)).concat(".");
        }
        item.setSurnameInstructor(snp);
        item.setCategoryInstructor(itemP.getSportsCategory());

        return item;

    }

    public void getInstructorsTable(Integer idHiking) throws SQLException {

        HikingSchedule hikingSchedule = (HikingSchedule) instance.find(HikingSchedule.class, idHiking);
        List<Participants> participantsList = hikingSchedule.getParticipantsList();
        List<InstructorsTable> instructorsTableList = new ArrayList<>();
        List<InstructorsTable> participantsTableList = new ArrayList<>();

        for(int i=0; i<participantsList.size(); ++i){

            InstructorsTable item = toInstructorsTable(participantsList.get(i));

            if(participantsList.get(i).getPosition() == 1) {
                instructorsTableList.add(item);
            }
            else{
                participantsTableList.add(item);
            }

        }

        InstructorsOfHiking.setDataInstructorsTable(FXCollections.observableArrayList(instructorsTableList));
        InstructorsOfHiking.setDataParticipantsTable(FXCollections.observableArrayList(participantsTableList));

    }

    public List<InstructorsTable> getParticipants(int idHiking) throws SQLException {

        HikingSchedule hikingSchedule = (HikingSchedule)instance.find(HikingSchedule.class, idHiking);
        List<Participants> fromHiking = hikingSchedule.getParticipantsList();

        TypedQuery<Participants> participantsTypedQuery = entityManager.createQuery("from Participants p", Participants.class);

        List<Participants> participantsList = participantsTypedQuery.getResultList();
        List<InstructorsTable> instructorsTableList = new ArrayList<>();

        for(int i=0; i<participantsList.size(); ++i){
            if(!fromHiking.contains(participantsList.get(i))) {
                InstructorsTable item = toInstructorsTable(participantsList.get(i));
                instructorsTableList.add(item);
            }
        }

        return instructorsTableList;

    }

    public boolean changeInstructor(int idHiking, int idInstructor) throws SQLException{

        try {
            Query nativeQuery = entityManager.createNativeQuery("{call ChangeInstr(?, ?)}")
                    .setParameter(1, idHiking)
                    .setParameter(2, idInstructor);

            nativeQuery.getSingleResult();
            entityManager.clear();
            return true;
        }
        catch (Throwable exp){
            return false;
        }
    }

    public void addHikingParticipant(int idHiking, int idParticipant) throws SQLException {

        Participants participant = (Participants) instance.find(Participants.class, idParticipant);
        HikingSchedule hikingSchedule = (HikingSchedule) instance.find(HikingSchedule.class, idHiking);

        hikingSchedule.getParticipantsList().add(participant);

    }

    public void deleteParticipantsOfHiking(int idHiking, int idParticipant) throws SQLException {

        Query nativeQuery = entityManager.createNativeQuery("{call deleteParticipantOfHiking(?, ?)}")
                .setParameter(1, idParticipant)
                .setParameter(2, idHiking);

        nativeQuery.getSingleResult();
        entityManager.clear();

    }

}
